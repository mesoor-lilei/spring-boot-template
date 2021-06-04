package com.example.template.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.support.TransactionTemplate

abstract class BaseService {
    @Autowired
    private lateinit var transactionTemplate: TransactionTemplate

    /**
     * 调用 execute 函数始终返回可空类型，使用 `as` 强制转换后始终返回不可空类型。
     * 使用闭包 + `as` 解决以上问题。
     *
     * JpaRepository 自带函数默认已设置事务，单独使用 JpaRepository 自带函数时不需要使用此函数。
     */
    @Suppress("UNCHECKED_CAST")
    protected fun <T> transaction(block: () -> T) = transactionTemplate.execute { block() } as T
}
