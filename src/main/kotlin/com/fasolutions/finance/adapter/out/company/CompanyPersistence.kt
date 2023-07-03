package com.fasolutions.finance.adapter.out.company

import com.fasolutions.finance.application.domain.Company
import com.fasolutions.finance.application.port.out.company.CompanyPersistencePort
import org.springframework.stereotype.Component

@Component
class CompanyPersistence(
    private val companyDocRepository: CompanyDocRepository,
    private val companyDocMapper: CompanyDocMapper
) : CompanyPersistencePort {

    override fun save(company: Company): String {
        companyDocMapper.forward(company).let {
            companyDocRepository.save(it)
        }
        return company.code
    }

    override fun findByCode(code: String) =
        companyDocRepository.findByCode(code = code)
            ?.let(companyDocMapper::backward)

    override fun findAll(): List<Company> {
        return companyDocRepository.findAll().toList().map(companyDocMapper::backward)
    }
}
