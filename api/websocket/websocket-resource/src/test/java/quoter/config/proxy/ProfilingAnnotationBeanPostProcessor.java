package quoter.config.proxy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import quoter.config.annotation.Profiling;
import quoter.config.profiling.ProfilingController;

import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arthur Asatryan.
 * Date: 1/25/18
 * Time: 11:02 PM
 */
public class ProfilingAnnotationBeanPostProcessor implements BeanPostProcessor {

    private ProfilingController controller = new ProfilingController();

    private Map<String, Class> beanNameToClass = new HashMap<>();

    public ProfilingAnnotationBeanPostProcessor() throws Exception {
        ManagementFactory.getPlatformMBeanServer()
                .registerMBean(
                        controller,
                        new ObjectName("profiling", "name", "controller")
                );
    }

    @Override
    public Object postProcessBeforeInitialization(final Object o, final String s) throws BeansException {
        final Class<?> aClass = o.getClass();
        if (aClass.isAnnotationPresent(Profiling.class)) {
            beanNameToClass.put(s, aClass);
        }
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(final Object o, final String s) throws BeansException {
        final Class beanClass = beanNameToClass.get(s);
        if (beanClass != null) {
            return Proxy.newProxyInstance(o.getClass().getClassLoader(), o.getClass().getInterfaces(), (proxy, method, args) -> {
                if (controller.isEnabled()) {
                    System.out.println("Start profiling...");
                    final long before = System.nanoTime();
                    final Object invoke = method.invoke(o, args);
                    final long after = System.nanoTime();
                    System.out.println("Time interval - " + (after - before));
                    System.out.println("Profiling is done!");
                    return invoke;
                } else {
                    return method.invoke(o, args);
                }
            });
        }
        return o;
    }
}
