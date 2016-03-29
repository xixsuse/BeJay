package rocks.itsnotrocketscience.bejay.event.create;

/**
 * Created by sirfunkenstine on 29/03/16.
 */
public class EventCreatePresenterImpl implements EventCreateContract.EventCreatePresenter {

    EventCreateContract.EventCreateView view;

    @Override public void onViewAttached(EventCreateContract.EventCreateView view) {
        this.view = view;
    }

    @Override public void onViewDetached() {
        this.view = null;
    }

    @Override public void onDestroy() {

    }
}
