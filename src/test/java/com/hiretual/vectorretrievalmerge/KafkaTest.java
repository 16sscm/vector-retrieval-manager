package com.hiretual.vectorretrievalmerge;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaTest {
    @Test
    public void asListTest(){
        int[] arr = {1,2,3};
        List list = Arrays.asList(arr);
        System.out.println(list.size());
    }
    @Test
    public void KafkaTests() throws InterruptedException {
        //1.配置属性值
        Properties properties = new Properties();
        //kafka是服务器地址
        properties.put("bootstrap.servers",  "kafka.db.testhtm:9092");
        //定义消费者组
        properties.put("group.id", "test.vector.retrieval1");
        //自动提交（offset）
        properties.put("enable.auto.commit", "false");
//        //自动处理的间隔时间1秒
//        properties.put("auto.commit.interval.ms", "1000");

        properties.put("max.poll.records","500");
        properties.put("auto.offset.reset", "earliest");
        //key和values的持久化设置
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        //2.创建消费者
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        //3.订阅消费topic(可以有多个topic)
        kafkaConsumer.subscribe(Arrays.asList("online-dataprocess.etl.profile-to-ml-review.0"));
//        kafkaConsumer.partitionsFor("a");
        Duration duration=Duration.ofMillis(1000);

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");



        //4.执行消费的操作
        while (true) {
            //100ms消费一次
            //kafkaConsumer.poll(100)读出来，读到records
            ConsumerRecords<String, String> records = kafkaConsumer.poll(duration);
//            System.out.println(records.count());
//            Date date = new Date(System.currentTimeMillis());
//            System.out.println(formatter.format(date));
//            System.out.println();
            for (ConsumerRecord<String, String> record : records) {
                System.out.println("-----------------");
               // print offset，key，value
                System.out.printf("offset = %d, key=%s, value = %s", record.offset(), record.key(),record.value());

            }
            kafkaConsumer.commitSync();
            Thread.sleep(2000);
        }


    }

}
