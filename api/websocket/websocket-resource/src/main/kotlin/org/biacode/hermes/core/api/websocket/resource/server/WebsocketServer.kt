package org.biacode.hermes.core.api.websocket.resource.server

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.nio.NioEventLoopGroup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.annotation.PreDestroy


/**
 * Created by Arthur Asatryan.
 * Date: 1/22/18
 * Time: 6:40 PM
 */
@Component
class WebsocketServer {

    @Value("\${tcp.port}")
    private val tcpPort: Int = 0

    @Value("\${netty.ssl}")
    private val nettySsl: Boolean = false

    @Autowired
    private lateinit var serverBootstrap: ServerBootstrap

    @Autowired
    private lateinit var bossGroup: NioEventLoopGroup

    @Autowired
    private lateinit var workerGroup: NioEventLoopGroup

    private lateinit var serverChannel: Channel

    @PreDestroy
    fun stop() {
        serverChannel.close()
        serverChannel.parent().close()
        bossGroup.shutdownGracefully()
        workerGroup.shutdownGracefully()
    }

    fun start() {
        serverChannel = serverBootstrap.bind(tcpPort).sync().channel()
        println("Open your web browser and navigate to " + (if (nettySsl) "https" else "http") + "://127.0.0.1:" + tcpPort + '/'.toString())
        serverChannel.closeFuture().sync().channel()
    }
}