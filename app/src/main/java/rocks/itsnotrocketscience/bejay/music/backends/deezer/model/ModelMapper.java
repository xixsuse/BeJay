package rocks.itsnotrocketscience.bejay.music.backends.deezer.model;

import java.util.ArrayList;
import java.util.List;

import rocks.itsnotrocketscience.bejay.music.backends.deezer.api.Deezer;

public class ModelMapper {
    public static rocks.itsnotrocketscience.bejay.music.model.Track map(rocks.itsnotrocketscience.bejay.music.backends.deezer.model.Track deezerTrack) {
        if(deezerTrack != null) {
            rocks.itsnotrocketscience.bejay.music.model.Track track = new rocks.itsnotrocketscience.bejay.music.model.Track();
            track.setTitle(deezerTrack.getTitle());
            track.setDuration(deezerTrack.getDuration());
            track.setAlbum(map(deezerTrack.getAlbum()));
            track.setArtist(map(deezerTrack.getArtist()));
            track.setId(deezerTrack.getId().toString());

            track.setProvider(Deezer.PROVIDER_NAME);
            return track;
        }

        return null;
    }

    public static rocks.itsnotrocketscience.bejay.music.model.Artist map(rocks.itsnotrocketscience.bejay.music.backends.deezer.model.Artist deezerArtist) {
        if(deezerArtist != null) {
            rocks.itsnotrocketscience.bejay.music.model.Artist artist = new rocks.itsnotrocketscience.bejay.music.model.Artist();
            artist.setId(deezerArtist.getId().toString());
            artist.setName(deezerArtist.getName());
            artist.setPicture(deezerArtist.getPicture());
            artist.setProvider(Deezer.PROVIDER_NAME);
            return artist;
        }

        return null;
    }

    public static rocks.itsnotrocketscience.bejay.music.model.Album map(rocks.itsnotrocketscience.bejay.music.backends.deezer.model.Album deezerAlbum) {
        if(deezerAlbum != null) {
            rocks.itsnotrocketscience.bejay.music.model.Album album = new rocks.itsnotrocketscience.bejay.music.model.Album();
            album.setProvider(Deezer.PROVIDER_NAME);
            album.setId(deezerAlbum.getId().toString());
            album.setArtist(map(deezerAlbum.getArtist()));
            album.setTracks(map(deezerAlbum.getTracks()));
            album.setTitle(deezerAlbum.getTitle());
            album.setCover(deezerAlbum.getCover());
            return album;
        }

        return null;
    }

    public static rocks.itsnotrocketscience.bejay.music.model.Playlist map(rocks.itsnotrocketscience.bejay.music.backends.deezer.model.Playlist deezerPlaylist) {
        if(deezerPlaylist != null) {
            rocks.itsnotrocketscience.bejay.music.model.Playlist playlist = new rocks.itsnotrocketscience.bejay.music.model.Playlist();
            playlist.setProvider(Deezer.PROVIDER_NAME);
            playlist.setId(deezerPlaylist.getId().toString());
            playlist.setTitle(deezerPlaylist.getTitle());
            playlist.setNumberOfTracks(deezerPlaylist.getNumberOfTracks());
            playlist.setPicture(deezerPlaylist.getPicture());
            playlist.setPublic(deezerPlaylist.isPublic());
            if(deezerPlaylist.getUser() != null) {
                playlist.setUser(deezerPlaylist.getUser().getName());
            }
            playlist.setTracks(map(deezerPlaylist.getTracks()));
            return playlist;
        }
        return null;
    }

    public static List<rocks.itsnotrocketscience.bejay.music.model.Track> map(Collection<rocks.itsnotrocketscience.bejay.music.backends.deezer.model.Track> deezerTracks) {
        if(deezerTracks != null && deezerTracks.getData() != null) {
            List<rocks.itsnotrocketscience.bejay.music.model.Track> tracks = new ArrayList<>();
            for(rocks.itsnotrocketscience.bejay.music.backends.deezer.model.Track deezerTrack : deezerTracks.getData()) {
                tracks.add(map(deezerTrack));
            }
            return tracks;
        }

        return null;
    }
}
