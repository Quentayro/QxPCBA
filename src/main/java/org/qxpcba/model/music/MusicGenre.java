package org.qxpcba.model.music;

public class MusicGenre {
    private String displayText;
    private int id;
    private String spotifyId;

    public MusicGenre(String displayText, int id, String spotifyId) {
        this.displayText = displayText;
        this.id = id;
        this.spotifyId = spotifyId;
    }

    public String getDisplayText() {
        return this.displayText;
    }

    public int getId() {
        return this.id;
    }

    public String getSpotifyId() {
        return this.spotifyId;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSpotifyId(String spotifyId) {
        this.spotifyId = spotifyId;
    }
}
