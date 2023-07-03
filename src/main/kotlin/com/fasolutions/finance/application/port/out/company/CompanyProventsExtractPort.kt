package com.fasolutions.finance.application.port.out.company

import com.fasolutions.finance.application.domain.CompanyProvents

interface CompanyProventsExtractPort {
    fun extractByCode(code: String): CompanyProvents
}
