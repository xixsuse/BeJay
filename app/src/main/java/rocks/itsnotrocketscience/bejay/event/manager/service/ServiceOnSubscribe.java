package rocks.itsnotrocketscience.bejay.event.manager.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import java.util.HashSet;
import java.util.Set;

import rocks.itsnotrocketscience.bejay.event.manager.Event;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by nemi on 02/05/16.
 */
class ServiceOnSubscribe implements Observable.OnSubscribe<Event>, ServiceConnection {
    private final Context context;
    private final Set<Subscriber<? super Event>> subscribers = new HashSet<>();
    private Event event;

    ServiceOnSubscribe(Context context) {
        this.context = context;
    }

    @Override
    public void call(Subscriber<? super Event> subscriber) {
        if (!subscriber.isUnsubscribed()) {
            subscribe(subscriber);
        }
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        onServiceConnected((EventBinder) service);
    }

    private void onServiceConnected(EventBinder eventBinder) {
        try {
            event = eventBinder.getEvent();
            onServiceConnected();
        } catch (Exception error) {
            onConnectionFailed(error);
        }
    }

    private void onServiceConnected() {
        for (Subscriber<? super Event> subscriber : subscribers) {
            subscriber.onNext(event);
        }
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        for (Subscriber<? super Event> subscriber : subscribers) {
            subscriber.onCompleted();
        }
    }

    private void subscribe(Subscriber<? super Event> subscriber) {
        if (subscribers.add(subscriber) && subscribers.size() == 1) {
            bindService();
        } else if (event != null) {
            subscriber.onNext(event);
        }

        ServiceSubscription s = new ServiceSubscription(subscriber, this);
        subscriber.add(s);
    }

    private Intent getServiceIntent() {
        return new Intent(context, EventService.class);
    }

    void unSubscribe(Subscriber<? super Event> serviceSubscription) {
        if (subscribers.remove(serviceSubscription) && subscribers.size() == 0) {
            unbindService();
        }
    }

    private void bindService() {
        if(!context.bindService(getServiceIntent(), this, Context.BIND_AUTO_CREATE)) {
            onConnectionFailed(new Exception("connection failed"));
        }
    }

    private void onConnectionFailed(Throwable error) {
        for(Subscriber<? super Event> s : subscribers) {
            s.onError(error);
        }
    }

    private void unbindService() {
        context.unbindService(this);
    }
}
