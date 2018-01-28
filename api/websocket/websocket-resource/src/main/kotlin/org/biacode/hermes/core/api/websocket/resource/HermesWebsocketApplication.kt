package org.biacode.hermes.core.api.websocket.resource

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ImportResource


/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 4:54 PM
 */
@EnableAutoConfiguration
@SpringBootApplication
@ImportResource("classpath:bootContext.xml")
class HermesWebsocketApplication {

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
