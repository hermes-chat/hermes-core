package org.biacode.hermes.core.api.websocket.resource.server

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame
import io.netty.handler.codec.http.websocketx.WebSocketFrame
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler
import org.biacode.hermes.core.api.websocket.resource.server.ChannelRepository.Companion.DEFAULT_ROOM_NAME
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


/**
 * Created by Arthur Asatryan.
 * Date: 1/22/18
 * Time: 4:28 PM
 */
@Component
@ChannelHandler.Sharable
class WebSocketFrameHandler : SimpleChannelInboundHandler<WebSocketFrame>() {

    @Autowired
    private lateinit var channelRepository: ChannelRepository

    @Autowired
    private lateinit var jacksonObjectMapper: ObjectMapper

    override fun channelActive(ctx: ChannelHandlerContext) {
        super.channelActive(ctx)
    }

    override fun channelInactive(ctx: ChannelHandlerContext) {
        super.channelInactive(ctx)
    }

    override fun channelRead0(ctx: ChannelHandlerContext, frame: WebSocketFrame) {
        // remove webSocketIndexPageHandler for optimizing channel pipeline
        val webSocketIndexPageHandler = ctx.channel().pipeline().get("webSocketIndexPageHandler")
        if (webSocketIndexPageHandler != null) {
            ctx.channel().pipeline().remove("webSocketIndexPageHandler")
        }
        // ping and pong frames already handled
        when (frame) {
            is TextWebSocketFrame -> {
                val request = frame.retain().text()
                val jsonMap: Map<String, String> = jacksonObjectMapper.readValue(request, object : TypeReference<Map<Any, Any>>() {})
                if (jsonMap.containsKey("roomName")) {
                    jsonMap["roomName"]?.let {
                        channelRepository.createRoom(it).add(ctx.channel())
                    }
                }
                logger.info("{} received {}", ctx.channel(), request)
                ctx.channel().writeAndFlush(TextWebSocketFrame(request))
            }
            is CloseWebSocketFrame -> {
                println("WebSocket Client received closing")
                ctx.channel().close()
            }
            else -> {
                val message = "unsupported frame type: " + frame.javaClass.name
                throw UnsupportedOperationException(message)
            }
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        logger.error(cause.message, cause)
    }

    override fun userEventTriggered(ctx: ChannelHandlerContext, evt: Any) {
        super.userEventTriggered(ctx, evt)
        if (evt is WebSocketServerProtocolHandler.HandshakeComplete) {
            channelRepository.addChannelToRoom(DEFAULT_ROOM_NAME, ctx.channel())
            logger.info("handshake completed for channel - {}", ctx.channel())
            setOf(
                    "webSocketIndexPageHandler"
            ).forEach {
                ctx.channel().pipeline().remove(it)
            }
        }
    }

    companion object {
        private val logger = LoggerFactory.getLogger(WebSocketFrameHandler::class.java)
    }
}