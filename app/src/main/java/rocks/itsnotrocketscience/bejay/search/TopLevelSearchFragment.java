package rocks.itsnotrocketscience.bejay.search;

import android.app.SearchManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.base.InjectedActivity;
import rocks.itsnotrocketscience.bejay.base.InjectedFragment;
import rocks.itsnotrocketscience.bejay.search.contracat.TopLevelSearchContract;
import rocks.itsnotrocketscience.bejay.search.di.SearchComponent;

public class TopLevelSearchFragment extends InjectedFragment<SearchComponent> implements TopLevelSearchContract.View {

    @Inject TopLevelSearchContract.Presenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent().inject(this);
    }

    @Override
    public void showSearchResults(TopLevelSearchContract.SearchResult searchResult) {

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onViewAttached(this);
        presenter.search(getArguments().getString(SearchManager.QUERY), 5);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.onViewDetached();
    }

    @Override
    public SearchComponent getComponent() {
        InjectedActivity<SearchComponent> activity = (InjectedActivity<SearchComponent>) getActivity();
        return activity.getComponent();
    }
}
