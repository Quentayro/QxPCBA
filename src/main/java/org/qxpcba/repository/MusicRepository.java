package org.qxpcba.repository;

import java.util.ArrayList;
import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import org.qxpcba.model.music.SpotifyArtist;
import org.qxpcba.model.music.SpotifySimplifiedAlbum;
import org.qxpcba.model.music.SpotifySimplifiedTrack;

@Repository
public class MusicRepository {
    private JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(MusicRepository.class);

    public MusicRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void postArtist(ArrayList<SpotifySimplifiedAlbum> albumsToAdd, ArrayList<SpotifyArtist> artistsToAdd,
            HashSet<String> genresToAdd,
            ArrayList<SpotifySimplifiedTrack> tracksToAdd) {
        try {
            // TODO : Delete the following queries
            jdbcTemplate.update("DELETE FROM t_music_tracks;");
            jdbcTemplate.update("ALTER TABLE t_music_tracks AUTO_INCREMENT = 1");
            jdbcTemplate.update("DELETE FROM t_music_albums;");
            jdbcTemplate.update("ALTER TABLE t_music_albums AUTO_INCREMENT = 1");
            jdbcTemplate.update("DELETE FROM t_music_artists;");
            jdbcTemplate.update("ALTER TABLE t_music_artists AUTO_INCREMENT = 1");
            jdbcTemplate.update("DELETE FROM t_music_genres;");
            jdbcTemplate.update("ALTER TABLE t_music_genres AUTO_INCREMENT = 1");
            this.insertIntoTMusicArtists(artistsToAdd);
            this.insertIntoTMusicAlbums(albumsToAdd);
            this.insertIntoTMusicTracks(tracksToAdd);
            this.insertIntoTMusicGenres(genresToAdd);
            System.out.println("postArtist repository logic done");
        } catch (Exception e) {
            System.out.println(e);
            this.logger.error("MusicRepository - postArtist(albumsToAdd, artistsToAdd, tracksToAdd) failed");
            throw e;
        }

    }

    private void insertIntoTMusicAlbums(ArrayList<SpotifySimplifiedAlbum> albumsToAdd) {
        String query = "INSERT INTO t_music_albums (c_name, c_picture, c_release_day, c_release_month, c_release_year, c_spotify_id, c_tracks_number, c_type) VALUES\n";

        int albumsNumber = albumsToAdd.size();
        int index = 1;
        for (SpotifySimplifiedAlbum album : albumsToAdd) {
            query += "('" + album.getName().replaceAll("'", "''") + "','" + album.getPicture() + "',";
            String[] releaseDate = album.getReleaseDate().split("-");
            String releaseDatePrecision = album.getReleaseDatePrecision();
            if (releaseDatePrecision.equals("day")) {
                query += releaseDate[2];
            } else {
                query += "NULL";
            }
            query += ",";
            if (releaseDatePrecision.equals("year")) {
                query += "NULL";
            } else {
                query += releaseDate[1];
            }
            query += "," + releaseDate[0] + ",'" + album.getSpotifyId() + "'," +
                    album.getTracksNumber() + ",'"
                    + album.getAlbumType() + "')";
            if (index == albumsNumber) {
                query += ";";
            } else {
                query += ",\n";
            }
            index++;
        }

        try {
            jdbcTemplate.update(query);
        } catch (Exception e) {
            this.logger.error("MusicRepository - insertIntoTMusicAlbums(albumsToAdd) failed");
            throw e;
        }
    }

    private void insertIntoTMusicArtists(ArrayList<SpotifyArtist> artistsToAdd) {
        String query = "INSERT INTO t_music_artists (c_name, c_picture, c_spotify_id) VALUES\n";

        int artistsNumber = artistsToAdd.size();
        int index = 1;
        for (SpotifyArtist artist : artistsToAdd) {
            query += "('" + artist.getName().replaceAll("'", "''") + "',";
            if (artist.getPicture() == null) {
                query += "NULL";
            } else {
                query += "'" + artist.getPicture() + "'";
            }
            query += ",'" + artist.getSpotifyId() + "')";
            if (index == artistsNumber) {
                query += ";";
            } else {
                query += ",\n";
            }
            index++;
        }

        try {
            jdbcTemplate.update(query);
        } catch (Exception e) {
            this.logger.error("MusicRepository - insertIntoTMusicArtists(artistsToAdd) failed");
            throw e;
        }
    }

    private void insertIntoTMusicGenres(HashSet<String> genresToAdd) {
        String query = "INSERT INTO t_music_genres (c_spotify_id) VALUES\n";

        int genresNumber = genresToAdd.size();
        int index = 1;
        for (String genre : genresToAdd) {
            query += "('" + genre + "')";
            if (index == genresNumber) {
                query += ";";
            } else {
                query += ",\n";
            }
            index++;
        }

        try {
            jdbcTemplate.update(query);
        } catch (Exception e) {
            this.logger.error("MusicRepository - insertIntoTMusicGenres(genresToAdd) failed");
            throw e;
        }
    }

    private void insertIntoTMusicTracks(ArrayList<SpotifySimplifiedTrack> tracksToAdd) {
        String query = "INSERT INTO t_music_tracks (c_album_spotify_id, c_disc_number, c_duration, c_name, c_order, c_spotify_id) VALUES\n";

        int index = 1;
        int tracksNumber = tracksToAdd.size();
        for (SpotifySimplifiedTrack track : tracksToAdd) {
            query += "('" + track.getAlbumSpotifyId() + "'," + track.getDiscNumber() + "," + track.getDuration() + ",'"
                    + track.getName().replaceAll("'", "''") + "'," + track.getOrder() + ",'" + track.getSpotifyId()
                    + "')";
            if (index == tracksNumber) {
                query += ";";
            } else {
                query += ",\n";
            }
            index++;
        }

        try {
            jdbcTemplate.update(query);
        } catch (Exception e) {
            this.logger.error("MusicRepository - insertIntoTMusicTracks(tracksToAdd) failed");
            throw e;
        }
    }
}
