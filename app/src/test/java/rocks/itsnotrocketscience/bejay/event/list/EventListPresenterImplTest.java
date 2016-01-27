package rocks.itsnotrocketscience.bejay.event.list;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.dao.EventsDao;
import rocks.itsnotrocketscience.bejay.models.Event;
import rx.Observable;
import rx.schedulers.Schedulers;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by nemi on 27/01/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class EventListPresenterImplTest {

    @Mock EventListContract.EventListView view;
    @Mock EventsDao eventsDao;
    @Mock Events networkEvents;
    @Captor ArgumentCaptor<ArrayList<Event>> eventsArgumentCaptor;

    EventListPresenterImpl eventListPresenter;


    @Before
    public void setUp() throws Exception {
        eventListPresenter = spy(new EventListPresenterImpl(eventsDao, networkEvents));
        doReturn(Schedulers.immediate()).when(eventListPresenter).mainScheduler();
    }

    @Test
    public void testLoadEventsNoEvents() {
        doReturn(Observable.just(null)).when(eventsDao).all();
        doReturn(Observable.just(null)).when(networkEvents).list();

        eventListPresenter.onViewAttached(view);
        eventListPresenter.loadEvents();

        verify(view).setProgressVisible(eq(true));
        verify(view).showError();
        verify(view).setProgressVisible(eq(false));
    }

    @Test
    public void testLoadEventsFromDisk() {
        ArrayList<Event> eventsFromDisk = mock(ArrayList.class);
        doReturn(Observable.just(eventsFromDisk)).when(eventsDao).all();
        doReturn(Observable.just(null)).when(networkEvents).list();

        eventListPresenter.onViewAttached(view);
        eventListPresenter.loadEvents();

        verify(view).setProgressVisible(true);
        verify(view).onEventsLoaded(eq(eventsFromDisk));
        verify(view).setProgressVisible(false);
    }

    @Test
    public void testLoadEventsFromNetwork() {
        ArrayList<Event> eventsFromNetwork = mock(ArrayList.class);
        doReturn(Observable.just(null)).when(eventsDao).all();
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return Observable.just(eventsArgumentCaptor.getValue());
            }
        }).when(eventsDao).save(eventsArgumentCaptor.capture());
        doReturn(Observable.just(eventsFromNetwork)).when(networkEvents).list();

        eventListPresenter.onViewAttached(view);
        eventListPresenter.loadEvents();

        verify(view).setProgressVisible(true);
        verify(view).onEventsLoaded(eq(eventsFromNetwork));
        verify(view).setProgressVisible(false);

        verify(eventsDao).save(eq(eventsFromNetwork));
    }
}