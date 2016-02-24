package rocks.itsnotrocketscience.bejay.music;

import java.util.List;

import rx.Observable;

public interface Pager<T> {
    int UNKNOWN = -1;

    long total();
    Observable<List<T>> firstPage();
    Observable<List<T>> nextPage();
    Observable<List<T>> prevPage();
}
