package com.fasolutions.finance.adapter.out.company

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CompanyDocRepository : CrudRepository<CompanyDoc, String> {

    fun findByCode(code: String): CompanyDoc?
}
