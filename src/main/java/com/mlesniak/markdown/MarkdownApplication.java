package com.mlesniak.markdown;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class MarkdownApplication implements CommandLineRunner {
	Logger logger = LoggerFactory.getLogger(MarkdownApplication.class);

	// Constructor injection makes no sense here.
	@Autowired
	private VersionService versionService;
	@Autowired
	Environment environment;

	public static void main(String[] args) { // NOSONAR False positive.
		new SpringApplicationBuilder(MarkdownApplication.class).logStartupInfo(false).run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		// I'm not sure I like google's auto formatter settings...
		logger.info("Build info: commit={}, buildTime={}", versionService.getCommit(),
				versionService.getBuildTime());
		String port = environment.getProperty("server.port");
		logger.info("Starting webserver on port={}", port);
	}
}
