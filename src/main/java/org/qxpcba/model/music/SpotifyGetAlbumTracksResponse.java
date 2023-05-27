package org.qxpcba.model.music;

public class SpotifyGetAlbumTracksResponse {
    private SpotifySimplifiedTrack[] tracks;
    private int tracksNumber;

    public SpotifySimplifiedTrack[] getTracks() {
        return this.tracks;
    }

    public int getTracksNumber() {
        return this.tracksNumber;
    }

    public void setItems(SpotifySimplifiedTrack[] tracks) {
        this.tracks = tracks;
    }

    public void setTotal(int tracksNumber) {
        this.tracksNumber = tracksNumber;
    }
}
