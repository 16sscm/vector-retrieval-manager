package com.hiretual.vectorretrievalmerge.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
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
    private static String[] searchEngines;
    private String additionalEngine="";
    static {
        searchEngines = new String[4];
//        searchEngines[0]="0";
//        searchEngines[1]="1";
//        searchEngines[2]="2";
//        searchEngines[3]="3";
        searchEngines[0] = "http://10.100.10.19:8898";
        searchEngines[1] = "http://10.100.10.19:8897";
        searchEngines[2] = "http://10.100.10.19:8896";
        searchEngines[3] = "http://10.100.10.19:8895";
        // searchEngines[1] = "http://10.100.10.19:8897";
       

    }

    @Autowired
    RestfulTaskExecutor taskExecutor;
    private  static final String addRoute = "/doc/add";
    private static final String deleteRoute = "/doc/delete";
    private static final String commitRoute="/index/commit";
    private static final String mergeRoute="/index/merge";

    @Override
    public void dispatch() {
        int numEngine=searchEngines.length;
      
        for (int i=0;i<numEngine;i++){
            String engine = searchEngines[i];
            String url = engine + addRoute;
            DistributeInfo distributeInfo=new DistributeInfo(numEngine, i);
            taskExecutor.postRequest2Engine(distributeInfo,url);
        }
        logger.info("dispatch done");

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
    public void commitMainIndex(){
        for(String engine:searchEngines){
            taskExecutor.getRequest2Engine(engine+commitRoute);
        }
    }

    /**
     * for check
     * which may be called frequently
     */
    @Override
    public void commitAdditionalIndex(){
        taskExecutor.getRequest2Engine(additionalEngine+commitRoute);
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
