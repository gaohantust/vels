package com.cnooc.platform.config;
/**
 * @ClassName MaximoJpaConfig.java
 * @author Tong
 * @version V1.0
 * @Description TODO
 * @createTime 2021年01月11日 13:54:00
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Map;

/**
 * @program: vels
 * @description: MaximoEntityManager
 * @author: TONG
 * @create: 2021-01-11 13:54
 **/
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactoryMaximo",
        transactionManagerRef = "transactionManagerMaximo",
        basePackages = {"com.cnooc.maximo.**"}) //设置Repository所在位置
public class MaximoJpaConfig {
    @Autowired
    @Qualifier(value = "maximoDataSource")
    private DataSource maximoDataSource;
    @Autowired
    private JpaProperties jpaProperties;
    @Autowired
    private HibernateProperties hibernateProperties;
    private Map getVendorProperties() {
        return hibernateProperties.determineHibernateProperties(jpaProperties.getProperties(), new HibernateSettings());
    }
    //其他数据源
    @Bean(name = "entityManagerFactoryMaximo")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryMaximo(EntityManagerFactoryBuilder builder) {
        return builder
                .dataSource(maximoDataSource)// 设置数据源
                .properties(jpaProperties.getProperties())// 设置jpa配置
                .properties(getVendorProperties())// 设置hibernate配置
                //.packages("com.cnooc.**.domain")
                .packages("com.cnooc.maximo.**.domain")//设置实体类所在位置
                .persistenceUnit("maximoPersistenceUnit")// 设置持久化单元名，用于@PersistenceContext注解获取EntityManager时指定数据源
                .build();
    }
    @Bean(name = "emMaximo")
    public EntityManager maximoEntityManager(EntityManagerFactoryBuilder builder) {
        EntityManager entityManager= entityManagerFactoryMaximo(builder).getObject().createEntityManager();
        return entityManager;
    }
    @Bean(name = "transactionManagerMaximo")
    public PlatformTransactionManager transactionManagerMaximo(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactoryMaximo(builder).getObject());
    }
}
