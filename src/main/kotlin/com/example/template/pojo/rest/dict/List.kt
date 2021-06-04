package com.example.template.pojo.rest.dict

import org.hibernate.validator.constraints.Length
import org.hibernate.validator.constraints.Range
import javax.validation.constraints.Min

class ListIn(
    @field:Min(value = 1)
    val page: Int = 0,

    @field:Range(min = 1, max = 200)
    val size: Int = 0,

    @field:Length(min = 1, max = 20)
    val value: String = ""
)

class ListOut(
    val id: Long,

    val type: String,

    val key: String,

    val value: String
)
