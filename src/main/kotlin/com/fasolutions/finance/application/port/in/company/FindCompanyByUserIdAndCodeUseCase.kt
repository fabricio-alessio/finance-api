package com.fasolutions.finance.application.port.`in`.company

import com.fasolutions.finance.application.domain.UserCompany

interface FindCompanyByUserIdAndCodeUseCase {

    fun findByUserIdAndCode(userId: Int, code: String): UserCompany
}
