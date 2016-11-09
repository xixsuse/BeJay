package rocks.itsnotrocketscience.bejay.event.create;


import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.managers.DateTimeUtils;
import rocks.itsnotrocketscience.bejay.models.Event;
import rocks.itsnotrocketscience.bejay.models.EventVisitor;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sirfunkenstine on 29/03/16.
 *
 */
public class EventCreatePresenterImpl implements EventCreateContract.EventCreatePresenter{

    private EventCreateContract.EventCreateView view;
    private final Events events;
    private final DateTimeUtils dateTimeUtils;

    public EventCreatePresenterImpl(Events event, DateTimeUtils dateTimeUtils) {
        this.dateTimeUtils=dateTimeUtils;
        this.events = event;
    }

    @Override public void onViewAttached(EventCreateContract.EventCreateView view) {
        this.view = view;
        fetchInitialDate();
        fetchInitialTime();
    }

    @Override public void onViewDetached() {
        this.view = null;
    }

    @Override public void onDestroy() {}

    @Override public void postEvent(Event event) {
        events.postEvent(event)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Event>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public final void onError(Throwable e) {
                        view.showError(e.toString(), -1);
                    }
                    @Override
                    public final void onNext(Event response) {
                        view.finish();
                    }
                });
    }

    @Override public void fetchInitialDate() {
        view.onInitialDateSet(DateTimeUtils.getLocalDate());
    }

    @Override public void fetchInitialTime() {
        view.onInitialTimeSet(DateTimeUtils.getLocalTime());
    }

    @Override public void processEvent(Event event) {
        EventVisitor visitor = new EventVisitor();
        event.accept(visitor);
        if(visitor.hasError()){
            onErrorFound(visitor.getMessage(),visitor.getResource());
        }else{
            postEvent(event);
        }
    }

    @Override public void onErrorFound(String error, int resource) {
        if (resource == EventVisitor.NO_RESOURCE) {
            view.showToastError(error);
        } else {
            view.showError(error, resource);
        }
    }
}






