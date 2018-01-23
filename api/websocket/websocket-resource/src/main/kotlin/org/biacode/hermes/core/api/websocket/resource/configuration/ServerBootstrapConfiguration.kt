package org.biacode.hermes.core.api.websocket.resource.configuration

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import org.biacode.hermes.core.api.websocket.resource.server.WebSocketServerInitializer
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Created by Arthur Asatryan.
 * Date: 1/23/18
 * Time: 10:53 PM
 */
@Configuration
class ServerBootstrapConfiguration {

    //region Dependencies
    @Autowired
    private lateinit var bossGroup: NioEventLoopGroup

    @Autowired
    private lateinit var workerGroup: NioEventLoopGroup

    @Autowired
    private lateinit var webSocketServerInitializer: WebSocketServerInitializer
    //endregion

    //region Beans
    @Bean
    fun serverBootstrap(): ServerBootstrap {
        logger.debug("Bootstrapping websocket server...")
        return ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .handler(LoggingHandler(LogLevel.DEBUG))
                .childHandler(webSocketServerInitializer)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
    }
    //endregion

    //region Companion object
    companion object {
        private val logger = LoggerFactory.getLogger(ServerBootstrapConfiguration::class.java)
    }
    //endregion
}