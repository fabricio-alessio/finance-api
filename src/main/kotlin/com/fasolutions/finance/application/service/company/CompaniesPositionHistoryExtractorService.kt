package com.fasolutions.finance.application.service.company

import com.fasolutions.finance.application.domain.Company
import com.fasolutions.finance.application.domain.CompanyPositionHistory
import com.fasolutions.finance.application.port.`in`.company.ExtractCompaniesPositionHistoryUseCase
import com.fasolutions.finance.application.port.out.company.CompaniesPositionHistoryExtractPort
import com.fasolutions.finance.application.port.out.company.CompanyPersistencePort
import org.springframework.stereotype.Component

@Component
class CompaniesPositionHistoryExtractorService(
    private val historyExtractPort: CompaniesPositionHistoryExtractPort,
    private val companyPersistencePort: CompanyPersistencePort
) : ExtractCompaniesPositionHistoryUseCase {
    override fun extractWithBearer(bearer: String) {
        val companiesPositions = historyExtractPort.extract(bearer)
        companiesPositions.positions.forEach { (code, position) ->
            persistCompanyPositionHistory(code, position)
        }
    }

    private fun persistCompanyPositionHistory(code: String, position: CompanyPositionHistory.Position) {
        val companyFound = companyPersistencePort.findByCode(code)
        companyFound?.let {
            it.positionHistory?.changePositionByDate(position)
        }
        val companyToSave = companyFound ?: Company(
            code = code,
            positionHistory = CompanyPositionHistory(positions = mutableListOf(position)),
            indicators = null,
            evaluations = null
        )
        companyPersistencePort.save(companyToSave)
    }
}