package org.biacode.hermes.core.service.account.impl

import org.biacode.hermes.core.domain.account.Account
import org.biacode.hermes.core.persistence.repository.account.AccountRepository
import org.biacode.hermes.core.service.account.AccountService
import org.biacode.hermes.core.service.account.dto.CreateAccountDto
import org.biacode.hermes.core.service.account.exception.AccountAlreadyExistsForEmailException
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 4:54 PM
 */
@Service
class AccountServiceImpl : AccountService {

    //region Dependencies
    @Autowired
    private lateinit var accountRepository: AccountRepository
    //endregion

    //region Public methods
    @Transactional
    override fun create(dto: CreateAccountDto): Account {
        assertAccountDoesNotExistsForEmail(dto)
        return accountRepository.save(Account(dto.email))
    }

    @Transactional(readOnly = true)
    override fun findByEmail(email: String): Optional<Account> {
        return Optional.ofNullable(accountRepository.findByEmail(email))
    }
    //endregion

    //region Utility methods
    private fun assertAccountDoesNotExistsForEmail(dto: CreateAccountDto) {
        findByEmail(dto.email).ifPresent {
            logger.error("Account with email - {} already exists", dto.email)
            throw AccountAlreadyExistsForEmailException(dto.email, "Account with email - ${dto.email} already exists")
        }
    }
    //endregion

    //region Companion object
    private val logger = LoggerFactory.getLogger(AccountServiceImpl::class.java)
    //endregion
}