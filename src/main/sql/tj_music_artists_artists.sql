CREATE TABLE tj_music_artists_artists(
    c_id MEDIUMINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    c_artist_spotify_id VARCHAR(31) NOT NULL,
    c_suggested_artist_spotify_id VARCHAR(31) NOT NULL,
    FOREIGN KEY (c_artist_spotify_id) REFERENCES t_music_artists(c_spotify_id),
    FOREIGN KEY (c_suggested_artist_spotify_id) REFERENCES t_music_artists(c_spotify_id)
);