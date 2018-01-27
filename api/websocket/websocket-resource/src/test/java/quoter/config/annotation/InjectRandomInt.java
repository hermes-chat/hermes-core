package quoter.config.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Arthur Asatryan.
 * Date: 1/25/18
 * Time: 10:21 PM
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectRandomInt {

    int min();

    int max();
}
