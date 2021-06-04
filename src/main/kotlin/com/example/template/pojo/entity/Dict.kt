package com.example.template.pojo.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.*

@Where(clause = "deleted_at is null")
@Table(
    uniqueConstraints = [
        UniqueConstraint(
            name = "type_key_uindex",
            columnNames = ["type", "key"]
        )
    ]
)
@Entity
class Dict(
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

    val type: String? = null,

    val key: String? = null,

    val value: String? = null
)
