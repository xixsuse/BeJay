package rocks.itsnotrocketscience.bejay.dagger;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import dagger.Module;
import dagger.Provides;
import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.dao.EventsDao;
import rocks.itsnotrocketscience.bejay.event.list.EventListContract;
import rocks.itsnotrocketscience.bejay.event.list.EventListPresenterImpl;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;
import rocks.itsnotrocketscience.bejay.managers.AppLauncher;
import rocks.itsnotrocketscience.bejay.managers.Launcher;

@Module
public class ActivityModule {
    private final AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides @PerActivity Launcher providesLauncher(SharedPreferences sharedPreferences) {
        return new AppLauncher(activity, sharedPreferences);
    }

    @Provides EventListContract.EventListPresenter providesEventListPresenter(EventsDao eventsDao, Events networkEvents, AccountManager accountManager) {
        return new EventListPresenterImpl(eventsDao, networkEvents, accountManager);
    }
}
