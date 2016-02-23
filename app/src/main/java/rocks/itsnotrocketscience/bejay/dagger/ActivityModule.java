package rocks.itsnotrocketscience.bejay.dagger;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.dao.EventsDao;
import rocks.itsnotrocketscience.bejay.event.list.EventListContract;
import rocks.itsnotrocketscience.bejay.event.list.EventListPresenterImpl;
import rocks.itsnotrocketscience.bejay.event.single.EventContract;
import rocks.itsnotrocketscience.bejay.event.single.EventPresenterImpl;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;
import rocks.itsnotrocketscience.bejay.managers.AppLauncher;
import rocks.itsnotrocketscience.bejay.managers.Launcher;
import rocks.itsnotrocketscience.bejay.search.ModelAdapter;
import rocks.itsnotrocketscience.bejay.search.ModelViewFactory;
import rocks.itsnotrocketscience.bejay.search.ModelViewHolderFactory;
import rocks.itsnotrocketscience.bejay.search.SearchContract;
import rocks.itsnotrocketscience.bejay.search.SearchPresenter;
import rocks.itsnotrocketscience.bejay.search.SearchProvider;
import rocks.itsnotrocketscience.bejay.search.model.Album;
import rocks.itsnotrocketscience.bejay.search.model.Artist;
import rocks.itsnotrocketscience.bejay.search.model.Track;
import rocks.itsnotrocketscience.bejay.view.CircleImageTransformation;

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

    @Provides SearchContract.Presenter<Track> providesTrackSearchPresenter(SearchProvider<Track> search) {
        return new SearchPresenter<>(search);
    }

    @Provides SearchContract.Presenter<Artist> providesArtistSearchPresenter(SearchProvider<Artist> search) {
        return new SearchPresenter<>(search);
    }

    @Provides SearchContract.Presenter<Album> providesAlbumSearchPresenter(SearchProvider<Album> search) {
        return new SearchPresenter<>(search);
    }

    @Provides LayoutInflater providesLayoutInflater() {
        return activity.getLayoutInflater();
    }

    @Provides Resources providesResources() {
        return activity.getResources();
    }

    @Provides ModelAdapter providesTrackModelAdapter(ModelViewFactory viewFactory, ModelViewHolderFactory viewHolderFactory) {
        return new ModelAdapter(viewFactory, viewHolderFactory);
    }


    @Provides Picasso providesPicasso() {
        return Picasso.with(activity);
    }

    @Provides Transformation providesCircleTransformation() {
        return new CircleImageTransformation();
    }
}
