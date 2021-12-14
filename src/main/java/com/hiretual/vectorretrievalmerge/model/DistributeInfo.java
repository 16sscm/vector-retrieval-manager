package com.hiretual.vectorretrievalmerge.model;

public class DistributeInfo {
    int totalInstance;
    int seqNum;
    public DistributeInfo(int totalInstance,int seqNum){
        this.seqNum=seqNum;
        this.totalInstance=totalInstance;
    }
    public int getSeqNum(){
        return this.seqNum;
    }

    public int getTotalInstance(){
        return this.totalInstance;
    }
}
