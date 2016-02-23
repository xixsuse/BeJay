package rocks.itsnotrocketscience.bejay.search;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.PublishSubject;

/**
 * Created by nemi on 20/02/2016.
 */
public class SearchPresenter<T> implements SearchContract.Presenter<T> {


    private final SearchProvider<T> trackSearchProvider;
    private SearchContract.View view;
    private Search<T> trackSearch;

    private final PublishSubject<Boolean> onDestroy = PublishSubject.create();

    @Inject
    public SearchPresenter(SearchProvider<T> trackSearchProvider) {
        this.trackSearchProvider = trackSearchProvider;
    }

    @Override
    public void search(String query, int pageSize) {
        if(trackSearch != null) {
            // TODO: reset current search ???
        }
        trackSearch = trackSearchProvider.newSearch(query, pageSize);
        loadMoreResults();

    }

    @Override
    public void loadMoreResults() {
        if(trackSearch != null) {
            trackSearch.loadNextPage()
                    .compose(newOnDestroyTransformer())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(tracks -> {
                        view.onSearchResultsLoaded(tracks);
                    });
        }
    }

    @Override
    public void onViewAttached(SearchContract.View view) {
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
