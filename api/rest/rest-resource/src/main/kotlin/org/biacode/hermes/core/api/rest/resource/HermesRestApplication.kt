package org.biacode.hermes.core.api.rest.resource

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ImportResource

/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 4:54 PM
 */
@SpringBootApplication
@ImportResource("classpath:bootContext.xml")
class HermesRestApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(HermesRestApplication::class.java, *args)
        }
    }
}
