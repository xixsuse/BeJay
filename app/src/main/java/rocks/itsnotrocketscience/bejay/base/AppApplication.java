package rocks.itsnotrocketscience.bejay.base;

import android.app.Application;

import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.event.PlayerState;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;

import rocks.itsnotrocketscience.bejay.dagger.AppComponent;
import rocks.itsnotrocketscience.bejay.dagger.AppModule;
import rocks.itsnotrocketscience.bejay.dagger.DaggerAppComponent;

/**
 * Created by centralstation on 11/09/15.
 */
public class AppApplication extends Application {

    /**
     * App module instantiated at construction time. NOTE: do not use this instance before onCreate is called!!!!
     * */
    private final AppModule appModule;
    private AppComponent appComponent;


    public AppApplication() {
        appModule = new AppModule(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder().appModule(appModule).build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
