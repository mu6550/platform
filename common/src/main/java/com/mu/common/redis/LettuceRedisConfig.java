package com.mu.common.redis;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * redis配置类
 *
 * @author 穆江魁
 */
@Configuration
@PropertySource("classpath:redis.properties")
public class LettuceRedisConfig {

    /**
     * 数据库索引
     */
    @Value("${spring.redis.database}")
    private int database;

    /**
     * 连接地址
     */
    @Value("${spring.redis.host}")
    private String host;

    /**
     * 密码
     */
    @Value("${spring.redis.password}")
    private String password;

    /**
     * 端口
     */
    @Value("${spring.redis.port}")
    private int port;

    /**
     * 超时
     */
    @Value("${spring.redis.timeout}")
    private long timeout;

    /**
     * 最大空闲连接
     */
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle;

    /**
     * 最小空闲连接
     */
    @Value("${spring.redis.lettuce.pool.min-idle}")
    private int minIdle;

    /**
     * 最大连接数
     */
    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxActive;

    /**
     * 最大阻塞等待时间
     */
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private long maxWait;

    /**
     * 1.配置RedisTemplate,实现自定义的序列化，
     */

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    /**
     * 配置lettucePool连接池
     *
     * @param config config
     * @return org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
     */
    @Bean
    public LettuceConnectionFactory lettuceConnectionFactory(GenericObjectPoolConfig config) {
        // 单机版本配置
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setDatabase(database);
        redisStandaloneConfiguration.setPassword(RedisPassword.of(password));
        redisStandaloneConfiguration.setPort(port);

        // 集群部署配置
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();

        // 设置连接池
        LettucePoolingClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofMillis(timeout))
                .poolConfig(config)
                .build();
        // 实例化工厂
        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
    }

    /**
     * GenericObjectPoolConfig 连接池配置
     *
     * @return org.apache.commons.pool2.impl.GenericObjectPoolConfig
     */
    @Bean
    public GenericObjectPoolConfig genericObjectPoolConfig() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxIdle(maxIdle);
        genericObjectPoolConfig.setMinIdle(minIdle);
        genericObjectPoolConfig.setMaxTotal(maxActive);
        genericObjectPoolConfig.setMaxWaitMillis(timeout);
        return genericObjectPoolConfig;
    }
}