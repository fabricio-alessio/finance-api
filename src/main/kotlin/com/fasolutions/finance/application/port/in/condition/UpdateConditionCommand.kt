package com.fasolutions.finance.application.port.`in`.condition

data class UpdateConditionCommand(
    val fieldName: String,
    val minimum: Double,
    val maximum: Double
)
