package com.fasolutions.finance.application.port.out.company

import com.fasolutions.finance.application.domain.Company

interface CompanyPersistencePort {

    fun save(company: Company): String

    fun findByCode(code: String): Company?

    fun findAll(): List<Company>
}
