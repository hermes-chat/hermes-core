package org.biacode.hermes.core.api.websocket.resource.controller.account

import com.fasterxml.jackson.annotation.JsonProperty
import org.biacode.hermes.core.service.account.AccountService
import org.biacode.hermes.core.service.account.dto.CreateAccountDto
import org.biacode.hermes.spring.netty.core.annotation.HermesCommand
import org.biacode.hermes.spring.netty.core.annotation.HermesController
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 1/28/18
 * Time: 1:21 AM
 */
@HermesController
class AccountController {

    @Autowired
    private lateinit var accountService: AccountService

    @HermesCommand("account/create")
    fun create(request: CreateAccountRequest) {
        accountService.create(CreateAccountDto(request.email))
    }

}

data class CreateAccountRequest(
        @JsonProperty("email")
        val email: String
) : WebsocketCommandAwareNettyControllerRequest()

