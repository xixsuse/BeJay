package rocks.itsnotrocketscience.bejay.search.contract;

public interface Presenter<V> {
    void onViewAttached(V view);
    void onViewDetached();
}
