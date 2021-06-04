package com.example.template.controller

import com.example.template.pojo.rest.user.LoginIn
import com.example.template.pojo.rest.user.SaveIn
import com.example.template.service.UserService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("user")
class UserController(
    private val service: UserService
) {
    @PostMapping
    fun save(@Valid @RequestBody `in`: SaveIn) = service.save(`in`)

    @GetMapping("{id}")
    fun findById(@PathVariable id: Long) = service.findById(id)

    @PostMapping("login")
    fun login(@Valid @RequestBody `in`: LoginIn) = service.login(`in`)
}
