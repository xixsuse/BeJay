package rocks.itsnotrocketscience.bejay.event.manager;

import android.app.Activity;

import rx.Observable;

/**
 * Created by nemi on 02/05/16.
 */
public interface Resolution {
    enum ResolutionResult {
        SUCCESS, ERROR, CANCELLED
    }

    Observable<ResolutionResult> resolve(Activity activity);
}
