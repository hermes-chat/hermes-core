package org.biacode.hermes.core.api.websocket.resource

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.ImportResource
import org.springframework.context.annotation.Profile
import org.springframework.context.annotation.PropertySource


/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 4:54 PM
 */
@SpringBootApplication
@ImportResource("classpath:bootContext.xml")
@PropertySource(value = ["classpath:netty/local/netty-server.properties"])
class HermesWebsocketApplication {

    //region Configuration profiles
    @Configuration
    @Profile("production")
    @PropertySource("classpath:netty/production/netty-server.properties")
    internal class Production

    @Configuration
    @Profile("local")
    @PropertySource("classpath:netty/local/netty-server.properties")
    internal class Local
    //endregion

    //region Companion object
    companion object {

        private val logger = LoggerFactory.getLogger(HermesWebsocketApplication::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            logger.debug("Creating spring application context")
            SpringApplication.run(HermesWebsocketApplication::class.java, *args)
        }

    }
    //endregion
}
