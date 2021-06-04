package com.example.template.pojo.rest.dict

import org.hibernate.validator.constraints.Length

class SaveIn(
    val id: Long?,

    @field:Length(min = 1, max = 20)
    val type: String = "",

    @field:Length(min = 1, max = 20)
    val key: String = "",

    @field:Length(min = 1, max = 20)
    val value: String = ""
)

class SaveOut(
    val id: Long
)
