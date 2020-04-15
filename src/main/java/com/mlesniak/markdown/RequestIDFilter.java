package com.mlesniak.markdown;

import java.io.IOException;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RequestIDFilter implements Filter {
    Logger logger = LoggerFactory.getLogger(RequestIDFilter.class);

    @Autowired
    private VersionService versionService;
    private String commit;

    @PostConstruct
    public void initialize() {
        commit = versionService.getCommit();
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String uuid = UUID.randomUUID().toString();
        try {
            MDC.put("request", uuid);
            MDC.put("commit", commit);
            logger.info("Starting request with request={}", uuid);
            chain.doFilter(request, response);
            logger.info("Finished request with request={}", uuid);
        } finally {
            MDC.remove("request");
            MDC.remove("commit");
        }
    }

    // other methods
}
