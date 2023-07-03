package com.fasolutions.finance.adapter.`in`.report

import com.fasolutions.finance.application.port.`in`.company.CompanyReportUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import mu.KotlinLogging
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/report")
@Tag(name = "Report")
class ReportControllerV1(
    private val companyReportUseCase: CompanyReportUseCase,
    private val tickerReportExternalMapper: TickerReportExternalMapper
) {

    private val log = KotlinLogging.logger { }

    @Operation(summary = "Get all tickers calculated")
    @GetMapping(
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun allTickers(@RequestHeader userId: Int): TickersReportExternal {
        log.info { "Report tickers for userId $userId" }
        return companyReportUseCase.generateReport(userId).let(tickerReportExternalMapper::forward)
    }
}
