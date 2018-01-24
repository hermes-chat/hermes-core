package org.biacode.hermes.core.api.rest.facade.account.impl

import org.biacode.hermes.core.api.rest.facade.account.AccountServiceFacade
import org.biacode.hermes.core.api.rest.model.account.request.CreateAccountRequest
import org.biacode.hermes.core.api.rest.model.account.response.CreateAccountResponse
import org.biacode.hermes.core.api.rest.model.common.ResultResponseModel
import org.biacode.hermes.core.service.account.AccountService
import org.biacode.hermes.core.service.account.dto.CreateAccountDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 1/14/18
 * Time: 4:14 PM
 */
@Component
class AccountServiceFacadeImpl : AccountServiceFacade {

    //region Dependencies
    @Autowired
    private lateinit var accountService: AccountService
    //endregion

    //region Public methods
    override fun create(request: CreateAccountRequest): ResultResponseModel<CreateAccountResponse> {
        val account = accountService.create(CreateAccountDto(request.email))
        return ResultResponseModel(CreateAccountResponse(account.uuid))
    }
    //endregion
}