package com.fasolutions.finance.application.port.`in`.company

import com.fasolutions.finance.application.domain.Company

data class CreateCompanyCommand(
    val company: Company
)
