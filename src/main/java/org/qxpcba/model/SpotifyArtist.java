package org.qxpcba.model;

public class SpotifyArtist extends SpotifySimplifiedArtist {
    private String[] genres;
    private String picture;

    public String[] getGenres() {
        return this.genres;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public void setImages(SpotifyImage[] pictures) {
        if (pictures.length == 0) {
            this.picture = "";
        } else {
            this.picture = pictures[0].getUrl();
        }
    }
}
