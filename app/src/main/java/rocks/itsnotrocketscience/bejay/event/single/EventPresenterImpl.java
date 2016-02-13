package rocks.itsnotrocketscience.bejay.event.single;

import android.widget.Toast;

import javax.inject.Inject;

import rocks.itsnotrocketscience.bejay.api.retrofit.Events;
import rocks.itsnotrocketscience.bejay.models.Event;
import rocks.itsnotrocketscience.bejay.models.Song;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sirfunkenstine on 12/02/16.
 *
 */
public class EventPresenterImpl implements EventContract.EventPresenter {

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
    public void onDestroy() {
    }

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
    public void adSong(Song song) {

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
}