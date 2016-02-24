package rocks.itsnotrocketscience.bejay.music.backends.deezer.api;

import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import rocks.itsnotrocketscience.bejay.music.Pager;
import rocks.itsnotrocketscience.bejay.music.backends.deezer.model.PageResponse;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func3;

public class SearchResultPager<T, R> implements Pager<R> {
    private long total = UNKNOWN;

    private final String query;
    private final Long pageSize;
    private Func3<String, Long, Long, Observable<PageResponse<T>>> search;
    private Func1<T, R> mapper;
    private Cursor next;
    private Cursor prev;


    static class Cursor {
        final Long index;
        final Long limit;

        public Cursor(Long index, Long limit) {
            this.index = index;
            this.limit = limit;
        }

        static Cursor parse(String cursor) {
            if(TextUtils.isEmpty(cursor)) {
                return null;
            }

            Uri uri = Uri.parse(cursor);

            return new Cursor(Long.valueOf(uri.getQueryParameter(Deezer.INDEX)), Long.valueOf(uri.getQueryParameter(Deezer.LIMIT)));
        }
    }

    public SearchResultPager(Func3<String, Long, Long, Observable<PageResponse<T>>> search, Func1<T, R> mapper, String query, Long pageSize) {
        this.query = query;
        this.pageSize = pageSize;
        this.search = search;
        this.mapper = mapper;
    }

    @Override
    public long total() {
        return total;
    }

    @Override
    public Observable<List<R>> firstPage() {
        return loadPage(new Cursor(null, pageSize));
    }


    @Override
    public Observable<List<R>> nextPage() {
        return loadPage(next);
    }

    @Override
    public Observable<List<R>> prevPage() {
        return loadPage(prev);
    }

    public Observable<List<R>> loadPage(Cursor cursor) {
        if(cursor != null) {
            return search.call(query, cursor.index, cursor.limit)
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(onNext())
                    .flatMap(pageResponse -> Observable.from(pageResponse.getData()))
                    .map(mapper)
                    .collect(() -> new ArrayList<R>(), (list, item) -> list.add(item));
        }

        return Observable.empty();
    }

    private Action1<PageResponse<T>> onNext() {
        return pageResponse -> {
            this.next = Cursor.parse(pageResponse.getNext());
            this.prev = Cursor.parse(pageResponse.getPrev());
            this.total = pageResponse.getTotal();
        };
    }
}
