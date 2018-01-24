package org.biacode.hermes.core.persistence.repository.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Configuration

/**
 * Created by Arthur Asatryan.
 * Date: 1/22/18
 * Time: 12:29 PM
 */
@Configuration
@EntityScan("org.biacode.hermes.core.domain")
class EntityScanConfiguration