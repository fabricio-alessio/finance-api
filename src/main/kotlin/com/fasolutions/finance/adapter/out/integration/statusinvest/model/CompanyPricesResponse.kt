package com.fasolutions.finance.adapter.out.integration.statusinvest.model

data class CompanyPricesResponse(
    var code: String?,
    val prices: List<Price>
)
