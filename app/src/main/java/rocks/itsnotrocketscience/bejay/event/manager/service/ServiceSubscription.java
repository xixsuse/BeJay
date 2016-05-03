package rocks.itsnotrocketscience.bejay.event.manager.service;

import rocks.itsnotrocketscience.bejay.event.manager.Event;
import rocks.itsnotrocketscience.bejay.music.player.EventPlayer;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by nemi on 02/05/16.
 */
class ServiceSubscription implements Subscription {
    private final Subscriber<? super Event> subscriber;
    private final ServiceOnSubscribe onSubscribe;

    public ServiceSubscription(Subscriber<? super Event> subscriber,
                               ServiceOnSubscribe onSubscribe) {
        this.subscriber = subscriber;
        this.onSubscribe = onSubscribe;
    }

    @Override
    public void unsubscribe() {
        onSubscribe.unSubscribe(subscriber);
    }

    @Override
    public boolean isUnsubscribed() {
        return subscriber.isUnsubscribed();
    }
}
