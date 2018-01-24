package org.biacode.hermes.core.api.rest.facade.account

import org.biacode.hermes.core.api.rest.model.account.request.CreateAccountRequest
import org.biacode.hermes.core.api.rest.model.account.response.CreateAccountResponse
import org.biacode.hermes.core.api.rest.model.common.ResultResponseModel

/**
 * Created by Arthur Asatryan.
 * Date: 1/14/18
 * Time: 4:14 PM
 */
interface AccountServiceFacade {
    fun create(request: CreateAccountRequest): ResultResponseModel<CreateAccountResponse>
}