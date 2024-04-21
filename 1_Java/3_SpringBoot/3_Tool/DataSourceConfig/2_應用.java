runtimeOnly 'com.mysql:mysql-connector-j'

<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <scope>runtime</scope>
</dependency>
-------------------------------------------------------------------------------------------------------------------
server:
  port: 9001
spring:
  datasource:
    opvauth:
      jdbc-url: "jdbc:mysql://127.0.0.1:3306/opv_auth_dev?serverTimezone=Asia/Taipei"
      jdbc-url: "jdbc:sqlserver://127.0.0.1:1433;database=opview_task"
      username: "root"
      password: "1234"
-------------------------------------------------------------------------------------------------------------------
package com.eland.config.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.eland.dao.opvauth", entityManagerFactoryRef = "opvAuthEntityManagerFactory", transactionManagerRef = "opvAuthTransactionManager")
public class OpvAuthConfig {

    @Bean
    @Primary // 只有第1個資料庫才需要Primary標籤
    @ConfigurationProperties(prefix = "spring.datasource.opvauth")
    public DataSource opvAuthDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean opvAuthEntityManagerFactory(@Qualifier("opvAuthDataSource") DataSource opvAuthDataSource, EntityManagerFactoryBuilder builder) {
        return builder.dataSource(opvAuthDataSource).packages("com.eland.entity.opvauth").persistenceUnit("opvAuthDs").build();
    }

    @Bean
    public PlatformTransactionManager opvAuthTransactionManager(@Qualifier("opvAuthEntityManagerFactory") LocalContainerEntityManagerFactoryBean opvAuthEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(opvAuthEntityManagerFactory.getObject()));
    }

}
