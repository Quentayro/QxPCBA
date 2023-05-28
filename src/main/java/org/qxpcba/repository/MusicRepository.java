package org.qxpcba.repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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

    private void insertIntoTjMusicAlbumsArtists(ArrayList<SpotifySimplifiedAlbum> albumsToAdd) {
        if (albumsToAdd.size() != 0) {
            String query = "INSERT INTO tj_music_albums_artists (c_album_spotify_id, c_artist_spotify_id) VALUES\n";

            for (SpotifySimplifiedAlbum album : albumsToAdd) {
                SpotifySimplifiedArtist[] artists = album.getArtists();
                String albumSpotifyId = album.getSpotifyId();

                for (SpotifySimplifiedArtist artist : artists) {
                    query += "('" + albumSpotifyId + "','" + artist.getSpotifyId() + "'),\n";
                }
            }

            query = query.substring(0, query.length() - 2) + ";";

            try {
                this.jdbcTemplate.update(query);
            } catch (Exception e) {
                this.logger.error("MusicRepository - insertIntoTjMusicAlbumsArtists(albumsToAdd) failed");
                throw e;
            }
        }

    }

    private void insertIntoTjMusicAlbumsGenres(ArrayList<SpotifySimplifiedAlbum> albumsToAdd) {
        if (albumsToAdd.size() != 0) {
            String query = "INSERT INTO tj_music_albums_genres (c_album_spotify_id, c_genres_spotify_id) VALUES\n";
            boolean areValuesEmpty = true;

            for (SpotifySimplifiedAlbum album : albumsToAdd) {
                String[] genres = album.getGenres();
                String albumSpotifyId = album.getSpotifyId();

                for (String genre : genres) {
                    query += "('" + albumSpotifyId + "','" + genre + "'),\n";

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
            ArrayList<SpotifyArtist> suggestedArtistsToAdd) {
        String query = "INSERT INTO tj_music_artists_artists (c_artist_spotify_id, c_suggested_artist_spotify_id) VALUES\n";

        for (SpotifyArtist artist : suggestedArtistsToAdd) {
            String suggestedArtistSpotifyId = artist.getSpotifyId();
            if (!artistSpotifyId.equals(suggestedArtistSpotifyId)) {
                query += "('" + artistSpotifyId + "','" + suggestedArtistSpotifyId + "'),\n";
            }
        }

        query = query.substring(0, query.length() - 2) + ";";

        try {
            this.jdbcTemplate.update(query);
        } catch (Exception e) {
            this.logger
                    .error("MusicRepository - insertIntoTjMusicArtistsArtists(artistSpotifyId, suggestedArtistsToAdd) failed");
            throw e;
        }
    }

    private void insertIntoTjMusicArtistsGenres(ArrayList<SpotifyArtist> artistsToAdd) {
        if (artistsToAdd.size() != 0) {
            String query = "INSERT INTO tj_music_artists_genres (c_artist_spotify_id, c_genres_spotify_id) VALUES\n";

            for (SpotifyArtist artist : artistsToAdd) {
                String[] genres = artist.getGenres();
                String artistSpotifyid = artist.getSpotifyId();

                for (String genre : genres) {
                    query += "('" + artistSpotifyid + "','" + genre + "'),\n";
                }
            }

            query = query.substring(0, query.length() - 2) + ";";

            try {
                this.jdbcTemplate.update(query);
            } catch (Exception e) {
                this.logger.error("MusicRepository - insertIntoTjMusicArtistsGenres(artistsToAdd) failed");
                throw e;
            }
        }
    }

    private void insertIntoTjMusicArtiststracks(ArrayList<SpotifySimplifiedTrack> tracksToAdd) {
        if (tracksToAdd.size() != 0) {
            String query = "INSERT INTO tj_music_artists_tracks (c_artist_spotify_id, c_track_spotify_id) VALUES\n";

            for (SpotifySimplifiedTrack track : tracksToAdd) {
                for (SpotifySimplifiedArtist artist : track.getArtists()) {
                    query += "('" + artist.getSpotifyId() + "','" + track.getSpotifyId() + "'),\n";
                }
            }

            query = query.substring(0, query.length() - 2) + ";";

            try {
                this.jdbcTemplate.update(query);
            } catch (Exception e) {
                this.logger.error("MusicRepository - insertIntoTjMusicArtiststracks(tracksToAdd) failed");
                throw e;
            }
        }
    }

    private void insertIntoTMusicAlbums(ArrayList<SpotifySimplifiedAlbum> albumsToAdd) {
        if (albumsToAdd.size() != 0) {
            String query = "INSERT INTO t_music_albums (c_name, c_picture, c_release_day, c_release_month, c_release_year, c_spotify_id, c_tracks_number, c_type) VALUES\n";

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
            }

            query = query.substring(0, query.length() - 2) + ";";

            try {
                this.jdbcTemplate.update(query);
            } catch (Exception e) {
                this.logger.error("MusicRepository - insertIntoTMusicAlbums(albumsToAdd) failed");
                throw e;
            }
        }
    }

    private void insertIntoTMusicArtists(ArrayList<SpotifyArtist> artistsToAdd) {
        if (artistsToAdd.size() != 0) {
            String query = "INSERT INTO t_music_artists (c_name, c_picture, c_spotify_id) VALUES\n";

            for (SpotifyArtist artist : artistsToAdd) {
                query += "('" + artist.getName().replaceAll("'", "''") + "',";
                if (artist.getPicture() == null) {
                    query += "NULL";
                } else {
                    query += "'" + artist.getPicture() + "'";
                }
                query += ",'" + artist.getSpotifyId() + "'),\n";
            }

            query = query.substring(0, query.length() - 2) + ";";

            try {
                this.jdbcTemplate.update(query);
            } catch (Exception e) {
                this.logger.error("MusicRepository - insertIntoTMusicArtists(artistsToAdd) failed");
                throw e;
            }
        }
    }

    private void insertIntoTMusicGenres(HashSet<String> genresToAdd) {
        if (genresToAdd.size() != 0) {
            String query = "INSERT INTO t_music_genres (c_spotify_id) VALUES\n";

            for (String genre : genresToAdd) {
                query += "('" + genre + "'),\n";
            }

            query = query.substring(0, query.length() - 2) + ";";

            try {
                this.jdbcTemplate.update(query);
            } catch (Exception e) {
                this.logger.error("MusicRepository - insertIntoTMusicGenres(genresToAdd) failed");
                throw e;
            }
        }
    }

    private void insertIntoTMusicTracks(ArrayList<SpotifySimplifiedTrack> tracksToAdd) {
        if (tracksToAdd.size() != 0) {
            String query = "INSERT INTO t_music_tracks (c_album_spotify_id, c_disc_number, c_duration, c_name, c_order, c_spotify_id) VALUES\n";

            for (SpotifySimplifiedTrack track : tracksToAdd) {
                query += "('" + track.getAlbumSpotifyId() + "'," + track.getDiscNumber() + "," + track.getDuration()
                        + ",'"
                        + track.getName().replaceAll("'", "''") + "'," + track.getOrder() + ",'" + track.getSpotifyId()
                        + "'),\n";
            }

            query = query.substring(0, query.length() - 2) + ";";

            try {
                this.jdbcTemplate.update(query);
            } catch (Exception e) {
                this.logger.error("MusicRepository - insertIntoTMusicTracks(tracksToAdd) failed");
                throw e;
            }
        }
    }

    public void postArtist(ArrayList<SpotifySimplifiedAlbum> albumsToAdd, String artistSpotifyId,
            ArrayList<SpotifyArtist> artistsToAdd,
            HashSet<String> genresToAdd,
            ArrayList<SpotifyArtist> suggestedArtistsToAdd,
            ArrayList<SpotifySimplifiedTrack> tracksToAdd) {
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
            System.out.println("postArtist repository logic done");
        } catch (Exception e) {
            System.out.println(e);
            this.logger.error("MusicRepository - postArtist(albumsToAdd, artistsToAdd, tracksToAdd) failed");
            throw e;
        }
    }

    public HashSet<String> selectAddedArtistsSpotifyIds() {
        String query = "SELECT artists.c_spotify_id FROM t_music_artists artists LEFT JOIN tj_music_artists_artists suggestedArtists ON suggestedartists.c_suggested_artist_spotify_id = artists.c_spotify_id WHERE suggestedartists.c_suggested_artist_spotify_id IS NULL;";

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

    public HashSet<String> selectSpotifyIdsFromTMusicAlbums() {
        String query = "SELECT c_spotify_id FROM t_music_albums;";

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
        String query = "SELECT c_spotify_id FROM t_music_artists;";

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
        String query = "SELECT c_spotify_id FROM t_music_genres;";

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
        String query = "SELECT c_spotify_id FROM t_music_tracks;";

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
}
