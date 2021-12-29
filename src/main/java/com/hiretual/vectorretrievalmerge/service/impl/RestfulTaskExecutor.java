package com.hiretual.vectorretrievalmerge.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.concurrent.Future;

import com.fasterxml.jackson.databind.JsonNode;
import com.hiretual.vectorretrievalmerge.model.DistributeInfo;

@Component
public class RestfulTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(RestfulTaskExecutor.class);
    @Async
    public Future<String> executeSearch(String jsonString, String searchEngine) {
        logger.info("execute search:"+searchEngine);
        
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity(jsonString, headers);
        RestTemplate restTemplate = new RestTemplate();
        long t11 = System.currentTimeMillis();
        String response = restTemplate.postForObject(searchEngine, request, String.class);
        long t12 = System.currentTimeMillis();
        Future<String> future = new AsyncResult<String>(response);
        logger.info(searchEngine +" | " + (t12 - t11));
        return future;
    }
    /**
     * request with body
     * @param json
     * @param url
     */
    @Async
    public void postRequest2Engine(DistributeInfo distributeInfo, String url) {
        logger.info("url:"+url+","+"distribute start");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // String json=JsonParser.getJsonString(list);
        HttpEntity<String> request = new HttpEntity(distributeInfo, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            String response = restTemplate.postForObject(url, request, String.class);
            logger.info("url:"+url+","+"response:"+response);
        } catch (Exception e) {
            logger.error("post document request error:", e);
        }

    }
     /**
     * request without body
     * @param url
     */
    @Async
    public void getRequest2Engine( String url) {
        logger.info("url:"+url+","+"distribute start");
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
    @Async
    public void postRequest2Engine(JsonNode json, String url) {
        logger.info("url:"+url+","+"distribute start");
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
