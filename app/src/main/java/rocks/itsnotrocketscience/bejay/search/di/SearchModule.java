package rocks.itsnotrocketscience.bejay.search.di;

import android.content.Context;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import dagger.Module;
import dagger.Provides;
import rocks.itsnotrocketscience.bejay.music.Api;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.Search;
import rocks.itsnotrocketscience.bejay.music.model.Album;
import rocks.itsnotrocketscience.bejay.music.model.Artist;
import rocks.itsnotrocketscience.bejay.music.model.Playlist;
import rocks.itsnotrocketscience.bejay.music.model.Track;
import rocks.itsnotrocketscience.bejay.search.contract.AlbumDetailsContract;
import rocks.itsnotrocketscience.bejay.search.contract.PlaylistDetailsContract;
import rocks.itsnotrocketscience.bejay.search.contract.TopLevelSearchContract;
import rocks.itsnotrocketscience.bejay.search.presenter.AlbumDetailsPresenter;
import rocks.itsnotrocketscience.bejay.search.presenter.PlaylistDetailsPresenter;
import rocks.itsnotrocketscience.bejay.search.presenter.SearchPresenter;
import rocks.itsnotrocketscience.bejay.search.presenter.TopLevelSearchPresenter;
import rocks.itsnotrocketscience.bejay.view.CircleImageTransformation;

@Module
public class SearchModule {
    private final Context context;

    public SearchModule(Context context) {
        this.context = context;
    }

    @Provides Picasso providesPicasso() {
        return Picasso.with(context);
    }

    @Provides
    Transformation providesCircleTransformation() {
        return new CircleImageTransformation();
    }


    @Provides Api.Search providesSearchApi(rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Search searchApi) {
        return new Search(searchApi);
    }

    @Provides Api.Artist providesArtistApi(rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Artist artistApi) {
        return new rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Artist(artistApi);
    }

    @Provides Api.Album providesAlbumApi(rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Album albumApi) {
        return new rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Album(albumApi);
    }

    @Provides Api.Playlist providesPlaylistApi(rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Playlist playlistApi) {
        return new rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Playlist(playlistApi);
    }

    @Provides TopLevelSearchContract.Presenter providesTopLevelSearchPresenter(TopLevelSearchPresenter presenter) {
        return presenter;
    }

    @Provides
    PlaylistDetailsContract.Presenter providesPlaylistDetailsPresenter(Api.Playlist api) {
        return new PlaylistDetailsPresenter(api);
    }

    @Provides
    AlbumDetailsContract.Presenter providesAlbumDetailsPresenter(Api.Album api) {
        return new AlbumDetailsPresenter(api);
    }

    @Provides
    SearchPresenter<Track> providesTrackSearchPresenter(Api.Search search) {
        return new SearchPresenter<>(search::track);
    }

    @Provides
    SearchPresenter<Album> providesAlbumSearchPresenter(Api.Search search) {
        return new SearchPresenter<>(search::album);
    }

    @Provides
    SearchPresenter<Artist> providesArtistSearchPresenter(Api.Search search) {
        return new SearchPresenter<>(search::artist);
    }

    @Provides
    SearchPresenter<Playlist> providesPlayliststSearchPresenter(Api.Search search) {
        return new SearchPresenter<>(search::playlist);
    }
}
