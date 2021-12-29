package com.hiretual.vectorretrievalmerge.config;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.hiretual.vectorretrievalmerge.utils.JsonParser;
import com.hiretual.vectorretrievalmerge.utils.RawJsonReader;

public class SearchEngineConfig {
    private static String configFile="/root/engine_config.json";
    public static List<String> getSearchEngines(){
        List<String>engines=new ArrayList<>();
        JsonNode jsonNode=JsonParser.getJsonNode(RawJsonReader.readJsonFile(configFile));
        for(JsonNode node :jsonNode){
            engines.add(node.asText());
        }
        return engines;
    }
}
