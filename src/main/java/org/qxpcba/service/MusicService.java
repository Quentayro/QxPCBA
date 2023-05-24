package org.qxpcba.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import org.qxpcba.model.SpotifyArtist;
import org.qxpcba.model.SpotifyGetAcessTokenResponse;
import org.qxpcba.model.SpotifyGetAlbumsResponse;
import org.qxpcba.model.SpotifyGetArtistRelatedArtistsResponse;
import org.qxpcba.model.SpotifyGetArtistsResponse;
import org.qxpcba.model.SpotifyGetAlbumTracksResponse;
import org.qxpcba.model.SpotifySimplifiedAlbum;
import org.qxpcba.model.SpotifySimplifiedArtist;
import org.qxpcba.model.SpotifySimplifiedTrack;
import org.qxpcba.utils.Constants;
import org.qxpcba.utils.Secrets;

@Service
public class MusicService {
    private Logger logger = LoggerFactory.getLogger(MusicService.class);
    private String spotifyAccessToken;
    private Date spotifyAccessTokenExpirationDate = new Date();
    private WebClient webClient = WebClient.create();

    public String postArtist(String artistSpotifyId) {
        System.out.println("XXX - POST ARTIST - XXX"); // TODO : delete
        System.out.println("artistSpotifyId : " + artistSpotifyId); // TODO : delete
        try {
            ArrayList<SpotifySimplifiedAlbum> albumsToAdd = this.spotifyGetAlbums(artistSpotifyId);
            System.out.println("albumsToAdd.size : " + albumsToAdd.size()); // TODO : delete

            ArrayList<SpotifySimplifiedTrack> tracksToAdd = new ArrayList<SpotifySimplifiedTrack>();
            for (SpotifySimplifiedAlbum album : albumsToAdd) {
                tracksToAdd.addAll(this.spotifyGetAlbumTracks(album.getSpotifyId()));
            }
            System.out.println("tracksToAdd.size : " + tracksToAdd.size()); // TODO : delete

            HashMap<String, Boolean> artistsToAddSpotifyIds = this.spotifyGetArtistRelatedArtistsIds(artistSpotifyId);
            for (SpotifySimplifiedAlbum album : albumsToAdd) {
                for (SpotifySimplifiedArtist artist : album.getArtists()) {
                    artistsToAddSpotifyIds.put(artist.getSpotifyId(), true);
                }
            }
            for (SpotifySimplifiedTrack track : tracksToAdd) {
                for (SpotifySimplifiedArtist artist : track.getArtists()) {
                    artistsToAddSpotifyIds.put(artist.getSpotifyId(), true);
                }
            }
            System.out.println("artistsToAddSpotifyIds.size : " + artistsToAddSpotifyIds.size()); // TODO : delete

            ArrayList<SpotifyArtist> artistsToAdd = this.spotifyGetArtists(artistsToAddSpotifyIds);
            System.out.println("artistsToAdd.size : " + artistsToAdd.size()); // TODO : delete

            // TODO : Database
        } catch (Exception e) {
            this.logger.error("MusicService - postArtist(" + artistSpotifyId + ") failed");
            throw e;
        }

        return artistSpotifyId;
    }

    private String spotifyGetAccessToken() {
        if (spotifyAccessToken == null || spotifyAccessTokenExpirationDate.before(new Date())) {
            try {
                SpotifyGetAcessTokenResponse spotifyAccessTokenResponse = this.webClient.post()
                        .uri("https://accounts.spotify.com/api/token")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .bodyValue(
                                "grant_type=client_credentials&client_id=" + Constants.spotifyClientId
                                        + "&client_secret=" + Secrets.spotifyClientSecret)
                        .retrieve()
                        .bodyToMono(SpotifyGetAcessTokenResponse.class)
                        .block();

                this.spotifyAccessToken = spotifyAccessTokenResponse.getTokenType() + " "
                        + spotifyAccessTokenResponse.getAccessToken();

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                calendar.add(Calendar.SECOND, spotifyAccessTokenResponse.getExpiresIn());
                this.spotifyAccessTokenExpirationDate = calendar.getTime();

            } catch (Exception e) {
                this.logger.error("MusicService - spotifyGetAccessToken() failed");
                throw e;
            }
        }

        return this.spotifyAccessToken;
    }

