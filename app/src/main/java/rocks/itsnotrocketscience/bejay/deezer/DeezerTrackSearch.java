package rocks.itsnotrocketscience.bejay.deezer;

import java.util.ArrayList;
import java.util.List;

import rocks.itsnotrocketscience.bejay.deezer.api.Search;
import rocks.itsnotrocketscience.bejay.tracks.Track;
import rocks.itsnotrocketscience.bejay.tracks.search.TrackSearch;
import rx.Observable;
import rx.functions.Func1;

import static rocks.itsnotrocketscience.bejay.deezer.Deezer.PROVIDER_NAME;

/**
 * Created by nemi on 20/02/2016.
 */
public class DeezerTrackSearch implements TrackSearch {
    private static final Func1<rocks.itsnotrocketscience.bejay.deezer.model.Track, Track> MAP_MODEL = deezerTrack -> {
        Track track = new Track();
        track.setTitle(deezerTrack.getTitle());
        track.setDuration(deezerTrack.getDuration());
        track.setArtist(deezerTrack.getArtist().getName());
        track.setAlbumName(deezerTrack.getAlbum().getTitle());
        track.setId(deezerTrack.getId().toString());
        track.setProvider(PROVIDER_NAME);
        return track;
    };

    private final Search search;
    private final String query;
    private final long limit;
    private long nextPage;
    private long total;

    public DeezerTrackSearch(Search search, String query, int limit) {
        this.search = search;
        this.query = query;
        this.limit = limit;
        this.nextPage = 0;
        this.total = -1;
    }

    @Override
    public Observable<List<Track>> loadMoreResults() {
        if(nextPageValid()) {
            return search.track(query, nextPage++, limit)
                    .map(resultSet -> {
                        DeezerTrackSearch.this.total = resultSet.getTotal();
                        return resultSet.getData();
                    }).flatMap(tracks -> Observable.from(tracks))
                    .map(MAP_MODEL)
                    .collect(() -> new ArrayList<>(), (tracks1, track) -> tracks1.add(track));

        }

        // no more resuls
        return Observable.empty();
    }

    boolean nextPageValid() {
        // nothing has been loaded yet
        if(total == -1) {
            return true;
        }

        // total items loaded is less than pages loaded times page limit
        return (nextPage - 1) * limit < total;
    }

}
