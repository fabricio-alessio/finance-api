package com.fasolutions.finance.application.service.company.report

import com.fasolutions.finance.application.domain.Company
import com.fasolutions.finance.application.domain.CompanyEvaluations
import com.fasolutions.finance.application.domain.CompanyReport
import com.fasolutions.finance.application.domain.CompanyReport.LimitValue
import com.fasolutions.finance.application.domain.IndicatorCode
import com.fasolutions.finance.application.domain.Field

class ReportCalculator {

    fun calculate(companies: List<Company>): List<CompanyReport> {
        val reports = companies.mapNotNull(this::calculateForCompany)
        val sumTotals = reports.sumOf { it.totalPrice ?: 0.0 }
        return reports.map {
            it.copy(
                totalPercent = (it.totalPrice ?: 0.0) / sumTotals * 100
            )
        }
    }

    private fun calculateForCompany(company: Company): CompanyReport? {
        val basics = company.indicators?.basics ?: throw IllegalArgumentException("Must have basics")
        val provents = company.indicators?.provents ?: throw IllegalArgumentException("Must have provents")
        val price = company.indicators?.price ?: throw IllegalArgumentException("Must have price")
        val indicatorHistory = company.indicators?.indicatorHistory ?: throw IllegalArgumentException("Must have indicatorHistory")
        val position = company.positionHistory?.positions?.lastOrNull()
        val prices = company.indicators?.priceHistory ?: throw IllegalArgumentException("Must have priceHistory")
        val price30DaysAgo = prices.nearAtCountDaysAgo(30)
        val price5DaysAgo = prices.nearAtCountDaysAgo(5)
        val evaluations = company.evaluations ?: CompanyEvaluations(
            observedPayout = 0.0,
            proventPredictions = emptyList()
        )
        if (price > 0) {
            return CompanyReport(
                code = company.code,
                name = basics.name,
                sector = basics.sector,
                subSector = basics.subSector,
                type = basics.type,
                dividendLastFiveYears = LimitValue(field = Field.DIVIDEND_LAST_FIVE_YEARS, value = provents.averageLastYears(5) / price * 100),
                dividendLastTwoYears = LimitValue(field = Field.DIVIDEND_LAST_TWO_YEARS, value = provents.averageLastYears(2) / price * 100),
                price = LimitValue(field = Field.PRICE, value = price),
                fairPrice = indicatorHistory.fairPrice(),
                fairPricePercent = LimitValue(field = Field.FAIR_PRICE_PERCENT, value = (price / indicatorHistory.fairPrice() * 100) - 100),
                lpa = indicatorHistory.valueByCode(IndicatorCode.LPA),
                vpa = indicatorHistory.valueByCode(IndicatorCode.VPA),
                lpaPricePercent = indicatorHistory.valueByCode(IndicatorCode.LPA) / price * 100,
                quantity = position?.totalQuantity,
                averagePrice = position?.averagePrice,
                averagePricePercent = position?.averagePrice?.let { (price / it * 100) - 100 },
                totalPrice = position?.totalQuantity?.let { it * price },
                roic = LimitValue(field = Field.ROIC, value = indicatorHistory.valueByCode(IndicatorCode.ROIC)),
                totalPercent = 0.0,
                fairAveragePrice = indicatorHistory.fairAveragePrice(),
                fairAveragePricePercent = LimitValue(field = Field.FAIR_AVERAGE_PRICE_PERCENT, value = (price / indicatorHistory.fairAveragePrice() * 100) - 100),
                valorization30Days = (price - price30DaysAgo) / price30DaysAgo * 100,
                valorization5Days = (price - price5DaysAgo) / price5DaysAgo * 100,
                observedPayout = LimitValue(field = Field.OBSERVED_PAYOUT, value = evaluations.observedPayout),
                dividendNextTreeYears = LimitValue(field = Field.DIVIDEND_NEXT_TREE_YEARS, value = evaluations.averageNextProvents() / price * 100),
                filtered = false
            )
        } else {
            return null
        }
    }
}