package com.fasolutions.finance.adapter.`in`.condition

import com.fasolutions.finance.application.port.`in`.condition.FindConditionByFieldNameUseCase
import com.fasolutions.finance.application.port.`in`.condition.UpdateConditionCommand
import com.fasolutions.finance.application.port.`in`.condition.UpdateConditionUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/conditions")
@Tag(name = "Conditions")
class ConditionControllerV1(
    private val updateConditionUseCase: UpdateConditionUseCase,
    private val findConditionByFieldNameUseCase: FindConditionByFieldNameUseCase
) {
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Edit condition by field name")
    @PutMapping(
        value = ["/{fieldName}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun editCondition(@PathVariable fieldName: String, @RequestBody conditionExternal: ConditionExternal) {
        updateConditionUseCase.updateCondition(
            UpdateConditionCommand(
                fieldName = fieldName,
                minimum = conditionExternal.minimum,
                maximum = conditionExternal.maximum
            )
        )
    }

    @Operation(summary = "Find condition by field name")
    @GetMapping(
        value = ["/{fieldName}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getByFieldName(@PathVariable fieldName: String): ConditionExternal {
        val condition = findConditionByFieldNameUseCase.findByFieldName(fieldName)
        return ConditionExternal(
            minimum = condition.minimum,
            maximum = condition.maximum
        )
    }
}