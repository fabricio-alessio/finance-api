package com.fasolutions.finance.adapter.`in`.error

import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "error-detail", description = "Detalhes de um erro")
internal data class ErrorDetailExternal(
    @Schema(description = "field", example = "name")
    val field: String,

    @Schema(description = "message", example = "must not be null", required = false, nullable = true)
    val message: String?
)
