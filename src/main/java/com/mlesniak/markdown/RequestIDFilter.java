package com.mlesniak.markdown;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RequestIDFilter implements Filter {
    Logger LOG = LoggerFactory.getLogger(StaticController.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String uuid = UUID.randomUUID().toString();
        MDC.put("request", uuid);
        LOG.info("Starting request with request={}", uuid);
        chain.doFilter(request, response);
        LOG.info("Finished request with request={}", uuid);
        MDC.remove("request");
    }

    // other methods
}
