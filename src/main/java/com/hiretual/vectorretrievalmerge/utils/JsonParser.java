package com.hiretual.vectorretrievalmerge.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.hiretual.vectorretrievalmerge.model.KNNResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class JsonParser {
    private static final Logger logger = LoggerFactory.getLogger(JsonParser.class);
    public static JsonNode getJsonNode(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode returnNode = mapper.createObjectNode();
        try {
            returnNode = mapper.readTree(jsonString);
        } catch (Exception e) {
            logger.error("fail to parse json string to json object|" + jsonString, e);
        }

        return returnNode;
    }
    public static String transformEsPayload(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode jsonnode = mapper.readTree(jsonString);

            if(jsonnode.has("esSearchArray")){
                JsonNode esSearchArray=jsonnode.get("esSearchArray");
                ObjectNode objectNode=(ObjectNode)esSearchArray;
                objectNode.put("configAction","search_user");
                return mapper.writeValueAsString(objectNode);
            }else {
                logger.error("fail to parse es payload,has no field esSearchArray");
                return null;
            }

        } catch (Exception e) {
            logger.error("fail to parse json string to json object|" + jsonString, e);
            return null;
        }
    }
    public static String extractEmbedding(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode jsonnode = mapper.readTree(jsonString);

            if(jsonnode.has("astask_embeds")){
                JsonNode embeddings=jsonnode.get("astask_embeds");
                JsonNode embedding=embeddings.get(0);
                return mapper.writeValueAsString(embedding);
            }else {
                logger.error("fail to parse es payload,has no field esSearchArray");
                return null;
            }

        } catch (Exception e) {
            logger.error("fail to parse json string to json object|" + jsonString, e);
           
        }
        return null;
    }
    public static List<KNNResult> transformJson2KNNResults(String jsonString){
        ObjectMapper mapper = new ObjectMapper();
        try {
            List<KNNResult> list = mapper.readValue(jsonString,new TypeReference<List<KNNResult>>() { });
            return list;
        } catch (JsonProcessingException e) {
            logger.error("fail to parse json string to KNNResult list|" + jsonString, e);
            return null;
        }

    }
    public static String getJsonString( Object object){
        ObjectMapper mapper = new ObjectMapper();
        String str="";
        try {
            str = mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("fail to parse object to json string" , e);
        }
        return str;
    }
}
