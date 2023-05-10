package com.fasolutions.finance.adapter.`in`.error

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "error-message", description = "Mensagem de erro")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
internal data class ErrorMessageExternal(
    @Schema(description = "Código, pode ser útil para i18n", example = "error.unexpected")
    val code: String,

    @Schema(description = "Resumo", example = "Internal server error")
    val summary: String?,

    @Schema(description = "Detalhes", required = false, nullable = true, defaultValue = "[]")
    val details: List<ErrorDetailExternal>
)
