package org.qxpcba.model.music;

public class SpotifyGetArtistRelatedArtistsResponse {
    private SpotifyArtist[] artists;

    public SpotifyArtist[] getArtists() {
        return this.artists;
    }

    public void setArtists(SpotifyArtist[] artists) {
        this.artists = artists;
    }
}
