package org.biacode.hermes.core.service.account.impl

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.fail
import org.biacode.hermes.core.domain.account.Account
import org.biacode.hermes.core.persistence.repository.account.AccountRepository
import org.biacode.hermes.core.service.account.AccountService
import org.biacode.hermes.core.service.account.exception.AccountAlreadyExistsForEmailException
import org.biacode.hermes.core.service.test.AbstractServiceImplTest
import org.biacode.hermes.core.service.test.helper.CommonTestHelper.Companion.email
import org.easymock.EasyMock.*
import org.easymock.Mock
import org.easymock.TestSubject
import org.junit.Test

/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 4:54 PM
 */
class AccountServiceImplTest : AbstractServiceImplTest() {

    //region Test subject and mocks
    @TestSubject
    private val accountService: AccountService = AccountServiceImpl()

    @Mock
    private lateinit var accountRepository: AccountRepository
    //endregion

    //region Test methods

    //region init
    @Test
    fun `has account service`() {
        assertThat(accountService).isNotNull()
    }

    @Test
    fun `has account repository`() {
        assertThat(accountRepository).isNotNull()
    }
    //endregion

    //region create
    @Test
    fun `create when account with email already exists`() {
        // test data
        resetAll()
        val dto = helper.buildCreateAccountDto()
        val account = helper.buildAccount()
        // expectations
        expect(accountRepository.findByEmail(dto.email)).andReturn(account)
        replayAll()
        // test scenario
        try {
            accountService.create(dto)
            fail("Exception should be thrown")
        } catch (ex: AccountAlreadyExistsForEmailException) {
            assertThat(dto.email).isEqualTo(ex.email)
        }
        verifyAll()
    }

    @Test
    fun `test create`() {
        // test data
        resetAll()
        val dto = helper.buildCreateAccountDto()
        // expectations
        expect(accountRepository.findByEmail(dto.email)).andReturn(null)
        expect(accountRepository.save(isA(Account::class.java))).andAnswer({ getCurrentArguments()[0] as Account })
        replayAll()
        // test scenario
        val result = accountService.create(dto)
        helper.assertCreateAccountDtoWithResult(dto, result)
        verifyAll()
    }
    //endregion

    //region findByEmail
    @Test
    fun `test find by email when not found`() {
        // test data
        resetAll()
        // expectations
        expect(accountRepository.findByEmail(email)).andReturn(null)
        replayAll()
        // test scenario
        accountService.findByEmail(email).let {
            assertThat(it).isNotNull()
            assertThat(it.isPresent).isFalse()
        }
        verifyAll()
    }

    @Test
    fun `test find by email when found`() {
        // test data
        resetAll()
        val account = helper.buildAccount(email)
        // expectations
        expect(accountRepository.findByEmail(email)).andReturn(account)
        replayAll()
        // test scenario
        accountService.findByEmail(email).let {
            assertThat(it).isNotNull()
            assertThat(it.isPresent).isTrue()
            assertThat(it.get().email).isEqualTo(email)
        }
        verifyAll()
    }
    //endregion

    //endregion

}

