package org.qxpcba.repository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import org.qxpcba.model.music.MusicArtist;
import org.qxpcba.model.music.MusicGenre;
import org.qxpcba.model.music.SpotifyArtist;
import org.qxpcba.model.music.SpotifySimplifiedAlbum;
import org.qxpcba.model.music.SpotifySimplifiedArtist;
import org.qxpcba.model.music.SpotifySimplifiedTrack;

@Repository
public class MusicRepository {
    private JdbcTemplate jdbcTemplate;
    private Logger logger = LoggerFactory.getLogger(MusicRepository.class);

    public MusicRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private void deleteSuggestedArtistSuggestions(String artistSpotifyId) {
        String query = "DELETE FROM\n"
                + "tj_music_artists_artists\n"
                + "WHERE\n"
                + "c_suggested_artist_spotify_id = '" + artistSpotifyId + "';";

        try {
            this.jdbcTemplate.update(query);
        } catch (Exception e) {
            this.logger.error("MusicRepository - deleteSuggestedArtistSuggestions(artistSpotifyId) failed");
            throw e;
        }
    }

    public HashSet<MusicArtist> getArtists() {
        try {
            HashSet<MusicArtist> artists = this.selectArtists();
            this.selectArtistsGenres(artists);
            return artists;
        } catch (Exception e) {
            this.logger.error("MusicRepository - getArtists() failed");
            throw e;
        }
    }

    public HashSet<MusicArtist> getSuggestedArtists() {
        try {
            HashSet<MusicArtist> artists = this.selectSuggestsedArtists();
            this.selectArtistsGenres(artists);
            return artists;
        } catch (Exception e) {
            this.logger.error("MusicRepository - getSuggestedArtists() failed");
            throw e;
        }
    }

    private void insertIntoTjMusicAlbumsArtists(HashSet<SpotifySimplifiedAlbum> albumsToAdd) {
        if (albumsToAdd.size() != 0) {
            String query = "INSERT INTO\n"
                    + "tj_music_albums_artists\n"
                    + "(c_album_spotify_id,\n"
                    + "c_artist_spotify_id)\n"
                    + "VALUES\n";
            boolean areValuesEmpty = true;

            for (SpotifySimplifiedAlbum album : albumsToAdd) {
                SpotifySimplifiedArtist[] artists = album.getArtists();
                String albumSpotifyId = album.getSpotifyId();

                for (SpotifySimplifiedArtist artist : artists) {
                    query += "('" + albumSpotifyId + "','" + artist.getSpotifyId() + "'),\n";

                    areValuesEmpty = false;
                }
            }

            query = query.substring(0, query.length() - 2) + ";";

            if (!areValuesEmpty) {
                try {
                    this.jdbcTemplate.update(query);
                } catch (Exception e) {
                    this.logger.error("MusicRepository - insertIntoTjMusicAlbumsArtists(albumsToAdd) failed");
                    throw e;
                }
            }
        }
    }

    private void insertIntoTjMusicAlbumsGenres(HashSet<SpotifySimplifiedAlbum> albumsToAdd) {
        if (albumsToAdd.size() != 0) {
            String query = "INSERT INTO\n"
                    + "tj_music_albums_genres\n"
                    + "(c_album_spotify_id,\n"
                    + "c_genres_spotify_id)\n"
                    + "VALUES\n";
            boolean areValuesEmpty = true;

            for (SpotifySimplifiedAlbum album : albumsToAdd) {
                String[] genres = album.getGenres();
                String albumSpotifyId = album.getSpotifyId();

                for (String genre : genres) {
                    query += "('" + albumSpotifyId + "','" + genre.replaceAll("'", "''") + "'),\n";

                    areValuesEmpty = false;
                }
            }

            query = query.substring(0, query.length() - 2) + ";";

            if (!areValuesEmpty) {
                try {
                    this.jdbcTemplate.update(query);
                } catch (Exception e) {
                    this.logger.error("MusicRepository - insertIntoTjMusicAlbumsGenres(albumsToAdd) failed");
                    throw e;
                }
            }
        }
    }

