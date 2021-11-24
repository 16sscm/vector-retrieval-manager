package com.hiretual.vectorretrievalmerge.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.hiretual.vectorretrievalmerge.service.IndexBuildService;
import com.hiretual.vectorretrievalmerge.utils.HashBucket;
import com.hiretual.vectorretrievalmerge.utils.RequestParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Future;

@Service
public class IndexBuildServiceImpl implements IndexBuildService {
    private static final Logger logger = LoggerFactory.getLogger(IndexBuildServiceImpl.class);
    private static String[] searchEngines;

    static {
        searchEngines = new String[1];
//        searchEngines[0]="0";
//        searchEngines[1]="1";
//        searchEngines[2]="2";
//        searchEngines[3]="3";
        searchEngines[0] = "http://10.100.10.19:8899";

    }

    private static String route = "/doc/add";

    @Override
    public void dispatch(JsonNode documentList) {
//        int testSize=8;
//        int bucket[]=new int[testSize];
        for (JsonNode document : documentList) {
            String uid = RequestParser.getDocUid(document);
//            int bucketIndex=HashBucket.getBucketIndex(uid,testSize);
//            bucket[bucketIndex]++;
            int bucketIndex = HashBucket.getBucketIndex(uid, searchEngines.length);
            String engine = searchEngines[bucketIndex];
            String url = engine + route;
            post2Engine(document, url);
        }
        logger.info("dispatch done");

    }

    @Override
    public void post2Engine(JsonNode json, String searchEngine) {
        logger.info("post request,engine:" + searchEngine);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity(json, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            String response = restTemplate.postForObject(searchEngine, request, String.class);
            if (!response.equals("0")) {
                logger.warn("fail to post document to engine,error code:", response);
            }
        } catch (Exception e) {
            logger.error("post document request error:", e);
        }

    }
}
