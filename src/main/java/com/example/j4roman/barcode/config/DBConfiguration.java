package com.example.j4roman.barcode.config;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Properties;

@Configuration
public class DBConfiguration {

    private static final Logger log = LoggerFactory.getLogger(DBConfiguration.class);

    @Autowired
    private SpringDataSourceProperies springDataSourceProperies;

    @Autowired
    private HibernateProperties hibernateProperties;

    @Autowired
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(springDataSourceProperies.getDriverClassName());
        dataSource.setUrl(springDataSourceProperies.getUrl());
        dataSource.setUsername(springDataSourceProperies.getUsername());
        dataSource.setPassword(springDataSourceProperies.getPassword());
        log.info("dataSource is initialized with {}", springDataSourceProperies);
        return dataSource;
    }

    @Autowired
    @Bean(name = "sessionFactory")
    public SessionFactory getSessionFactory(DataSource dataSource) throws Exception {
        Properties properties = new Properties();
        for(Map.Entry<String, String> entry : hibernateProperties.getKeyValue().entrySet()) {
            properties.put(entry.getKey(), entry.getValue());
        }

        LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();

        factoryBean.setPackagesToScan(new String[] { "" });
        factoryBean.setDataSource(dataSource);
        factoryBean.setHibernateProperties(properties);
        factoryBean.afterPropertiesSet();
        SessionFactory sf = factoryBean.getObject();
        log.info("sessionFactory is initialized with {}", hibernateProperties);
        return sf;
    }

    @Autowired
    @Bean(name = "transactionManager")
    public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        log.info("transactionManager is initialized");
        return transactionManager;
    }
}
