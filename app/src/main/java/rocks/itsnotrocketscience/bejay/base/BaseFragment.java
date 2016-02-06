package rocks.itsnotrocketscience.bejay.base;

import rocks.itsnotrocketscience.bejay.dagger.ComponentProvider;

/**
 * Created by centralstation on 11/09/15.
 */
public class BaseFragment<T> extends InjectedFragment<T> {

    @Override
    public T getComponent() {
        ComponentProvider<T> componentComponentProvider = (ComponentProvider<T>) getActivity();
        return componentComponentProvider.getComponent();
    }
}
