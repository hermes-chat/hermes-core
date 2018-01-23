package org.biacode.hermes.core.api.websocket.resource.configuration

import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.SelfSignedCertificate
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by Arthur Asatryan.
 * Date: 1/23/18
 * Time: 11:02 PM
 */
@Configuration
class SslContextConfiguration {

    //region Dependencies
    @Value("\${netty.ssl}")
    private val nettySsl: Boolean = false
    //endregion

    //region Beans
    @Bean
    fun sslContext(): SslContext? {
        return if (nettySsl) {
            logger.debug("Configuring SSL")
            val ssc = SelfSignedCertificate()
            SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build()
        } else {
            logger.debug("SSL is disabled")
            null
        }
    }
    //endregion

    //region Companion object
    companion object {
        private val logger = LoggerFactory.getLogger(SslContextConfiguration::class.java)
    }
    //endregion
}