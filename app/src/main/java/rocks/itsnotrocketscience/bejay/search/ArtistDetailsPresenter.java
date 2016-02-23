package rocks.itsnotrocketscience.bejay.search;

import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.search.model.Album;
import rocks.itsnotrocketscience.bejay.search.model.Artist;
import rocks.itsnotrocketscience.bejay.search.model.ArtistDetails;
import rocks.itsnotrocketscience.bejay.search.model.Track;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.subjects.BehaviorSubject;

public class ArtistDetailsPresenter implements ArtistDetailsContract.Presenter {

    ArtistDetailsContract.View view;
    rocks.itsnotrocketscience.bejay.deezer.api.Artist artistApi;
    Func1<rocks.itsnotrocketscience.bejay.deezer.model.Track, Track> trackMapper;
    Func1<rocks.itsnotrocketscience.bejay.deezer.model.Album, Album> albumMapper;

    @Inject
    public ArtistDetailsPresenter(rocks.itsnotrocketscience.bejay.deezer.api.Artist artistApi,
                                  Func1<rocks.itsnotrocketscience.bejay.deezer.model.Track, Track> trackMapper,
                                  Func1<rocks.itsnotrocketscience.bejay.deezer.model.Album, Album> albumMapper) {
        this.artistApi = artistApi;
        this.trackMapper = trackMapper;
        this.albumMapper = albumMapper;
    }

    @Override
    public void loadArtist(Artist artist) {
        Long id = Long.valueOf(artist.getId());
        Observable.combineLatest(loadTopTracks(id), loadDiscography(id), (topTracks, discography) -> {
            ArtistDetails artistDetails = new ArtistDetails();
            artistDetails.setArtist(artist);
            artistDetails.setTopTracks(topTracks);
            artistDetails.setDiscography(discography);
            return artistDetails;
        }).first().observeOn(AndroidSchedulers.mainThread())
                .subscribe(artistDetails -> view.onLoaded(artistDetails));

    }

    private Observable<List<Track>> loadTopTracks(Long artistId) {
        return artistApi.top(artistId) // get top tracks from deezer api
                .flatMap( trackCollection -> Observable.from(trackCollection.getData())) // emmit each item in the collection one at a time
                .map(trackMapper) // map to local model
                .collect(() -> new ArrayList<Track>(), (topTracks, track) -> topTracks.add(track)); // collection result
    }

    private Observable<List<Album>> loadDiscography(Long artistId) {
        final BehaviorSubject<Long> nextIndex = BehaviorSubject.create();
        nextIndex.onNext(null);
        return nextIndex.flatMap(index -> artistApi.albums(artistId, index)).doOnNext(response -> onNext(response.getNext(), nextIndex))
                .flatMap(albumPageResponse -> Observable.from(albumPageResponse.getData()))
                .map(albumMapper)
                .collect(() -> new ArrayList<Album>(), (albums, album) -> albums.add(album));
    }

    private void onNext(String next, Observer<Long> nextObserver) {
        if(!TextUtils.isEmpty(next)) {
            String index = Uri.parse(next).getQueryParameter("index");
            if (!TextUtils.isEmpty(index)) {
                nextObserver.onNext(Long.valueOf(index));
            } else {
                nextObserver.onCompleted();
            }
        } else {
            nextObserver.onCompleted();
        }
    }

    @Override
    public void onViewAttached(ArtistDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        this.view = null;
    }
}
