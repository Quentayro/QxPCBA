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

INSERT INTO
    t_music_albums (
        c_name,
        c_picture,
        c_release_day,
        c_release_month,
        c_release_year,
        c_spotify_id,
        c_tracks_number,
        c_type
    )
VALUES
    (
        'nameTest',
        'pictureTest',
        1,
        1,
        1970,
        'spotifyIdTest0',
        1,
        'typeTest'
    ),
    (
        'nameTest',
        'pictureTest',
        NULL,
        NULL,
        1970,
        'spotifyIdTest1',
        1,
        'typeTest'
    );