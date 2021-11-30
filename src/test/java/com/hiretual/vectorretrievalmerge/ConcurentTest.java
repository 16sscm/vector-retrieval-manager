package com.hiretual.vectorretrievalmerge;

import java.util.*;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurentTest {


    public static  ArrayList<String> Permutation(String str) {

        ArrayList<String>ret=new ArrayList<>();
        if(str.length()==1){
            ret.add(str);
            return ret;
        }
        Set<String>set=new HashSet<>();
        for(int i=0;i<str.length();i++){
            char c=str.charAt(i);
            List<String>subs=Permutation(str.substring(0,i)+str.substring(i+1));
            for(String sub:subs){
                set.add(c+sub);
            }
        }
        ret.addAll(set);
        return ret;
    }
    public static void main(String[] args) {

//        Class clazz=ConcurentTest.class;
//        clazz.isAnnotationPresent();
        String str="aab";
        Permutation(str);
        String t=str.substring(0,0);
        Queue<Integer> queue = new LinkedList<>();
        queue.poll();
        queue.add(1);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            int finalI = i;
            executorService.execute(()->{

                System.out.println(finalI);
            });
        }
        executorService.shutdown();
    }
}
