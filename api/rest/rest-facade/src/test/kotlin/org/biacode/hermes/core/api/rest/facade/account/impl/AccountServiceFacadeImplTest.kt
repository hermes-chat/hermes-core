package org.biacode.hermes.core.api.rest.facade.account.impl

import org.assertj.core.api.Assertions.assertThat
import org.biacode.hermes.core.api.rest.facade.test.AbstractFacadeImplTest
import org.biacode.hermes.core.api.rest.model.account.request.CreateAccountRequest
import org.biacode.hermes.core.api.rest.model.account.response.CreateAccountResponse
import org.biacode.hermes.core.service.account.AccountService
import org.easymock.EasyMock.expect
import org.easymock.Mock
import org.easymock.TestSubject
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 1/14/18
 * Time: 4:39 PM
 */
class AccountServiceFacadeImplTest : AbstractFacadeImplTest() {

    //region Test subject and mocks
    @TestSubject
    private val accountServiceFacade = AccountServiceFacadeImpl()

    @Mock
    private lateinit var accountService: AccountService
    //endregion

    //region Test methods
    @Test
    fun `test create`() {
        // test data
        resetAll()
        val account = helper.buildAccount()
        val dto = helper.buildCreateAccountDto(account.email!!)
        val request = CreateAccountRequest(dto.email)
        // expectations
        expect(accountService.create(dto)).andReturn(account)
        replayAll()
        // test scenario
        accountServiceFacade.create(request).let {
            helper.assertSuccessResultResponse(it)
            assertThat(it).isNotNull()
            assertThat(it.hasErrors()).isFalse()
            assertThat((it.result as CreateAccountResponse).uuid).isEqualTo(account.uuid)
        }
        verifyAll()
    }
    //endregion
}