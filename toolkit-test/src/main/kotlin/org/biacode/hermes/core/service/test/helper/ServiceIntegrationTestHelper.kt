package org.biacode.hermes.core.service.test.helper

import org.biacode.hermes.core.domain.account.Account
import org.biacode.hermes.core.service.account.AccountService
import org.biacode.hermes.core.service.account.dto.CreateAccountDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 6:59 PM
 */
@Component
class ServiceIntegrationTestHelper : CommonTestHelper() {

    //region Dependencies
    @Autowired
    private lateinit var accountService: AccountService
    //endregion

    //region Public methods
    fun buildAndPersistAccount(dto: CreateAccountDto = buildCreateAccountDto()): Account {
        return accountService.create(dto)
    }
    //endregion
}