package org.biacode.hermes.core.api.websocket.resource.configuration

import org.biacode.hermes.core.api.websocket.resource.configuration.annotation.WebsocketCommand
import org.biacode.hermes.core.api.websocket.resource.controller.common.WebsocketCommandType
import org.biacode.hermes.core.api.websocket.resource.controller.common.WebsocketRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.util.ClassUtils
import java.lang.reflect.Method

/**
 * Created by Arthur Asatryan.
 * Date: 1/27/18
 * Time: 8:56 PM
 */
@Configuration
class WebsocketCommandRouteGeneratorBeanFactoryPostProcessor : ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private lateinit var beanFactory: ConfigurableListableBeanFactory

    @Bean
    fun websocketRouteWrapper(): WebsocketRouteWrapper {
        return WebsocketRouteWrapper()
    }

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val context = event.applicationContext
        val websocketRouteWrapper = websocketRouteWrapper()
        context.beanDefinitionNames.forEach { beanDefinitionName ->
            val beanDefinition = beanFactory.getBeanDefinition(beanDefinitionName)
            val beanClassName = beanDefinition.beanClassName ?: return@forEach
            val originalClass = ClassUtils.resolveClassName(beanClassName, ClassLoader.getSystemClassLoader())
            for (originalMethod in originalClass.methods) {
                if (originalMethod.isAnnotationPresent(WebsocketCommand::class.java)) {
                    val beanObject = context.getBean(beanDefinitionName)
                    val beanMethod = beanObject.javaClass.getMethod(originalMethod.name, *originalMethod.parameterTypes)
                    val annotation = originalMethod.getAnnotation(WebsocketCommand::class.java)
                    for (parameterType in originalMethod.parameterTypes) {
                        val resolvedClass = ClassUtils.resolveClassName(
                                parameterType.name,
                                ClassLoader.getSystemClassLoader()
                        )
                        if (WebsocketRequest::class.java.isAssignableFrom(resolvedClass)) {
                            websocketRouteWrapper.addRoute(
                                    WebsocketCommandType.getValueFor(annotation.value),
                                    WebsocketRoute(beanMethod, resolvedClass, beanObject)
                            )
                            break
                        }
                    }
                }
            }
        }
    }
}

class WebsocketRouteWrapper {
    private val websocketRoutes = mutableMapOf<WebsocketCommandType, WebsocketRoute>()

    fun addRoute(commandType: WebsocketCommandType, websocketRoute: WebsocketRoute) {
        websocketRoutes[commandType] = websocketRoute
    }

    fun getRoute(commandType: WebsocketCommandType): WebsocketRoute {
        return websocketRoutes[commandType]!!
    }
}

data class WebsocketRoute(val method: Method, val requestClass: Class<*>, val beanObject: Any)