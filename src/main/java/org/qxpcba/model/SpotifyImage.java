package org.qxpcba.model;

public class SpotifyImage {
    private String url;

    public String getUrl() {
        return this.url.split("https://i.scdn.co/image/")[1];
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
