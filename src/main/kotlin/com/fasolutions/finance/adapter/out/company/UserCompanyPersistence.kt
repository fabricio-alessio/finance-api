package com.fasolutions.finance.adapter.out.company

import com.fasolutions.finance.application.domain.UserCompany
import com.fasolutions.finance.application.port.out.company.UserCompanyPersistencePort
import org.springframework.stereotype.Component

@Component
class UserCompanyPersistence(
    private val companyDocRepository: UserCompanyDocRepository,
    private val companyDocMapper: UserCompanyDocMapper
) : UserCompanyPersistencePort {

    override fun save(company: UserCompany): String {
        companyDocMapper.forward(company).let {
            companyDocRepository.save(it)
        }
        return company.id.toString()
    }

    override fun findByUserIdAndCode(userId: Int, code: String) =
        companyDocRepository.findByUserIdAndCode(userId = userId, code = code)
            ?.let(companyDocMapper::backward)

    override fun findAllByUserId(userId: Int): List<UserCompany> =
        companyDocRepository.findAllByUserId(userId).toList().map(companyDocMapper::backward)
}
