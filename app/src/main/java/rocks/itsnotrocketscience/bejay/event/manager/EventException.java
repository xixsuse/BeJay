package rocks.itsnotrocketscience.bejay.event.manager;

/**
 * Created by nemi on 02/05/16.
 */
public class EventException extends Exception {

    private final Resolution resolution;

    public EventException(Resolution resolution) {
        this.resolution = resolution;
    }

    public boolean isRecoverable() {
        return resolution != null;
    }

    public Resolution getResolution() {
        if(!isRecoverable()) {
            throw new IllegalStateException("not resolvable");
        }

        return resolution;
    }
}
