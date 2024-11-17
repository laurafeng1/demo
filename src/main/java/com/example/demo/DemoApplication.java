package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	private final static Logger logger = LoggerFactory.getLogger(DemoApplication.class);
	public static void main(String[] args) {
		logger.trace("this is the trace log");
		logger.error("this is en error log");
		logger.debug("this is a debug log");
		logger.info("this is an info log");
		logger.warn("this is a warn log");
		SpringApplication.run(DemoApplication.class, args);
	}

}
