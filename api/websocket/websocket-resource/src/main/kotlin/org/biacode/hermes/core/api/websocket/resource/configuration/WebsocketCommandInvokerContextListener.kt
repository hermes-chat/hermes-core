package org.biacode.hermes.core.api.websocket.resource.configuration

import org.biacode.hermes.core.api.websocket.resource.configuration.annotation.WebsocketCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import java.lang.reflect.Method

/**
 * Created by Arthur Asatryan.
 * Date: 1/26/18
 * Time: 12:44 AM
 */
@Configuration
class WebsocketCommandInvokerContextListener : ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private lateinit var factory: ConfigurableListableBeanFactory

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        val context = event.applicationContext
        val websocketPathRoute: MutableMap<String, Pair<Any, Method>> = mutableMapOf()
        for (beanDefinitionName in context.beanDefinitionNames) {
            val beanDefinition = factory.getBeanDefinition(beanDefinitionName)
            val beanClassName = beanDefinition.beanClassName
            if (beanClassName != null) {
                val originalClass = Class.forName(beanClassName)
                if (originalClass != null) {
                    for (method in originalClass.methods) {
                        if (method.isAnnotationPresent(WebsocketCommand::class.java)) {
                            val annotation = method.getAnnotation(WebsocketCommand::class.java)
                            val bean = context.getBean(beanDefinitionName)
                            val proxyMethod = bean.javaClass.getMethod(method.name, *method.parameterTypes)
                            websocketPathRoute[annotation.value] = bean to proxyMethod
//                    proxyMethod.invoke(bean)
                        }
                    }
                }
            }
        }
        factory.registerSingleton("websocketPathRouter", websocketPathRoute)
    }
}