    private void insertIntoTjMusicArtistsArtists(String artistSpotifyId,
            HashSet<SpotifyArtist> suggestedArtistsToAdd) {
        String query = "INSERT INTO\n"
                + "tj_music_artists_artists\n"
                + "(c_artist_spotify_id,\n"
                + "c_suggested_artist_spotify_id)\n"
                + "VALUES\n";
        boolean areValuesEmpty = true;

        for (SpotifyArtist artist : suggestedArtistsToAdd) {
            String suggestedArtistSpotifyId = artist.getSpotifyId();
            if (!artistSpotifyId.equals(suggestedArtistSpotifyId)) {
                query += "('" + artistSpotifyId + "','" + suggestedArtistSpotifyId + "'),\n";

                areValuesEmpty = false;
            }
        }

        query = query.substring(0, query.length() - 2) + ";";

        if (!areValuesEmpty) {
            try {
                this.jdbcTemplate.update(query);
            } catch (Exception e) {
                this.logger
                        .error("MusicRepository - insertIntoTjMusicArtistsArtists(artistSpotifyId, suggestedArtistsToAdd) failed");
                throw e;
            }
        }
    }

    private void insertIntoTjMusicArtistsGenres(HashSet<SpotifyArtist> artistsToAdd) {
        if (artistsToAdd.size() != 0) {
            String query = "INSERT INTO\n"
                    + "tj_music_artists_genres\n"
                    + "(c_artist_spotify_id,\n"
                    + "c_genres_spotify_id)\n"
                    + "VALUES\n";
            boolean areValuesEmpty = true;

            for (SpotifyArtist artist : artistsToAdd) {
                String[] genres = artist.getGenres();
                String artistSpotifyid = artist.getSpotifyId();

                for (String genre : genres) {
                    query += "('" + artistSpotifyid + "','" + genre.replaceAll("'", "''") + "'),\n";

                    areValuesEmpty = false;
                }
            }

            query = query.substring(0, query.length() - 2) + ";";

            if (!areValuesEmpty) {
                try {
                    this.jdbcTemplate.update(query);
                } catch (Exception e) {
                    this.logger.error("MusicRepository - insertIntoTjMusicArtistsGenres(artistsToAdd) failed");
                    throw e;
                }
            }
        }
    }

    private void insertIntoTjMusicArtiststracks(HashSet<SpotifySimplifiedTrack> tracksToAdd) {
        if (tracksToAdd.size() != 0) {
            String query = "INSERT INTO\n"
                    + "tj_music_artists_tracks\n"
                    + "(c_artist_spotify_id,\n"
                    + "c_track_spotify_id)\n"
                    + "VALUES\n";
            boolean areValuesEmpty = true;

            for (SpotifySimplifiedTrack track : tracksToAdd) {
                for (SpotifySimplifiedArtist artist : track.getArtists()) {
                    query += "('" + artist.getSpotifyId() + "','" + track.getSpotifyId() + "'),\n";

                    areValuesEmpty = false;
                }
            }

            query = query.substring(0, query.length() - 2) + ";";

            if (!areValuesEmpty) {
                try {
                    this.jdbcTemplate.update(query);
                } catch (Exception e) {
                    this.logger.error("MusicRepository - insertIntoTjMusicArtiststracks(tracksToAdd) failed");
                    throw e;
                }
            }
        }
    }

