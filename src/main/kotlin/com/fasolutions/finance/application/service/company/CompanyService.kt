package com.fasolutions.finance.application.service.company

import com.fasolutions.finance.application.domain.CompanyEvaluations
import com.fasolutions.finance.application.domain.NotFoundException
import com.fasolutions.finance.application.domain.UserCompany
import com.fasolutions.finance.application.port.`in`.company.CreateCompanyCommand
import com.fasolutions.finance.application.port.`in`.company.CreateCompanyUseCase
import com.fasolutions.finance.application.port.`in`.company.EditCompanyEvaluationsCommand
import com.fasolutions.finance.application.port.`in`.company.EditCompanyEvaluationsUseCase
import com.fasolutions.finance.application.port.`in`.company.FindCompanyByUserIdAndCodeUseCase
import com.fasolutions.finance.application.port.`in`.company.FindCompanyEvaluationsByCodeUseCase
import com.fasolutions.finance.application.port.`in`.company.LoadAllCompanyEvaluationsUseCase
import com.fasolutions.finance.application.port.out.company.CompanyPersistencePort
import com.fasolutions.finance.application.port.out.company.UserCompanyPersistencePort
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class CompanyService(
    private val companyPersistencePort: CompanyPersistencePort,
    private val userCompanyPersistencePort: UserCompanyPersistencePort
) : CreateCompanyUseCase,
    FindCompanyByUserIdAndCodeUseCase,
    EditCompanyEvaluationsUseCase,
    FindCompanyEvaluationsByCodeUseCase,
    LoadAllCompanyEvaluationsUseCase {

    private val log = KotlinLogging.logger { }

    override fun create(command: CreateCompanyCommand): String {
        log.info { "Receive command to create a company of code ${command.company.code}" }
        if (companyPersistencePort.findByCode(command.company.code) != null) {
            throw IllegalArgumentException("Company of code " + command.company.code + " already exists!")
        }
        return companyPersistencePort.save(company = command.company)
    }

    override fun findByUserIdAndCode(userId: Int, code: String): UserCompany {
        log.info { "Find company by userId $userId and code $code" }
        return userCompanyPersistencePort.findByUserIdAndCode(userId, code)?.let { it } ?: throw NotFoundException("UserCompany", "UserId $userId and code $code not found")
    }

    override fun editEvaluations(command: EditCompanyEvaluationsCommand): CompanyEvaluations {
        log.info { "Edit evaluations by code ${command.code} userId ${command.userId} evaluations ${command.companyEvaluations}" }
        val companyFound = userCompanyPersistencePort.findByUserIdAndCode(command.userId, command.code)
        companyFound?.let {
            it.evaluations = command.companyEvaluations
        }
        val companyToSave = companyFound ?: UserCompany(
            id = UUID.randomUUID(),
            userId = command.userId,
            code = command.code,
            evaluations = command.companyEvaluations,
            positionHistory = null
        )
        userCompanyPersistencePort.save(companyToSave)
        return command.companyEvaluations
    }

    override fun findEvaluationsByUserIdCode(userId: Int, code: String): CompanyEvaluations {
        val companyFound = findByUserIdAndCode(userId, code)
        return companyFound.evaluations?.let { it } ?: throw NotFoundException("User company evaluations", "UserId $userId and code $code not found")
    }

    override fun loadAllEvaluations() {
    }
}
