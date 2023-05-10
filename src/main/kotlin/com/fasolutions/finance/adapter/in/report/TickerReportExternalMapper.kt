package com.fasolutions.finance.adapter.`in`.report

import com.fasolutions.finance.adapter.`in`.report.TickersReportExternal.LimitValue
import com.fasolutions.finance.application.domain.CompanyReport
import org.springframework.stereotype.Component

@Component
class TickerReportExternalMapper {
    fun forward(source: List<CompanyReport>) =
        TickersReportExternal(
            tickers = source.map(this::forwardTickerReport)
        )

    private fun forwardTickerReport(source: CompanyReport) =
        TickersReportExternal.TickerReport(
            code = source.code,
            name = source.name,
            sector = source.sector,
            div5 = forwardLimitValue(source.dividendLastFiveYears),
            div2 = forwardLimitValue(source.dividendLastTwoYears),
            divF = forwardLimitValue(source.dividendNextTreeYears),
            price = forwardLimitValue(source.price),
            payout = forwardLimitValue(source.observedPayout),
            quantity = source.quantity ?: 0,
            total = forwardLimitValue(source.totalPrice ?: 0.0),
            avgPrice = forwardLimitValue(source.averagePrice ?: 0.0),
            percAvgPrice = forwardLimitValue(source.averagePricePercent ?: 0.0),
            percFairPrice = forwardLimitValue(source.fairPricePercent),
            percFairPriceAvg = forwardLimitValue(source.fairAveragePricePercent),
            val30Days = forwardLimitValue(source.valorization30Days),
            val5Days = forwardLimitValue(source.valorization5Days),
            roic = forwardLimitValue(source.roic),
            percTotal = forwardLimitValue(source.totalPercent),
            filtered = source.filtered
        )

    private fun forwardLimitValue(source: CompanyReport.LimitValue) =
        LimitValue(
            value = source.value,
            out = source.out
        )

    private fun forwardLimitValue(source: Double) =
        LimitValue(
            value = source,
            out = false
        )
}