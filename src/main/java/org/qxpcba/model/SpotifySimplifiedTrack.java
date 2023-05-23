package org.qxpcba.model;

public class SpotifySimplifiedTrack {
    private SpotifySimplifiedArtist[] artists;
    private int discNumber;
    private int duration;
    private String name;
    private int order;
    private String spotifyId;

    public SpotifySimplifiedArtist[] getArtists() {
        return this.artists;
    }

    public int getDiscNumber() {
        return this.discNumber;
    }

    public int getDuration() {
        return this.duration;
    }

    public int getOrder() {
        return this.order;
    }

    public String getName() {
        return this.name;
    }

    public String getSpotifyId() {
        return this.spotifyId;
    }

    public void setArtists(SpotifySimplifiedArtist[] artists) {
        this.artists = artists;
    }

    public void setDisc_number(int discNumber) {
        this.discNumber = discNumber;
    }

    public void setDuration_ms(int duration) {
        this.duration = duration;
    }

    public void setId(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTrack_number(int order) {
        this.order = order;
    }
}
