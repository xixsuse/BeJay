package rocks.itsnotrocketscience.bejay.music;

import java.util.List;

import rx.Observable;

public interface Api {

    interface Search {
        Pager<rocks.itsnotrocketscience.bejay.music.model.Track> track(String query, Long pageSize);
        Pager<rocks.itsnotrocketscience.bejay.music.model.Album> album(String query, Long pageSize);
        Pager<rocks.itsnotrocketscience.bejay.music.model.Artist> artist(String query, Long pageSize);
        Pager<rocks.itsnotrocketscience.bejay.music.model.Playlist> playlist(String query, Long pageSize);
    }

    interface Artist extends Model<rocks.itsnotrocketscience.bejay.music.model.Artist> {
        Observable<List<rocks.itsnotrocketscience.bejay.music.model.Track>> topTracks(String id);
        Observable<List<rocks.itsnotrocketscience.bejay.music.model.Album>> albums(String id);
    }

    interface Model<M> {
        Observable<M> get(String id);
    }

    interface Track extends Model<rocks.itsnotrocketscience.bejay.music.model.Track>{

    }

    interface Album extends Model<rocks.itsnotrocketscience.bejay.music.model.Album> {

    }

    interface Playlist extends Model<rocks.itsnotrocketscience.bejay.music.model.Playlist> {

    }
}