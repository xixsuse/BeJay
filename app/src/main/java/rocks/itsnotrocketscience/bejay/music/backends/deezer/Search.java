package rocks.itsnotrocketscience.bejay.music.backends.deezer;

import rocks.itsnotrocketscience.bejay.music.Api;
import rocks.itsnotrocketscience.bejay.music.Pager;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.api.SearchResultPager;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.model.ModelMapper;
import rocks.itsnotrocketscience.bejay.music.model.Album;
import rocks.itsnotrocketscience.bejay.music.model.Playlist;
import rocks.itsnotrocketscience.bejay.music.model.Track;


public class Search implements Api.Search {
    private final rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Search api;

    public Search(rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Search api) {
        this.api = api;
    }

    @Override
    public Pager<Track> track(String query, Long pageSize) {
        return new SearchResultPager<>(api::track, track -> ModelMapper.map(track), query, pageSize);
    }

    @Override
    public Pager<Album> album(String query, Long pageSize) {
        return new SearchResultPager<>(api::album, album -> ModelMapper.map(album), query, pageSize);
    }

    @Override
    public Pager<rocks.itsnotrocketscience.bejay.music.model.Artist> artist(String query, Long pageSize) {
        return new SearchResultPager<>(api::artist, artist -> ModelMapper.map(artist), query, pageSize);
    }

    @Override
    public Pager<Playlist> playlist(String query, Long pageSize) {
        return new SearchResultPager<>(api::playlist, playlist -> ModelMapper.map(playlist), query, pageSize);
    }
}
