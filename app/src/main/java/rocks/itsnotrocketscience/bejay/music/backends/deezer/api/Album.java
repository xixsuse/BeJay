package rocks.itsnotrocketscience.bejay.music.backends.deezer.api;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.music.Api;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.model.ModelMapper;
import rx.Observable;

public class Album implements Api.Album {

    private final rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Album api;

    @Inject
    public Album(rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Album api) {
        this.api = api;
    }

    @Override
    public Observable<rocks.itsnotrocketscience.bejay.music.model.Album> get(String id) {
        return api.get(Long.valueOf(id)).map(deezerAlbum -> ModelMapper.map(deezerAlbum));
    }
}
