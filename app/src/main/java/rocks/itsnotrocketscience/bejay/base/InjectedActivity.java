package rocks.itsnotrocketscience.bejay.base;

import android.support.v7.app.AppCompatActivity;

import rocks.itsnotrocketscience.bejay.dagger.AppComponent;
import rocks.itsnotrocketscience.bejay.dagger.ComponentProvider;

public abstract class InjectedActivity<T> extends AppCompatActivity
        implements ComponentProvider<T> {

    protected AppComponent getAppComponent() {
        AppApplication application = (AppApplication) getApplication();
        return application.getAppComponent();
    }
}
