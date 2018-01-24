package org.biacode.hermes.core.persistence.repository.account

import org.biacode.hermes.core.domain.account.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 4:54 PM
 */
@Repository
interface AccountRepository : JpaRepository<Account, Long> {
    fun findByEmail(email: String): Account?
}