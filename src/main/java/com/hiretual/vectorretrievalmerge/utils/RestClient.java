package com.hiretual.vectorretrievalmerge.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

public class RestClient {

    private static final Logger logger = LoggerFactory.getLogger(RestClient.class);

    public static String astaskToESPayload(String content){
        String url="http://talentenginev2.api.testhtm/sourcing/astaskToESPayload";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity(content, headers);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(url, request, String.class);
        return response;
    }

    public static String astaskToEmbedding(String content){
        String url="http://10.100.10.19:6000/astask/astask_tranform";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("X-API-KEY","Qro19LxU4rMbPqrSU2ymWJhHSeOCtq6e");
        HttpEntity<String> request = new HttpEntity(content, headers);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(url, request, String.class);
        return response;
    }

    public static String generateEsQuery(String content) {
        String url = "http://elasticsearch.db.testhtm/api/v3/user/generate_es_query";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity(content, headers);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.postForObject(url, request, String.class);
//        logger.info("es query: " + response);
        return response;
    }
}