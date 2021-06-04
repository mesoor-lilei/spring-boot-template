package com.example.template.service

import com.example.template.constant.RedisKeyConstant.DICT
import com.example.template.exception.DICT_ALREADY_EXISTS
import com.example.template.exception.DICT_NOT_FOUND
import com.example.template.pojo.entity.Dict
import com.example.template.pojo.rest.PageOut
import com.example.template.pojo.rest.dict.ListIn
import com.example.template.pojo.rest.dict.ListOut
import com.example.template.pojo.rest.dict.SaveIn
import com.example.template.pojo.rest.dict.SaveOut
import com.example.template.repository.DictRepository
import com.example.template.util.RedisUtil
import org.springframework.data.domain.Example
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit
import javax.servlet.http.HttpServletResponse

@Service
class DictService(
    private val response: HttpServletResponse,

    private val repository: DictRepository
) : BaseService() {
    /** 保存 */
    fun save(request: SaveIn) = with(request) {
        fun save(): Dict {
            return transaction {
                repository.save(
                    Dict(
                        id = id,
                        type = type,
                        key = key,
                        value = value
                    )
                ).also {
                    // 删除缓存
                    RedisUtil.delete("$DICT:$type:$key")
                }
            }
        }

        if (id == null) {
            if (repository.countByTypeAndKey(type, key) > 0) throw DICT_ALREADY_EXISTS
            response.status = HttpStatus.CREATED.value()
            SaveOut(save().id!!)
        } else {
            response.status = HttpStatus.OK.value()
            save()
            null
        }
    }

    /** 删除 */
    fun deleteById(id: Long) {
        val entity = repository.findById(id).orElseThrow { DICT_NOT_FOUND }
        transaction {
            repository.deleteById(id)
            with(entity) {
                // 删除缓存
                RedisUtil.delete("$DICT:$type:$key")
            }
        }
    }

    /** 分页查询 */
    fun findPage(request: ListIn) = with(request) {
        val example = Example.of(Dict(value = value))
        val page = repository.findAll(example, PageRequest.of(page, size))
        PageOut(
            page.totalElements,
            page.content.map {
                ListOut(it.id!!, it.type!!, it.key!!, it.value!!)
            }
        )
    }

    /** 根据 type 获取字典 List */
    fun valueList(type: String) = repository.findSelectList(type)

    /** 根据 type 获取字典 Map */
    fun valueMap(type: String) = valueList(type)
        .associate { it.key to it.value }

    /** 根据 type 和 key 获取 value */
    fun value(type: String, key: String): String {
        val fullKey = "$DICT:$type:$key"
        return RedisUtil.get(fullKey) ?: run {
            val value = repository.findValue(type, key) ?: throw DICT_NOT_FOUND
            // 缓存 1 分钟
            value.also { RedisUtil.set(fullKey, it, 1L, TimeUnit.MINUTES) }
        }
    }
}
