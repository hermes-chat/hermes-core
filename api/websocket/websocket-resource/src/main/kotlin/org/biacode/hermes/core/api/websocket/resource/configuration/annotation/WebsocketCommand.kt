package org.biacode.hermes.core.api.websocket.resource.configuration.annotation

/**
 * Created by Arthur Asatryan.
 * Date: 1/25/18
 * Time: 3:46 PM
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WebsocketCommand(val value: String)