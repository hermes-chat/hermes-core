package org.biacode.hermes.core.api.websocket.resource.server

import io.netty.channel.Channel
import io.netty.channel.group.ChannelGroup
import io.netty.channel.group.ChannelGroupFuture
import io.netty.channel.group.ChannelGroupFutureListener
import io.netty.channel.group.DefaultChannelGroup
import io.netty.util.concurrent.DefaultEventExecutor
import io.netty.util.concurrent.EventExecutor
import io.netty.util.internal.PlatformDependent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentMap
import javax.annotation.PostConstruct

/**
 * Created by Arthur Asatryan.
 * Date: 1/23/18
 * Time: 12:43 AM
 */
@Component
class ChannelRepository {

    private var rooms: ConcurrentMap<String, ChannelGroup> = PlatformDependent.newConcurrentHashMap<String, ChannelGroup>()

    fun createRoom(name: String): DefaultChannelGroup {
        logger.debug("creating room with name - {}", name)
        val defaultChannelGroup = DefaultChannelGroup(channelGroupEventExecutor)
        defaultChannelGroup.newCloseFuture().addListener(object : ChannelGroupFutureListener {

            override fun operationComplete(future: ChannelGroupFuture?) {
                rooms.remove(name)
            }

        })
        rooms.putIfAbsent(name, defaultChannelGroup)
        return defaultChannelGroup
    }

    fun addChannelToRoom(roomName: String, channel: Channel) {
        // TODO: ???
        rooms[roomName]?.add(channel)
    }

    fun findChannelGroup(roomName: String): Optional<ChannelGroup> {
        return Optional.ofNullable(rooms[roomName])
    }

    init {
        println("in class")
    }

    @PostConstruct
    fun postConstruct() {
        println("in post construct")
        val defaultChannelGroup = DefaultChannelGroup(channelGroupEventExecutor)
        defaultChannelGroup.newCloseFuture().addListener(object : ChannelGroupFutureListener {

            override fun operationComplete(future: ChannelGroupFuture?) {
                rooms.remove(DEFAULT_ROOM_NAME)
            }

        })
        rooms[DEFAULT_ROOM_NAME] = defaultChannelGroup
    }

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ChannelRepository::class.java)

        const val DEFAULT_ROOM_NAME = "guest"

        private val channelGroupEventExecutor: EventExecutor = DefaultEventExecutor()

        init {
            println("in companion")
        }
    }
}