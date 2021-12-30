package com.hiretual.vectorretrievalmerge.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.hiretual.vectorretrievalmerge.config.SearchEngineConfig;
import com.hiretual.vectorretrievalmerge.model.KNNResult;
import com.hiretual.vectorretrievalmerge.model.QueryWrapper;
import com.hiretual.vectorretrievalmerge.service.SearchService;
import com.hiretual.vectorretrievalmerge.utils.JsonParser;
import com.hiretual.vectorretrievalmerge.utils.RestClient;
import com.hiretual.vectorretrievalmerge.utils.TopKResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Service
public class SearchServiceImpl implements SearchService {
    private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);
    private static List<String> searchEngines=SearchEngineConfig.getSearchEngines();
    private static final String searchRoute ="/search";
    @Autowired
    RestfulTaskExecutor taskExecutor;

    public List<KNNResult> search(JsonNode query,int topK) {
        long t=System.currentTimeMillis();
        // String esPayload= RestClient.astaskToESPayload(query);
        JsonNode esPayload=query.get("esSearchArray");
        // long t1=System.currentTimeMillis();
        esPayload=JsonParser.transformEsPayload(esPayload);
        long t2=System.currentTimeMillis();
        String esQuery=RestClient.generateEsQuery(esPayload);
        long t3=System.currentTimeMillis();
        String embedding=RestClient.astaskToEmbedding(query);
        embedding=JsonParser.extractEmbedding(embedding);
        if(embedding.equals("null")){
            logger.info("unsupport astask to embedding");
            return null;
        }
        long t4=System.currentTimeMillis();
        QueryWrapper queryWrapper=new QueryWrapper(embedding,esQuery);
        long t5=System.currentTimeMillis();
        String queryJsonString=JsonParser.getJsonString(queryWrapper);
        long t6=System.currentTimeMillis();
        try{
            List<KNNResult>knnResults=distribute(queryJsonString,topK);
            long t7=System.currentTimeMillis();
            logger.info("search:"+(t2-t)+"|"+(t3-t2)+"|"+(t4-t3)+"|"+(t5-t4)+"|"+(t6-t5)+"|"+(t7-t6));
            return knnResults;
        }catch (Exception e){
            logger.error("fail to distribute search task",e);
            return null;
        }

    }
    public List<KNNResult> distribute(String queryJsonString,int topK) throws InterruptedException, ExecutionException {
        TopKResult topKResult = new TopKResult(topK);
        List<Future<String>> lstFuture = new ArrayList<>();// 存放所有的线程，用于获取结果
        long t=System.currentTimeMillis();
       
        for (String searchEngine:searchEngines) {
            while (true) {
                try {
                    //  throw TaskRejectedException if thread pool size exceeds MaxPoolSize ，then wait and retry
                    Future<String> stringFuture = taskExecutor.executeSearch(queryJsonString,searchEngine+searchRoute);
                    lstFuture.add(stringFuture);
                    break;
                } catch (TaskRejectedException e) {
                    logger.warn("线程池满，等待1S。");
                    Thread.sleep(500);
                }
            }
        }
    
        //thread safe
        Vector<KNNResult>vector=new Vector<>();
        for ( int i=0;i<lstFuture.size();i++) {
           
            Future<String> future=lstFuture.get(i);
            //block until the thread is finish
            String response=future.get();
            if(response==null){
                logger.warn("engine return null,unsupport astask to lucene query");
            }
            else if(response.equals("0")){
                logger.info("search engine:"+searchEngines.get(i)+" ,future get:"+response+",empty result");
            }else if(response.equals("-1")){
                logger.info("search engine:"+searchEngines.get(i)+" ,future get:"+response+",search engine error");
            }else {
                List<KNNResult> knnResults=JsonParser.transformJson2KNNResults(response);
                vector.addAll(knnResults);
            }

        }
        long t2=System.currentTimeMillis();
        logger.info("end execute,cost:"+(t2-t));
        for(KNNResult knnResult:vector){
            topKResult.add(knnResult);
        }
        long t3=System.currentTimeMillis();
        logger.info("end build heap,cost:"+(t3-t2));
        List<KNNResult>ret=topKResult.sortedList();
        long t4=System.currentTimeMillis();
        logger.info("end sort,cost:"+(t4-t3));
        return ret;

    }
}
