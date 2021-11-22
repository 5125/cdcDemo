package com.enable.cdc.debeziumWorkerNode1.config;

import io.debezium.connector.sqlserver.SqlServerConnector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebeziumConnectorConfig {

    private String TABLE_NAME = "public.student";

    @Bean
    public io.debezium.config.Configuration debeziumTableConnector() {
        return io.debezium.config.Configuration.create()
                .with("connector.class", SqlServerConnector.class.getName())
                .with("offset.storage",  "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("offset.storage.file.filename", "/Users/rbg831/Documents/Sohan/Projects/POC/embedded-debezium/student-cdc-relay/student-offset.dat")
                .with("offset.flush.interval.ms", 60000)
                .with("name", "student-postgres-connector")
//                .with("database.server.name", studentDBHost+"-"+studentDBName)
//                .with("database.hostname", studentDBHost)
//                .with("database.port", studentDBPort)
//                .with("database.user", studentDBUserName)
//                .with("database.password", studentDBPassword)
//                .with("database.dbname", studentDBName)
                .with("table.whitelist", TABLE_NAME).build();
    }
}
