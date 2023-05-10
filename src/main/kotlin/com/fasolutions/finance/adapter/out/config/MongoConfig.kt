package com.fasolutions.finance.adapter.out.config

import com.fasolutions.finance.adapter.out.company.CompanyDocRepository
import com.fasolutions.finance.adapter.out.condition.ConditionDocRepository
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@EnableMongoAuditing
@EnableMongoRepositories(basePackageClasses = [CompanyDocRepository::class, ConditionDocRepository::class])
@Configuration
class MongoConfig
