package com.fasolutions.finance.adapter.out.condition

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("conditions")
@TypeAlias("conditions")
data class ConditionDoc(
    @Id
    val fieldName: String,
    val minimum: Double,
    val maximum: Double,
    @CreatedDate
    val createdAt: LocalDateTime
)