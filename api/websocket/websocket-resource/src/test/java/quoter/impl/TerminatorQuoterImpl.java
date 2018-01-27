package quoter.impl;

import quoter.Quoter;
import quoter.config.annotation.DeprecatedClass;
import quoter.config.annotation.InjectRandomInt;
import quoter.config.annotation.PostProxy;
import quoter.config.annotation.Profiling;

import javax.annotation.PostConstruct;

/**
 * Created by Arthur Asatryan.
 * Date: 1/25/18
 * Time: 10:06 PM
 */
@DeprecatedClass(newImpl = T1000.class)
@Profiling
public class TerminatorQuoterImpl implements Quoter {

    @InjectRandomInt(min = 2, max = 7)
    private int repeat;

    private String message;

    public TerminatorQuoterImpl() {
        System.out.println("First constructor");
    }

    @PostConstruct
    public void init() {
        System.out.println("Second constructor");
    }


    @PostProxy
    @Override
    public void sayQuote() {
        System.out.println("Third constructor");
        for (int i = 0; i < repeat; i++) {
            System.out.println("message - " + message);
        }
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setRepeat(final int repeat) {
        this.repeat = repeat;
    }
}
