package rocks.itsnotrocketscience.bejay.event.create;

import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding.widget.RxTextView;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;

import rocks.itsnotrocketscience.bejay.R;
import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.models.Event;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func2;
import rx.functions.Func3;
import rx.functions.Func6;
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
                    public void onCompleted() {
                    }

                    @Override
                    public final void onError(Throwable e) {
                        view.showError(e.toString());
                    }

                    @Override
                    public final void onNext(Event response) {
                        view.finish();
                    }
                });
    }

    @Override public String getDate(String startDate, String startTime) {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd hh mm a");
        String all = String.format("%s %s", startDate, startTime);
        DateTime dt = fmt.parseDateTime(all);
        return dt.toString();
    }

    @Override public void setStartDateTime(TextView tvStartDate, TextView tvStartTime) {

        LocalDate localDate = new LocalDate();
        tvStartDate.setText(localDate.toString());

        LocalTime localTime = new LocalTime();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("hh mm a");
        tvStartTime.setText(localTime.toString(fmt));

    }

    @Override public void addValidationObserver(EditText etTitle,
                                                EditText etDetails,
                                                EditText etPlace,
                                                EditText etGPS,
                                                TextView tvStartDate,
                                                TextView tvStartTime,
                                                TextView tvEndDate,
                                                TextView tvEndTime) {

    }
}






