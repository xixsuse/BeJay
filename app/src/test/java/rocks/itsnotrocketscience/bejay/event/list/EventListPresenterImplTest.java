package rocks.itsnotrocketscience.bejay.event.list;

import com.google.android.gms.maps.model.LatLng;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.dao.EventsDao;
import rocks.itsnotrocketscience.bejay.managers.AccountManager;
import rocks.itsnotrocketscience.bejay.managers.Launcher;
import rocks.itsnotrocketscience.bejay.map.LocationProvider;
import rocks.itsnotrocketscience.bejay.models.Event;
import rx.Observable;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static rocks.itsnotrocketscience.bejay.event.list.EventListPresenterImpl.NO_EVENTS;
import static rocks.itsnotrocketscience.bejay.event.list.EventListPresenterImpl.NO_FRIEND_EVENTS;
import static rocks.itsnotrocketscience.bejay.event.list.EventListPresenterImpl.NO_LOCAL_EVENTS;
import static rocks.itsnotrocketscience.bejay.event.list.EventListPresenterImpl.NO_RESULTS;

/**
 * Created by nemi on 27/01/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class EventListPresenterImplTest {

    @Mock EventListContract.EventListView view;
    @Mock EventsDao eventsDao;
    @Mock Events networkEvents;
    @Mock AccountManager accountManager;
    @Mock LocationProvider locationProvider;
    @Mock Launcher launcher;
    @Captor ArgumentCaptor<ArrayList<Event>> eventsArgumentCaptor;
    EventListType listType = EventListType.ALL;

    private EventListPresenterImpl eventListPresenter;


    @Before
    public void setUp() throws Exception {
        eventListPresenter = spy(new EventListPresenterImpl(eventsDao, networkEvents, accountManager, launcher, locationProvider));
        eventListPresenter.setListType(listType);
        doReturn(Schedulers.immediate()).when(eventListPresenter).mainScheduler();

    }

//    @Test
//    public void testLoadEventsFromDisk() {
//        ArrayList<Event> eventsFromDisk =  new ArrayList<Event>();
//        doReturn(Observable.just(eventsFromDisk)).when(eventsDao).all();
//        doReturn(Observable.just(null)).when(networkEvents).list();
//
//        eventListPresenter.onViewAttached(view);
//        eventListPresenter.loadEvents();
//
//        verify(view).setProgressVisible(true);
//        verify(view).onEventsLoaded(eq(eventsFromDisk));
//        verify(view).setProgressVisible(false);
//    }

    @Test
    public void testLoadEventsFromNetwork() {
        ArrayList<Event> eventsFromNetwork = new ArrayList<>();
        eventsFromNetwork.add(new Event());
        doReturn(Observable.just(null)).when(eventsDao).all();
        doAnswer(invocation -> Observable.just(eventsArgumentCaptor.getValue())).when(eventsDao).save(eventsArgumentCaptor.capture());
        doReturn(Observable.just(eventsFromNetwork)).when(networkEvents).list();

        eventListPresenter.onViewAttached(view);
        eventListPresenter.loadEvents();

        verify(view).setProgressVisible(true);
        verify(view).onEventsLoaded(eq(eventsFromNetwork));
        verify(view).setProgressVisible(false);

        verify(eventsDao).save(eq(eventsFromNetwork));
    }

    @Test
    public void testLoadEventsNoEvents() {
        doReturn(Observable.just(null)).when(eventsDao).all();
        doReturn(Observable.just(null)).when(networkEvents).list();

        eventListPresenter.onViewAttached(view);
        eventListPresenter.loadEvents();

        verify(view).setProgressVisible(eq(true));
        verify(view).showError(NO_EVENTS);
        verify(view).setProgressVisible(eq(false));
    }

    @Test
    public void testLoadEventsNoFriendEvents() {
        doReturn(Observable.just(null)).when(networkEvents).friendsEvents();
        eventListPresenter.onViewAttached(view);
        eventListPresenter.setListType(EventListType.FRIENDS);
        eventListPresenter.loadEvents();
        verify(view).showError(NO_FRIEND_EVENTS);
    }

    @Test
    public void testLoadEventsSearch() {
        eventListPresenter.onViewAttached(view);
        eventListPresenter.setListType(EventListType.SEARCH);
        eventListPresenter.loadEvents();
        verifyZeroInteractions(view);
    }

    @Test
    public void testEventsSearch() {
        doReturn(Observable.just(null)).when(networkEvents).searchEvents(anyString());
        eventListPresenter.onViewAttached(view);
        eventListPresenter.setListType(EventListType.SEARCH);
        eventListPresenter.searchEvent("test");
        verify(view).showError(NO_RESULTS);
    }

    @Test
    public void testLoadEventsNoLocalEvents() {
        doReturn(true).when(locationProvider).hasLastKnownLocation();
        doReturn(new LatLng(1, 1)).when(locationProvider).getLastKnownLatLng();
        doReturn(Observable.just(null)).when(networkEvents).publicNearbyEvents(1, 1);
        eventListPresenter.onViewAttached(view);
        eventListPresenter.setListType(EventListType.PUBLIC_LOCAL);
        eventListPresenter.loadEvents();
        verify(view).showError(NO_LOCAL_EVENTS);
    }

    @Test
    public void testLoadEventsNoLastKnownLocation(){
        eventListPresenter.setListType(EventListType.PUBLIC_LOCAL);
        doReturn(false).when(locationProvider).hasLastKnownLocation();
        eventListPresenter.loadEvents();
        verify(locationProvider).fetchLocation();
    }

    @Test
    public void testOpenCreateEvent(){
        eventListPresenter.openCreateEvent();
        verify(launcher).openCreateEvent();

    }

    @Test
    public void testOnLocationRetrieved(){
        doNothing().when(eventListPresenter).loadEvents();
        eventListPresenter.onLocationRetrieved(new LatLng(1,1));
        verify(eventListPresenter).loadEvents();

    }

    @Test
    public void testGetType(){
        assertTrue(eventListPresenter.getType()==EventListType.ALL);
    }

    @Test
    public void testOnDestroy(){
        eventListPresenter.onDestroy();
        verify(locationProvider).disconnect();
    }

    @Test
    public void testOnViewAttached(){
        eventListPresenter.onViewAttached(view);
        verify(locationProvider).connect();
    }

}