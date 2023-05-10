package com.fasolutions.finance.application.service.company

import com.fasolutions.finance.ResourceLoader
import com.fasolutions.finance.application.domain.Company
import com.fasolutions.finance.application.domain.CompanyEvaluations
import com.fasolutions.finance.application.domain.NotFoundException
import com.fasolutions.finance.application.port.`in`.company.CreateCompanyCommand
import com.fasolutions.finance.application.port.`in`.company.CreateCompanyUseCase
import com.fasolutions.finance.application.port.`in`.company.EditCompanyEvaluationsCommand
import com.fasolutions.finance.application.port.`in`.company.EditCompanyEvaluationsUseCase
import com.fasolutions.finance.application.port.`in`.company.FindCompanyByCodeUseCase
import com.fasolutions.finance.application.port.`in`.company.FindCompanyEvaluationsByCodeUseCase
import com.fasolutions.finance.application.port.`in`.company.LoadAllCompanyEvaluationsUseCase
import com.fasolutions.finance.application.port.out.company.CompanyPersistencePort
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class CompanyService(
    private val companyPersistencePort: CompanyPersistencePort,
) : CreateCompanyUseCase, FindCompanyByCodeUseCase, EditCompanyEvaluationsUseCase,
    FindCompanyEvaluationsByCodeUseCase, LoadAllCompanyEvaluationsUseCase {

    private val log = KotlinLogging.logger { }

    override fun create(command: CreateCompanyCommand): String {
        log.info { "Receive command to create a company of code ${command.company.code}" }
        if (companyPersistencePort.findByCode(command.company.code) != null) {
            throw IllegalArgumentException("Company of code " + command.company.code + " already exists!")
        }
        return companyPersistencePort.save(company = command.company)
    }

    override fun findByCode(code: String): Company {
        log.info { "Find company by code $code" }
        return companyPersistencePort.findByCode(code)?.let { it } ?: throw NotFoundException("Company", "Code $code not found")
    }

    override fun editEvaluations(command: EditCompanyEvaluationsCommand): CompanyEvaluations {
        log.info { "Edit evaluations by code ${command.code} evaluations ${command.companyEvaluations}" }
        val companyFound = companyPersistencePort.findByCode(command.code)
        companyFound?.let {
            it.evaluations = command.companyEvaluations
        }
        val companyToSave = companyFound ?: Company(
            code = command.code,
            evaluations = command.companyEvaluations,
            indicators = null,
            positionHistory = null
        )
        companyPersistencePort.save(companyToSave)
        return command.companyEvaluations
    }

    override fun findEvaluationsByCode(code: String): CompanyEvaluations {
        val companyFound = findByCode(code)
        return companyFound.evaluations?.let { it } ?: throw NotFoundException("Company evaluations", "Code $code not found")
    }

    override fun loadAllEvaluations() {
        val evaluationsFile = ResourceLoader.getEvaluationsFile()
        val lines = ResourceLoader.loadFileAsLines(evaluationsFile)
        lines.forEach { line ->
            val values = line.split("|")
            val code = values[0]
            val payout = values[1]
            val div23 = values[2]
            val div24 = values[3]
            val div25 = values[4]
            val evaluations = CompanyEvaluations(
                observedPayout = payout.toDouble(),
                proventPredictions = listOf(
                    CompanyEvaluations.ProventPrediction(2023, div23.toDouble()),
                    CompanyEvaluations.ProventPrediction(2024, div24.toDouble()),
                    CompanyEvaluations.ProventPrediction(2025, div25.toDouble()),
                )
            )
            persistEvaluations(code, evaluations)
        }
    }

    private fun persistEvaluations(code: String, evaluations: CompanyEvaluations) {
        val companyFound = companyPersistencePort.findByCode(code)
        companyFound?.let {
            it.evaluations = evaluations
        }
        val companyToSave = companyFound ?: Company(
            code = code,
            evaluations = evaluations,
            indicators = null,
            positionHistory = null
        )
        companyPersistencePort.save(companyToSave)
    }
}
