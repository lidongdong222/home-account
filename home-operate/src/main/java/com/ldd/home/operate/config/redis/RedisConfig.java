package com.ldd.home.operate.config.redis;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    String redisHost;

    @Value("${spring.data.redis.port}")
    String redisPort;

    @Value("${spring.data.redis.password}")
    String redisPassword;

//    设置序列化规则，使用Json序列化，安全性较低，有
    @Bean
    public RedisTemplate redisTemplate(@Autowired RedisTemplate redisTemplate){
        redisTemplate.setDefaultSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setKeySerializer(new Jackson2JsonRedisSerializer(Object.class));
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashKeySerializer(new Jackson2JsonRedisSerializer(Object.class));
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;
    }



    /**
     * 单例模式
     */
    @Bean
    public RedissonClient getRedissonClient(){
        Config config = new Config();
        config.useSingleServer().setAddress("redis://"+redisHost+":"+redisPort).setPassword(redisPassword);
        return Redisson.create(config);
    }

    /**
     * 集群模式（三主三从）
     */
    //    @Bean
//    public RedissonClient getRedissonClient(){
//        Config config = new Config();
//        config.useClusterServers()
//                //设置集群节点
//                .addNodeAddress(RedissonNodes.stream().map(n->{
//                    return "redis://"+n;
//                }).collect(Collectors.toList()).toArray(new String[RedissonNodes.size()]))
//                //设置集群节点扫描时间
//                .setScanInterval(redissonScanInterval)
//                //设置对于master节点中连接池最大数
//                .setMasterConnectionPoolSize(redissonMasterConnPoolSize)
//                //设置对于master节点中连接池最小数
//                .setMasterConnectionMinimumIdleSize(redissonMasterConnMinimumIdleSize)
//                //设置对于slave节点中连接池最大数
//                .setSlaveConnectionPoolSize(redissonSlaveConnPoolSize)
//                //设置对于slave节点中连接池最小数
//                .setSlaveConnectionMinimumIdleSize(redissonSlaveConnMinimumIdleSize)
//                //如果当前连接池里的连接数量超过了最小空闲数，而同时连接空闲时间又超过了该数值，那么这些连接将会被自动关闭
//                //并从连接池中去掉，单位毫秒
//                .setIdleConnectionTimeout(redissonIdleConnTimeout)
//                //同任何节点建立连接的等待时间 毫秒
//                .setConnectTimeout(redissonConnTimeout)
//                //等待节点回复命令的时间，单位毫秒
//                .setTimeout(redissonTimeout)
//                //当于某个节点断开时，等待与其重新建立的时间间隔
//                .setRetryInterval(redissonRetryInterval);
//        return Redisson.create(config);
//    }


}
