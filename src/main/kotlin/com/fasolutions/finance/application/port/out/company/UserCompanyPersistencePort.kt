package com.fasolutions.finance.application.port.out.company

import com.fasolutions.finance.application.domain.UserCompany

interface UserCompanyPersistencePort {

    fun save(company: UserCompany): String

    fun findByUserIdAndCode(userId: Int, code: String): UserCompany?

    fun findAllByUserId(userId: Int): List<UserCompany>
}
