package rocks.itsnotrocketscience.bejay.event.single;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import com.cantrowitz.rxbroadcast.RxBroadcast;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.api.Constants;
import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.models.Event;
import rocks.itsnotrocketscience.bejay.models.Like;
import rocks.itsnotrocketscience.bejay.models.Song;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sirfunkenstine on 12/02/16.
 *
 */
public class EventPresenterImpl implements EventContract.EventPresenter {

    private static final String TAG = "EventPresenterImpl";
    private EventContract.EventView view;
    private final SharedPreferences preferences;
    private Subscription subscription;
    private final Events event;

    @Inject
    public EventPresenterImpl(SharedPreferences preferences, Events event){
        this.preferences=preferences;
        this.event=event;
        view = null;
    }

    @Override
    public void onViewAttached(EventContract.EventView view) {
        this.view=view;
    }

    public void onViewDetached() {
        this.view=null;
    }

    @Override
    public void onDestroy() {}

    @Override
    public void loadEvent(int id) {
        view.setProgressVisible(true);
        event.get(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Event>() {
                    @Override
                    public void onCompleted() {
                        view.setProgressVisible(false);
                    }
                    @Override
                    public final void onError(Throwable e) {
                        view.showError(e.toString());
                    }
                    @Override
                    public final void onNext(Event response) {
                        view.onEventLoaded(response);
                    }
                });
    }

    @Override
    public void addSong(Song song) {
        event.postSong(song)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Song>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public final void onError(Throwable e) {
                        view.showError(e.toString());
                    }
                    @Override
                    public final void onNext(Song response) {
                        view.onSongAdded(response);
                    }
                });
    }

    private void addLike(final Song song,int pos) {
        event.postLike(new Like(song)).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Like>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public final void onError(Throwable e) {
                        view.showError(e.toString());
                    }
                    @Override
                    public final void onNext(Like response) {
                        updateLikeUi(song, response, pos);
                    }
                });
    }

    private void deleteLike(final Song song, int pos) {
        event.deleteLike(song.getLikeId()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Like>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public final void onError(Throwable e) {
                        view.showError(e.toString());
                    }
                    @Override
                    public final void onNext(Like response) {
                        updateLikeUi(song,response, pos);
                    }
                });
    }
    @Override
    public void toggleLike(Song song, int pos) {
        if (song.hasLikeId()) {
            deleteLike(song, pos);
        } else {
            addLike(song, pos);
        }
    }

    private void updateLikeUi(Song song, Like response, int pos) {
        song.updateLikes(response!=null ? 1 :-1);
        song.updateLiked(response!=null ? response.getId() :-1);
        view.notifyItemChanged(pos);
    }

    @Override
    public void registerUpdateReceiver(Context context) {
        Observable<Intent> observable = RxBroadcast.fromBroadcast(context, new IntentFilter(EventActivity.EVENT_RECEIVER_ID));
        subscription= observable.subscribe(s -> {
            loadEvent(preferences.getInt(Constants.EVENT_PK, -1));
        });
    }

    @Override
    public void unregisterUpdateReceiver() {
       subscription.unsubscribe();
    }
}