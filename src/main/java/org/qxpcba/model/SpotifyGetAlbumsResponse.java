package org.qxpcba.model;

public class SpotifyGetAlbumsResponse {
    private SpotifySimplifiedAlbum[] albums;
    private int albumsNumber;

    public SpotifySimplifiedAlbum[] getAlbums() {
        return this.albums;
    }

    public int getAlbumsNumber() {
        return this.albumsNumber;
    }

    public void setItems(SpotifySimplifiedAlbum[] albums) {
        this.albums = albums;
    }

    public void setTotal(int albumsNumber) {
        this.albumsNumber = albumsNumber;
    }
}
