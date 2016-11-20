package rocks.itsnotrocketscience.bejay.event.single;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;

import javax.inject.Inject;

import com.cantrowitz.rxbroadcast.RxBroadcast;

import rocks.itsnotrocketscience.bejay.api.Constants;
import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.models.Like;
import rocks.itsnotrocketscience.bejay.models.Song;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sirfunkenstine on 12/02/16.
 */
public class EventPresenterImpl implements EventContract.EventPresenter {

    private EventContract.EventView view;
    private final SharedPreferences preferences;
    private Subscription subscription;
    private final Events event;

    @Inject
    public EventPresenterImpl(SharedPreferences preferences, Events event) {
        this.preferences = preferences;
        this.event = event;
        view = null;
    }

    @Override
    public void onViewAttached(EventContract.EventView view) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        view = null;
    }

    @Override
    public void onDestroy() {
        //not implemented
    }

    @Override
    public void loadEvent(int id) {
        view.setProgressVisible(true);
        event.get(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> view.onEventLoaded(response),
                        e -> view.setProgressVisible(false),
                        () -> view.setProgressVisible(false));
    }

    @Override
    public void addSong(Song song) {
        event.postSong(song)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> view.onSongAdded(response),
                        e -> view.showToast(e.toString()));
    }

    private void addLike(Song song, int pos) {
        event.postLike(new Like(song)).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> updateLikeUi(song, response, pos),
                        e -> view.showToast(e.toString()));
    }

    private void deleteLike(Song song, int pos) {
        event.deleteLike(song.getLikeId()).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> updateLikeUi(song, response, pos),
                        e -> view.showToast(e.toString()));
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
        song.updateLikes((null != response) ? 1 : -1);
        song.updateLiked((null != response) ? response.getId() : -1);
        view.notifyItemChanged(pos);
    }

    @Override
    public void registerUpdateReceiver(Context context) {
        Observable<Intent> observable = RxBroadcast.fromBroadcast(context, new IntentFilter(EventActivity.EVENT_RECEIVER_ID));
        subscription = observable.subscribe(s -> {
            view.showToast("gcm message received");
            loadEvent(preferences.getInt(Constants.EVENT_PK, -1));
        });
    }

    @Override
    public void unregisterUpdateReceiver() {
        subscription.unsubscribe();
    }
}