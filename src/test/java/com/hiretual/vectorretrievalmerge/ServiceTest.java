package com.hiretual.vectorretrievalmerge;

import com.hiretual.vectorretrievalmerge.model.KNNResult;
import com.hiretual.vectorretrievalmerge.service.SearchService;
import com.hiretual.vectorretrievalmerge.service.impl.SearchServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ServiceTest {
    @Autowired
    SearchService searchService ;
//    SearchService searchService = new SearchServiceImpl();

    // @Test
    public void testSearch() {
        long t = System.currentTimeMillis();
        String query = "{\"astask\":{\"astask_recruiter_id\": \"team58d991c7c8b986.21845665\",\"astask_current_companies\":[\"cloudera\",\"cloudflare\"],\"astask_education_level\":[\"bachelor\",\"master\",\"ph.d\"],\"astask_excluded_companies\":[\"amazon\",\"amazon web services\",\"amazon lab126\",\"amazon robotics\",\"amazon.com\",\"twitch\",\"facebook\",\"netflix\",\"amazon music\"],\"astask_excluded_past_companies\":[\"amazon\",\"amazon web services\",\"amazon lab126\",\"amazon robotics\",\"amazon.com\",\"twitch\",\"facebook\",\"netflix\",\"amazon music\"],\"astask_excluded_titles\":[\"engineering manager\",\"director\",\"qa engineer\",\"sre\",\"solutions architect\",\"sdet\",\"quality e\",\"site reliability engineer\",\"vp\",\"front end\",\"test engineer\",\"data scientist\",\"data engineer\"],\"astask_experience\":[\"4-6\",\"6-8\",\"8-10\",\"10-15\",\"15-20\"],\"astask_location\":[\"seattle\"],\"astask_range\":\"80km\",\"astask_skills\":[\"java\",\"c++\",\"c#\",\"scala\",\"mapreduce\",\"apache spark\",\"apache storm\",\"python\",\"apache kafka\"],\"astask_titles\":[\"software development engineer\",\"software engineer\",\"lead software engineer\",\"lead software developer\",\"staff software engineer\",\"lead developer\",\"java developer\"],\"current_companies_extended\":true,\"mandatory_skills_extended\":true,\"astask_disabled_filter_check\":{\"locationsRange\":true}},\"needTranslation\":true}";
        // List<KNNResult> list=searchService.search(query,1000);
        // for(KNNResult knnResult:list){
        //     System.out.println(knnResult);
        // }
        long e = System.currentTimeMillis();
        long cost = e - t;
        System.out.println("cost:" + cost);
    }

}
