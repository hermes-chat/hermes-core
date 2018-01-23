package org.biacode.hermes.core.api.websocket.resource.configuration

import io.netty.channel.nio.NioEventLoopGroup
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by Arthur Asatryan.
 * Date: 1/23/18
 * Time: 10:55 PM
 */
@Configuration
class EventLoopGroupConfiguration {

    //region Dependencies
    @Value("\${netty.boss.thread.count}")
    private val bossCount: Int = 0

    @Value("\${netty.worker.thread.count}")
    private val workerCount: Int = 0
    //endregion

    //region Beans
    @Bean(destroyMethod = "shutdownGracefully")
    fun bossGroup(): NioEventLoopGroup {
        logger.debug("Creating boss loop group with boss thread count - {}", bossCount)
        return NioEventLoopGroup(bossCount)
    }

    @Bean(destroyMethod = "shutdownGracefully")
    fun workerGroup(): NioEventLoopGroup {
        logger.debug("Creating worker loop group with worker thread count - {}", bossCount)
        return NioEventLoopGroup(workerCount)
    }
    //endregion

    //region Companion object
    companion object {
        private val logger = LoggerFactory.getLogger(EventLoopGroupConfiguration::class.java)
    }
    //endregion
}