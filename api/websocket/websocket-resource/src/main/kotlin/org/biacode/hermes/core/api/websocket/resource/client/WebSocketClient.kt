package org.biacode.hermes.core.api.websocket.resource.client

import io.netty.bootstrap.Bootstrap
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.http.DefaultHttpHeaders
import io.netty.handler.codec.http.HttpClientCodec
import io.netty.handler.codec.http.HttpObjectAggregator
import io.netty.handler.codec.http.websocketx.*
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketClientCompressionHandler
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URI

/**
 * Created by Arthur Asatryan.
 * Date: 1/23/18
 * Time: 2:33 PM
 */
object WebSocketClient {

    private val URL = System.getProperty("url", "ws://127.0.0.1:8081/websocket")

    @Throws(Exception::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val uri = URI(URL)
        val scheme = if (uri.scheme == null) "ws" else uri.scheme
        val host = if (uri.host == null) "127.0.0.1" else uri.host
        val port: Int
        port = if (uri.port == -1) {
            when {
                "ws".equals(scheme, ignoreCase = true) -> 80
                "wss".equals(scheme, ignoreCase = true) -> 443
                else -> -1
            }
        } else {
            uri.port
        }

        if (!"ws".equals(scheme, ignoreCase = true) && !"wss".equals(scheme, ignoreCase = true)) {
            System.err.println("Only WS(S) is supported.")
            return
        }

        val ssl = "wss".equals(scheme, ignoreCase = true)
        val sslCtx: SslContext?
        if (ssl) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build()
        } else {
            sslCtx = null
        }

        val group = NioEventLoopGroup()
        try {
            // Connect with V13 (RFC 6455 aka HyBi-17). You can change it to V08 or V00.
            // If you change it to V00, ping is not supported and remember to change
            // HttpResponseDecoder to WebSocketHttpResponseDecoder in the pipeline.
            val handler = WebSocketClientHandler(
                    WebSocketClientHandshakerFactory.newHandshaker(
                            uri,
                            WebSocketVersion.V13, null,
                            true,
                            DefaultHttpHeaders()
                    )
            )

            val b = Bootstrap()
            b.group(group)
                    .channel(NioSocketChannel::class.java)
                    .handler(object : ChannelInitializer<SocketChannel>() {
                        override fun initChannel(ch: SocketChannel) {
                            val p = ch.pipeline()
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc(), host, port))
                            }
                            p.addLast(
                                    HttpClientCodec(),
                                    HttpObjectAggregator(8192),
                                    WebSocketClientCompressionHandler.INSTANCE,
                                    handler
                            )
                        }
                    })

            val ch = b.connect(uri.host, port).sync().channel()
            handler.handshakeFuture()!!.sync()

            val console = BufferedReader(InputStreamReader(System.`in`))
            while (true) {
                val msg = console.readLine()
                if (msg == null) {
                    break
                } else if ("bye" == msg.toLowerCase()) {
                    ch.writeAndFlush(CloseWebSocketFrame())
                    ch.closeFuture().sync()
                    break
                } else if ("ping" == msg.toLowerCase()) {
                    val frame = PingWebSocketFrame(Unpooled.wrappedBuffer(byteArrayOf(8, 1, 8, 1)))
                    ch.writeAndFlush(frame)
                } else {
                    val frame = TextWebSocketFrame(msg)
                    ch.writeAndFlush(frame)
                }
            }
        } finally {
            group.shutdownGracefully()
        }
    }
}