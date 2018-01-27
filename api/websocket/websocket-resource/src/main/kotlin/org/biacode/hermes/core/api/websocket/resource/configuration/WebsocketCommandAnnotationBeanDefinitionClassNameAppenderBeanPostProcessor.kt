package org.biacode.hermes.core.api.websocket.resource.configuration

import org.biacode.hermes.core.api.websocket.resource.configuration.annotation.WebsocketCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory
import org.springframework.context.annotation.Configuration

/**
 * Created by Arthur Asatryan.
 * Date: 1/27/18
 * Time: 9:33 PM
 *
 * This BPP will fix bean definition class name nullability issue for classes
 * which are containing method annotated with WebsocketCommand.
 */
@Configuration
class WebsocketCommandAnnotationBeanDefinitionClassNameAppenderBeanPostProcessor : BeanPostProcessor {

    //region Dependencies
    @Autowired
    private lateinit var configurableListableBeanFactory: ConfigurableListableBeanFactory
    //endregion

    //region Public methods
    override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any {
        if (classContainsMethodWithAnnotation(bean, WebsocketCommand::class.java)) {
            val beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanName)
            if (beanDefinition.beanClassName == null) {
                beanDefinition.beanClassName = bean.javaClass.canonicalName
            }
        }
        return bean
    }

    override fun postProcessAfterInitialization(bean: Any, beanName: String): Any {
        return bean
    }
    //endregion

    //region Utility methods
    private fun classContainsMethodWithAnnotation(bean: Any, clazz: Class<out Annotation>) = bean.javaClass.methods
            .any { it.isAnnotationPresent(clazz) }
    //endregion
}