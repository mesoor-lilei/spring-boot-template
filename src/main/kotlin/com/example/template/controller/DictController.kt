package com.example.template.controller

import com.example.template.pojo.rest.dict.ListIn
import com.example.template.pojo.rest.dict.SaveIn
import com.example.template.service.DictService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("dict")
class DictController(
    private val service: DictService
) {
    @PostMapping
    fun save(@Valid @RequestBody `in`: SaveIn) = service.save(`in`)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = service.deleteById(id)

    @PostMapping("page")
    fun page(@Valid @RequestBody `in`: ListIn) = service.findPage(`in`)

    @GetMapping("type/{type}/key/{key}")
    fun value(@PathVariable type: String, @PathVariable key: String) = service.value(type, key)

    @GetMapping("type/{type}/list")
    fun valueList(@PathVariable type: String) = service.valueList(type)

    @GetMapping("type/{type}/map")
    fun valueMap(@PathVariable type: String) = service.valueMap(type)
}
