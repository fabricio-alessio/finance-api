package com.fasolutions.finance.application.domain.condition

import com.fasolutions.finance.application.domain.Field

data class Condition(
    val field: Field,
    var minimum: Double = 0.0,
    var maximum: Double = 0.0
) {
    fun filter(value: Double) = value in minimum..maximum
}
