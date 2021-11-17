package com.hiretual.vectorretrievalmerge.model;

public class QueryWrapper {
    String embedding;
    String esQuery;

    public QueryWrapper(String embedding,String esQuery) {
        this.embedding = embedding;
        this.esQuery=esQuery;
    }

    public String getEsQuery() {
        return esQuery;
    }

    public String getEmbedding() {
        return embedding;
    }
}
