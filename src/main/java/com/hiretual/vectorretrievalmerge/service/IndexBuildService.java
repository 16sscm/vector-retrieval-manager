package com.hiretual.vectorretrievalmerge.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface IndexBuildService {
    void dispatch(JsonNode documentList);
    void update(JsonNode document);
    public void commitMainIndex();
    public void commitAdditionalIndex();
    public void mergeMainIndex();
    public void mergeAdditionalIndex();
}
