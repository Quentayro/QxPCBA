package org.qxpcba.model;

public class SpotifyGetArtistsResponse {
    private SpotifyArtist[] artists;

    public SpotifyArtist[] getArtists() {
        return this.artists;
    }

    public void setArtists(SpotifyArtist[] artists) {
        this.artists = artists;
    }
}
