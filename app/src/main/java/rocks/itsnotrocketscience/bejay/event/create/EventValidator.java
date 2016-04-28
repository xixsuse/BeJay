package rocks.itsnotrocketscience.bejay.event.create;

/**
 * Created by sirfunkenstine on 28/04/16.
 */

import org.joda.time.DateTime;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.models.Event;

public class EventValidator {
    private final Event event;
    private final EventCreateContract.EventCreateView view;

    public EventValidator(Event event, EventCreateContract.EventCreateView view) {
        this.event = event;
        this.view = view;
    }

    public boolean isValid() {
        return validTitle() && validPlace() && validGPS() && hasStartTime() && hasEndTime() && validDate();
    }

    private boolean validDate() {
        if (new DateTime(event.getStartDate()).isAfter(new DateTime(event.getEndDate()))) {
            view.showError("Start Date Must be before End Date",-1);
            return true;
        }
        return false;
    }

    private boolean hasEndTime() {
        if (!event.hasEndDate()) {
            view.showError("End date time not set",-1);
            return true;
        }
        return false;
    }

    private boolean hasStartTime() {
        if (!event.hasStartTime()) {
            view.showError("Start date time not set",-1);
            return true;
        }
        return false;
    }

    private boolean validGPS() {
        if (!event.hasGps()) {
            view.showError("Must set GPS", R.id.etGPS);
            return true;
        }
        return false;
    }

    private boolean validPlace() {
        if (event.getPlace() == null) {
            view.showError("Place is required",R.id.etPlace);
            return true;
        }
        return false;
    }

    private boolean validTitle() {
        if (event.getTitle().length() < 3) {
            view.showError("title is too short",R.id.etTitle);
            return true;
        }
        return false;
    }
}