    private ArrayList<SpotifySimplifiedTrack> spotifyGetAlbumTracks(String albumSpotifyId) {
        int offset = 0;
        int tracksNumber = 1;
        ArrayList<SpotifySimplifiedTrack> tracksToAdd = new ArrayList<SpotifySimplifiedTrack>();

        try {
            while (offset < tracksNumber) {
                SpotifyGetAlbumTracksResponse spotifyGetAlbumTracksResponse = this.webClient.get()
                        .uri("https://api.spotify.com/v1/albums/" + albumSpotifyId + "/tracks?limit=50&offset="
                                + offset)
                        .header("Authorization", this.spotifyGetAccessToken())
                        .retrieve()
                        .bodyToMono(SpotifyGetAlbumTracksResponse.class)
                        .block();

                offset += 50;
                tracksNumber = spotifyGetAlbumTracksResponse.getTracksNumber();

                for (SpotifySimplifiedTrack track : spotifyGetAlbumTracksResponse.getTracks()) {
                    tracksToAdd.add(track);
                }
            }
        } catch (Exception e) {
            this.logger.error("MusicService - spotifyGetAlbumTracks(" + albumSpotifyId + ") failed");
            throw e;
        }

        return tracksToAdd;
    }

    private ArrayList<SpotifySimplifiedAlbum> spotifyGetAlbums(String artistSpotifyId) {
        int albumsNumber = 1;
        ArrayList<SpotifySimplifiedAlbum> albumsToAdd = new ArrayList<SpotifySimplifiedAlbum>();
        int offset = 0;

        try {
            while (offset < albumsNumber) {
                SpotifyGetAlbumsResponse spotifyGetAlbumsResponse = this.webClient.get()
                        .uri("https://api.spotify.com/v1/artists/" + artistSpotifyId + "/albums?limit=50&offset="
                                + offset)
                        .header("Authorization", this.spotifyGetAccessToken())
                        .retrieve()
                        .bodyToMono(SpotifyGetAlbumsResponse.class)
                        .block();

                offset += 50;
                albumsNumber = spotifyGetAlbumsResponse.getAlbumsNumber();

                for (SpotifySimplifiedAlbum album : spotifyGetAlbumsResponse.getAlbums()) {
                    if (album.getAlbumType().equals("album") || album.getAlbumType().equals("single")
                            || album.getGroup().equals("compilation")) {
                        albumsToAdd.add(album);
                    }
                }
            }
        } catch (Exception e) {
            this.logger.error("MusicService - spotifyGetAlbums(" + artistSpotifyId + ") failed");
            throw e;
        }

        return albumsToAdd;
    }

    private HashMap<String, Boolean> spotifyGetArtistRelatedArtistsIds(String artistSpotifyId) {
        HashMap<String, Boolean> relatedArtistsIds = new HashMap<String, Boolean>();

        try {
            SpotifyGetArtistRelatedArtistsResponse spotifyGetArtistRelatedArtistsResponse = this.webClient.get()
                    .uri("https://api.spotify.com/v1/artists/" + artistSpotifyId + "/related-artists")
                    .header("Authorization", this.spotifyGetAccessToken())
                    .retrieve()
                    .bodyToMono(SpotifyGetArtistRelatedArtistsResponse.class)
                    .block();

            for (SpotifyArtist artist : spotifyGetArtistRelatedArtistsResponse.getArtists()) {
                relatedArtistsIds.put(artist.getSpotifyId(), true);
            }
        } catch (Exception e) {
            this.logger.error("MusicService - spotifyGetArtistRelatedArtistsIds(" + artistSpotifyId + ") failed");
            throw e;
        }

        return relatedArtistsIds;
    }

    private ArrayList<SpotifyArtist> spotifyGetArtists(HashMap<String, Boolean> artistsSpotifyIds) {
        ArrayList<SpotifyArtist> artistsToAdd = new ArrayList<SpotifyArtist>();

        int requestsNumber = artistsSpotifyIds.size() / 50;
        if (artistsSpotifyIds.size() % 50 != 0) {
            requestsNumber += 1;
        }

        String[] requestsArtistsSpotifyIds = new String[requestsNumber];
        Arrays.fill(requestsArtistsSpotifyIds, "");

        int index = 0;
        for (String artistSpotifyId : artistsSpotifyIds.keySet()) {
            String artistSpotifyIdToAdd = "";
            if (index % 50 != 0) {
                artistSpotifyIdToAdd += ",";
            }
            artistSpotifyIdToAdd += artistSpotifyId;
            requestsArtistsSpotifyIds[index / 50] += artistSpotifyIdToAdd;
            index += 1;
        }

        for (String requestArtistsSpotifyIds : requestsArtistsSpotifyIds) {
            try {
                SpotifyGetArtistsResponse spotifyGetArtistRelatedArtistsResponse = this.webClient.get()
                        .uri("https://api.spotify.com/v1/artists?ids=" + requestArtistsSpotifyIds)
                        .header("Authorization", this.spotifyGetAccessToken())
                        .retrieve()
                        .bodyToMono(SpotifyGetArtistsResponse.class)
                        .block();

                for (SpotifyArtist artist : spotifyGetArtistRelatedArtistsResponse.getArtists()) {
                    artistsToAdd.add(artist);
                }
            } catch (Exception e) {
                this.logger.error("MusicService - spotifyGetArtists(artistsSpotifyIds) failed");
                throw e;
            }
        }

        return artistsToAdd;
    }
}
