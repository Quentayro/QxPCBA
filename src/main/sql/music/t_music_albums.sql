CREATE TABLE t_music_albums (
    c_id MEDIUMINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    c_creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    c_final_score_date TIMESTAMP,
    c_name VARCHAR(127) NOT NULL,
    c_picture VARCHAR(63) NOT NULL,
    c_release_day TINYINT UNSIGNED,
    c_release_month TINYINT UNSIGNED,
    c_release_year SMALLINT UNSIGNED NOT NULL,
    c_spotify_id VARCHAR(31) NOT NULL UNIQUE,
    c_tracks_number SMALLINT UNSIGNED NOT NULL,
    c_type VARCHAR(15) NOT NULL
);