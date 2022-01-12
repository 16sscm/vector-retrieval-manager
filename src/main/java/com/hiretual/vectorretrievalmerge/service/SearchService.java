package com.hiretual.vectorretrievalmerge.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.hiretual.vectorretrievalmerge.model.KNNResult;

import java.util.List;
import java.util.concurrent.ExecutionException;


public interface SearchService {

    public List<KNNResult> search(JsonNode query);
    public List<KNNResult> distribute(String queryJsonString,int topK) throws InterruptedException, ExecutionException;

}
