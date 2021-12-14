package com.hiretual.vectorretrievalmerge.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.hiretual.vectorretrievalmerge.service.IndexBuildService;
import com.hiretual.vectorretrievalmerge.utils.RequestParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
@Component
@RestController
public class IndexBuildController {
    @Autowired
    IndexBuildService indexBuildServiceImpl;

    @RequestMapping(value="/doc/add", method= RequestMethod.GET)
    public void insertDocument() {
        
        indexBuildServiceImpl.dispatch();
    }

    @RequestMapping(value="/index/commit", method= RequestMethod.GET, produces="application/json;charset=UTF-8")
    public void commit() {
        indexBuildServiceImpl.commitMainIndex();
    }
    @RequestMapping(value="/index/merge", method= RequestMethod.GET, produces="application/json;charset=UTF-8")
    public void merge() {
        indexBuildServiceImpl.mergeMainIndex();
    }
}
