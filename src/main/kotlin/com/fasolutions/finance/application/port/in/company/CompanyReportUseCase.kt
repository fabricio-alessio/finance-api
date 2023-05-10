package com.fasolutions.finance.application.port.`in`.company

import com.fasolutions.finance.application.domain.CompanyReport

interface CompanyReportUseCase {
    fun generateReport(): List<CompanyReport>
}