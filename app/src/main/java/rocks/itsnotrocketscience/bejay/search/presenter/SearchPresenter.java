package rocks.itsnotrocketscience.bejay.search.presenter;

import com.google.auto.factory.AutoFactory;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.music.Pager;
import rocks.itsnotrocketscience.bejay.music.model.Model;
import rocks.itsnotrocketscience.bejay.search.contracat.SearchContract;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.subjects.PublishSubject;

/**
 * Created by nemi on 20/02/2016.
 */
public class SearchPresenter<T extends Model> implements SearchContract.Presenter {


    private SearchContract.View view;
    private Pager<T> search;
    protected final Func2<String, Long, Pager<T>> searchFunc;

    private final PublishSubject<Boolean> onDestroy = PublishSubject.create();

    @Inject
    public SearchPresenter(Func2<String, Long, Pager<T>> searchFunc) {
        this.searchFunc = searchFunc;
    }

    @Override
    public void search(String query, long pageSize) {
        search = searchFunc.call(query, pageSize);
        search.firstPage()
                .compose(newOnDestroyTransformer())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> view.onSearchResultsLoaded(items));
    }


    @Override
    public void loadMoreResults() {
        if(search != null) {
            search.nextPage()
                    .compose(newOnDestroyTransformer())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(items -> view.onSearchResultsLoaded(items));
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
