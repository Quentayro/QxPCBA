package org.qxpcba.model.music;

import com.fasterxml.jackson.databind.JsonNode;

public class SpotifyGetAlbumsResponse {
    private SpotifySimplifiedAlbum[] albums;
    private int albumsNumber;

    public SpotifySimplifiedAlbum[] getAlbums() {
        return this.albums;
    }

    public int getAlbumsNumber() {
        return this.albumsNumber;
    }

    public void setItems(JsonNode[] items) {
        SpotifySimplifiedAlbum[] albums = new SpotifySimplifiedAlbum[items.length];

        int index = 0;
        for (JsonNode item : items) {
            albums[index] = new SpotifySimplifiedAlbum(item);
            index++;
        }

        this.albums = albums;
    }

    public void setTotal(int albumsNumber) {
        this.albumsNumber = albumsNumber;
    }
}
