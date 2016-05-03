package rx.android.schedulers;

import java.util.concurrent.Executor;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by nemi on 02/05/16.
 */
public class AndroidSchedulers {
    public static final Scheduler IN_PLACE_SCHEDULER = Schedulers.from(new Executor() {
        @Override
        public void execute(Runnable command) {
            command.run();
        }
    });

    public static Scheduler mainThread() {
        return IN_PLACE_SCHEDULER;
    }
}
