//package com.example.products.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//import org.springframework.web.client.RestTemplate;
//
//@Component
//public class AppsConfigurations {
//    @Bean
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }
//    @Bean
//    public RedisTemplate redisTempplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
//        redisTemplate.setConnectionFactory(redisConnectionFactory);
//        return redisTemplate;
//    }
//}
