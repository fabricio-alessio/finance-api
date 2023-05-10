package com.fasolutions.finance.application.port.out.company

import com.fasolutions.finance.application.domain.CompanyBasics

interface CompanyBasicsExtractPort {
    fun extractByCode(code: String): CompanyBasics
}