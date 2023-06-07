package org.qxpcba.model.music;

import com.fasterxml.jackson.databind.JsonNode;

public class SpotifySimplifiedAlbum {
    private SpotifySimplifiedArtist[] artists;
    private String[] genres; // ok
    private String group; // ok
    private String name; // ok
    private String picture;
    private String releaseDate; // ok
    private String releaseDatePrecision; // ok
    private String spotifyId; // ok
    private int tracksNumber; // ok
    private String type; // ok

    public SpotifySimplifiedAlbum() {
    }

    public SpotifySimplifiedAlbum(JsonNode album) {
        JsonNode artistsNode = album.get("artists");
        if (artistsNode.isArray()) {
            SpotifySimplifiedArtist[] artists = new SpotifySimplifiedArtist[artistsNode.size()];
            int index = 0;
            for (JsonNode artistNode : artistsNode) {
                artists[index] = new SpotifySimplifiedArtist(artistNode.get("name").asText(),
                        artistNode.get("id").asText());
                index++;
            }
            this.artists = artists;
        }

        JsonNode genresNode = album.get("genres");
        if (genresNode != null) {
            if (genresNode.isArray()) {
                String[] genres = new String[genresNode.size()];
                int index = 0;
                for (JsonNode genre : genresNode) {
                    genres[index] = genre.asText();
                    index++;
                }
                this.genres = genres;
            }
        }

        this.group = album.get("album_group").asText();
        this.name = album.get("name").asText();
        this.releaseDate = album.get("release_date").asText();
        this.releaseDatePrecision = album.get("release_date_precision").asText();
        this.spotifyId = album.get("id").asText();
        this.tracksNumber = album.get("total_tracks").asInt();
        this.type = album.get("album_type").asText();

    }

    public String getAlbumType() {
        return this.type;
    }

    public SpotifySimplifiedArtist[] getArtists() {
        return this.artists;
    }

    public String[] getGenres() {
        if (this.genres == null) {
            String[] emptyArray = {};
            this.genres = emptyArray;
        }
        return this.genres;
    }

    public String getGroup() {
        return this.group;
    }

    public String getName() {
        return this.name;
    }

    public String getPicture() {
        return this.picture;
    }

    public String getReleaseDate() {
        return this.releaseDate;
    }

    public String getReleaseDatePrecision() {
        return this.releaseDatePrecision;
    }

    public String getSpotifyId() {
        return this.spotifyId;
    }

    public int getTracksNumber() {
        return this.tracksNumber;
    }

    public void setAlbum_group(String group) {
        this.group = group;
    }

    public void setAlbum_type(String type) {
        this.type = type;
    }

    public void setArtists(SpotifySimplifiedArtist[] artists) {
        this.artists = artists;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }

    public void setId(String spotifyId) {
        this.spotifyId = spotifyId;
    }

    public void setImages(SpotifyImage[] pictures) {
        this.picture = pictures[0].getUrl();
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRelease_date(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setRelease_date_precision(String releaseDatePrecision) {
        this.releaseDatePrecision = releaseDatePrecision;
    }

    public void setTotal_tracks(int tracksNumber) {
        this.tracksNumber = tracksNumber;
    }
}
