package org.biacode.hermes.core.api.websocket.resource.server

import io.netty.buffer.Unpooled
import io.netty.channel.*
import io.netty.handler.codec.http.*
import io.netty.handler.codec.http.HttpMethod.GET
import io.netty.handler.codec.http.HttpResponseStatus.*
import io.netty.handler.codec.http.HttpVersion.HTTP_1_1
import io.netty.handler.ssl.SslHandler
import io.netty.util.CharsetUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

/**
 * Created by Arthur Asatryan.
 * Date: 1/22/18
 * Time: 4:25 PM
 */
@ChannelHandler.Sharable
class WebSocketIndexPageHandler(private val websocketPath: String) : SimpleChannelInboundHandler<FullHttpRequest>() {

    @Autowired
    private lateinit var channelRepository: ChannelRepository

    override fun channelActive(ctx: ChannelHandlerContext?) {
        super.channelActive(ctx)
    }

    override fun channelInactive(ctx: ChannelHandlerContext?) {
        super.channelInactive(ctx)
    }

    override fun channelRead0(ctx: ChannelHandlerContext, request: FullHttpRequest) {
        // Handle a bad request.
        if (!request.decoderResult().isSuccess) {
            sendHttpResponse(ctx, request, DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST))
            return
        }
        // Allow only GET methods.
        if (request.method() !== GET) {
            sendHttpResponse(ctx, request, DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN))
            return
        }
        // Send the index page
        if ("/" == request.uri() || "/index.html" == request.uri()) {
            val webSocketLocation = getWebSocketLocation(ctx.pipeline(), request, websocketPath)
            val content = WebSocketServerIndexPage.getContent(webSocketLocation)
            val res = DefaultFullHttpResponse(HTTP_1_1, OK, content)
            res.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8")
            HttpUtil.setContentLength(res, content.readableBytes().toLong())
            sendHttpResponse(ctx, request, res)
        } else {
            sendHttpResponse(ctx, request, DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND))
        }
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }

    companion object {

        private val logger = LoggerFactory.getLogger(WebSocketIndexPageHandler::class.java)

        private fun sendHttpResponse(ctx: ChannelHandlerContext, req: FullHttpRequest, res: FullHttpResponse) {
            // Generate an error page if response getStatus code is not OK (200).
            if (res.status().code() != 200) {
                val buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8)
                res.content().writeBytes(buf)
                buf.release()
                HttpUtil.setContentLength(res, res.content().readableBytes().toLong())
            }
            // Send the response and close the connection if necessary.
            val channelFuture = ctx.channel().writeAndFlush(res)
            if (!HttpUtil.isKeepAlive(req) || res.status().code() != 200) {
                channelFuture.addListener(ChannelFutureListener.CLOSE)
            }
        }

        private fun getWebSocketLocation(cp: ChannelPipeline, request: HttpRequest, path: String): String {
            var protocol = "ws"
            if (cp.get(SslHandler::class.java) != null) {
                // SSL in use so use Secure WebSockets
                protocol = "wss"
            }
            return protocol + "://" + request.headers().get(HttpHeaderNames.HOST) + path
        }
    }
}
