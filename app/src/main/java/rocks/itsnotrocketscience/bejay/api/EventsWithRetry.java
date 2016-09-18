package rocks.itsnotrocketscience.bejay.api;

import java.util.ArrayList;
import java.util.Map;

import retrofit.http.Body;
import retrofit.http.Path;
import retrofit.http.Query;
import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.models.Event;
import rocks.itsnotrocketscience.bejay.models.Like;
import rocks.itsnotrocketscience.bejay.models.Song;
import rx.Observable;

/**
 * Created by centralstation on 1/29/16.
 */
class EventsWithRetry implements Events {

    private final Events events;

    public EventsWithRetry(Events events) {
        this.events = events;
    }

    @Override
    public Observable<ArrayList<Event>> list() {
        return events.list().retryWhen(ApiManager.defaultRetry());
    }

    @Override
    public Observable<ArrayList<Event>> friendsEvents() {
        return events.friendsEvents();
    }

    @Override
    public Observable<ArrayList<Event>> publicNearbyEvents(@Query("lat") long lat, @Query("lng") long lng) {
        return events.publicNearbyEvents(lat, lng);
    }

    @Override
    public Observable<ArrayList<Event>> searchEvents(@Query("search_term") String term) {
        return events.searchEvents(term);
    }

    @Override
    public Observable<Event> get(@Path("id") int id) {
        return events.get(id).retryWhen(ApiManager.defaultRetry());
    }

    @Override
    public Observable<Event> checkIn(int id) {
        return events.checkIn(id).retryWhen(ApiManager.defaultRetry());
    }

    @Override
    public Observable<Event> checkOut(int id) {
        return events.checkOut(id).retryWhen(ApiManager.defaultRetry());
    }

    @Override
    public Observable<Map<String, String>> play(@Path("id") int id, @Query("song_id") int song_id) {
        return events.play(id, song_id);
    }

    @Override
    public Observable<Map<String, String>> pause(@Path("id") int id) {
        return events.pause(id);
    }

    @Override
    public Observable<Song> postSong(@Body Song song) {
        return events.postSong(song).retryWhen(ApiManager.defaultRetry());
    }

    @Override
    public Observable<Like> postLike(@Query("song") Like song) {
        return events.postLike(song).retryWhen(ApiManager.defaultRetry());
    }

    @Override
    public Observable<Like> deleteLike(@Path("id") int id) {
        return events.deleteLike(id).retryWhen(ApiManager.defaultRetry());
    }

    @Override
    public Observable<Event> postEvent(@Body Event event) {
        return events.postEvent(event);
    }
}