package rocks.itsnotrocketscience.bejay.music.backends.deezer.api;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.music.Api;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.model.ModelMapper;
import rx.Observable;

public class Playlist implements Api.Playlist {

    private final rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Playlist api;

    @Inject
    public Playlist(rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Playlist api) {
        this.api = api;
    }

    @Override
    public Observable<rocks.itsnotrocketscience.bejay.music.model.Playlist> get(String id) {
        return api.get(Long.valueOf(id)).map(ModelMapper::map);
    }
}
