package rocks.itsnotrocketscience.bejay.tracks;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.tracks.search.TrackSearch;
import rocks.itsnotrocketscience.bejay.tracks.search.TrackSearchContract;
import rocks.itsnotrocketscience.bejay.tracks.search.TrackSearchManager;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;

/**
 * Created by nemi on 20/02/2016.
 */
public class SearchPresenter implements TrackSearchContract.Presenter {


    private final TrackSearchManager trackSearchManager;
    private TrackSearchContract.View view;
    private TrackSearch trackSearch;

    private final PublishSubject<Boolean> onDestroy = PublishSubject.create();

    @Inject
    public SearchPresenter(TrackSearchManager trackSearchManager) {
        this.trackSearchManager = trackSearchManager;
    }

    @Override
    public void loadSearchSuggestions(String query) {
        //TODO: implement
    }

    @Override
    public void search(String query, int pageSize) {
        if(trackSearch != null) {
            // TODO: reset current search
        }
        trackSearch = trackSearchManager.newSearch(query, pageSize);
        loadMoreResults();

    }

    @Override
    public void loadMoreResults() {
        if(trackSearch != null) {
            trackSearch.loadMoreResults()
                    .compose(newOnDestroyTransformer())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(tracks -> {
                        view.onSearchResultsLoaded(tracks);
                    });
        }
    }

    @Override
    public void onViewAttached(TrackSearchContract.View view) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        onDestroy.onNext(true);
    }

    private <T> Observable.Transformer<T, T> newOnDestroyTransformer() {
        return source -> source.takeUntil(onDestroy);
    }

}
