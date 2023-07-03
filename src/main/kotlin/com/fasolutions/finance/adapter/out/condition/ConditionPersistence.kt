package com.fasolutions.finance.adapter.out.condition

import com.fasolutions.finance.application.domain.condition.Condition
import com.fasolutions.finance.application.port.out.condition.ConditionPersistencePort
import org.springframework.stereotype.Component

@Component
class ConditionPersistence(
    private val conditionDocRepository: ConditionDocRepository,
    private val conditionDocMapper: ConditionDocMapper
) : ConditionPersistencePort {
    override fun save(condition: Condition): String {
        conditionDocMapper.forward(condition).let {
            conditionDocRepository.save(it)
        }
        return condition.field.name
    }

    override fun findByFieldName(fieldName: String) =
        conditionDocRepository.findByFieldName(fieldName)
            ?.let(conditionDocMapper::backward)

    override fun findAll(): List<Condition> {
        return conditionDocRepository.findAll().toList().map(conditionDocMapper::backward)
    }
}
