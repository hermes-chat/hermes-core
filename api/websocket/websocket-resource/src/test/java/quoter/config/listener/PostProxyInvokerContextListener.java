package quoter.config.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.ClassUtils;
import quoter.config.annotation.PostProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Arthur Asatryan.
 * Date: 1/26/18
 * Time: 12:16 AM
 */
public class PostProxyInvokerContextListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ConfigurableListableBeanFactory configurableListableBeanFactory;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        final ApplicationContext context = event.getApplicationContext();
        final String[] beanNames = context.getBeanDefinitionNames();
        for (final String beanName : beanNames) {
            final BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanName);
            final Class<?> originalClass = ClassUtils.resolveClassName(beanDefinition.getBeanClassName(), ClassLoader.getSystemClassLoader());
            try {
                final Method[] methods = originalClass.getDeclaredMethods();
                for (final Method method : methods) {
                    if (method.isAnnotationPresent(PostProxy.class)) {
                        final Object bean = context.getBean(beanName);
                        final Method currentMethod = bean.getClass().getMethod(method.getName(), method.getParameterTypes());
                        currentMethod.invoke(bean);
                    }
                }
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
