package rocks.itsnotrocketscience.bejay.dao;

import java.util.ArrayList;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.models.Event;
import rx.Observable;

/**
 * Created by nemi on 27/01/2016.
 */
public class EventsDao {

    @Inject
    public EventsDao() {

    }

    public Observable<ArrayList<Event>> all() {
        return Observable.just(null);
    }

    public Observable<ArrayList<Event>> save(ArrayList<Event> events) {
        return Observable.just(events);
    }
}
