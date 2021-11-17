package com.hiretual.vectorretrievalmerge.utils;

import com.hiretual.vectorretrievalmerge.model.KNNResult;

import java.util.*;

public class TopKResult {
    private PriorityQueue<KNNResult> queue;
    private int maxSize; //堆的最大容量

    public TopKResult(int maxSize) {
        if (maxSize <= 0) {
            throw new IllegalStateException();
        }
        this.maxSize = maxSize;
        this.queue = new PriorityQueue<>(maxSize, new Comparator<KNNResult>() {
            @Override
            public int compare(KNNResult o1, KNNResult o2) {
                // 最大堆用o2 - o1，最小堆用o1 - o2
                return o2.compareTo(o1);
            }
        });
    }
    public void addList(KNNResult[]knnResults){
        for(KNNResult knnResult:knnResults){
            add(knnResult);
        }

    }
    public void add(KNNResult knnResult) {
        if (queue.size() < maxSize) {
            queue.add(knnResult);
        } else {
            KNNResult peek = queue.peek();
            if (knnResult.compareTo(peek) < 0) {
                queue.poll();
                queue.add(knnResult);
            }
        }
    }

    public List<KNNResult> sortedList() {
        List<KNNResult> list = new ArrayList<>(queue);
        Collections.sort(list);
        return list;
    }


}
