package spring_boot.session14bt02.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.hibernate.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class HibernateConfig {
    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean factoryBean(DataSource dataSource) {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPackagesToScan("spring_boot.session14demo.model.entity");

        Properties prop = new Properties();
        prop.put("hibernate.show_sql",env.getProperty("hibernate.show_sql"));
        prop.put("hibernate.hbm2ddl.auto",env.getProperty("hibernate.ddl_auto"));
        prop.put("hibernate.dialect",env.getProperty("hibernate.dialect"));
        prop.put("hibernate.format_sql",env.getProperty("hibernate.format_sql"));

        bean.setHibernateProperties(prop);
        return bean;
    }
}
