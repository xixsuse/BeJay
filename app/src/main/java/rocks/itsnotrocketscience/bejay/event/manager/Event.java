package rocks.itsnotrocketscience.bejay.event.manager;

import android.content.Context;

import rocks.itsnotrocketscience.bejay.music.player.EventPlayer;

/**
 * Created by nemi on 02/05/16.
 */
public class Event {
    private final EventPlayer eventPlayer;

    public Event(EventPlayer eventPlayer) {
        this.eventPlayer = eventPlayer;
    }

    public EventPlayer getEventPlayer() {
        return eventPlayer;
    }
}
