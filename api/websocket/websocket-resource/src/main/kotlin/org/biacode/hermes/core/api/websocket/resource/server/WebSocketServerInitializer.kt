package org.biacode.hermes.core.api.websocket.resource.server

import io.netty.channel.ChannelInitializer
import io.netty.channel.socket.SocketChannel
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.HttpServerCodec
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler
import io.netty.handler.ssl.SslContext
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

/**
 * Created by Arthur Asatryan.
 * Date: 1/22/18
 * Time: 4:13 PM
 */
@Component
class WebSocketServerInitializer : ChannelInitializer<SocketChannel>() {

    //region Dependencies
    @Autowired
    private lateinit var webSocketFrameHandler: WebSocketFrameHandler

    @Autowired
    private var sslCtx: SslContext? = null
    //endregion

    //region Public methods
    public override fun initChannel(ch: SocketChannel) {
        logger.debug("Creating channel pipeline")
        val pipeline = ch.pipeline()
        if (sslCtx != null) {
            logger.debug("Adding SSL handler to pipeline")
            pipeline.addLast(sslCtx!!.newHandler(ch.alloc()))
        }
        pipeline
                .addLast("httpServerCodec", HttpServerCodec())
                .addLast("httpObjectAggregator", HttpObjectAggregator(65536))
                .addLast("webSocketServerCompressionHandler", WebSocketServerCompressionHandler())
                .addLast("webSocketServerProtocolHandler", WebSocketServerProtocolHandler(WEBSOCKET_PATH, null, true))
                .addLast("webSocketFrameHandler", webSocketFrameHandler)
        logger.debug("Channel pipeline successfully created")
    }
    //endregion

    companion object {
        private const val WEBSOCKET_PATH = "/websocket"

        private val logger = LoggerFactory.getLogger(WebSocketServerInitializer::class.java)
    }
}
