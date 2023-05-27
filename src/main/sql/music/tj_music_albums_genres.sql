CREATE TABLE tj_music_albums_genres(
    c_id MEDIUMINT UNSIGNED AUTO_INCREMENT NOT NULL PRIMARY KEY,
    c_album_spotify_id VARCHAR(31) NOT NULL,
    c_genres_spotify_id VARCHAR(127) NOT NULL,
    FOREIGN KEY (c_album_spotify_id) REFERENCES t_music_albums(c_spotify_id),
    FOREIGN KEY (c_genres_spotify_id) REFERENCES t_music_genres(c_spotify_id)
);