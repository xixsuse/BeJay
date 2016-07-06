package rocks.itsnotrocketscience.bejay.models;

import org.joda.time.DateTime;

import rocks.itsnotrocketscience.bejay.R;

/**
 * Created by sirfunkenstine on 04/05/16.
 */
public class EventVisitor implements Visitor {
    public static int NO_RESOURCE = -1;
    boolean hasError = false;
    private int resource;
    private String message;

    @Override public void visit(Event event) {
        if (event.getTitle().length() < 3) {
            setErrorFields("title is too short", R.id.etTitle);
        } else if (new DateTime(event.getStartDate()).isAfter(new DateTime(event.getEndDate()))) {
            setErrorFields("Start Date Must be before End Date", NO_RESOURCE);
        } else if (!event.hasEndDate()) {
            setErrorFields("End date time not set", NO_RESOURCE);
        } else if (!event.hasStartTime()) {
            setErrorFields("Start date time not set", NO_RESOURCE);
        } else if (!event.hasGps()) {
            setErrorFields("Must set GPS", R.id.etGPS);
        } else if (event.getPlace() == null) {
            setErrorFields("Place is required", R.id.etPlace);
        }
    }

    private void setErrorFields(String message, int resource) {
        this.message = message;
        this.resource = resource;
        hasError = true;
    }

    public boolean hasError() {
        return hasError;
    }

    public int getResource() {
        return resource;
    }

    public String getMessage() {
        return message;
    }
}
