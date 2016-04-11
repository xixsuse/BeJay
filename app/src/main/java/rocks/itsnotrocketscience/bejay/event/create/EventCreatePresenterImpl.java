package rocks.itsnotrocketscience.bejay.event.create;

import android.util.Log;

import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.models.Event;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sirfunkenstine on 29/03/16.
 */
public class EventCreatePresenterImpl implements EventCreateContract.EventCreatePresenter {

    EventCreateContract.EventCreateView view;
    private final Events events;

    public EventCreatePresenterImpl(Events event) {
        this.events = event;
    }

    @Override public void onViewAttached(EventCreateContract.EventCreateView view) {
        this.view = view;
    }

    @Override public void onViewDetached() {
        this.view = null;
    }

    @Override public void onDestroy() { }

    @Override public void postEvent(Event event) {
        events.postEvent(event)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Event>() {
                    @Override
                    public void onCompleted() {}

                    @Override
                    public final void onError(Throwable e) {
                        view.showError(e.toString());
                    }

                    @Override
                    public final void onNext(Event response) {
                        Log.d("Yeah!", "onNext: sucess");
                    }
                });
    }
}
