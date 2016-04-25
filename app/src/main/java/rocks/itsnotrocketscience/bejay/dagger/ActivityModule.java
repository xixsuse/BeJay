package rocks.itsnotrocketscience.bejay.dagger;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;


import dagger.Module;
import dagger.Provides;
import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.dao.EventsDao;
import rocks.itsnotrocketscience.bejay.event.create.EventCreateContract;
import rocks.itsnotrocketscience.bejay.event.create.EventCreatePresenterImpl;
import rocks.itsnotrocketscience.bejay.event.list.EventListContract;
import rocks.itsnotrocketscience.bejay.event.list.EventListPresenterImpl;
import rocks.itsnotrocketscience.bejay.event.single.EventContract;
import rocks.itsnotrocketscience.bejay.event.single.EventPresenterImpl;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;
import rocks.itsnotrocketscience.bejay.managers.AppLauncher;
import rocks.itsnotrocketscience.bejay.managers.Launcher;
import rocks.itsnotrocketscience.bejay.map.MapContract;
import rocks.itsnotrocketscience.bejay.map.MapPresenterImpl;
import rocks.itsnotrocketscience.bejay.search.ModelAdapter;
import rocks.itsnotrocketscience.bejay.search.view.ModelViewFactory;
import rocks.itsnotrocketscience.bejay.search.view.ModelViewHolderFactory;
import rocks.itsnotrocketscience.bejay.widgets.DatePickerDialogFragment;
import rocks.itsnotrocketscience.bejay.widgets.TimePickerDialogFragment;

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

    @Provides EventContract.EventPresenter providesEventPresenter(Events networkEvent) {
        return new EventPresenterImpl(networkEvent);
    }

    @Provides EventCreateContract.EventCreatePresenter providesEventCreatePresenter(Events networkEvent) {
        return new EventCreatePresenterImpl(networkEvent);
    }

    @Provides MapContract.MapPresenter providesMapPresenter(Launcher launcher) {
        return new MapPresenterImpl(activity, launcher);
    }

    @Provides DatePickerDialogFragment providesDatePicker() {
        return DatePickerDialogFragment.newInstance();
    }

    @Provides TimePickerDialogFragment providesTimePicker() {
        return TimePickerDialogFragment.newInstance();
    }

    @Provides LayoutInflater providesLayoutInflater() {
        return activity.getLayoutInflater();
    }

    @Provides Resources providesResources() {
        return activity.getResources();
    }

    @Provides ModelAdapter providesModelAdapter(ModelViewFactory viewFactory, ModelViewHolderFactory viewHolderFactory) {
        return new ModelAdapter(viewFactory, viewHolderFactory);
    }
}
