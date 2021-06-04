package com.example.template.util

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.HashOperations
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

/** Redis 工具类 */
@Component
@Suppress("UNCHECKED_CAST")
class RedisUtil private constructor() {
    @Autowired
    fun setTemplate(template: RedisTemplate<String, Any>) {
        Companion.template = template
        value = template.opsForValue()
        hash = template.opsForHash()
    }

    @Autowired
    fun setStringTemplate(stringTemplate: StringRedisTemplate) {
        Companion.stringTemplate = stringTemplate
        stringValue = stringTemplate.opsForValue()
    }

    companion object {
        private lateinit var template: RedisTemplate<String, Any>
        private lateinit var value: ValueOperations<String, Any>
        private lateinit var hash: HashOperations<String, Any, Any>

        private lateinit var stringTemplate: StringRedisTemplate
        private lateinit var stringValue: ValueOperations<String, String>

        /** 删除缓存 */
        fun delete(key: String) = template.delete(key)

        /** 读取缓存 */
        fun <T> get(key: String) = value.get(key) as T

        /** 读取字符串缓存 */
        fun getString(key: String) = stringValue.get(key)

        /** 写入缓存 */
        fun set(key: String, value: Any) = Companion.value.set(key, value)

        /** 写入字符串缓存 */
        fun setString(key: String, value: String) = stringValue.set(key, value)

        /** 写入缓存并设置过期时间 */
        fun set(key: String, value: Any, expireTime: Long, timeUnit: TimeUnit) =
            Companion.value.set(key, value, expireTime, timeUnit)

        /** 写入字符串缓存并设置过期时间 */
        fun setString(key: String, value: String, expireTime: Long, timeUnit: TimeUnit) =
            stringValue.set(key, value, expireTime, timeUnit)

        /** hash 添加 */
        fun hashPut(key: String, hashKey: String, value: Any) = hash.put(key, hashKey, value)

        /** hash 添加多个 */
        fun hashPutAll(key: String, m: Map<Any, Any>) = hash.putAll(key, m)

        /** hash 获取数据 */
        fun <T> hashGet(key: String, hashKey: String) = hash.get(key, hashKey) as T

        /** hash 获取所有 */
        fun <T> hashGetAll(key: String): Map<String, T> = hash.entries(key) as Map<String, T>

        /** hash 数据删除 */
        fun hashDelete(key: String, vararg hashKeys: String) = hash.delete(key, *hashKeys)

        /** 递增 */
        fun increment(key: String, number: Long) = value.increment(key, number)

        /** 递增 */
        fun increment(key: String) = value.increment(key)
    }
}
