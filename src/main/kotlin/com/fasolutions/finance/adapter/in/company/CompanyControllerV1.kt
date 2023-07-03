package com.fasolutions.finance.adapter.`in`.company

import com.fasolutions.finance.application.port.`in`.company.EditCompanyEvaluationsCommand
import com.fasolutions.finance.application.port.`in`.company.EditCompanyEvaluationsUseCase
import com.fasolutions.finance.application.port.`in`.company.FindCompanyEvaluationsByCodeUseCase
import com.fasolutions.finance.application.port.`in`.company.LoadAllCompanyEvaluationsUseCase
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/companies")
@Tag(name = "Companies")
class CompanyControllerV1(
    private val editCompanyEvaluationsUseCase: EditCompanyEvaluationsUseCase,
    private val findCompanyEvaluationsByCodeUseCase: FindCompanyEvaluationsByCodeUseCase,
    private val companyEvaluationsExternalMapper: CompanyEvaluationsExternalMapper,
    private val loadAllCompanyEvaluationsUseCase: LoadAllCompanyEvaluationsUseCase
) {
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Edit company evaluations")
    @PutMapping(
        value = ["/{code}/evaluations"],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun editEvaluations(
        @PathVariable code: String,
        @RequestBody companyEvaluationsExternal: CompanyEvaluationsExternal,
        @RequestHeader userId: Int
    ) {
        companyEvaluationsExternalMapper.backward(companyEvaluationsExternal).let {
            editCompanyEvaluationsUseCase.editEvaluations(
                EditCompanyEvaluationsCommand(
                    code = code,
                    userId = userId,
                    companyEvaluations = it
                )
            )
        }
    }

    @Operation(summary = "Find company evaluations by code")
    @GetMapping(
        value = ["/{code}/evaluations"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getEvaluationsByCode(
        @PathVariable code: String,
        @RequestHeader userId: Int
    ): CompanyEvaluationsExternal =
        findCompanyEvaluationsByCodeUseCase.findEvaluationsByUserIdCode(userId, code).let {
            companyEvaluationsExternalMapper.forward(it)
        }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Load all evaluations")
    @PostMapping(
        value = ["/evaluations"]
    )
    fun loadAllEvaluations() {
        loadAllCompanyEvaluationsUseCase.loadAllEvaluations()
    }
}
