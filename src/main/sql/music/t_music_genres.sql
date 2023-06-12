CREATE TABLE t_music_genres (
    c_id MEDIUMINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    c_creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    c_display_text VARCHAR(127),
    c_spotify_id VARCHAR(127) NOT NULL UNIQUE
);