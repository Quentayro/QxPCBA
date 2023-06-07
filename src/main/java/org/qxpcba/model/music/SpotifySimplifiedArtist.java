package org.qxpcba.model.music;

public class SpotifySimplifiedArtist {
    private String name;
    private String spotifyId;

    public SpotifySimplifiedArtist() {
    }

    public SpotifySimplifiedArtist(String name, String spotifyId) {
        this.name = name;
        this.spotifyId = spotifyId;
    }

    public String getName() {
        return this.name;
    }

    public String getSpotifyId() {
        return this.spotifyId;
    }

    public void setId(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
