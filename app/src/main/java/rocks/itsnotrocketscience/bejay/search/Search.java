package rocks.itsnotrocketscience.bejay.search;

import java.util.List;

import rx.Observable;

public interface Search<T> {
    Observable<List<T>> loadPrevPage();
    Observable<List<T>> loadNextPage();
}
