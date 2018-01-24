package org.biacode.hermes.core.service.account

import org.assertj.core.api.Assertions.assertThat
import org.biacode.hermes.core.service.test.AbstractServiceIntegrationTest
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 6:38 PM
 */
class AccountServiceIntegrationTest : AbstractServiceIntegrationTest() {

    //region Dependencies
    @Autowired
    private lateinit var accountService: AccountService
    //endregion

    //region Test methods
    @Test
    fun `test dependencies`() {
        assertThat(accountService).isNotNull()
        assertThat(helper).isNotNull()
    }

    @Test
    fun `test create`() {
        // given
        helper.buildCreateAccountDto().let { dto ->
            // when
            accountService.create(dto).let { result ->
                // then
                assertThat(result).isNotNull()
                assertThat(result.email).isEqualTo(dto.email)
            }
        }
    }

    @Test
    fun `test find by email`() {
        // given
        helper.buildAndPersistAccount().let { account ->
            // when
            accountService.findByEmail(account.email!!).let {
                // then
                assertThat(it).isNotNull()
                assertThat(it.isPresent).isTrue()
                assertThat(it.get().email).isEqualTo(account.email)
            }
        }
    }
    //endregion

}