package rocks.itsnotrocketscience.bejay.music.backends.deezer.api;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.music.Api;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.model.ModelMapper;
import rx.Observable;

public class Track implements Api.Track {

    private final rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Track api;

    @Inject
    public Track(rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Track api) {
        this.api = api;
    }

    @Override
    public Observable<rocks.itsnotrocketscience.bejay.music.model.Track> get(String id) {
        return api.get(Long.valueOf(id)).map(ModelMapper::map);
    }
}
