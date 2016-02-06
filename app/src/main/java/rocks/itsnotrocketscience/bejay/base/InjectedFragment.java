package rocks.itsnotrocketscience.bejay.base;

import android.support.v4.app.Fragment;

import rocks.itsnotrocketscience.bejay.dagger.ComponentProvider;

public abstract class InjectedFragment<T> extends Fragment implements ComponentProvider<T> {
}
