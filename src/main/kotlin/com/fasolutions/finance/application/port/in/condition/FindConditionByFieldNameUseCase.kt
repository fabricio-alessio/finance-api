package com.fasolutions.finance.application.port.`in`.condition

import com.fasolutions.finance.application.domain.condition.Condition

interface FindConditionByFieldNameUseCase {
    fun findByFieldName(fieldName: String): Condition
}
