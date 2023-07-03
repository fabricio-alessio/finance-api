package com.fasolutions.finance.application.service.condition

import com.fasolutions.finance.application.domain.Field
import com.fasolutions.finance.application.domain.NotFoundException
import com.fasolutions.finance.application.domain.condition.Condition
import com.fasolutions.finance.application.port.`in`.condition.FindConditionByFieldNameUseCase
import com.fasolutions.finance.application.port.`in`.condition.UpdateConditionCommand
import com.fasolutions.finance.application.port.`in`.condition.UpdateConditionUseCase
import com.fasolutions.finance.application.port.out.condition.ConditionPersistencePort
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class ConditionEditionService(
    private val conditionPersistencePort: ConditionPersistencePort
) : UpdateConditionUseCase, FindConditionByFieldNameUseCase {

    private val log = KotlinLogging.logger { }

    override fun findByFieldName(fieldName: String): Condition {
        log.info { "Find condition by fieldName $fieldName" }
        return conditionPersistencePort.findByFieldName(fieldName)?.let { it } ?: throw NotFoundException("Condition", "Field $fieldName not found")
    }

    override fun updateCondition(command: UpdateConditionCommand) {
        log.info { "Update condition with command $command" }
        val conditionFound = conditionPersistencePort.findByFieldName(command.fieldName)
            ?: Condition(Field.valueOf(command.fieldName))

        conditionFound.minimum = command.minimum
        conditionFound.maximum = command.maximum
        conditionPersistencePort.save(conditionFound)
    }
}
