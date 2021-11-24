package com.hiretual.vectorretrievalmerge.utils;

public class HashBucket {
    public static int getBucketIndex(String uid,int num){
        int hashcode=uid.hashCode();
        double numD=(double)num;
        double tmp=(double)hashcode;
        tmp=tmp/Integer.MAX_VALUE/2*num;
        int index=(int)(Math.floor(tmp+numD/2));
        return index;
    }
}
