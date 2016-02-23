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
import rocks.itsnotrocketscience.bejay.search.DeezerSearchProvider;
import rocks.itsnotrocketscience.bejay.search.SearchProvider;
import rocks.itsnotrocketscience.bejay.search.model.Album;
import rocks.itsnotrocketscience.bejay.search.model.Artist;
import rocks.itsnotrocketscience.bejay.db.ModelDbHelper;
import rocks.itsnotrocketscience.bejay.deezer.Deezer;
import rocks.itsnotrocketscience.bejay.deezer.api.Search;
import rocks.itsnotrocketscience.bejay.gcm.GcmUtils;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;
import rocks.itsnotrocketscience.bejay.search.model.Playlist;
import rocks.itsnotrocketscience.bejay.search.model.Track;
import rx.functions.Func1;

/**
 * Created by lduf0001 on 21/12/15.
 */
@Module
public class AppModule {

    Application application;

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

    @Provides @Singleton Search providesSearch(@Deezer RestAdapter restAdapter) {
        return restAdapter.create(Search.class);
    }

    @Provides @Singleton
    Func1<rocks.itsnotrocketscience.bejay.deezer.model.Track, Track> providesTrackModelMapper() {
        return deezerTrack -> {
            Track track = new Track();
            track.setTitle(deezerTrack.getTitle());
            track.setDuration(deezerTrack.getDuration());
            track.setArtist(deezerTrack.getArtist().getName());
            track.setAlbumName(deezerTrack.getAlbum().getTitle());
            track.setId(deezerTrack.getId().toString());
            track.setCover(deezerTrack.getArtist().getPicture());
            track.setProvider(Deezer.PROVIDER_NAME);
            return track;
        };
    }

    @Provides @Singleton Func1<rocks.itsnotrocketscience.bejay.deezer.model.Album, Album> providesAlbumMapper() {
        return deezerAlbum -> {
            Album album = new Album();
            album.setProvider(Deezer.PROVIDER_NAME);
            album.setId(deezerAlbum.getId().toString());
            album.setArtist(deezerAlbum.getArtist().getName());
            album.setTitle(deezerAlbum.getTitle());
            album.setCover(deezerAlbum.getCover());
            return album;
        };
    }

    @Provides @Singleton Func1<rocks.itsnotrocketscience.bejay.deezer.model.Artist, Artist> providesArtistMapper() {
        return deezerArtist -> {
            Artist artist = new Artist();
            artist.setId(deezerArtist.getId().toString());
            artist.setName(deezerArtist.getName());
            artist.setPicture(deezerArtist.getPicture());
            artist.setProvider(Deezer.PROVIDER_NAME);
            return artist;
        };
    }

    @Provides @Singleton Func1<rocks.itsnotrocketscience.bejay.deezer.model.Playlist, Playlist> providesPlaylistMapper() {
        return deezerPlaylist -> {
            Playlist playlist = new Playlist();
            playlist.setProvider(Deezer.PROVIDER_NAME);
            playlist.setId(deezerPlaylist.getId().toString());
            playlist.setTitle(deezerPlaylist.getTitle());
            playlist.setNumberOfTracks(deezerPlaylist.getNumberOfTracks());
            playlist.setPicture(deezerPlaylist.getPicture());
            playlist.setPublic(deezerPlaylist.isPublic());
            playlist.setUser(deezerPlaylist.getUser().getName());
            return playlist;
        };
    }

    @Provides
    SearchProvider<Track> providesTrackSearchFactory(rocks.itsnotrocketscience.bejay.deezer.api.Search searchApi,
                                                     Func1<rocks.itsnotrocketscience.bejay.deezer.model.Track, Track> mapper) {
        return new DeezerSearchProvider<>(searchApi::track, mapper);
    }

    @Provides
    SearchProvider<Artist> providesArtistSearchFactory(rocks.itsnotrocketscience.bejay.deezer.api.Search searchApi,
                                                       Func1<rocks.itsnotrocketscience.bejay.deezer.model.Artist, Artist> mapper) {
        return new DeezerSearchProvider<>(searchApi::artist, mapper);
    }

    @Provides
    SearchProvider<Album> providesAlbumSearchFactory(rocks.itsnotrocketscience.bejay.deezer.api.Search searchApi,
                                                     Func1<rocks.itsnotrocketscience.bejay.deezer.model.Album, Album> mapper) {
        return new DeezerSearchProvider<>(searchApi::album, mapper);
    }

    @Provides
    SearchProvider<Playlist> providesPlaylistSearchFactory(rocks.itsnotrocketscience.bejay.deezer.api.Search searchApi,
                                                     Func1<rocks.itsnotrocketscience.bejay.deezer.model.Playlist, Playlist> mapper) {
        return new DeezerSearchProvider<>(searchApi::playlist, mapper);
    }
}
