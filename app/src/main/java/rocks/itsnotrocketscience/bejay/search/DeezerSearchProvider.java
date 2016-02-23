package rocks.itsnotrocketscience.bejay.search;

import rocks.itsnotrocketscience.bejay.deezer.model.PageResponse;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func3;

public class DeezerSearchProvider<T, R> implements SearchProvider<R> {
    private Func3<String, Long, Long, Observable<PageResponse<T>>> search;
    private Func1<T, R> mapper;

    public DeezerSearchProvider(Func3<String, Long, Long, Observable<PageResponse<T>>> search, Func1<T, R> mapper) {
        this.search = search;
        this.mapper = mapper;
    }

    @Override
    public Search<R> newSearch(String query, long limit) {
        return new rocks.itsnotrocketscience.bejay.deezer.Search<>(query, limit, search, mapper);
    }
}
