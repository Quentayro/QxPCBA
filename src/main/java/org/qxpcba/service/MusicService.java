package org.qxpcba.service;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.qxpcba.model.music.MusicGenre;
import org.qxpcba.model.music.MusicGetArtistResponse;
import org.qxpcba.model.music.MusicGetArtistsResponse;
import org.qxpcba.model.music.SpotifyArtist;
import org.qxpcba.model.music.SpotifyGetAcessTokenResponse;
import org.qxpcba.model.music.SpotifyGetAlbumTracksResponse;
import org.qxpcba.model.music.SpotifyGetAlbumsResponse;
import org.qxpcba.model.music.SpotifyGetArtistRelatedArtistsResponse;
import org.qxpcba.model.music.SpotifyGetArtistsResponse;
import org.qxpcba.model.music.SpotifySimplifiedAlbum;
import org.qxpcba.model.music.SpotifySimplifiedArtist;
import org.qxpcba.model.music.SpotifySimplifiedTrack;
import org.qxpcba.repository.MusicRepository;
import org.qxpcba.utils.Constants;
import org.qxpcba.utils.Secrets;

@Service
public class MusicService {
    private Logger logger = LoggerFactory.getLogger(MusicService.class);
    private final int msWaitTime = 1;
    private MusicRepository musicRepository;
    private String spotifyAccessToken;
    private Date spotifyAccessTokenExpirationDate = new Date();
    private WebClient webClient = WebClient.create();

    public MusicService(MusicRepository musicRepository) {
        this.musicRepository = musicRepository;
    }

    public MusicGetArtistResponse getArtist(String spotifyId) {
        return null; // TODO
    }

    public MusicGetArtistsResponse getArtists() {
        return new MusicGetArtistsResponse(this.musicRepository.getArtists(),
                this.musicRepository.getGenres(),
                this.musicRepository.getSuggestedArtists());
    }

    public void postArtist(String artistSpotifyId) throws Exception {
        try {
            HashSet<String> addedButNotSuggestedArtists = this.musicRepository.selectAddedArtistsSpotifyIds();
            if (!addedButNotSuggestedArtists.contains(artistSpotifyId)) {
                HashSet<SpotifySimplifiedAlbum> albumsToAdd = this.spotifyGetAlbums(artistSpotifyId);

                HashSet<SpotifySimplifiedTrack> tracksToAdd = new HashSet<SpotifySimplifiedTrack>();
                for (SpotifySimplifiedAlbum album : albumsToAdd) {
                    tracksToAdd.addAll(this.spotifyGetAlbumTracks(album.getSpotifyId()));
                }

                HashSet<String> genresToAdd = new HashSet<String>();

                HashSet<String> artistsToAddSpotifyIds = this
                        .spotifyGetArtistRelatedArtistsIds(artistSpotifyId);
                for (SpotifySimplifiedAlbum album : albumsToAdd) {
                    for (SpotifySimplifiedArtist artist : album.getArtists()) {
                        artistsToAddSpotifyIds.add(artist.getSpotifyId());

                        for (String genre : album.getGenres()) {
                            genresToAdd.add(genre);
                        }
                    }
                }
                for (SpotifySimplifiedTrack track : tracksToAdd) {
                    for (SpotifySimplifiedArtist artist : track.getArtists()) {
                        artistsToAddSpotifyIds.add(artist.getSpotifyId());
                    }
                }
                HashSet<SpotifyArtist> artistsToAdd = this.spotifyGetArtists(artistsToAddSpotifyIds);

                for (SpotifyArtist artist : artistsToAdd) {
                    for (String genre : artist.getGenres()) {
                        genresToAdd.add(genre);
                    }
                }

                // Filtering data to add according to what is already in the database
                HashSet<String> addedAlbumsSpotifyIds = this.musicRepository.selectSpotifyIdsFromTMusicAlbums();

                HashSet<SpotifySimplifiedAlbum> filteredAlbumsToAdd = new HashSet<SpotifySimplifiedAlbum>();
                for (SpotifySimplifiedAlbum album : albumsToAdd) {
                    if (!addedAlbumsSpotifyIds.contains(album.getSpotifyId())) {
                        filteredAlbumsToAdd.add(album);
                    }
                }

                HashSet<String> addedArtistsSpotifyIds = this.musicRepository.selectSpotifyIdsFromTMusicArtists();

                HashSet<SpotifyArtist> filteredArtistsToAdd = new HashSet<SpotifyArtist>();
                for (SpotifyArtist artist : artistsToAdd) {
                    if (!addedArtistsSpotifyIds.contains(artist.getSpotifyId())) {
                        filteredArtistsToAdd.add(artist);
                    }
                }

                HashSet<String> addedGenresSpotifyIds = this.musicRepository.selectSpotifyIdsFromTMusicGenres();

                HashSet<String> filteredGenresToAdd = new HashSet<String>();
                for (String genre : genresToAdd) {
                    if (!addedGenresSpotifyIds.contains(genre)) {
                        filteredGenresToAdd.add(genre);
                    }
                }

                HashSet<String> addedTracksSpotifyIds = this.musicRepository.selectSpotifyIdsFromTMusicTracks();

                HashSet<SpotifySimplifiedTrack> filteredTracksToAdd = new HashSet<SpotifySimplifiedTrack>();
                for (SpotifySimplifiedTrack track : tracksToAdd) {
                    if (!addedTracksSpotifyIds.contains(track.getSpotifyId())) {
                        filteredTracksToAdd.add(track);
                    }
                }

                HashSet<SpotifyArtist> filteredSuggestedArtistsToAdd = new HashSet<SpotifyArtist>();
                for (SpotifyArtist artist : artistsToAdd) {
                    if (!addedButNotSuggestedArtists.contains(artist.getSpotifyId())) {
                        filteredSuggestedArtistsToAdd.add(artist);
                    }
                }

                this.musicRepository.postArtist(filteredAlbumsToAdd, artistSpotifyId, filteredArtistsToAdd,
                        filteredGenresToAdd, filteredSuggestedArtistsToAdd, filteredTracksToAdd);
            } else {
                throw new Exception();
            }
        } catch (Exception e) {
            this.logger.error("MusicService - postArtist(artistSpotifyId) failed");
            throw e;
        }
    }

