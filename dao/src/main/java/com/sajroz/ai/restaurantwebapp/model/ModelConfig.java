package com.sajroz.ai.restaurantwebapp.model;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class ModelConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    protected void init() {
        logger.info("INITIALIZE ModelCongif");
    }

    /**
     * Definicja beanu wymagana do poprawnego odczytywania properties za pomoca adnotacji PropertiySource.
     *
     * @return PropertySourcesPlaceholderConfigurer
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /**
     * Konfiguracja entityManagerFactory.
     *
     * @return LocalContainerEntityManagerFactoryBean - EntityManagerFactory zdefiniowany w spring orm.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.sajroz.ai.restaurantwebapp.model.entity");
        em.setJpaVendorAdapter(jpaVendorAdapter());
        em.setJpaProperties(additionalProperties());
        return em;
    }


    /**
     * Metoda pobiera dataSource przez JNDI.
     *
     * @return DataSource
     */
    @Bean
    public DataSource dataSource() {
        //łączenie z bazą danych przez JNDI
        JndiDataSourceLookup lookup = new JndiDataSourceLookup();
        return lookup.getDataSource("jdbc/restaurant");
    }

    /**
     * Konfiguracja jpaVendorAdapter. Metoda dodaje wyswietlanie sql wygenerowanych przez hibernate'a oraz ustawia dialekt sqla na MySQL.
     *
     * @return JpaVendorAdapter
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setDatabasePlatform("org.hibernate.dialect.MySQL5Dialect");
        return adapter;
    }

    /**
     * Info: https://docs.jboss.org/hibernate/orm/5.0/manual/en-US/html/ch03.html#configuration-optional
     *
     * @return Propeteries
     */
    private Properties additionalProperties() {
        Properties properties = new Properties();
        // NIE WLACZAC - automatyczna aktualizacja schematu db
        properties.setProperty("hibernate.hbm2ddl.auto", "create");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");
        properties.setProperty("hibernate.jdbc.fetch_size", "0");
        // w MySQL to chyba nie dziala (JDBC Batch nie dziala przy AUTO ID jesli dobrze pamietam)
        properties.setProperty("hibernate.jdbc.batch_size", "20");
        // kodowanie
        properties.setProperty("hibernate.connection.CharSet", "utf8");
        properties.setProperty("hibernate.connection.characterEncoding", "utf8");
        properties.setProperty("hibernate.connection.useUnicode", "true");
        properties.setProperty("hibernate.hbm2ddl.import_files_sql_extractor", "com.sajroz.ai.restaurantwebapp.dao.CustomSqlExtractor");
        return properties;
    }

    /**
     * Konfiguracja JpaTransactionManager ktory jest odpowiedzialny za zarzadzanie transakcjami.
     *
     * @param emf EntityManagerFactory
     * @return JpaTransactionManager
     */
    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(emf);
        return manager;
    }
}
