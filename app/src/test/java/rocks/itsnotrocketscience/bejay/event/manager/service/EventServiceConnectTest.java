package rocks.itsnotrocketscience.bejay.event.manager.service;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import rocks.itsnotrocketscience.bejay.event.manager.Event;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.observers.Subscribers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by nemi on 02/05/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class EventServiceConnectTest {
    @Mock Context context;
    @Captor ArgumentCaptor<ServiceConnection> serviceConnectionArgumentCaptor;
    @Mock EventBinder eventBinder;
    @Mock Event event;
    @Mock Action1<Event> onNext;
    @Mock Action1<Throwable> onError;
    @Mock Action0 onComplete;
    Subscriber<Event> subscriber;

    @Before
    public void setUp() throws Exception {
        doReturn(event).when(eventBinder).getEvent();
        subscriber = Subscribers.create(onNext, onError, onComplete);
    }

    @Test
    public void testGetEventSingleSubscriber() {
        Mockito.doAnswer(invocation -> {
            ServiceConnection serviceConnection = serviceConnectionArgumentCaptor.getValue();
            serviceConnection.onServiceConnected(null, eventBinder);
            return true;
        }).when(context).bindService(Mockito.any(Intent.class), serviceConnectionArgumentCaptor.capture(), anyInt());

        Subscription subscription = EventService.start(context).subscribe(subscriber);
        verify(onNext).call(eq(event));
        subscription.unsubscribe();
        verify(context).unbindService(serviceConnectionArgumentCaptor.getValue());
    }

    @Test
    public void testGetEventMultipleSubscribers() {
        Mockito.doAnswer(invocation -> {
            ServiceConnection serviceConnection = serviceConnectionArgumentCaptor.getValue();
            serviceConnection.onServiceConnected(null, eventBinder);
            return true;
        }).when(context).bindService(any(Intent.class), serviceConnectionArgumentCaptor.capture(), anyInt());
        Subscriber<Event> secondSubscriber = Subscribers.create(onNext, onError, onComplete);
        Observable<Event> observable = EventService.start(context);
        Subscription subscription = observable.subscribe(subscriber);
        Subscription secondSubscription = observable.subscribe(secondSubscriber);
        verify(onNext, times(2)).call(eq(event));
        subscription.unsubscribe();
        verify(context, never()).unbindService(serviceConnectionArgumentCaptor.getValue());
        secondSubscription.unsubscribe();
        verify(context).unbindService(serviceConnectionArgumentCaptor.getValue());
    }

    @Test
    public void testServiceDisconnected() {
        Mockito.doAnswer(invocation -> {
            ServiceConnection serviceConnection = serviceConnectionArgumentCaptor.getValue();
            serviceConnection.onServiceConnected(null, eventBinder);
            return true;
        }).when(context).bindService(any(Intent.class), serviceConnectionArgumentCaptor.capture(), anyInt());
        EventService.start(context).subscribe(subscriber);
        serviceConnectionArgumentCaptor.getValue().onServiceDisconnected(null);
        verify(onComplete).call();
    }

    @Test
    public void testBindFails() {
        doReturn(false).when(context).bindService(any(Intent.class), serviceConnectionArgumentCaptor.capture(), anyInt());
        EventService.start(context).subscribe(subscriber);
        verify(onError).call(Mockito.notNull(Throwable.class));
    }

    @Test
    public void testEventInstantiationFails() throws Exception {
        Exception eventCreationError = new Exception("event creation failed");
        doThrow(eventCreationError).when(eventBinder).getEvent();
        Mockito.doAnswer(invocation -> {
            ServiceConnection serviceConnection = serviceConnectionArgumentCaptor.getValue();
            serviceConnection.onServiceConnected(null, eventBinder);
            return true;
        }).when(context).bindService(any(Intent.class), serviceConnectionArgumentCaptor.capture(), anyInt());
        EventService.start(context).subscribe(subscriber);
        verify(onError).call(eq(eventCreationError));
    }

}