package com.fasolutions.finance.application.port.out.condition

import com.fasolutions.finance.application.domain.condition.Condition

interface ConditionPersistencePort {
    fun save(condition: Condition): String

    fun findByFieldName(fieldName: String): Condition?

    fun findAll(): List<Condition>
}