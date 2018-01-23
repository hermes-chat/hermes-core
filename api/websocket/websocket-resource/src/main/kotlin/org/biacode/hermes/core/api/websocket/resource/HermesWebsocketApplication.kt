package org.biacode.hermes.core.api.websocket.resource

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.SelfSignedCertificate
import org.biacode.hermes.core.api.websocket.resource.server.WebSocketServerInitializer
import org.biacode.hermes.core.api.websocket.resource.server.WebsocketServer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.*


/**
 * Created by Arthur Asatryan.
 * Date: 1/13/18
 * Time: 4:54 PM
 */
@SpringBootApplication
@ImportResource("classpath:bootContext.xml")
@PropertySource(value = ["classpath:netty/local/netty-server.properties"])
class HermesWebsocketApplication : CommandLineRunner {

    @Configuration
    @Profile("production")
    @PropertySource("classpath:netty/production/netty-server.properties")
    internal class Production

    @Configuration
    @Profile("local")
    @PropertySource("classpath:netty/local/netty-server.properties")
    internal class Local

    @Value("\${netty.ssl}")
    private val nettySsl: Boolean = false

    @Value("\${boss.thread.count}")
    private val bossCount: Int = 0

    @Value("\${worker.thread.count}")
    private val workerCount: Int = 0

    @Autowired
    private lateinit var bossGroup: NioEventLoopGroup

    @Autowired
    private lateinit var workerGroup: NioEventLoopGroup

    @Autowired
    private lateinit var webSocketServerInitializer: WebSocketServerInitializer

    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper().registerModule(KotlinModule())
    }

    @Bean
    fun sslCtx(): SslContext? {
        // Configure SSL.
        return if (nettySsl) {
            val ssc = SelfSignedCertificate()
            SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build()
        } else {
            null
        }
    }

    @Bean(destroyMethod = "shutdownGracefully")
    fun bossGroup(): NioEventLoopGroup {
        return NioEventLoopGroup(bossCount)
    }

    @Bean(destroyMethod = "shutdownGracefully")
    fun workerGroup(): NioEventLoopGroup {
        return NioEventLoopGroup(workerCount)
    }

    @Bean
    fun serverBootstrap(): ServerBootstrap {
        return ServerBootstrap()
                .group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel::class.java)
                .handler(LoggingHandler(LogLevel.DEBUG))
                .childHandler(webSocketServerInitializer)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
    }

    override fun run(vararg arguments: String?) {
    }

    companion object {

        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            val applicationContext = SpringApplication.run(HermesWebsocketApplication::class.java, *args)
            applicationContext.getBean(WebsocketServer::class.java).start()
        }

    }
}
