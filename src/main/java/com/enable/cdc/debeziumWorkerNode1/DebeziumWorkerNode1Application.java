package com.enable.cdc.debeziumWorkerNode1;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class DebeziumWorkerNode1Application extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(DebeziumWorkerNode1Application.class, args);
	}

}