    private void insertIntoTMusicAlbums(HashSet<SpotifySimplifiedAlbum> albumsToAdd) {
        if (albumsToAdd.size() != 0) {
            String query = "INSERT INTO\n"
                    + "t_music_albums\n"
                    + "(c_name,\n"
                    + "c_picture,\n"
                    + "c_release_day,\n"
                    + "c_release_month,\n"
                    + "c_release_year,\n"
                    + "c_spotify_id,\n"
                    + "c_tracks_number,\n"
                    + "c_type)\n"
                    + "VALUES\n";
            boolean areValuesEmpty = true;

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
                        + album.getAlbumType() + "'),\n";

                areValuesEmpty = false;
            }

            query = query.substring(0, query.length() - 2) + ";";

            if (!areValuesEmpty) {
                try {
                    this.jdbcTemplate.update(query);
                } catch (Exception e) {
                    this.logger.error("MusicRepository - insertIntoTMusicAlbums(albumsToAdd) failed");
                    throw e;
                }
            }
        }
    }

    private void insertIntoTMusicArtists(HashSet<SpotifyArtist> artistsToAdd) {
        if (artistsToAdd.size() != 0) {
            String query = "INSERT INTO\n"
                    + "t_music_artists\n"
                    + "(c_name,\n"
                    + "c_picture,\n"
                    + "c_spotify_id)\n"
                    + "VALUES\n";
            boolean areValuesEmpty = true;

            for (SpotifyArtist artist : artistsToAdd) {
                query += "('" + artist.getName().replaceAll("'", "''") + "',";
                if (artist.getPicture() == null) {
                    query += "NULL";
                } else {
                    query += "'" + artist.getPicture() + "'";
                }
                query += ",'" + artist.getSpotifyId() + "'),\n";

                areValuesEmpty = false;
            }

            query = query.substring(0, query.length() - 2) + ";";

            if (!areValuesEmpty) {
                try {
                    this.jdbcTemplate.update(query);
                } catch (Exception e) {
                    this.logger.error("MusicRepository - insertIntoTMusicArtists(artistsToAdd) failed");
                    throw e;
                }
            }
        }
    }

    private void insertIntoTMusicGenres(HashSet<String> genresToAdd) {
        if (genresToAdd.size() != 0) {
            String query = "INSERT INTO\n"
                    + "t_music_genres\n"
                    + "(c_spotify_id)\n"
                    + "VALUES\n";
            boolean areValuesEmpty = true;

            for (String genre : genresToAdd) {
                query += "('" + genre.replaceAll("'", "''") + "'),\n";

                areValuesEmpty = false;
            }

            query = query.substring(0, query.length() - 2) + ";";

            if (!areValuesEmpty) {
                try {
                    this.jdbcTemplate.update(query);
                } catch (Exception e) {
                    this.logger.error("MusicRepository - insertIntoTMusicGenres(genresToAdd) failed");
                    throw e;
                }
            }
        }
    }

    private void insertIntoTMusicTracks(HashSet<SpotifySimplifiedTrack> tracksToAdd) {
        if (tracksToAdd.size() != 0) {
            String query = "INSERT INTO\n"
                    + "t_music_tracks\n"
                    + "(c_album_spotify_id,\n"
                    + "c_disc_number,\n"
                    + "c_duration,\n"
                    + "c_name,\n"
                    + "c_order,\n"
                    + "c_spotify_id)\n"
                    + "VALUES\n";
            boolean areValuesEmpty = true;

            for (SpotifySimplifiedTrack track : tracksToAdd) {
                query += "('" + track.getAlbumSpotifyId() + "'," + track.getDiscNumber() + "," + track.getDuration()
                        + ",'"
                        + track.getName().replaceAll("'", "''") + "'," + track.getOrder() + ",'" + track.getSpotifyId()
                        + "'),\n";

                areValuesEmpty = false;
            }

            query = query.substring(0, query.length() - 2) + ";";

            if (!areValuesEmpty) {
                try {
                    this.jdbcTemplate.update(query);
                } catch (Exception e) {
                    this.logger.error("MusicRepository - insertIntoTMusicTracks(tracksToAdd) failed");
                    throw e;
                }
            }
        }
    }

    public void postArtist(HashSet<SpotifySimplifiedAlbum> albumsToAdd, String artistSpotifyId,
            HashSet<SpotifyArtist> artistsToAdd,
            HashSet<String> genresToAdd,
            HashSet<SpotifyArtist> suggestedArtistsToAdd,
            HashSet<SpotifySimplifiedTrack> tracksToAdd) {
        try {
            this.insertIntoTMusicArtists(artistsToAdd);
            this.insertIntoTMusicAlbums(albumsToAdd);
            this.insertIntoTMusicTracks(tracksToAdd);
            this.insertIntoTMusicGenres(genresToAdd);
            this.insertIntoTjMusicAlbumsArtists(albumsToAdd);
            this.insertIntoTjMusicAlbumsGenres(albumsToAdd);
            this.insertIntoTjMusicArtistsArtists(artistSpotifyId, suggestedArtistsToAdd);
            this.insertIntoTjMusicArtistsGenres(artistsToAdd);
            this.insertIntoTjMusicArtiststracks(tracksToAdd);
            this.deleteSuggestedArtistSuggestions(artistSpotifyId);
        } catch (Exception e) {
            this.logger.error("MusicRepository - postArtist(albumsToAdd, artistsToAdd, tracksToAdd) failed");
            throw e;
        }
    }

    public void postGenreDisplayText(MusicGenre genre) {
        String query = "UPDATE\n"
                + "t_music_genres\n"
                + "SET\n"
                + "c_display_text = '" + genre.getDisplayText().replaceAll("'", "''") + "'\n"
                + "WHERE\n"
                + "c_spotify_id = '" + genre.getSpotifyId().replaceAll("'", "''") + "';";

        try {
            this.jdbcTemplate.update(query);
        } catch (Exception e) {
            this.logger.error("MusicRepository - postGenreDisplayText(genre) failed");
            throw e;
        }
    }

    public HashSet<String> selectAddedArtistsSpotifyIds() {
        String query = "SELECT\n"
                + "artists.c_spotify_id\n"
                + "FROM\n"
                + "t_music_artists artists\n"
                + "LEFT JOIN\n"
                + "tj_music_artists_artists suggested_artists\n"
                + "ON\n"
                + "suggested_artists.c_suggested_artist_spotify_id = artists.c_spotify_id\n"
                + "WHERE\n"
                + "suggested_artists.c_suggested_artist_spotify_id IS NULL;";

        HashSet<String> addedArtistsSpotifyIds = new HashSet<String>();

        try {
            List<Map<String, Object>> addedArtistsSpotifyIdList = this.jdbcTemplate.queryForList(query);
            for (Map<String, Object> addedArtistSpotifyIdResult : addedArtistsSpotifyIdList) {
                addedArtistsSpotifyIds.add(addedArtistSpotifyIdResult.get("c_spotify_id").toString());
            }
        } catch (Exception e) {
            this.logger.error("MusicRepository - selectAddedArtistsSpotifyIds() failed");
            throw e;
        }

        return addedArtistsSpotifyIds;
    }

    private HashSet<MusicArtist> selectArtists() {
        String query = "SELECT\n"
                + "tma.c_id,\n"
                + "tma.c_first_rank_date,\n"
                + "tma.c_name,\n"
                + "vima.c_original_a,\n"
                + "vima.c_original_b,\n"
                + "vima.c_original_c,\n"
                + "vima.c_original_d,\n"
                + "vma.c_original_score,\n"
                + "vima.c_original_s,\n"
                + "vima.c_original_tracks_duration,\n"
                + "vima.c_original_tracks_number,\n"
                + "tma.c_picture,\n"
                + "vima.c_remix_a,\n"
                + "vima.c_remix_b,\n"
                + "vima.c_remix_c,\n"
                + "vima.c_remix_d,\n"
                + "vima.c_remix_s,\n"
                + "vima.c_remix_tracks_duration,\n"
                + "vima.c_remix_tracks_number,\n"
                + "tma.c_spotify_id,\n"
                + "vima.c_total_a,\n"
                + "vima.c_total_b,\n"
                + "vima.c_total_c,\n"
                + "vima.c_total_d,\n"
                + "vma.c_total_score,\n"
                + "vima.c_total_s,\n"
                + "vima.c_total_tracks_duration,\n"
                + "vima.c_total_tracks_number\n"
                + "FROM\n"
                + "t_music_artists tma\n"
                + "INNER JOIN\n"
                + "v_music_artists vma\n"
                + "ON\n"
                + "tma.c_id = vma.c_id\n"
                + "INNER JOIN\n"
                + "vi_music_artists vima\n"
                + "ON\n"
                + "tma.c_id = vima.c_id;";

        HashSet<MusicArtist> artists = new HashSet<MusicArtist>();

        try {
            List<Map<String, Object>> artistsList = this.jdbcTemplate.queryForList(query);
            for (Map<String, Object> artistResult : artistsList) {
                String firstRankDate = null;
                if (artistResult.get("c_first_rank_date") != null) {
                    firstRankDate = artistResult.get("c_first_rank_date").toString();
                }

                float originalScore = 0;
                if (artistResult.get("c_original_score") != null) {
                    originalScore = ((BigDecimal) artistResult.get("c_original_score")).floatValue();
                }

                int originalTracksDuration = 0;
                if (artistResult.get("c_original_tracks_duration") != null) {
                    originalTracksDuration = ((BigDecimal) artistResult.get("c_original_tracks_duration")).intValue();
                }

                String picture = null;
                if (artistResult.get("c_picture") != null) {
                    picture = artistResult.get("c_picture").toString();
                }

                int remixTracksDuration = 0;
                if (artistResult.get("c_remix_tracks_duration") != null) {
                    remixTracksDuration = ((BigDecimal) artistResult.get("c_remix_tracks_duration")).intValue();
                }

                float totalScore = 0;
                if (artistResult.get("c_total_score") != null) {
                    totalScore = ((BigDecimal) artistResult.get("c_total_score")).floatValue();
                }

                int totalTracksDuration = 0;
                if (artistResult.get("c_total_tracks_duration") != null) {
                    totalTracksDuration = ((BigDecimal) artistResult.get("c_total_tracks_duration")).intValue();
                }

                artists.add(new MusicArtist(firstRankDate,
                        (int) artistResult.get("c_id"),
                        artistResult.get("c_name").toString(),
                        ((Long) artistResult.get("c_original_a")).intValue(),
                        ((Long) artistResult.get("c_original_b")).intValue(),
                        ((Long) artistResult.get("c_original_c")).intValue(),
                        ((Long) artistResult.get("c_original_d")).intValue(),
                        originalScore,
                        ((Long) artistResult.get("c_original_s")).intValue(),
                        originalTracksDuration,
                        ((Long) artistResult.get("c_original_tracks_number")).intValue(),
                        picture,
                        ((Long) artistResult.get("c_remix_a")).intValue(),
                        ((Long) artistResult.get("c_remix_b")).intValue(),
                        ((Long) artistResult.get("c_remix_c")).intValue(),
                        ((Long) artistResult.get("c_remix_d")).intValue(),
                        ((Long) artistResult.get("c_remix_s")).intValue(),
                        remixTracksDuration,
                        ((Long) artistResult.get("c_remix_tracks_number")).intValue(),
                        artistResult.get("c_spotify_id").toString(),
                        ((Long) artistResult.get("c_total_a")).intValue(),
                        ((Long) artistResult.get("c_total_b")).intValue(),
                        ((Long) artistResult.get("c_total_c")).intValue(),
                        ((Long) artistResult.get("c_total_d")).intValue(),
                        totalScore,
                        ((Long) artistResult.get("c_total_s")).intValue(),
                        totalTracksDuration,
                        ((Long) artistResult.get("c_total_tracks_number")).intValue()));
            }

            return artists;
        } catch (Exception e) {
            this.logger.error("MusicRepository - selectArtists() failed");
            throw e;
        }
    }

    private void selectArtistsGenres(HashSet<MusicArtist> artists) {
        for (MusicArtist artist : artists) {
            String query = "SELECT\n"
                    + "c_genres_spotify_id\n"
                    + "FROM\n"
                    + "tj_music_artists_genres\n"
                    + "WHERE\n"
                    + "c_artist_spotify_id = '" + artist.getSpotifyId() + "';";

            HashSet<String> genres = new HashSet<String>();

            try {
                List<Map<String, Object>> artistsGenresList = this.jdbcTemplate.queryForList(query);
                for (Map<String, Object> artistGenreResult : artistsGenresList) {
                    genres.add(artistGenreResult.get("c_genres_spotify_id").toString());
                }
            } catch (Exception e) {
                this.logger.error("MusicRepository - selectArtistsGenres(artists) failed");
                throw e;
            }

            artist.setGenres(genres);
        }
    }

    public HashSet<MusicGenre> getGenres() {
        String query = "SELECT\n" +
                " c_display_text,\n" +
                " c_id,\n" +
                " c_spotify_id\n"
                + "FROM\n"
                + "t_music_genres tmg;";

        HashSet<MusicGenre> genres = new HashSet<MusicGenre>();

        try {
            List<Map<String, Object>> genresList = this.jdbcTemplate.queryForList(query);
            for (Map<String, Object> genreResult : genresList) {
                String displayText = null;
                if (genreResult.get("c_display_text") != null) {
                    displayText = genreResult.get("c_display_text").toString();
                }

                genres.add(new MusicGenre(displayText,
                        (int) genreResult.get("c_id"),
                        genreResult.get("c_spotify_id").toString()));
            }
        } catch (Exception e) {
            this.logger.error("MusicRepository - getGenres() failed");
            throw e;
        }

        return genres;
    }

    public HashSet<String> selectSpotifyIdsFromTMusicAlbums() {
        String query = "SELECT\n"
                + "c_spotify_id\n"
                + "FROM\n"
                + "t_music_albums;";

        HashSet<String> albumsSpotifyIds = new HashSet<String>();

        try {
            List<Map<String, Object>> albumsSpotifyIdList = this.jdbcTemplate.queryForList(query);
            for (Map<String, Object> albumSpotifyIdResult : albumsSpotifyIdList) {
                albumsSpotifyIds.add(albumSpotifyIdResult.get("c_spotify_id").toString());
            }
        } catch (Exception e) {
            this.logger.error("MusicRepository - selectSpotifyIdsFromTMusicAlbums() failed");
            throw e;
        }

        return albumsSpotifyIds;
    }

    public HashSet<String> selectSpotifyIdsFromTMusicArtists() {
        String query = "SELECT\n"
                + "c_spotify_id\n"
                + "FROM\n"
                + "t_music_artists;";

        HashSet<String> artistsSpotifyIds = new HashSet<String>();

        try {
            List<Map<String, Object>> artistsSpotifyIdList = this.jdbcTemplate.queryForList(query);
            for (Map<String, Object> artistSpotifyIdResult : artistsSpotifyIdList) {
                artistsSpotifyIds.add(artistSpotifyIdResult.get("c_spotify_id").toString());
            }
        } catch (Exception e) {
            this.logger.error("MusicRepository - selectSpotifyIdsFromTMusicArtists() failed");
            throw e;
        }

        return artistsSpotifyIds;
    }

    public HashSet<String> selectSpotifyIdsFromTMusicGenres() {
        String query = "SELECT\n"
                + "c_spotify_id\n"
                + "FROM\n"
                + "t_music_genres;";

        HashSet<String> genresSpotifyIds = new HashSet<String>();

        try {
            List<Map<String, Object>> genresSpotifyIdList = this.jdbcTemplate.queryForList(query);
            for (Map<String, Object> genreSpotifyIdResult : genresSpotifyIdList) {
                genresSpotifyIds.add(genreSpotifyIdResult.get("c_spotify_id").toString());
            }
        } catch (Exception e) {
            this.logger.error("MusicRepository - selectSpotifyIdsFromTMusicGenres() failed");
            throw e;
        }

        return genresSpotifyIds;
    }

    public HashSet<String> selectSpotifyIdsFromTMusicTracks() {
        String query = "SELECT\n"
                + "c_spotify_id\n"
                + "FROM\n"
                + "t_music_tracks;";

        HashSet<String> tracksSpotifyIds = new HashSet<String>();

        try {
            List<Map<String, Object>> tracksSpotifyIdList = this.jdbcTemplate.queryForList(query);
            for (Map<String, Object> trackSpotifyIdResult : tracksSpotifyIdList) {
                tracksSpotifyIds.add(trackSpotifyIdResult.get("c_spotify_id").toString());
            }
        } catch (Exception e) {
            this.logger.error("MusicRepository - selectSpotifyIdsFromTMusicTracks() failed");
            throw e;
        }

        return tracksSpotifyIds;
    }

    private HashSet<MusicArtist> selectSuggestsedArtists() {
        String query = "SELECT\n"
                + "tma.c_id,\n"
                + "tma.c_first_rank_date,\n"
                + "tma.c_name,\n"
                + "vmsa.c_original_a,\n"
                + "vmsa.c_original_b,\n"
                + "vmsa.c_original_c,\n"
                + "vmsa.c_original_d,\n"
                + "vmsa.c_original_score,\n"
                + "vmsa.c_original_s,\n"
                + "vmsa.c_original_tracks_number,\n"
                + "tma.c_picture,\n"
                + "vmsa.c_remix_a,\n"
                + "vmsa.c_remix_b,\n"
                + "vmsa.c_remix_c,\n"
                + "vmsa.c_remix_d,\n"
                + "vmsa.c_remix_s,\n"
                + "vmsa.c_remix_tracks_number,\n"
                + "tma.c_spotify_id,\n"
                + "vmsa.c_total_a,\n"
                + "vmsa.c_total_b,\n"
                + "vmsa.c_total_c,\n"
                + "vmsa.c_total_d,\n"
                + "vmsa.c_total_score,\n"
                + "vmsa.c_total_s,\n"
                + "vmsa.c_total_tracks_number\n"
                + "FROM\n"
                + "t_music_artists tma\n"
                + "INNER JOIN\n"
                + "v_music_suggested_artists vmsa\n"
                + "ON\n"
                + "tma.c_id = vmsa.c_id;";

        HashSet<MusicArtist> artists = new HashSet<MusicArtist>();

        try {
            List<Map<String, Object>> artistsList = this.jdbcTemplate.queryForList(query);
            for (Map<String, Object> artistResult : artistsList) {
                String firstRankDate = null;
                if (artistResult.get("c_first_rank_date") != null) {
                    firstRankDate = artistResult.get("c_first_rank_date").toString();
                }

                int originalTracksDuration = 0;
                if (artistResult.get("c_original_tracks_duration") != null) {
                    originalTracksDuration = ((BigDecimal) artistResult.get("c_original_tracks_duration")).intValue();
                }

                String picture = null;
                if (artistResult.get("c_picture") != null) {
                    picture = artistResult.get("c_picture").toString();
                }

                int remixTracksDuration = 0;
                if (artistResult.get("c_remix_tracks_duration") != null) {
                    remixTracksDuration = ((BigDecimal) artistResult.get("c_remix_tracks_duration")).intValue();
                }

                int totalTracksDuration = 0;
                if (artistResult.get("c_total_tracks_duration") != null) {
                    totalTracksDuration = ((BigDecimal) artistResult.get("c_total_tracks_duration")).intValue();
                }

                artists.add(new MusicArtist(firstRankDate,
                        (int) artistResult.get("c_id"),
                        artistResult.get("c_name").toString(),
                        ((BigDecimal) artistResult.get("c_original_a")).intValue(),
                        ((BigDecimal) artistResult.get("c_original_b")).intValue(),
                        ((BigDecimal) artistResult.get("c_original_c")).intValue(),
                        ((BigDecimal) artistResult.get("c_original_d")).intValue(),
                        ((BigDecimal) artistResult.get("c_original_score")).floatValue(),
                        ((BigDecimal) artistResult.get("c_original_s")).intValue(),
                        originalTracksDuration,
                        ((BigDecimal) artistResult.get("c_original_tracks_number")).intValue(),
                        picture,
                        ((BigDecimal) artistResult.get("c_remix_a")).intValue(),
                        ((BigDecimal) artistResult.get("c_remix_b")).intValue(),
                        ((BigDecimal) artistResult.get("c_remix_c")).intValue(),
                        ((BigDecimal) artistResult.get("c_remix_d")).intValue(),
                        ((BigDecimal) artistResult.get("c_remix_s")).intValue(),
                        remixTracksDuration,
                        ((BigDecimal) artistResult.get("c_remix_tracks_number")).intValue(),
                        artistResult.get("c_spotify_id").toString(),
                        ((BigDecimal) artistResult.get("c_total_a")).intValue(),
                        ((BigDecimal) artistResult.get("c_total_b")).intValue(),
                        ((BigDecimal) artistResult.get("c_total_c")).intValue(),
                        ((BigDecimal) artistResult.get("c_total_d")).intValue(),
                        ((BigDecimal) artistResult.get("c_total_score")).floatValue(),
                        ((BigDecimal) artistResult.get("c_total_s")).intValue(),
                        totalTracksDuration,
                        ((BigDecimal) artistResult.get("c_total_tracks_number")).intValue()));
            }

            return artists;
        } catch (Exception e) {
            this.logger.error("MusicRepository - selectSuggestsedArtists() failed");
            throw e;
        }
    }
}
