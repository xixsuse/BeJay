package rocks.itsnotrocketscience.bejay.event.list;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import com.google.android.gms.maps.model.LatLng;

import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.dao.EventsDao;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;
import rocks.itsnotrocketscience.bejay.managers.Launcher;
import rocks.itsnotrocketscience.bejay.map.LocationProvider;
import rocks.itsnotrocketscience.bejay.models.Event;
import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static rocks.itsnotrocketscience.bejay.managers.AccountManager.EVENT_NONE;

/**
 * Created by nemi on 27/01/2016.
 */
public class EventListPresenterImpl implements EventListContract.EventListPresenter, LocationProvider.LocationRetrievedCallback {
    private static final Func1<List<Event>, Boolean> VALID_EVENT_LIST_FILTER = events -> events != null && events.size() != 0;

    public static final String NO_EVENTS = "No Events Found";
    public static final String NO_FRIEND_EVENTS = "No Friend Events Found";
    public static final String NO_LOCAL_EVENTS = "No Local Events";
    public static final String NO_RESULTS = "Search Returned No Events";


    private final EventsDao eventsDao;
    private final Events networkEvents;
    public final PublishSubject<Boolean> onDestroySubject;
    private final AccountManager accountManager;
    private final Launcher launcher;
    private LocationProvider locationProvider;

    private EventListContract.EventListView view;
    private EventListType listType;
    private List<Event> eventList;

    @Inject
    public EventListPresenterImpl(EventsDao eventsDao, Events networkEvents, AccountManager accountManager, Launcher launcher, LocationProvider locationProvider) {
        this.eventsDao = eventsDao;
        this.networkEvents = networkEvents;
        this.accountManager = accountManager;
        this.launcher = launcher;
        this.locationProvider = locationProvider;
        locationProvider.buildGoogleApiClient(this);
        onDestroySubject = PublishSubject.create();
    }

    @Override
    public void openCreateEvent() {
        launcher.openCreateEvent();
    }

    @Override
    public void onViewAttached(EventListContract.EventListView view) {
        this.view = view;
        locationProvider.connect();
    }

    @Override
    public void setListType(EventListType listType) {
        this.listType = listType;
    }

    @Override
    public void onViewDetached() {
        this.view = null;
    }

    @Override
    public void onDestroy() {
        onDestroySubject.onNext(true);
        locationProvider.disconnect();
    }

    @Override
    public void loadEvents() {
        if ((EventListType.PUBLIC_LOCAL == listType) && !locationProvider.hasLastKnownLocation()) {
            locationProvider.fetchLocation();
        } else if (EventListType.SEARCH != listType) {
            view.setProgressVisible(true);
            Observable.concat(Observable.just(eventList).filter(validEventListFilter()),
                    loadEventsFromNetwork(listType).filter(validEventListFilter()))
                    .compose(newOnDestroyTransformer())
                    .first()
                    .observeOn(mainScheduler())
                    .subscribe(events -> view.onEventsLoaded(events),
                            throwable -> showErrorForEventListType(listType),
                            () -> view.setProgressVisible(false));
        }
    }

    @Override
    public void searchEvent(String query) {
        view.setProgressVisible(true);
        networkEvents.searchEvents(query)
                .filter(validEventListFilter())
                .flatMap(eventsDao::save)
                .observeOn(mainScheduler())
                .subscribe(events -> view.onEventsLoaded(events),
                        throwable -> showErrorForEventListType(EventListType.SEARCH),
                        () -> view.setProgressVisible(false));
    }


    @Override
    public void openEventActivity() {
        launcher.openEventActivity(accountManager.getCheckedInEventId());
    }

    private void showErrorForEventListType(EventListType listType) {
        view.setProgressVisible(false);
        switch (listType) {
            case ALL:
                view.showError(NO_EVENTS);
                break;
            case FRIENDS:
                view.showError(NO_FRIEND_EVENTS);
                break;
            case SEARCH:
                view.showError(NO_RESULTS);
                break;
            case PUBLIC_LOCAL:
                view.showError(NO_LOCAL_EVENTS);
                break;
        }
    }

    Scheduler mainScheduler() {
        return AndroidSchedulers.mainThread();
    }

    private Func1<List<Event>, Boolean> validEventListFilter() {
        return VALID_EVENT_LIST_FILTER;
    }

    private Observable<List<Event>> loadEventsFromDisk() {
        return eventsDao.all().doOnNext(events -> this.eventList = events);
    }

    private Observable<ArrayList<Event>> loadEventsFromNetwork(EventListType listType) {
        switch (listType) {
            case ALL:
                return networkEvents.list().flatMap(eventsDao::save);
            case FRIENDS:
                return networkEvents.friendsEvents().flatMap(eventsDao::save);
            case PUBLIC_LOCAL:
                return getLocalEventsArrayListObservable();
            default:
                return networkEvents.list().flatMap(eventsDao::save);
        }
    }

    private Observable<ArrayList<Event>> getLocalEventsArrayListObservable() {
        if (locationProvider.hasLastKnownLocation()) {
            return networkEvents.publicNearbyEvents(locationProvider.getLastKnownLatLng().latitude,
                    locationProvider.getLastKnownLatLng().longitude).flatMap(eventsDao::save);
        }
        return Observable.empty();
    }

    private <T> Observable.Transformer<T, T> newOnDestroyTransformer() {
        return source -> source.takeUntil(onDestroySubject);
    }

    @Override
    public void checkIn(Event event, boolean force) {
        boolean checkedIn = accountManager.isCheckedIn();
        if (!checkedIn) {
            doCheckIn(event);
        } else if (event.getId() == accountManager.getCheckedInEventId()) {
            launcher.openEventActivity(event.getId());
        } else if (!force) {
            view.onCheckInFailed(event, CHECK_IN_CHECKOUT_NEEDED);
        } else {
            doCheckIn(event);
        }
    }

    private void doCheckIn(Event event) {
        Observable.just(accountManager.getCheckedInEventId()).flatMap(currentEventId -> {
            if (EVENT_NONE == currentEventId) {
                return networkEvents.checkIn(event.getId());
            }
            return Observable.concat(networkEvents.checkOut(currentEventId).ignoreElements(),
                    networkEvents.checkIn(event.getId()));
        }).compose(newOnDestroyTransformer())
                .subscribeOn(Schedulers.io())
                .observeOn(mainScheduler())
                .doOnNext(event1 -> accountManager.setCheckedIn(event.getId()))
                .subscribe(event1 -> launcher.openEventActivity(event.getId())
                        , throwable -> view.onCheckInFailed(event, CHECK_IN_FAILED));
    }

    @Override
    public void onLocationRetrieved(LatLng latLng) {
        loadEvents();
    }

    @Override
    public void requestPermission() {

    }

    @Override
    public EventListType getType() {
        return listType;
    }
}
