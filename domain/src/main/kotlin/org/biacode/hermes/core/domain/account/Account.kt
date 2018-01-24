package org.biacode.hermes.core.domain.account

import org.biacode.hermes.core.domain.common.AbstractUuidAndDateAwareDomainModel
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 5:48 PM
 */
@Entity
@Table(name = "account")
data class Account(
        @Column(name = "email", nullable = false)
        val email: String? = null
) : AbstractUuidAndDateAwareDomainModel()