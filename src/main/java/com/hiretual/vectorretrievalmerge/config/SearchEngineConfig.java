package com.hiretual.vectorretrievalmerge.config;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.hiretual.vectorretrievalmerge.utils.JsonParser;
import com.hiretual.vectorretrievalmerge.utils.RawJsonReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchEngineConfig {
    private static String configFile="/root/engine_config.json";
    private static List<String>engines=new ArrayList<>();
    private static final Logger logger=LoggerFactory.getLogger(SearchEngineConfig.class);
    static {
        logger.info("Load search engine config from:"+configFile);
        JsonNode jsonNode=JsonParser.getJsonNode(RawJsonReader.readJsonFile(configFile));
        for(JsonNode node :jsonNode){
            String engineUrl=node.asText();
            logger.info("read url:"+engineUrl);
            engines.add(engineUrl);
        }
    }
    public static List<String> getSearchEngines(){
        return engines;
    }
}
