package org.biacode.hermes.core.service.account

import org.biacode.hermes.core.domain.account.Account
import org.biacode.hermes.core.service.account.dto.CreateAccountDto
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 4:54 PM
 */
interface AccountService {
    fun create(dto: CreateAccountDto): Account

    fun findByEmail(email: String): Optional<Account>
}