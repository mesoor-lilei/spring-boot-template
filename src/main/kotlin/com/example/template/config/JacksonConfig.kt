package com.example.template.config

import com.example.template.constant.TimeConstant.DATA_TIME_PATTERN
import com.example.template.constant.TimeConstant.TIME_PATTERN
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Configuration
class JacksonConfig {
    @Bean
    fun builderCustomizer() = Jackson2ObjectMapperBuilderCustomizer { builder ->
        // JS 数字精度小于 Kotlin Long，响应数据时把 Long 类型属性转为字符串类型
        builder.serializerByType(Long::class.java, ToStringSerializer.instance)

        // 配置 LocalDateTime 序列化和反序列化
        builder.serializerByType(
            LocalDateTime::class.java, LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DATA_TIME_PATTERN))
        )
        builder.deserializerByType(
            LocalDateTime::class.java, LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DATA_TIME_PATTERN))
        )

        // 配置 LocalTime 序列化
        builder.serializerByType(
            LocalTime::class.java, LocalTimeSerializer(DateTimeFormatter.ofPattern(TIME_PATTERN))
        )
    }
}
