package com.fasolutions.finance.application.domain

data class CompanyIndicators(
    var price: Double,
    var basics: CompanyBasics,
    var indicatorHistory: CompanyIndicatorHistory,
    var priceHistory: CompanyPriceHistory,
    var provents: CompanyProvents
)
