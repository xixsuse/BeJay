package rocks.itsnotrocketscience.bejay.search.di;

import android.content.Context;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import rocks.itsnotrocketscience.bejay.deezer.Deezer;
import rocks.itsnotrocketscience.bejay.search.DeezerSearchProvider;
import rocks.itsnotrocketscience.bejay.search.SearchContract;
import rocks.itsnotrocketscience.bejay.search.SearchPresenter;
import rocks.itsnotrocketscience.bejay.search.SearchProvider;
import rocks.itsnotrocketscience.bejay.search.model.Album;
import rocks.itsnotrocketscience.bejay.search.model.Artist;
import rocks.itsnotrocketscience.bejay.search.model.Playlist;
import rocks.itsnotrocketscience.bejay.search.model.Track;
import rocks.itsnotrocketscience.bejay.view.CircleImageTransformation;
import rx.functions.Func1;

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


    @Provides SearchContract.Presenter<Track> providesTrackSearchPresenter(SearchProvider<Track> search) {
        return new SearchPresenter<>(search);
    }

    @Provides SearchContract.Presenter<Artist> providesArtistSearchPresenter(SearchProvider<Artist> search) {
        return new SearchPresenter<>(search);
    }

    @Provides SearchContract.Presenter<Album> providesAlbumSearchPresenter(SearchProvider<Album> search) {
        return new SearchPresenter<>(search);
    }

    @Provides SearchContract.Presenter<Playlist> providesPlaylistSearchPresenter(SearchProvider<Playlist> search) {
        return new SearchPresenter<>(search);
    }


    Track map(rocks.itsnotrocketscience.bejay.deezer.model.Track deezerTrack) {
        if(deezerTrack != null) {
            Track track = new Track();
            track.setTitle(deezerTrack.getTitle());
            track.setDuration(deezerTrack.getDuration());
            track.setAlbum(map(deezerTrack.getAlbum()));

            track.setArtist(map(deezerTrack.getArtist()));
            track.setId(deezerTrack.getId().toString());

            track.setProvider(Deezer.PROVIDER_NAME);
            return track;
        }

        return null;
    }

    Artist map(rocks.itsnotrocketscience.bejay.deezer.model.Artist deezerArtist) {
        if(deezerArtist != null) {
            Artist artist = new Artist();
            artist.setId(deezerArtist.getId().toString());
            artist.setName(deezerArtist.getName());
            artist.setPicture(deezerArtist.getPicture());
            artist.setProvider(Deezer.PROVIDER_NAME);
            return artist;
        }

        return null;
    }

    Album map(rocks.itsnotrocketscience.bejay.deezer.model.Album deezerAlbum) {
        if(deezerAlbum != null) {
            Album album = new Album();
            album.setProvider(Deezer.PROVIDER_NAME);
            album.setId(deezerAlbum.getId().toString());
            album.setArtist(map(deezerAlbum.getArtist()));
            album.setTracks(map(deezerAlbum.getTracks()));
            album.setTitle(deezerAlbum.getTitle());
            album.setCover(deezerAlbum.getCover());
            return album;
        }

        return null;
    }

    Playlist map(rocks.itsnotrocketscience.bejay.deezer.model.Playlist deezerPlaylist) {
        if(deezerPlaylist != null) {
            Playlist playlist = new Playlist();
            playlist.setProvider(Deezer.PROVIDER_NAME);
            playlist.setId(deezerPlaylist.getId().toString());
            playlist.setTitle(deezerPlaylist.getTitle());
            playlist.setNumberOfTracks(deezerPlaylist.getNumberOfTracks());
            playlist.setPicture(deezerPlaylist.getPicture());
            playlist.setPublic(deezerPlaylist.isPublic());
            playlist.setUser(deezerPlaylist.getUser().getName());
            playlist.setTracks(map(deezerPlaylist.getTracks()));

        }
        return null;
    }

    List<Track> map(List<rocks.itsnotrocketscience.bejay.deezer.model.Track> deezerTracks) {
        if(deezerTracks != null) {
            List<Track> tracks = new ArrayList<>();
            for(rocks.itsnotrocketscience.bejay.deezer.model.Track deezerTrack : deezerTracks) {
                tracks.add(map(deezerTrack));
            }
            return tracks;
        }

        return null;
    }

    @Provides
    Func1<rocks.itsnotrocketscience.bejay.deezer.model.Track, Track> providesTrackModelMapper() {
        return deezerTrack -> map(deezerTrack);
    }

    @Provides Func1<rocks.itsnotrocketscience.bejay.deezer.model.Album, Album> providesAlbumMapper() {
        return deezerAlbum -> map(deezerAlbum);
    }

    @Provides Func1<rocks.itsnotrocketscience.bejay.deezer.model.Artist, Artist> providesArtistMapper() {
        return deezerArtist -> map(deezerArtist);
    }

    @Provides Func1<rocks.itsnotrocketscience.bejay.deezer.model.Playlist, Playlist> providesPlaylistMapper() {
        return deezerPlaylist -> map(deezerPlaylist);
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
