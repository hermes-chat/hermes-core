package quoter.config.scope;

import javafx.util.Pair;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.context.annotation.Configuration;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arthur Asatryan.
 * Date: 1/27/18
 * Time: 4:42 PM
 */
@Configuration
public class PeriodicalScopeConfigurer implements Scope {

    private Map<String, Pair<LocalTime, Object>> map = new HashMap<>();

    @Override
    public Object get(final String s, final ObjectFactory<?> objectFactory) {
        if (map.containsKey(s)) {
            final Pair<LocalTime, Object> pair = map.get(s);
            final int secondsSinceLastRequest = LocalTime.now().getSecond() - pair.getKey().getSecond();
            if (secondsSinceLastRequest > 5) {
                map.put(s, new Pair<>(LocalTime.now(), objectFactory.getObject()));
            }
        } else {
            map.put(s, new Pair<>(LocalTime.now(), objectFactory.getObject()));
        }
        return map.get(s).getValue();
    }

    @Override
    public Object remove(final String s) {
        return null;
    }

    @Override
    public void registerDestructionCallback(final String s, final Runnable runnable) {

    }

    @Override
    public Object resolveContextualObject(final String s) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
