package com.hiretual.vectorretrievalmerge.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.hiretual.vectorretrievalmerge.service.IndexBuildService;
import com.hiretual.vectorretrievalmerge.utils.HashBucket;
import com.hiretual.vectorretrievalmerge.utils.JsonParser;
import com.hiretual.vectorretrievalmerge.utils.RequestParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IndexBuildServiceImpl implements IndexBuildService {
    private static final Logger logger = LoggerFactory.getLogger(IndexBuildServiceImpl.class);
    private static String[] searchEngines;
    private String additionalEngine="";
    static {
        searchEngines = new String[1];
//        searchEngines[0]="0";
//        searchEngines[1]="1";
//        searchEngines[2]="2";
//        searchEngines[3]="3";
        searchEngines[0] = "http://10.100.10.19:8898";
        // searchEngines[1] = "http://10.100.10.19:8897";
       

    }

    private  static final String addRoute = "/doc/add";
    private static final String deleteRoute = "/doc/delete";
    private static final String commitRoute="/index/commit";
    private static final String mergeRoute="/index/merge";

    @Override
    public void dispatch(JsonNode documentList) {
        int numEngine=searchEngines.length;
       Map<Integer,List<JsonNode>>map=new HashMap<>();
       for(int i=0;i<numEngine;i++){
           map.put(i,new ArrayList<>());
       }
        for (JsonNode document : documentList) {
            String uid = RequestParser.getDocUid(document);
//            int bucketIndex=HashBucket.getBucketIndex(uid,testSize);
//            bucket[bucketIndex]++;
            int bucketIndex = HashBucket.getBucketIndex(uid, numEngine);
            map.get(bucketIndex).add(document);
        }
        for (int i=0;i<numEngine;i++){
            String engine = searchEngines[i];
            String url = engine + addRoute;
            postRequest2Engine(map.get(i), url);
        }
        logger.info("dispatch done");

    }


    @Override
    public void update(JsonNode document) {
        for(String engine:searchEngines){
            postRequest2Engine(document,engine+deleteRoute);
        }
        postRequest2Engine(document,additionalEngine+addRoute);

    }

    /**
     * for check
     * should only be called once
     */
    @Override
    public void commitMainIndex(){
        for(String engine:searchEngines){
            getRequest2Engine(engine+commitRoute);
        }
    }

    /**
     * for check
     * which may be called frequently
     */
    @Override
    public void commitAdditionalIndex(){
        getRequest2Engine(additionalEngine+commitRoute);
    }
    /**
     * merge segment and start ann index
     * should only be called once strictly
     */
    @Override
    public void mergeMainIndex(){
        for(String engine:searchEngines){
            getRequest2Engine(engine+mergeRoute);
        }
    }

    /**
     *  merge segment and start ann index
     *  should be called cautiously
     */
    @Override
    public void mergeAdditionalIndex(){
        getRequest2Engine(additionalEngine+mergeRoute);
    }
    /**
     * request without body
     * @param url
     */
    private void getRequest2Engine( String url) {
      
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RestTemplate restTemplate = new RestTemplate();
        try {
            String response = restTemplate.getForObject(url,  String.class);
            logger.info("url:"+url+","+"response:"+response);

        } catch (Exception e) {
            logger.error("post  request error:", e);
        }

    }

    /**
     * request with body
     * @param json
     * @param url
     */
    private void postRequest2Engine(List<JsonNode> list, String url) {
       
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // String json=JsonParser.getJsonString(list);
        HttpEntity<String> request = new HttpEntity(list, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            String response = restTemplate.postForObject(url, request, String.class);
            logger.info("url:"+url+","+"response:"+response);
        } catch (Exception e) {
            logger.error("post document request error:", e);
        }

    }
    /**
     * request with body
     * @param json
     * @param url
     */
    private void postRequest2Engine(JsonNode json, String url) {
       
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // String json=JsonParser.getJsonString(list);
        HttpEntity<String> request = new HttpEntity(json, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            String response = restTemplate.postForObject(url, request, String.class);
            logger.info("url:"+url+","+"response:"+response);
        } catch (Exception e) {
            logger.error("post document request error:", e);
        }

    }
}
