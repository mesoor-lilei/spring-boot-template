package com.example.template.config

import org.hibernate.validator.HibernateValidator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.validation.Validation
import javax.validation.Validator

/** 参数校验配置 */
@Configuration
class ValidatorConfig {
    @Bean
    fun validator(): Validator =
        Validation.byProvider(HibernateValidator::class.java)
            .configure()
            // 普通模式：校验完所有的属性并返回所有的验证失败信息
            // 快速失败模式：有一个验证失败立即返回失败信息
            .addProperty("hibernate.validator.fail_fast", "true").buildValidatorFactory().validator
}
