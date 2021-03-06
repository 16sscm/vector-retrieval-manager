package com.hiretual.vectorretrievalmerge.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.hiretual.vectorretrievalmerge.config.SearchEngineConfig;
import com.hiretual.vectorretrievalmerge.model.DistributeInfo;
import com.hiretual.vectorretrievalmerge.service.IndexBuildService;
import com.hiretual.vectorretrievalmerge.utils.HashBucket;
import com.hiretual.vectorretrievalmerge.utils.JsonParser;
import com.hiretual.vectorretrievalmerge.utils.RequestParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IndexBuildServiceImpl implements IndexBuildService {
    private static final Logger logger = LoggerFactory.getLogger(IndexBuildServiceImpl.class);
    private static List<String> searchEngines=SearchEngineConfig.getSearchEngines();
    private String additionalEngine="";
  

    @Autowired
    RestfulTaskExecutor taskExecutor;
    private  static final String addRoute = "/doc/add";
    private static final String deleteRoute = "/doc/delete";
    private static final String indexSizeRoute="/index/size";
    private static final String mergeRoute="/index/merge";

    @Override
    public void dispatch() {
        int numEngine=searchEngines.size();
      
        for (int i=0;i<numEngine;i++){
            String engine = searchEngines.get(i);
            String url = engine + addRoute;
            DistributeInfo distributeInfo=new DistributeInfo(numEngine, i);
            taskExecutor.postRequest2Engine(distributeInfo,url);
        }
        

    }


    @Override
    public void update(JsonNode document) {
        for(String engine:searchEngines){
            taskExecutor.postRequest2Engine(document,engine+deleteRoute);
        }
        taskExecutor.postRequest2Engine(document,additionalEngine+addRoute);

    }

    /**
     * for check
     * should only be called once
     */
    @Override
    public void mainGetIndexSize(){
        for(String engine:searchEngines){
            taskExecutor.getRequest2Engine(engine+indexSizeRoute);
        }
    }

    /**
     * for check
     * which may be called frequently
     */
    @Override
    public void additionalGetIndexSize(){
        taskExecutor.getRequest2Engine(additionalEngine+indexSizeRoute);
    }
    /**
     * merge segment and start ann index
     * should only be called once strictly
     */
    @Override
    public void mergeMainIndex(){
        for(String engine:searchEngines){
            taskExecutor.getRequest2Engine(engine+mergeRoute);
        }
    }

    /**
     *  merge segment and start ann index
     *  should be called cautiously
     */
    @Override
    public void mergeAdditionalIndex(){
        taskExecutor.getRequest2Engine(additionalEngine+mergeRoute);
    }
   
}
