package rocks.itsnotrocketscience.bejay.search.presenter;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.music.Pager;
import rocks.itsnotrocketscience.bejay.music.model.Model;
import rocks.itsnotrocketscience.bejay.search.contract.PresenterBase;
import rocks.itsnotrocketscience.bejay.search.contract.SearchContract;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.subjects.PublishSubject;

/**
 * Created by nemi on 20/02/2016.
 */
public class SearchPresenter<T extends Model> extends PresenterBase<SearchContract.View> implements SearchContract.Presenter {


    private Pager<T> search;
    private final Func2<String, Long, Pager<T>> searchFunc;

    @Inject
    public SearchPresenter(Func2<String, Long, Pager<T>> searchFunc) {
        this.searchFunc = searchFunc;
    }

    @Override
    public void search(String query, long pageSize) {
        search = searchFunc.call(query, pageSize);
        getView().setProgressVisible(true);
        search.firstPage()
                .compose(onDetach())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(items -> getView().onSearchResultsLoaded(items),
                        (error) -> getView().showError(),
                        () -> getView().setProgressVisible(false));
    }


    @Override
    public void loadMoreResults() {
        if(search != null) {
            search.nextPage()
                    .compose(onDetach())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(onDetach())
                    .subscribe(items -> getView().onSearchResultsLoaded(items),
                            (error) -> getView().showError());
        }
    }
}
