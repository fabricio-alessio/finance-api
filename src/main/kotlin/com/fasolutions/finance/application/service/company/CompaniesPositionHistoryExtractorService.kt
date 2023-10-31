package com.fasolutions.finance.application.service.company

import com.fasolutions.finance.application.domain.CompanyPositionHistory
import com.fasolutions.finance.application.domain.UserCompany
import com.fasolutions.finance.application.port.`in`.company.ExtractCompaniesPositionHistoryUseCase
import com.fasolutions.finance.application.port.out.company.CompaniesPositionHistoryExtractPort
import com.fasolutions.finance.application.port.out.company.UserCompanyPersistencePort
import mu.KotlinLogging
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class CompaniesPositionHistoryExtractorService(
    private val historyExtractPort: CompaniesPositionHistoryExtractPort,
    private val companyPersistencePort: UserCompanyPersistencePort
) : ExtractCompaniesPositionHistoryUseCase {

    private val log = KotlinLogging.logger { }
    override fun extractWithBearer(userId: Int, bearer: String) {
        val companiesPositions = historyExtractPort.extract(bearer)
        companiesPositions.positions.forEach { (code, position) ->
            persistCompanyPositionHistory(userId, code, position)
        }
    }

    private fun persistCompanyPositionHistory(userId: Int, code: String, position: CompanyPositionHistory.Position) {
        log.info { "Extract company position code $code position $position" }
        val companyFound = companyPersistencePort.findByUserIdAndCode(userId, code)
        companyFound?.let {
            if (it.positionHistory != null) {
                it.positionHistory!!.changePositionByDate(position)
            } else {
                it.positionHistory = CompanyPositionHistory(positions = mutableListOf(position))
            }
        }
        val companyToSave = companyFound ?: UserCompany(
            id = UUID.randomUUID(),
            userId = userId,
            code = code,
            positionHistory = CompanyPositionHistory(positions = mutableListOf(position)),
            evaluations = null
        )
        companyPersistencePort.save(companyToSave)
    }
}
