package com.enable.cdc.debeziumWorkerNode1.producer;

import com.enable.cdc.debeziumWorkerNode1.config.KafkaProducerConfig;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.RecordMetadata;
import java.util.concurrent.ExecutionException;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.connect.data.Struct;
import org.springframework.stereotype.Service;

@Service
public class CDCProducerService {

    Producer<Long, String> producer;

    private CDCProducerService (Producer<Long, String> createProducer) {
        producer = createProducer;
    }

    public void processEvent (Struct struct) {
      //  Producer<Long, String> producer = KafkaProducerConfig.createProducer();
        final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>("topipcName",
                "This is record ");
        try {
            RecordMetadata metadata = producer.send(record).get();
            System.out.println("Record sent with key to partition " + metadata.partition()
                    + " with offset " + metadata.offset());
        } catch (ExecutionException e) {
            System.out.println("Error in sending record");
            System.out.println(e);
        } catch (InterruptedException e) {
            System.out.println("Error in sending record");
            System.out.println(e);
        }
    }
}
