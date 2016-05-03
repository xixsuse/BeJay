package rocks.itsnotrocketscience.bejay.event.manager.service;

import android.os.Binder;

import rocks.itsnotrocketscience.bejay.event.manager.*;

/**
 * Created by nemi on 02/05/16.
 */
class EventBinder extends Binder {
    private final EventService eventService;

    public EventBinder(EventService eventService) {
        this.eventService = eventService;
    }

    public Event getEvent() throws Exception {
        return eventService.getEvent();
    }
}
