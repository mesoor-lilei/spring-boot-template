package com.example.template.repository

import com.example.template.pojo.entity.Dict
import com.example.template.pojo.rest.dict.SelectOut
import org.springframework.data.jpa.repository.Query

interface DictRepository : BaseRepository<Dict, Long> {
    /** 根据 type 获取字典 */
    @Query(
        """
        select
          new com.example.template.pojo.rest.dict.SelectOut(key, value)
        from Dict
        where type = ?1
        """
    )
    fun findSelectList(type: String): List<SelectOut>

    @Query("select value from Dict where type = ?1 and key = ?2")
    fun findValue(type: String, key: String): String?

    fun countByTypeAndKey(type: String, key: String): Long
}
