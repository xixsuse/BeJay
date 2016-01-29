package rocks.itsnotrocketscience.bejay.api;

import java.util.ArrayList;

import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.models.Event;
import rx.Observable;

/**
 * Created by centralstation on 1/29/16.
 */
class EventsWithRetry implements Events {

    private final Events events;

    public EventsWithRetry(Events events) {
        this.events = events;
    }

    @Override
    public Observable<ArrayList<Event>> list() {
        return events.list().retryWhen(ApiManager.defaultRetry());
    }

    @Override
    public Observable<Event> get(int id) {
        return events.get(id).retryWhen(ApiManager.defaultRetry());
    }

    @Override
    public Observable<Event> checkIn(int id) {
        return events.checkIn(id).retryWhen(ApiManager.defaultRetry());
    }

    @Override
    public Observable<Event> checkOut(int id) {
        return events.checkOut(id).retryWhen(ApiManager.defaultRetry());
    }
}