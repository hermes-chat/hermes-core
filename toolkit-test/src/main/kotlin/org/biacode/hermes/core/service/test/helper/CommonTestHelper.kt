package org.biacode.hermes.core.service.test.helper

import org.assertj.core.api.Assertions
import org.biacode.hermes.core.domain.account.Account
import org.biacode.hermes.core.service.account.dto.CreateAccountDto
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 5:14 PM
 */
open class CommonTestHelper {

    //region Public methods

    //region Account
    fun buildAccount(
            email: String = Companion.email
    ) = Account(email)

    fun buildCreateAccountDto(
            email: String = Companion.email
    ) = CreateAccountDto(email)

    fun assertCreateAccountDtoWithResult(dto: CreateAccountDto, result: Account) {
        Assertions.assertThat(result).isNotNull()
        Assertions.assertThat(result.email).isEqualTo(dto.email)
    }
    //endregion

    //endregion

    //region Companion object
    companion object {
        val email = UUID.randomUUID().toString()
    }
    //endregion
}