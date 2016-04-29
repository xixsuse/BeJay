package rocks.itsnotrocketscience.bejay.event.create;

/**
 * Created by sirfunkenstine on 28/04/16.
 *
 */

import org.joda.time.DateTime;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.models.Event;

public class EventValidator {
    private final Event event;
    private final ValidationErrorListener listener;

    public EventValidator(Event event, ValidationErrorListener listener) {
        this.event = event;
        this.listener=listener;
    }

    public boolean isValid() {
        return validTitle() && validPlace() && validGPS() && hasStartTime() && hasEndTime() && validDate();
    }

    private boolean validDate() {
        if (new DateTime(event.getStartDate()).isAfter(new DateTime(event.getEndDate()))) {
            listener.onErrorFound("Start Date Must be before End Date", ValidationErrorListener.NO_RESOURCE);
            return false;
        }
        return true;
    }

    private boolean hasEndTime() {
        if (!event.hasEndDate()) {
            listener.onErrorFound("End date time not set",ValidationErrorListener.NO_RESOURCE);
            return false;
        }
        return true;
    }

    private boolean hasStartTime() {
        if (!event.hasStartTime()) {
            listener.onErrorFound("Start date time not set", ValidationErrorListener.NO_RESOURCE);
            return false;
        }
        return true;
    }

    private boolean validGPS() {
        if (!event.hasGps()) {
            listener.onErrorFound("Must set GPS", R.id.etGPS);
            return false;
        }
        return true;
    }

    private boolean validPlace() {
        if (event.getPlace() == null) {
            listener.onErrorFound("Place is required",R.id.etPlace);
            return false;
        }
        return true;
    }

    private boolean validTitle() {
        if (event.getTitle().length() < 3) {
            listener.onErrorFound("title is too short",R.id.etTitle);
            return false;
        }
        return true;
    }
}