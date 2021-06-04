package com.example.template.exception

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.NOT_FOUND

class OutException(override val message: String, val httpStatus: HttpStatus) : Exception()

val PARAMETER_ERROR = OutException("参数错误", BAD_REQUEST)

// 字典
val DICT_NOT_FOUND = OutException("字典找不到", NOT_FOUND)
val DICT_ALREADY_EXISTS = OutException("字典已存在", BAD_REQUEST)

// 用户
val USER_NOT_FOUND = OutException("用户找不到", NOT_FOUND)
val USER_ALREADY_EXISTS = OutException("用户已存在", BAD_REQUEST)
val PASSWORD_ERROR = OutException("密码错误", BAD_REQUEST)
