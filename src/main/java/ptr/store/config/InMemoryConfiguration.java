package ptr.store.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PreDestroy;
import java.util.Properties;


/**
 * In memory DB config
 *
 * */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("ptr.store.dao")
@ComponentScan("ptr.store.dao")
public class InMemoryConfiguration {


    private EmbeddedDatabase embeddedDatabase;

    @Bean(name = "hsqlDbInMemory")
    public EmbeddedDatabase hsqlDbInMemory() {
        if (this.embeddedDatabase == null) {
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            this.embeddedDatabase = builder.setType(EmbeddedDatabaseType.HSQL).build();
        }

        return this.embeddedDatabase;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){

        LocalContainerEntityManagerFactoryBean localContainerEntityManager = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManager.setDataSource(hsqlDbInMemory());
        localContainerEntityManager.setPackagesToScan("ptr.store.model");
        localContainerEntityManager.setPersistenceUnitName("inMemoryPU");

        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.HSQLDialect");
//        properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "create");
//        properties.put("hibernate.hbm2ddl.import_files", "");

        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        localContainerEntityManager.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        localContainerEntityManager.setJpaProperties(properties);
        localContainerEntityManager.afterPropertiesSet();
        return localContainerEntityManager;
    }

    @Bean
    @Qualifier("platformTransactionManager")
    public PlatformTransactionManager transactionManager(){

        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(this.entityManagerFactory().getObject());
        return jpaTransactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslationPostProcessor(){
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @PreDestroy
    public void destroy(){
        if(this.embeddedDatabase != null){
            this.embeddedDatabase.shutdown();
        }
    }

}
