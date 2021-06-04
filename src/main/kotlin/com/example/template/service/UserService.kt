package com.example.template.service

import com.example.template.exception.PASSWORD_ERROR
import com.example.template.exception.USER_ALREADY_EXISTS
import com.example.template.exception.USER_NOT_FOUND
import com.example.template.pojo.entity.User
import com.example.template.pojo.rest.user.GetOut
import com.example.template.pojo.rest.user.LoginIn
import com.example.template.pojo.rest.user.SaveIn
import com.example.template.pojo.rest.user.SaveOut
import com.example.template.repository.UserRepository
import org.apache.commons.codec.digest.DigestUtils.sha256Hex
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletResponse

@Service
class UserService(
    private val response: HttpServletResponse,

    private val repository: UserRepository
) : BaseService() {
    fun save(`in`: SaveIn) = with(`in`) {
        fun save(): User {
            val salt = RandomStringUtils.random(4)
            return repository.save(
                User(
                    id = id,
                    name = name,
                    password = sha256Hex(password + salt),
                    salt = salt,
                    birthday = birthday
                )
            )
        }

        if (id == null) {
            if (repository.countByName(name) > 0) throw USER_ALREADY_EXISTS
            response.status = HttpStatus.CREATED.value()
            SaveOut(save().id!!)
        } else {
            response.status = HttpStatus.OK.value()
            save()
            null
        }
    }

    fun findById(id: Long) =
        with(
            repository.findById(id).orElseThrow { USER_NOT_FOUND }
        ) {
            GetOut(name!!, birthday)
        }

    fun login(`in`: LoginIn): GetOut = with(`in`) {
        val entity = repository.findByName(name) ?: throw USER_NOT_FOUND
        if (entity.password == sha256Hex(password + entity.salt)) {
            GetOut(name, entity.birthday)
        }
        throw PASSWORD_ERROR
    }
}
