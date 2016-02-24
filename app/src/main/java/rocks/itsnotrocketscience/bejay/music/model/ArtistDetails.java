package rocks.itsnotrocketscience.bejay.music.model;

import java.util.List;

public class ArtistDetails {
    private Artist artist;
    private List<Track> topTracks;
    private List<Album> discography;

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public List<Track> getTopTracks() {
        return topTracks;
    }

    public void setTopTracks(List<Track> topTracks) {
        this.topTracks = topTracks;
    }

    public List<Album> getDiscography() {
        return discography;
    }

    public void setDiscography(List<Album> discography) {
        this.discography = discography;
    }
}
