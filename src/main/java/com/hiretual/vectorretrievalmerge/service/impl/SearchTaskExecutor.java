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

@Component
public class SearchTaskExecutor {
    private static final Logger logger = LoggerFactory.getLogger(SearchTaskExecutor.class);
    @Async
    public Future<String> execute(String jsonString, String searchEngine) {
        logger.info("execute search:"+searchEngine);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity(jsonString, headers);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(searchEngine, request, String.class);
        Future<String> future = new AsyncResult<String>(response);
        return future;
    }
}
