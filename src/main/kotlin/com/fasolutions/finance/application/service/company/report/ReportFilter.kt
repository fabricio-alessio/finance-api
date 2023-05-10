package com.fasolutions.finance.application.service.company.report

import com.fasolutions.finance.application.domain.CompanyReport
import com.fasolutions.finance.application.domain.Field
import com.fasolutions.finance.application.domain.condition.Condition

class ReportFilter(
    private val conditions: List<Condition>
) {
    fun calculateFiltered(reports: List<CompanyReport>) =
        reports.map {
            it.copy(
                observedPayout = filtered(it.observedPayout),
                price = filtered(it.price),
                roic = filteredNotZero(it.roic),
                fairPricePercent = filtered(it.fairPricePercent),
                fairAveragePricePercent = filtered(it.fairAveragePricePercent),
                dividendLastFiveYears = filtered(it.dividendLastFiveYears),
                dividendLastTwoYears = filtered(it.dividendLastTwoYears),
                dividendNextTreeYears = filtered(it.dividendNextTreeYears),
                filtered = calculate(it)
            )
        }

    private fun calculate(report: CompanyReport): Boolean {

        if (!calculateValue(report.dividendLastFiveYears)) return false;
        if (!calculateValue(report.observedPayout)) return false;
        if (!calculateValue(report.price)) return false;
        if (!calculateValue(report.roic) && report.roic.value != 0.0) return false;
        if (!calculateValue(report.fairPricePercent)) return false;
        if (!calculateValue(report.fairAveragePricePercent)) return false;
        if (!calculateValue(report.dividendLastFiveYears)) return false;
        if (!calculateValue(report.dividendLastTwoYears)) return false;
        if (!calculateValue(report.dividendNextTreeYears)) return false;

        return true
    }

    private fun filteredNotZero(limitValue: CompanyReport.LimitValue) =
        CompanyReport.LimitValue(
            field = limitValue.field,
            value = limitValue.value,
            out = !calculateValue(limitValue) && limitValue.value != 0.0
        )

    private fun filtered(limitValue: CompanyReport.LimitValue) =
        CompanyReport.LimitValue(
            field = limitValue.field,
            value = limitValue.value,
            out = !calculateValue(limitValue)
        )

    private fun calculateValue(limitValue: CompanyReport.LimitValue): Boolean {
        return findFilterByField(limitValue.field).filter(limitValue.value)
    }

    private fun findFilterByField(field: Field): Condition {
        return conditions.find { it.field == field }!!
    }
}
