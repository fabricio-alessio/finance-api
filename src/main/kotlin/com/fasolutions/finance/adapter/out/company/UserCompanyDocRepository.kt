package com.fasolutions.finance.adapter.out.company

import org.bson.types.ObjectId
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserCompanyDocRepository : CrudRepository<UserCompanyDoc, ObjectId> {

    fun findByUserIdAndCode(userId: Int, code: String): UserCompanyDoc?

    fun findAllByUserId(userId: Int): List<UserCompanyDoc>
}
