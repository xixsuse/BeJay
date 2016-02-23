package rocks.itsnotrocketscience.bejay.deezer;

import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import rocks.itsnotrocketscience.bejay.deezer.model.PageResponse;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func3;

import static java.lang.Long.parseLong;
import static rocks.itsnotrocketscience.bejay.deezer.Deezer.INDEX;


public class Search<T, R> implements rocks.itsnotrocketscience.bejay.search.Search<R> {
    private static class Cursor {
        final long index;
        final long limit;

        static Cursor parse(String cursor) {
            if(TextUtils.isEmpty(cursor)) {
                return null;
            }

            Uri uri = Uri.parse(cursor);

            return new Cursor(parseLong(uri.getQueryParameter(INDEX)),
                    parseLong(uri.getQueryParameter(Deezer.LIMIT)));
        }

        public Cursor(long index, long limit) {
            this.index = index;
            this.limit = limit;
        }
    }

    private final String query;
    private Cursor next;
    private Cursor prev;

    private Func3<String, Long, Long, Observable<PageResponse<T>>> search;
    private Func1<T, R> mapper;

    public Search(String query, Long limit, Func3<String, Long, Long, Observable<PageResponse<T>>> search, Func1<T, R> mapper) {
        this.query = query;

        this.search = search;
        this.mapper = mapper;

        next = new Cursor(0, limit);
    }


    @Override
    public Observable<List<R>> loadPrevPage() {
        return loadFromCursor(prev);
    }

    @Override
    public Observable<List<R>> loadNextPage() {
        return loadFromCursor(next);
    }

    private Observable<List<R>> loadFromCursor(Cursor cursor) {
        if(cursor != null) {
            return search.call(query, cursor.index, cursor.limit).doOnNext(pr -> {
                next = Cursor.parse(pr.getNext());
                prev = Cursor.parse(pr.getPrev());
            }).map(pr -> pr.getData())
                    .flatMap(ts -> Observable.from(ts))
                    .map(mapper).collect(() -> new ArrayList<>(), (lr, r) -> lr.add(r));

        }

        return Observable.empty();
    }
}
