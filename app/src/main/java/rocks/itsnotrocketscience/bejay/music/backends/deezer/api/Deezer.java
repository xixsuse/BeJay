package rocks.itsnotrocketscience.bejay.music.backends.deezer.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by nemi on 20/02/2016.
 */
@Retention(RetentionPolicy.SOURCE)
@Qualifier
public @interface Deezer {
    String PROVIDER_NAME = "deezer";

    String API_BASE_URL = "http://api.deezer.com/";

    String ID = "id";
    String READABLE = "readable";
    String TITLE = "title";
    String TITLE_SHORT = "short_title";
    String TITLE_VERSION = "title_version";
    String LINK = "link";
    String DURATION = "duration";
    String RANK = "rank";
    String EXPLICIT_LYRICS = "explicit_lyrics";
    String PREVIEW = "preview";
    String ARTIST = "artist";
    String ALBUM = "album";
    String NAME = "name";
    String SHARE = "share";
    String PICTURE = "picture";
    String PICTURE_SMALL = "picture_small";
    String PICTURE_MEDIUM = "picture_medium";
    String PICTURE_BIG = "picture_big";
    String NUMBER_OF_ALBUMS = "nb_album";
    String NUMBER_OF_FANS = "nb_fan";
    String HAS_SMART_RADIO = "radio";
    String TRACK_LIST = "tracklist";
    String UPC = "upc";
    String COVER = "cover";
    String COVER_SMALL = "cover_small";
    String COVER_MEDIUM = "cover_medium";
    String COVER_BIG = "cover_big";
    String GENRE_ID = "genre_id";
    String GENRES = "genres";
    String LABEL = "label";
    String NUMBER_OF_TRACKS = "nb_tracks";
    String FANS = "fans";
    String RATING = "rating";
    String RELEASE_DATE = "release_date";
    String RECORD_TYPE = "record_type";
    String AVAILABLE = "available";
    String ALTERNATIVE = "alternative";
    String CONTRIBUTORS = "contributors";
    String PUBLIC = "public";
    String TRACKS = "tracks";
    String USER = "user";
    String DATA = "data";
    String TOTAL = "total";
    String NEXT = "next";
    String PREV = "prev";
    String LIMIT = "limit";
    String INDEX = "index";


}
