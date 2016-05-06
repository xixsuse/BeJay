package rocks.itsnotrocketscience.bejay.map;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Created by sirfunkenstine on 05/05/16.
 */
public class FetchAddressHandlerThread extends HandlerThread {

    private Handler handler;

    public  FetchAddressHandlerThread( Handler handler) {
        super("FetchAddressHandlerThread");
        this.handler=handler;
    }

    public void postTask(Runnable task){
        handler.postDelayed(task, 1000);
    }

    public void prepareHandler(Handler handler){
       this.handler = handler;
    }

}