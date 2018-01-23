package org.biacode.hermes.core.api.websocket.resource.server

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.HttpServerCodec
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler
import io.netty.handler.ssl.SslContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 1/22/18
 * Time: 4:13 PM
 */
@Component
class WebSocketServerInitializer : ChannelInitializer<SocketChannel>() {

    @Autowired
    private lateinit var webSocketIndexPageHandler: WebSocketIndexPageHandler

    @Autowired
    private lateinit var webSocketFrameHandler: WebSocketFrameHandler

    @Autowired
    private var sslCtx: SslContext? = null

    @Bean
    fun webSocketIndexPageHandler(): WebSocketIndexPageHandler {
        return WebSocketIndexPageHandler(WEBSOCKET_PATH)
    }

    public override fun initChannel(ch: SocketChannel) {
        val pipeline = ch.pipeline()
        if (sslCtx != null) {
            pipeline.addLast(sslCtx!!.newHandler(ch.alloc()))
        }
        pipeline.addLast("httpServerCodec", HttpServerCodec())
        pipeline.addLast("httpObjectAggregator", HttpObjectAggregator(65536))
        pipeline.addLast("webSocketServerCompressionHandler", WebSocketServerCompressionHandler())
        pipeline.addLast("webSocketServerProtocolHandler", WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true))
        pipeline.addLast("webSocketIndexPageHandler", webSocketIndexPageHandler)
        pipeline.addLast("webSocketFrameHandler", webSocketFrameHandler)
    }

    companion object {
        private const val WEBSOCKET_PATH = "/websocket"
    }
}
