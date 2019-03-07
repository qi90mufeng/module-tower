package com.albert.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author fujin
 * @date 2019/03/07 多数据源读取配置文件
 * @since 1.0
 */
@Data
@Configuration
public class MultipleMongoProperties {

    @Bean(name="primaryMongoProperties")
    @Primary
    @ConfigurationProperties(prefix="mongodb.primary")
    public MongoProperties primaryMongoProperties() {
        return new MongoProperties();
    }

    @Bean(name="secondaryMongoProperties")
    @ConfigurationProperties(prefix="mongodb.secondary")
    public MongoProperties secondaryMongoProperties() {
        return new MongoProperties();
    }
}
