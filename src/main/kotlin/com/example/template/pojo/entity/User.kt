package com.example.template.pojo.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.Where
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Where(clause = "deleted_at is null")
@Table(name = "\"user\"")
@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(updatable = false)
    val createdBy: Long? = null,

    @Column(updatable = false)
    @CreationTimestamp
    val createdAt: LocalDateTime? = null,

    val updatedBy: Long? = null,

    @UpdateTimestamp
    val updatedAt: LocalDateTime? = null,

    @Column(insertable = false)
    val deletedAt: LocalDateTime? = null,

    val name: String? = null,

    val password: String? = null,

    /** 密码加密盐值 */
    val salt: String? = null,

    val birthday: LocalDate? = null
)
