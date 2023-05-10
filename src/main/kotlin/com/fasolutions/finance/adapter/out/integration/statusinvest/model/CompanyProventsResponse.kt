package com.fasolutions.finance.adapter.out.integration.statusinvest.model

data class CompanyProventsResponse(
    var code: String?,
    val assetEarningsYearlyModels: List<AssetEarningsYearlyModel>
)
