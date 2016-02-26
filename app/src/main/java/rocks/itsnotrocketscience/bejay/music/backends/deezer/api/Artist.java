package rocks.itsnotrocketscience.bejay.music.backends.deezer.api;

import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.music.Api;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.model.ModelMapper;
import rocks.itsnotrocketscience.bejay.music.model.Album;
import rocks.itsnotrocketscience.bejay.music.model.Track;
import rx.Observable;
import rx.subjects.BehaviorSubject;

import static rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer.INDEX;

public class Artist implements Api.Artist {
    private rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Artist api;

    @Inject
    public Artist(rocks.itsnotrocketscience.bejay.music.backends.deezer.restapi.Artist api) {
        this.api = api;
    }

    @Override
    public Observable<List<Track>> topTracks(String id) {
        return api.top(Long.valueOf(id)) // get top tracks from deezer api
                .flatMap( trackCollection -> Observable.from(trackCollection.getData())) // emmit each item in the collection one at a time
                .map(track -> ModelMapper.map(track)) // map to local model
                .collect(() -> new ArrayList<Track>(), (topTracks, track) -> topTracks.add(track)); // collection result
    }

    @Override
    public Observable<List<Album>> albums(String id) {
        final BehaviorSubject<Long> nextIndex = BehaviorSubject.create();
        nextIndex.onNext(null);
        return nextIndex.flatMap(index -> api.albums(Long.valueOf(id), index)).doOnNext(response -> {
            String next = response.getNext();
            if(!TextUtils.isEmpty(next)) {
                String index = Uri.parse(next).getQueryParameter(INDEX);
                if (!TextUtils.isEmpty(index)) {
                    nextIndex.onNext(Long.valueOf(index));
                } else {
                    nextIndex.onCompleted();
                }
            } else {
                nextIndex.onCompleted();
            }
        }).flatMap(albumPageResponse -> Observable.from(albumPageResponse.getData()))
                .map(album -> ModelMapper.map(album))
                .collect(() -> new ArrayList<Album>(), (albums, album) -> albums.add(album));
    }

    @Override
    public Observable<rocks.itsnotrocketscience.bejay.music.model.Artist> get(String id) {
        return api.get(Long.valueOf(id))
                .map(deezerArtist -> ModelMapper.map(deezerArtist));
    }
}