    public HashSet<MusicGenre> postGenreDisplayText(MusicGenre genre) {
        try {
            this.musicRepository.postGenreDisplayText(genre);
            return this.musicRepository.getGenres();
        } catch (Exception e) {
            this.logger.error("MusicService - postGenreDisplayText(genre) failed");
            throw e;
        }
    }

    private String spotifyGetAccessToken() throws Exception {
        if (spotifyAccessToken == null || spotifyAccessTokenExpirationDate.before(new Date())) {
            try {
                Thread.sleep(this.msWaitTime);
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

    private HashSet<SpotifySimplifiedTrack> spotifyGetAlbumTracks(String albumSpotifyId) throws Exception {
        int offset = 0;
        int tracksNumber = 1;
        HashSet<SpotifySimplifiedTrack> tracksToAdd = new HashSet<SpotifySimplifiedTrack>();

        try {
            while (offset < tracksNumber) {
                Thread.sleep(this.msWaitTime);
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
                    track.setAlbumSpotifyId(albumSpotifyId);
                    tracksToAdd.add(track);
                }
            }
        } catch (Exception e) {
            this.logger.error("MusicService - spotifyGetAlbumTracks(" + albumSpotifyId + ") failed");
            throw e;
        }

        return tracksToAdd;
    }

    private HashSet<SpotifySimplifiedAlbum> spotifyGetAlbums(String artistSpotifyId) throws Exception {
        int albumsNumber = 1;
        HashSet<SpotifySimplifiedAlbum> albumsToAdd = new HashSet<SpotifySimplifiedAlbum>();
        int offset = 0;

        try {
            while (offset < albumsNumber) {
                Thread.sleep(this.msWaitTime);
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

    private HashSet<String> spotifyGetArtistRelatedArtistsIds(String artistSpotifyId) throws Exception {
        HashSet<String> relatedArtistsIds = new HashSet<String>();

        try {
            Thread.sleep(this.msWaitTime);
            SpotifyGetArtistRelatedArtistsResponse spotifyGetArtistRelatedArtistsResponse = this.webClient.get()
                    .uri("https://api.spotify.com/v1/artists/" + artistSpotifyId + "/related-artists")
                    .header("Authorization", this.spotifyGetAccessToken())
                    .retrieve()
                    .bodyToMono(SpotifyGetArtistRelatedArtistsResponse.class)
                    .block();

            for (SpotifyArtist artist : spotifyGetArtistRelatedArtistsResponse.getArtists()) {
                relatedArtistsIds.add(artist.getSpotifyId());
            }
        } catch (Exception e) {
            this.logger.error("MusicService - spotifyGetArtistRelatedArtistsIds(" + artistSpotifyId + ") failed");
            throw e;
        }

        return relatedArtistsIds;
    }

    private HashSet<SpotifyArtist> spotifyGetArtists(HashSet<String> artistsSpotifyIds) throws Exception {
        HashSet<SpotifyArtist> artistsToAdd = new HashSet<SpotifyArtist>();

        int requestsNumber = artistsSpotifyIds.size() / 50;
        if (artistsSpotifyIds.size() % 50 != 0) {
            requestsNumber += 1;
        }

        String[] requestsArtistsSpotifyIds = new String[requestsNumber];
        Arrays.fill(requestsArtistsSpotifyIds, "");

        int index = 0;
        for (String artistSpotifyId : artistsSpotifyIds) {
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
                Thread.sleep(this.msWaitTime);
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
