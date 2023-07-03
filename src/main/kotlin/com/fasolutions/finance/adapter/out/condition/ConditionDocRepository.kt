package com.fasolutions.finance.adapter.out.condition

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ConditionDocRepository : CrudRepository<ConditionDoc, String> {

    fun findByFieldName(fieldName: String): ConditionDoc?
}
