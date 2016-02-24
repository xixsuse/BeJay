package rocks.itsnotrocketscience.bejay.search;

import rocks.itsnotrocketscience.bejay.base.InjectedActivity;
import rocks.itsnotrocketscience.bejay.base.InjectedFragment;
import rocks.itsnotrocketscience.bejay.search.di.SearchComponent;

public class BaseFragment extends InjectedFragment<SearchComponent> {
    @Override
    public SearchComponent getComponent() {
        InjectedActivity<SearchComponent> activity = (InjectedActivity<SearchComponent>) getActivity();
        return activity.getComponent();
    }
}
