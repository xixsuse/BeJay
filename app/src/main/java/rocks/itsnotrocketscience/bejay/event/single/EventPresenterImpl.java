package rocks.itsnotrocketscience.bejay.event.single;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.models.Event;
import rocks.itsnotrocketscience.bejay.models.Like;
import rocks.itsnotrocketscience.bejay.models.Song;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sirfunkenstine on 12/02/16.
 *
 */
public class EventPresenterImpl implements EventContract.EventPresenter {

    private static final String TAG = "EventPresenterImpl";
    EventContract.EventView view;
    Events event;

    @Inject
    public EventPresenterImpl(Events event){
        this.event=event;
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
        event.get(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Event>() {
                    @Override
                    public void onCompleted() {}
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

    private void addLike(final Song song) {
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
                       song.updateLiked(1);
                       view.notifyDataSetChanged();
                    }
                });
    }

    private void deleteLike(final Song song) {
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
                        song.updateLiked(-1);
                        view.notifyDataSetChanged();
                    }
                });
    }
    @Override
    public void toggleLike(Song song) {
        if (song.hasLikeId()) {
            deleteLike(song);
        } else {
            addLike(song);
        }
    }

    @Override
    public void registerUpdateReceiver(Context context) {
        context.registerReceiver(mMessageReceiver, new IntentFilter(EventActivity.EVENT_RECEIVER_ID));
    }

    @Override
    public void unregisterUpdateReceiver(Context context) {
        context.registerReceiver(mMessageReceiver, new IntentFilter(EventActivity.EVENT_RECEIVER_ID));
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceived: ");
        }
    };
}