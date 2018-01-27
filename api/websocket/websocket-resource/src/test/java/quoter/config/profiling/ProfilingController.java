package quoter.config.profiling;

/**
 * Created by Arthur Asatryan.
 * Date: 1/25/18
 * Time: 11:24 PM
 */
public class ProfilingController implements ProfilingControllerMBean {
    private boolean enabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
}
