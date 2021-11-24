package com.hiretual.vectorretrievalmerge.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.hiretual.vectorretrievalmerge.model.KNNResult;
import com.hiretual.vectorretrievalmerge.service.SearchService;
import com.hiretual.vectorretrievalmerge.utils.RequestParser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Component
@RestController
public class SearchController {

    @Autowired
    SearchService searchServiceImpl;

    @RequestMapping(value="/search", method= RequestMethod.POST)
    public List<KNNResult> search(@RequestBody String query) {

        return searchServiceImpl.search( query,1000);
    }
}
