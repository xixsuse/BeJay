package rocks.itsnotrocketscience.bejay.dagger;

import android.content.Context;
import android.content.SharedPreferences;

import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Singleton;

import dagger.Component;
import rocks.itsnotrocketscience.bejay.api.ApiManager;
import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.gcm.RegistrationIntentService;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;
import rocks.itsnotrocketscience.bejay.search.SearchFactory;
import rocks.itsnotrocketscience.bejay.search.model.Album;
import rocks.itsnotrocketscience.bejay.search.model.Artist;
import rocks.itsnotrocketscience.bejay.search.model.Track;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    SharedPreferences defaultSharedPreferences();
    AccountManager accountManager();
    ApiManager apiManager();
    Context context();
    BriteDatabase database();
    Events events();
    SearchFactory<Track> trackSearchProvider();
    SearchFactory<Artist> artistSearchProvider();
    SearchFactory<Album> albumSearchProvider();

    void inject(RegistrationIntentService registrationIntentService);
}
