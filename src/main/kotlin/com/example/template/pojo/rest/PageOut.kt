package com.example.template.pojo.rest

class PageOut<T>(
    val count: Long,

    val data: List<T>
)
