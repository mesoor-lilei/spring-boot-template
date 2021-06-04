package com.example.template.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface BaseRepository<T, ID : Any> : JpaRepository<T, ID> {
    @Modifying
    @Query("update #{#entityName} set deletedAt = current_timestamp where id = ?1")
    override fun deleteById(id: ID)
}
