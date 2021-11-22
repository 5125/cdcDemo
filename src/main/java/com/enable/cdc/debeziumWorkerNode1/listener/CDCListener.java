package com.enable.cdc.debeziumWorkerNode1.listener;

import com.enable.cdc.debeziumWorkerNode1.producer.CDCProducerService;
import io.debezium.config.Configuration;
import io.debezium.embedded.EmbeddedEngine;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.apache.kafka.connect.data.Struct;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
public class CDCListener {

    /**
     * Single thread pool which will run the Debezium engine asynchronously.
     */
    private final Executor executor = Executors.newSingleThreadExecutor();

    /**
     * The Debezium engine which needs to be loaded with the configurations, Started and Stopped - for the
     * CDC to work.
     */
    private final EmbeddedEngine engine;

    @Autowired
    private CDCProducerService cdcProducerService;
    /**
     * Constructor which loads the configurations and sets a callback method 'handleEvent', which is invoked when
     * a DataBase transactional operation is performed.
     *
     * @param debeziumTableConnector
     */
    private CDCListener(Configuration debeziumTableConnector) {
        this.engine = EmbeddedEngine
                .create()
                .using(debeziumTableConnector)
                .notifying(this::handleEvent).build();
    }

    /**
     * The method is called after the Debezium engine is initialized and started asynchronously using the Executor.
     */
    @PostConstruct
    private void start() {
        this.executor.execute(engine);
    }

    /**
     * This method is called when the container is being destroyed. This stops the debezium, merging the Executor.
     */
    @PreDestroy
    private void stop() {
        if (this.engine != null) {
            this.engine.stop();
        }
    }

    /**
     * This method is invoked when a transactional action is performed on any of the tables that were configured.
     *
     * @param sourceRecord
     */
    private void handleEvent(SourceRecord sourceRecord) {
        Struct sourceRecordValue = (Struct) sourceRecord.value();
        if (sourceRecordValue != null) {
        // log and proceed.

            cdcProducerService.processEvent(sourceRecordValue);
        } else {
            // do nothing
        }
    }
}
