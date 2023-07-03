package com.fasolutions.finance.application.domain

data class CompanyReport(
    val code: String,
    val sector: String,
    val subSector: String,
    val type: String,
    val name: String,
    val dividendLastFiveYears: LimitValue,
    val dividendLastTwoYears: LimitValue,
    val fairPricePercent: LimitValue,
    val price: LimitValue,
    val fairPrice: Double,
    val lpa: Double,
    val vpa: Double,
    val lpaPricePercent: Double,
    val quantity: Int?,
    val averagePrice: Double?,
    val averagePricePercent: Double?,
    val totalPrice: Double?,
    val roic: LimitValue,
    val totalPercent: Double,
    val fairAveragePrice: Double,
    val fairAveragePricePercent: LimitValue,
    val valorization30Days: Double,
    val valorization5Days: Double,
    val observedPayout: LimitValue,
    val dividendNextTreeYears: LimitValue,
    val filtered: Boolean,
    val pl: Double,
    val pvp: Double
) {
    data class LimitValue(
        val field: Field,
        val value: Double,
        val out: Boolean = false
    )
}
