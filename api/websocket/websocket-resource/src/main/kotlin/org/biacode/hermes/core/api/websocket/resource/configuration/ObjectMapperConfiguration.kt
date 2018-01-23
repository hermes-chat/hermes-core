package org.biacode.hermes.core.api.websocket.resource.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by Arthur Asatryan.
 * Date: 1/23/18
 * Time: 11:01 PM
 */
@Configuration
class ObjectMapperConfiguration {

    //region Beans
    @Bean
    fun objectMapper(): ObjectMapper {
        logger.debug("Creating object mapper.")
        return ObjectMapper().registerModule(KotlinModule())
    }
    //endregion

    //region Companion object
    companion object {
        private val logger = LoggerFactory.getLogger(ObjectMapperConfiguration::class.java)
    }
    //endregion

}