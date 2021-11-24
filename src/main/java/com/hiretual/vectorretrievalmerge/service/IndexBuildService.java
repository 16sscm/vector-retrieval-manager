package com.hiretual.vectorretrievalmerge.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface IndexBuildService {
    void dispatch(JsonNode documentList);
    void post2Engine(JsonNode json, String searchEngine);
}
