package org.qxpcba.repository;

import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import org.qxpcba.model.SpotifyArtist;
import org.qxpcba.model.SpotifySimplifiedAlbum;
import org.qxpcba.model.SpotifySimplifiedTrack;

@Repository
public class MusicRepository {
    private JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(MusicRepository.class);

    public MusicRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void postArtist(ArrayList<SpotifySimplifiedAlbum> albumsToAdd, ArrayList<SpotifyArtist> artistsToAdd,
            ArrayList<SpotifySimplifiedTrack> tracksToAdd) {
        this.insertIntoTMusicArtists(artistsToAdd);
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
}
