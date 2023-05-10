package com.fasolutions.finance.application.port.out.company

interface CompanyPriceExtractPort {
    fun extractByCode(code: String): Double
}