package quoter.config.proxy;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;
import quoter.config.annotation.InjectRandomInt;

import java.lang.reflect.Field;
import java.util.Random;

/**
 * Created by Arthur Asatryan.
 * Date: 1/25/18
 * Time: 10:26 PM
 */
public class InjectRandomIntAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(final Object o, final String s) throws BeansException {
        final Field[] fields = FieldUtils.getAllFields(o.getClass());
        for (final Field field : fields) {
            final InjectRandomInt annotation = field.getAnnotation(InjectRandomInt.class);
            if (annotation != null) {
                final int min = annotation.min();
                final int max = annotation.max();
                final Random random = new Random();
                int i = min + random.nextInt(max - min);
                field.setAccessible(true);
                ReflectionUtils.setField(field, o, i);
            }
        }
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(final Object o, final String s) throws BeansException {
        return o;
    }
}
