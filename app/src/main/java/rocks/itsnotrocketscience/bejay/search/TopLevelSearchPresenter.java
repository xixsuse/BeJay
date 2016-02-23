package rocks.itsnotrocketscience.bejay.search;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.search.model.Album;
import rocks.itsnotrocketscience.bejay.search.model.Artist;
import rocks.itsnotrocketscience.bejay.search.model.Model;
import rocks.itsnotrocketscience.bejay.search.model.Playlist;
import rocks.itsnotrocketscience.bejay.search.model.Track;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func0;

public class TopLevelSearchPresenter implements SearchContract.Presenter<Model> {

    SearchContract.View view;
    SearchProvider<Track> trackSearchProvider;
    SearchProvider<Album> albumSearchProvider;
    SearchProvider<Playlist> playlistSearchProvider;
    SearchProvider<Artist> artistSearchProvider;

    @Inject
    public TopLevelSearchPresenter(SearchProvider<Track> trackSearchProvider,
                                   SearchProvider<Album> albumSearchProvider,
                                   SearchProvider<Playlist> playlistSearchProvider,
                                   SearchProvider<Artist> artistSearchProvider) {
        this.trackSearchProvider = trackSearchProvider;
        this.albumSearchProvider = albumSearchProvider;
        this.playlistSearchProvider = playlistSearchProvider;
        this.artistSearchProvider = artistSearchProvider;
    }

    @Override
    public void search(String query, int pageSize) {
        Observable.concat(loadTracks(query, pageSize),
                loadAlbums(query, pageSize),
                loadPlaylists(query, pageSize),
                loadArtists(query, pageSize))
                .collect((Func0<List<Model>>) () -> new ArrayList<Model>(), (models, model) -> models.add(model))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(models -> view.onSearchResultsLoaded(models));
    }

    @Override
    public void loadMoreResults() {

    }

    Observable<Model> loadTracks(String query, int pageSize) {
        return trackSearchProvider.newSearch(query, pageSize)
                .loadNextPage()
                .flatMap(tracks -> Observable.from(tracks));
    }

    Observable<Model> loadAlbums(String query, int pageSize) {
        return albumSearchProvider.newSearch(query, pageSize)
                .loadNextPage()
                .flatMap(albums -> Observable.from(albums));
    }

    Observable<Model> loadPlaylists(String query, int pageSize) {
        return playlistSearchProvider.newSearch(query, pageSize)
                .loadNextPage()
                .flatMap(playlists -> Observable.from(playlists));
    }

    Observable<Model> loadArtists(String query, int pageSize) {
        return artistSearchProvider.newSearch(query, pageSize)
                .loadNextPage()
                .flatMap(artists -> Observable.from(artists));
    }

    @Override
    public void onViewAttached(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        this.view = null;
    }
}
