package rocks.itsnotrocketscience.bejay.event.list;

import java.util.ArrayList;

import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.dao.EventsDao;
import rocks.itsnotrocketscience.bejay.models.Event;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

/**
 * Created by nemi on 27/01/2016.
 */
public class EventListPresenterImpl implements EventListContract.EventListPresenter {
    static final Func1<ArrayList<Event>, Boolean> VALID_EVENT_LIST_FILTER = new Func1<ArrayList<Event>, Boolean>() {
        @Override
        public Boolean call(ArrayList<Event> events) {
            return events != null;
        }
    };


    final EventsDao eventsDao;
    final Events networkEvents;
    final PublishSubject<Boolean> onDestroy;

    EventListContract.EventListView view;
    ArrayList<Event> events;
    Subscriber<ArrayList<Event>> loadEventSubscriber;

    public EventListPresenterImpl(EventsDao eventsDao, Events networkEvents) {
        this.eventsDao = eventsDao;
        this.networkEvents = networkEvents;
        onDestroy = PublishSubject.create();
    }

    @Override
    public void onViewAttached(EventListContract.EventListView view) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        this.view = null;
    }

    @Override
    public void onDestroy() {
        onDestroy.onNext(true);
    }

    @Override
    public void loadEvents() {
        view.setProgressVisible(true);
        Observable.concat(Observable.just(events).filter(validEventListFilter()),
                loadEventsFromDisk().filter(validEventListFilter()),
                loadEventsFromNetwork().filter(validEventListFilter()))
                .compose(newOnDestroyTransformer())
                .first()
                .observeOn(mainScheduler())
                .subscribe(loadEventsSubscriber());
    }

    Scheduler mainScheduler() {
        return AndroidSchedulers.mainThread();
    }

    Subscriber<ArrayList<Event>> loadEventsSubscriber() {
        if(loadEventSubscriber == null) {
            loadEventSubscriber = new Subscriber<ArrayList<Event>>() {
                @Override
                public void onCompleted() {
                    view.setProgressVisible(false);
                }

                @Override
                public void onError(Throwable e) {
                    view.setProgressVisible(false);
                    view.showError();
                }

                @Override
                public void onNext(ArrayList<Event> events) {
                    view.onEventsLoaded(events);
                }
            };
        }
        return loadEventSubscriber;
    }

    private Func1<ArrayList<Event>, Boolean> validEventListFilter() {
        return VALID_EVENT_LIST_FILTER;
    }

    private Observable<ArrayList<Event>> loadEventsFromDisk() {
        return eventsDao.all().doOnNext(new Action1<ArrayList<Event>>() {
            @Override
            public void call(ArrayList<Event> events) {
                EventListPresenterImpl.this.events = events;
            }
        });
    }

    private Observable<ArrayList<Event>> loadEventsFromNetwork() {
        return networkEvents.list().flatMap(new Func1<ArrayList<Event>, Observable<ArrayList<Event>>>() {
            @Override
            public Observable<ArrayList<Event>> call(ArrayList<Event> events) {
                return eventsDao.save(events);
            }
        });
    }

    private <T> Observable.Transformer<T, T> newOnDestroyTransformer() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> source) {
                return source.takeUntil(onDestroy);
            }
        };
    }
}
