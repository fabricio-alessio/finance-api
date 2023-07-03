package com.fasolutions.finance.adapter.`in`.report

data class TickersReportExternal(
    val tickers: List<TickerReport>
) {
    data class TickerReport(
        val code: String,
        val name: String,
        val sector: String,
        val div5: LimitValue,
        val div2: LimitValue,
        val divF: LimitValue,
        val price: LimitValue,
        val payout: LimitValue,
        val quantity: Int,
        val total: LimitValue,
        val avgPrice: LimitValue,
        val percAvgPrice: LimitValue,
        val percFairPrice: LimitValue,
        val percFairPriceAvg: LimitValue,
        val val30Days: LimitValue,
        val val5Days: LimitValue,
        val roic: LimitValue,
        val percTotal: LimitValue,
        val filtered: Boolean,
        val pl: LimitValue,
        val pvp: LimitValue
    )

    data class LimitValue(
        val value: Double,
        val out: Boolean
    )
}
