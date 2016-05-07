package rocks.itsnotrocketscience.bejay.dagger;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Handler;

import dagger.Module;
import dagger.Provides;
import rocks.itsnotrocketscience.bejay.managers.AppLauncher;
import rocks.itsnotrocketscience.bejay.managers.Launcher;
import rocks.itsnotrocketscience.bejay.map.FetchAddressHandlerThread;
import rocks.itsnotrocketscience.bejay.map.FetchAddressTask;
import rocks.itsnotrocketscience.bejay.map.MapActivity;
import rocks.itsnotrocketscience.bejay.map.MapContract;
import rocks.itsnotrocketscience.bejay.map.MapPresenterImpl;

/**
 * Created by sirfunkenstine on 07/05/16.
 */
@Module
public class MapModule {

    private final MapActivity mapActivity;

    public MapModule(MapActivity mapActivity) {
        this.mapActivity = mapActivity;
    }

    @Provides @PerActivity Launcher providesLauncher(SharedPreferences sharedPreferences) {
        return new AppLauncher(mapActivity, sharedPreferences);
    }

    @Provides MapContract.MapPresenter providesMapPresenter(Launcher launcher, FetchAddressHandlerThread fetchAddressHandlerThread) {
        return new MapPresenterImpl(mapActivity, launcher, fetchAddressHandlerThread);
    }

    @Provides FetchAddressHandlerThread providesHandlerThread(){
        return new FetchAddressHandlerThread();
    }

    @Provides Handler providesHandler(FetchAddressHandlerThread handlerThread,MapContract.MapPresenter mapPresenter ){
        return  new  Handler(handlerThread.getLooper(),mapPresenter);
    }

    @Provides FetchAddressTask providesFetchAddress(Activity activity,Handler handler ){
        return new FetchAddressTask(activity.getApplicationContext(), handler);
    }

}