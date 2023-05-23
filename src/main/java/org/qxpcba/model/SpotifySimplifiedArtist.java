package org.qxpcba.model;

public class SpotifySimplifiedArtist {
    private String name;
    private String spotifyId;

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
