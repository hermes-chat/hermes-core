package org.biacode.hermes.core.api.websocket.resource.controller.account

import com.fasterxml.jackson.annotation.JsonProperty
import org.biacode.hermes.core.api.websocket.resource.configuration.annotation.WebsocketCommand
import org.biacode.hermes.core.api.websocket.resource.controller.common.CommandAwareWebsocketRequest
import org.biacode.hermes.core.service.account.AccountService
import org.biacode.hermes.core.service.account.dto.CreateAccountDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 1/28/18
 * Time: 1:21 AM
 */
@Component
class AccountController {

    @Autowired
    private lateinit var accountService: AccountService

    @WebsocketCommand("account/create")
    fun create(request: CreateAccountRequest) {
        accountService.create(CreateAccountDto(request.email))
    }

}

data class CreateAccountRequest(
        @JsonProperty("email")
        val email: String
) : CommandAwareWebsocketRequest()
