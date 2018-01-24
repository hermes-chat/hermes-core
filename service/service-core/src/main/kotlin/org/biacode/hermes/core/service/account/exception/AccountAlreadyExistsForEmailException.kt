package org.biacode.hermes.core.service.account.exception

/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 5:52 PM
 */
data class AccountAlreadyExistsForEmailException(
        val email: String,
        override val message: String
) : RuntimeException(message)