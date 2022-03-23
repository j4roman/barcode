package com.example.j4roman.barcode.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

@Configuration
public class LoggingConfiguration {

    private static final Logger log = LoggerFactory.getLogger(LoggingConfiguration.class);

    @Autowired
    @Bean(name = "logFilter")
    public CommonsRequestLoggingFilter getLogFilter() {
        CommonsRequestLoggingFilter filter
                = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(true);
        log.info("logFilter is initialized", filter);

        return filter;
    }
}
