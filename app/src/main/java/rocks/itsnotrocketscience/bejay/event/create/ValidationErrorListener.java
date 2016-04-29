package rocks.itsnotrocketscience.bejay.event.create;

/**
 * Created by sirfunkenstine on 29/04/16.
 */
public interface ValidationErrorListener {
    int NO_RESOURCE = -1;
    void onErrorFound(String error, int resource);
}
