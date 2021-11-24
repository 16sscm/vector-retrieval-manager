package com.hiretual.vectorretrievalmerge;

import com.hiretual.vectorretrievalmerge.utils.HashBucket;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HashTest {
    public static int toHash(String key) {
        int arraySize = 11113; // 数组大小一般取质数
        int hashCode = 0;
        for (int i = 0; i < key.length(); i++) { // 从字符串的左边开始计算
            int letterValue = key.charAt(i) - 96;// 将获取到的字符串转换成数字，比如a的码值是97，则97-96=1
            // 就代表a的值，同理b=2；
            hashCode = ((hashCode << 5) + letterValue) % arraySize;// 防止编码溢出，对每步结果都进行取模运算
        }
        return hashCode;
    }


    public static void main(String[] args) {
        test();

    }
    public static void test1(){
        int scale=100000;
        String[]testCases=genTestStrings(scale);
        int []res=new int[scale];
        for(int i=0;i<scale;i++){
            res[i]=toHash(testCases[i]);
        }
        verify1(res);
        System.out.println("done");
    }
    public static void test(){
        int scale=10000;
        String[]testCases=genTestStrings(scale);
        int []res=new int[scale];
        for(int i=0;i<scale;i++){
            res[i]=testCases[i].hashCode();
        }
        for(int i=1;i<21;i++){
            bucket(res,i);
        }

        System.out.println("done");
    }
    public static int[]verify1(int[]hashs){
        int unit=11113>>2;
        int buckets[]=new int[9];
        for(int hash:hashs){
            int index=(int)Math.floor((double)hash/(double)(unit))+4;
            buckets[index]=buckets[index]+1;
        }
        return buckets;
    }
    public static int[]bucket(int[]hashs,int num){

        int buckets[]=new int[num];
        double numD=(double)num;
        for(int hash:hashs){
            double tmp=(double)hash;
            tmp=tmp/Integer.MAX_VALUE/2*num;
            int index=(int)(Math.floor(tmp+numD/2));
            buckets[index]=buckets[index]+1;
        }
        return buckets;
    }
    public static String[] genTestStrings(int scale){
        String []testCases=new String[scale];
        int []possibleLen=new int[]{26,35,40};
        for(int i=0;i<scale;i++){
            Random random=new Random();
            int len=possibleLen[random.nextInt(3)];
            testCases[i]=getRandomString(len);
        }
        return testCases;
    }
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
    @Test
    public void testRealUidBucket() throws FileNotFoundException {

        List<String>testCase=toArrayByFileReader1("src/test/resource/test_uids");
        int bucketSize=8;
        int[]bucket=new int[bucketSize];
        for(String uid:testCase){
            int index= HashBucket.getBucketIndex(uid,bucketSize);
            bucket[index]++;
        }
        System.out.println(0);

    }
    public static List<String> toArrayByFileReader1(String name) throws FileNotFoundException {
        // 使用ArrayList来存储每行读取到的字符串
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            FileReader fr = new FileReader(name);
            BufferedReader bf = new BufferedReader(fr);
            String str;
            // 按行读取字符串
            while ((str = bf.readLine()) != null) {
                arrayList.add(str);
            }
            bf.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对ArrayList中存储的字符串进行处理
        return arrayList;
    }
}
