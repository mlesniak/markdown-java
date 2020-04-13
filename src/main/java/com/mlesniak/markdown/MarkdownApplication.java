package com.mlesniak.markdown;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class MarkdownApplication implements CommandLineRunner {
	Logger logger = LoggerFactory.getLogger(MarkdownApplication.class);

	public static void main(String[] args) {
		new SpringApplicationBuilder(MarkdownApplication.class).logStartupInfo(false).run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("Starting webserver");
	}
}
