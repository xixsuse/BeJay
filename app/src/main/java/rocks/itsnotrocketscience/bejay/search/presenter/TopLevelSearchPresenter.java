package rocks.itsnotrocketscience.bejay.search.presenter;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.music.Api;
import rocks.itsnotrocketscience.bejay.search.contract.PresenterBase;
import rocks.itsnotrocketscience.bejay.search.contract.TopLevelSearchContract;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

public class TopLevelSearchPresenter extends PresenterBase<TopLevelSearchContract.View> implements TopLevelSearchContract.Presenter{

    private Api.Search api;


    @Inject
    public TopLevelSearchPresenter(Api.Search api) {
        this.api = api;
    }

    @Override
    public void search(String query, long pageSize) {
        Observable.combineLatest(
                api.track(query, pageSize).firstPage(),
                api.album(query, pageSize).firstPage(),
                api.artist(query, pageSize).firstPage(),
                api.playlist(query, pageSize).firstPage(),
                (tracks, albums, artists, playlists) -> {
                    TopLevelSearchContract.SearchResult searchResult = new TopLevelSearchContract.SearchResult();
                    searchResult.setTracks(tracks);
                    searchResult.setAlbums(albums);
                    searchResult.setArtists(artists);
                    searchResult.setPlaylists(playlists);
                    return searchResult;
                }).compose(onDetach()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchResult -> getView().showSearchResults(searchResult));

    }
}
