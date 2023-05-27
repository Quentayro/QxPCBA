CREATE TABLE t_music_artists (
    c_id MEDIUMINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    c_creation_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    c_final_score_date TIMESTAMP,
    c_first_rank_date TIMESTAMP,
    c_name VARCHAR(127) NOT NULL,
    c_picture VARCHAR(63),
    c_spotify_id VARCHAR(31) NOT NULL UNIQUE
);

INSERT INTO
    t_music_artists (c_name, c_picture, c_spotify_id)
VALUES
    ('nameTest', 'pictureTest', 'spotifyIdTest0'),
    ('nameTest', NULL, 'spotifyIdTest1');