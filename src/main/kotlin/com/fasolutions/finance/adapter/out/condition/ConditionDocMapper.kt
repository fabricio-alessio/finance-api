package com.fasolutions.finance.adapter.out.condition

import com.fasolutions.finance.application.domain.Field
import com.fasolutions.finance.application.domain.condition.Condition
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ConditionDocMapper {
    fun backward(source: ConditionDoc) =
        Condition(
            field = Field.valueOf(source.fieldName),
            minimum = source.minimum,
            maximum = source.maximum
        )

    fun forward(source: Condition) =
        ConditionDoc(
            fieldName = source.field.name,
            minimum = source.minimum,
            maximum = source.maximum,
            createdAt = LocalDateTime.now()
        )
}
