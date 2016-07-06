package rocks.itsnotrocketscience.bejay.map;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Created by sirfunkenstine on 05/05/16.
 */
public class FetchAddressHandlerThread extends HandlerThread {
    private Handler handler;

    public FetchAddressHandlerThread() {
        super("FetchAddressHandlerThread");
    }

    public void cancelTask(Runnable task) {
        getHandler().removeCallbacks(task);
    }

    public void postTask(Runnable task) {
        getHandler().postDelayed(task, 2500);
    }

    private Handler getHandler() {
        synchronized (this) {
            if (handler == null) {
                this.handler = new Handler(getLooper());
            }
        }
        return handler;
    }

}