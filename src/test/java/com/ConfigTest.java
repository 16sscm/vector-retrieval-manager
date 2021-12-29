package com;

import java.util.List;

import com.hiretual.vectorretrievalmerge.config.SearchEngineConfig;

import org.junit.Test;

public class ConfigTest {
    @Test
    public void testEngineConfig(){
        List<String>engines=  SearchEngineConfig.getSearchEngines();
        System.out.println(engines.size());
    }
  
}
