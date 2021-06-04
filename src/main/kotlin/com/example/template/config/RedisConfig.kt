package com.example.template.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.RedisSerializer

/** Redis 配置 */
@Configuration
class RedisConfig {
    @Bean
    fun template(factory: RedisConnectionFactory) =
        RedisTemplate<String, Any>().apply {
            setConnectionFactory(factory)
            // 设置 key 序列化
            keySerializer = RedisSerializer.string()
        }
}
