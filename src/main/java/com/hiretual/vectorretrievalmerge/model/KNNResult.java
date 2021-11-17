package com.hiretual.vectorretrievalmerge.model;

public class KNNResult implements  Comparable<KNNResult> {
    private  String uid;
    private  float score;


    public KNNResult(){

    }
    public KNNResult(final String uid, final float score) {
        this.uid = uid;
        this.score = score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return this.uid;
    }

    public float getScore() {
        return this.score;
    }
    @Override
    public String toString(){
        return "uid:"+this.uid+",score:"+this.score;
    }
    @Override
    public int compareTo(KNNResult o) {
        float sub=this.score-o.getScore();
        //desc
        if(sub>0){
            return -1;
        }else if(sub==0){
            return 0;
        }else {
            return 1;
        }
    }
}
