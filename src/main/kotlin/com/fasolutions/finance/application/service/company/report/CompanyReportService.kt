package com.fasolutions.finance.application.service.company.report

import com.fasolutions.finance.application.domain.Company
import com.fasolutions.finance.application.domain.CompanyReport
import com.fasolutions.finance.application.domain.Field
import com.fasolutions.finance.application.domain.condition.Condition
import com.fasolutions.finance.application.port.`in`.company.CompanyReportUseCase
import com.fasolutions.finance.application.port.out.company.CompanyPersistencePort
import com.fasolutions.finance.application.port.out.company.UserCompanyPersistencePort
import com.fasolutions.finance.application.port.out.condition.ConditionPersistencePort
import com.fasolutions.finance.application.port.out.indicator.GetIndicatorCodesPort
import org.springframework.stereotype.Service
import java.util.stream.Collector

@Service
class CompanyReportService(
    private val companyPersistencePort: CompanyPersistencePort,
    private val userCompanyPersistencePort: UserCompanyPersistencePort,
    private val conditionPersistencePort: ConditionPersistencePort,
    private val getIndicatorCodesPort: GetIndicatorCodesPort
) : CompanyReportUseCase {
    override fun generateReport(userId: Int): List<CompanyReport> {
        val companies = companyPersistencePort.findAll()
        val indicatorCodes = getIndicatorCodesPort.findAll()
        val companiesOfIndicators = companies.filter{ indicatorCodes.contains(it.code) }
        val userCompanies = userCompanyPersistencePort.findAllByUserId(userId)
        val reportCalculator = ReportCalculator()
        val companyReports = reportCalculator.calculate(companiesOfIndicators, userCompanies)
        val reportFilter = ReportFilter(getConditions())
        return reportFilter.calculateFiltered(companyReports)
    }

    private fun getConditions() = conditionPersistencePort.findAll().ifEmpty { getRangeFilters() }

    private fun getRangeFilters() = listOf(
        Condition(field = Field.OBSERVED_PAYOUT, minimum = 0.0, maximum = 63.0),
        Condition(field = Field.PRICE, minimum = 0.0, maximum = 100.0),
        Condition(field = Field.FAIR_PRICE_PERCENT, minimum = -90.0, maximum = 0.0),
        Condition(field = Field.FAIR_AVERAGE_PRICE_PERCENT, minimum = -90.0, maximum = -14.0),
        Condition(field = Field.ROIC, minimum = 10.0, maximum = 50.0),
        Condition(field = Field.DIVIDEND_LAST_FIVE_YEARS, minimum = 5.0, maximum = 30.0),
        Condition(field = Field.DIVIDEND_LAST_TWO_YEARS, minimum = 4.5, maximum = 30.0),
        Condition(field = Field.DIVIDEND_NEXT_TREE_YEARS, minimum = 6.0, maximum = 30.0)
    )

    private fun filterCompaniesInsideIndicators(companies: List<Company>, indicatorCodes: List<String>) {
        companies.filter{ indicatorCodes.contains(it.code) }
    }
}
