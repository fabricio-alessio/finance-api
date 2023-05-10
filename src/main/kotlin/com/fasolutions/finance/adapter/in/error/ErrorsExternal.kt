package com.fasolutions.finance.adapter.`in`.error

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "errors", description = "Erros")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
internal data class ErrorsExternal(
    @Schema(description = "Mensagens", readOnly = true)
    val messages: List<ErrorMessageExternal>
) {
    constructor(errorMessage: ErrorMessageExternal) : this(listOf(errorMessage))

    constructor(
        errorCode: String,
        errorSummary: String?,
        details: List<ErrorDetailExternal> = emptyList()
    ) : this(
        ErrorMessageExternal(errorCode, errorSummary, details)
    )

    companion object {
        const val VALIDATION_ERROR_CODE = "error.validation"
        const val VALIDATION_ERROR_SUMMARY = "Validation error, check details"
    }
}
