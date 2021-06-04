package com.example.template.pojo.rest.user

import org.hibernate.validator.constraints.Length
import java.time.LocalDate

class SaveIn(
    val id: Long?,

    @field:Length(min = 1, max = 20)
    val name: String = "",

    @field:Length(min = 6, max = 10)
    val password: String = "",

    val birthday: LocalDate?
)

class SaveOut(
    val id: Long
)
