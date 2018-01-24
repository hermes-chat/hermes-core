package org.biacode.hermes.core.api.rest.resource.account

import org.biacode.hermes.core.api.rest.facade.account.AccountServiceFacade
import org.biacode.hermes.core.api.rest.model.account.request.CreateAccountRequest
import org.biacode.hermes.core.api.rest.model.account.response.CreateAccountResponse
import org.biacode.hermes.core.api.rest.model.common.ResultResponseModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created by Arthur Asatryan.
 * Date: 1/14/18
 * Time: 6:40 PM
 */
@RestController
@RequestMapping("/account")
class AccountResource {

    //region Dependencies
    @Autowired
    private lateinit var accountServiceFacade: AccountServiceFacade
    //endregion

    //region Public methods
    @PostMapping
    fun create(@RequestBody request: CreateAccountRequest): ResponseEntity<ResultResponseModel<CreateAccountResponse>> {
        return ResponseEntity.ok(accountServiceFacade.create(request))
    }
    //endregion
}