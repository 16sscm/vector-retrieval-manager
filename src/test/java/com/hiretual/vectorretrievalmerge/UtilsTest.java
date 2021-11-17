package com.hiretual.vectorretrievalmerge;

import com.hiretual.vectorretrievalmerge.model.KNNResult;
import com.hiretual.vectorretrievalmerge.utils.TopKResult;
import org.junit.jupiter.api.Test;

public class UtilsTest {
    @Test
    public void testTopK() {
        TopKResult pq = new TopKResult(5);
        pq.add(new KNNResult("_b", 0.8f));
        pq.add(new KNNResult("_a", 0.01f));
        pq.add(new KNNResult("a", 0.1f));
        pq.add(new KNNResult("b", 0.2f));
        pq.add(new KNNResult("c", 0.3f));
        pq.add(new KNNResult("d", 0.4f));
        pq.add(new KNNResult("e", 0.5f));
        pq.add(new KNNResult("f", 0.6f));
        KNNResult[]knnResults=new KNNResult[2];
        knnResults[0]=new KNNResult("g",0.9f);
        knnResults[1]= new KNNResult("g",0.99f);
        KNNResult[]knnResults1=new KNNResult[2];
        knnResults1[0]=new KNNResult("g",0.8f);
        knnResults1[1]=new KNNResult("g",0.76f);
        pq.addList(knnResults);
        pq.addList(knnResults1);
        for (KNNResult knnResult : pq.sortedList()) {
            System.out.println(knnResult);
        }


    }
}
