CREATE TABLE t_music_tracks (
    c_id MEDIUMINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    c_creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    c_album_spotify_id VARCHAR(31) NOT NULL,
    c_disc_number TINYINT UNSIGNED NOT NULL,
    c_duration MEDIUMINT UNSIGNED NOT NULL,
    c_is_available BOOLEAN NOT NULL DEFAULT TRUE,
    c_is_remix BOOLEAN NOT NULL DEFAULT FALSE,
    c_name VARCHAR(255) NOT NULL,
    c_order SMALLINT UNSIGNED NOT NULL,
    c_rank CHAR(1),
    c_rank_date TIMESTAMP,
    c_spotify_id VARCHAR(31) NOT NULL UNIQUE,
    FOREIGN KEY (c_album_spotify_id) REFERENCES t_music_albums(c_spotify_id)
);