package org.biacode.hermes.core.domain.common

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 5:55 PM
 */
@MappedSuperclass
abstract class AbstractBaseDomainModel(
        @Id
        @SequenceGenerator(name = "account_seq", sequenceName = "account_seq", allocationSize = 1)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq")
        var id: Long? = null
)

@MappedSuperclass
abstract class AbstractDateAwareDomainModel(
        @Column(name = "created")
        val created: LocalDateTime = LocalDateTime.now(),
        @Column(name = "updated")
        var updated: LocalDateTime? = null,
        @Column(name = "removed")
        var removed: LocalDateTime? = null
) : AbstractBaseDomainModel()

@MappedSuperclass
abstract class AbstractUuidAndDateAwareDomainModel(
        @Column(name = "uuid")
        val uuid: String = UUID.randomUUID().toString()
) : AbstractDateAwareDomainModel()