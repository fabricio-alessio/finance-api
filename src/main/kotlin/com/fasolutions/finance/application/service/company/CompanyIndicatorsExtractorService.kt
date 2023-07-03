package com.fasolutions.finance.application.service.company

import com.fasolutions.finance.application.domain.Company
import com.fasolutions.finance.application.domain.CompanyIndicators
import com.fasolutions.finance.application.port.`in`.company.ExtractCompanyIndicatorsUseCase
import com.fasolutions.finance.application.port.out.company.CompanyBasicsExtractPort
import com.fasolutions.finance.application.port.out.company.CompanyIndicatorHistoryExtractPort
import com.fasolutions.finance.application.port.out.company.CompanyPersistencePort
import com.fasolutions.finance.application.port.out.company.CompanyPriceExtractPort
import com.fasolutions.finance.application.port.out.company.CompanyPriceHistoryExtractPort
import com.fasolutions.finance.application.port.out.company.CompanyProventsExtractPort
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class CompanyIndicatorsExtractorService(
    private val companyIndicatorHistoryExtractPort: CompanyIndicatorHistoryExtractPort,
    private val companyPriceExtractPort: CompanyPriceExtractPort,
    private val companyPriceHistoryExtractPort: CompanyPriceHistoryExtractPort,
    private val companyProventsExtractPort: CompanyProventsExtractPort,
    private val companyBasicsExtractPort: CompanyBasicsExtractPort,
    private val companyPersistencePort: CompanyPersistencePort
) : ExtractCompanyIndicatorsUseCase {

    private val log = KotlinLogging.logger { }

    override fun extractByCode(code: String) {
        extractIndicators(code)?.let { indicators ->
            val companyFound = companyPersistencePort.findByCode(code)

            companyFound?.let {
                it.indicators = indicators
            }

            val companyToSave = companyFound
                ?: Company(
                    code = code,
                    indicators = indicators
                )

            companyPersistencePort.save(companyToSave)
        } ?: log.warn { "No indicators of code $code was extract!" }
    }

    private fun extractIndicators(code: String) =
        companyIndicatorHistoryExtractPort.extractByCode(code)?.let {
            CompanyIndicators(
                indicatorHistory = it,
                price = companyPriceExtractPort.extractByCode(code),
                priceHistory = companyPriceHistoryExtractPort.extractByCode(code),
                provents = companyProventsExtractPort.extractByCode(code),
                basics = companyBasicsExtractPort.extractByCode(code)
            )
        }
}
