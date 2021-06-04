package com.example.template.repository

import com.example.template.pojo.entity.User

interface UserRepository : BaseRepository<User, Long> {
    fun findByName(name: String): User?

    fun countByName(name: String): Long
}
