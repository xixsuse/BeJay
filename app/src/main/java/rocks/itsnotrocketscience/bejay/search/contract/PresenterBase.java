package rocks.itsnotrocketscience.bejay.search.contract;

import rx.Observable;
import rx.subjects.PublishSubject;

public class PresenterBase<V> implements Presenter<V> {
    private final PublishSubject<Boolean> onDetach = PublishSubject.create();
    private V view;

    @Override
    public void onViewAttached(V view) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        onDetach.onNext(true);
        this.view = null;
    }

    protected V getView() {
        return view;
    }

    protected <T> Observable.Transformer<T, T> onDetach() {
        return source -> source.takeUntil(onDetach);
    }
}
