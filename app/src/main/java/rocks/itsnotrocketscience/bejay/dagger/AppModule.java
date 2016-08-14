package rocks.itsnotrocketscience.bejay.dagger;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.RestAdapter;
import rocks.itsnotrocketscience.bejay.BuildConfig;
import rocks.itsnotrocketscience.bejay.api.ApiManager;
import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.db.ModelDbHelper;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Album;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Artist;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Playlist;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Search;
import rocks.itsnotrocketscience.bejay.gcm.GcmUtils;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;

/**
 * Created by lduf0001 on 21/12/15.
 */
@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton Application providesApplication() {
        return application;
    }

    @Provides @Singleton Context providesContext() {
        return application;
    }

    @Provides @Singleton SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides @Singleton AccountManager providesAccountManager(SharedPreferences sharedPreferences) {
        return new AccountManager(sharedPreferences);
    }

    @Provides @Singleton SqlBrite providesSqlBrite() {
        return SqlBrite.create();
    }

    @Provides @Singleton BriteDatabase providesDb(SqlBrite sqlBrite, Context context) {
        return sqlBrite.wrapDatabaseHelper(new ModelDbHelper(context));
    }

    @Provides @Singleton Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides @Singleton ApiManager provideRetrofitManager(Application application, AccountManager accountManager) {
        return new ApiManager(application,accountManager);
    }

    @Provides @Singleton Events providesEventsApi(ApiManager apiManager) {
        return apiManager.events();
    }

    @Provides @Singleton GcmUtils providesGcmUtils(SharedPreferences sharedPreferences) {
        return new GcmUtils(sharedPreferences);
    }

    @Provides @Deezer @Singleton RestAdapter providesDeezerRestAdapter() {
        return new RestAdapter.Builder()
                .setEndpoint(Deezer.API_BASE_URL)
                .setLogLevel(BuildConfig.RETROFIT_LOG_LEVEL)
                .build();
    }

    @Provides @Singleton Search providesDeezerSearchApi(@Deezer RestAdapter restAdapter) {
        return restAdapter.create(Search.class);
    }

    @Provides @Singleton
    Artist providesDeezerArtistApi(@Deezer RestAdapter restAdapter) {
        return restAdapter.create(Artist.class);
    }

    @Provides @Singleton
    Album providesDeezerAlbumApi(@Deezer RestAdapter restAdapter) {
        return restAdapter.create(Album.class);
    }

    @Provides @Singleton Playlist providesDeezerPlaylistApi(@Deezer RestAdapter restAdapter) {
        return restAdapter.create(Playlist.class);
    }

}
