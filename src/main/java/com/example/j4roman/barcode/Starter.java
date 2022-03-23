package com.example.j4roman.barcode;

import com.example.j4roman.barcode.config.HibernateProperties;
import com.example.j4roman.barcode.config.SpringDataSourceProperies;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnableConfigurationProperties({
        HibernateProperties.class,
        SpringDataSourceProperies.class })
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        DataSourceTransactionManagerAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class })
public class Starter extends SpringBootServletInitializer {

//    public static void main(String[] args) {
//        SpringApplication.run(applicationClass, args);
//    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(applicationClass);
//    }

//    private static Class<Starter> applicationClass = Starter.class;
}
