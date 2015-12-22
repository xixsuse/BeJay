package rocks.itsnotrocketscience.bejay.base;

import android.app.Application;
import android.preference.PreferenceManager;

import rocks.itsnotrocketscience.bejay.dagger.AppModule;
import rocks.itsnotrocketscience.bejay.dagger.NetComponent;
import rocks.itsnotrocketscience.bejay.dagger.NetModule;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;

/**
 * Created by centralstation on 11/09/15.
 */
public class AppApplication extends Application {

    private NetComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = rocks.itsnotrocketscience.bejay.dagger.DaggerNetComponent.builder()
                // list of modules that are part of this component need to be created here too
                .appModule(new AppModule(this))
                .netModule(new NetModule("https://api.github.com"))
                .build();

    }

    public NetComponent getNetComponent() {
        return component;
    }
}
