package rocks.itsnotrocketscience.bejay.event.list;

import java.util.ArrayList;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.dao.EventsDao;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;
import rocks.itsnotrocketscience.bejay.models.Event;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static rocks.itsnotrocketscience.bejay.managers.AccountManager.EVENT_NONE;

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
    final AccountManager accountManager;

    EventListContract.EventListView view;
    ArrayList<Event> events;
    Subscriber<ArrayList<Event>> loadEventSubscriber;

    @Inject
    public EventListPresenterImpl(EventsDao eventsDao, Events networkEvents, AccountManager accountManager) {
        this.eventsDao = eventsDao;
        this.networkEvents = networkEvents;
        this.accountManager = accountManager;
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
        return eventsDao.all().doOnNext(events1 -> EventListPresenterImpl.this.events = events1);
    }

    private Observable<ArrayList<Event>> loadEventsFromNetwork() {
        return networkEvents.list().flatMap(events1 -> eventsDao.save(events1));
    }

    private <T> Observable.Transformer<T, T> newOnDestroyTransformer() {
        return source -> source.takeUntil(onDestroy);
    }

    @Override
    public void checkIn(final Event event, boolean force) {
        boolean checkedIn = accountManager.isCheckedIn();
        if(!checkedIn) {
            doCheckIn(event);
        } else if(event.getId() == accountManager.getCheckedInEventId()) {
            view.onChecking(event);
        } else if(!force){
            view.onCheckInFailed(event, CHECK_IN_CHECKOUT_NEEDED);
        } else {
            doCheckIn(event);
        }
    }

    private void doCheckIn(final Event event) {
        Observable.just(accountManager.getCheckedInEventId()).flatMap(currentEventId -> {
            if (currentEventId == EVENT_NONE) {
                return networkEvents.checkIn(event.getId());
            }
            return Observable.concat(networkEvents.checkOut(currentEventId).ignoreElements(),
                    networkEvents.checkIn(event.getId()));
        }).compose(newOnDestroyTransformer())
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler())
                .doOnNext(event1 -> accountManager.setCheckedIn(event1.getId()))
                .subscribe(event1 -> view.onChecking(event1)
                        , throwable -> view.onCheckInFailed(event, CHECK_IN_FAILED));
    }
}
