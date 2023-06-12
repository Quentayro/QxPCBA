package org.qxpcba.model.music;

import java.util.HashSet;

public class MusicGetArtistsResponse {
    private HashSet<MusicArtist> artists;
    private HashSet<MusicGenre> genres;
    private HashSet<MusicArtist> suggestedArtists;

    public MusicGetArtistsResponse(HashSet<MusicArtist> artists,
            HashSet<MusicGenre> genres,
            HashSet<MusicArtist> suggestedArtists) {
        this.artists = artists;
        this.genres = genres;
        this.suggestedArtists = suggestedArtists;
    }

    public HashSet<MusicArtist> getArtists() {
        return this.artists;
    }

    public HashSet<MusicGenre> getGenres() {
        return this.genres;
    }

    public HashSet<MusicArtist> getSuggestedArtists() {
        return this.suggestedArtists;
    }

    public void setArtists(HashSet<MusicArtist> artists) {
        this.artists = artists;
    }

    public void setGenres(HashSet<MusicGenre> genres) {
        this.genres = genres;
    }

    public void setSuggestedArtists(HashSet<MusicArtist> suggestedArtists) {
        this.suggestedArtists = suggestedArtists;
    }
}
