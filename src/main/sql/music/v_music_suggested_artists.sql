CREATE VIEW vi_music_suggested_artists AS
SELECT
    tma.c_id,
    vima.c_original_a,
    vima.c_original_b,
    vima.c_original_c,
    vima.c_original_d,
    vima.c_original_s,
    vima.c_original_tracks_number,
    vima.c_remix_a,
    vima.c_remix_b,
    vima.c_remix_c,
    vima.c_remix_d,
    vima.c_remix_s,
    vima.c_remix_tracks_number,
    vima.c_total_a,
    vima.c_total_b,
    vima.c_total_c,
    vima.c_total_d,
    vima.c_total_s,
    vima.c_total_tracks_number
FROM
    t_music_artists tma
    LEFT JOIN tj_music_artists_artists tjmaa ON tma.c_spotify_id = tjmaa.c_suggested_artist_spotify_id
    INNER JOIN vi_music_artists vima ON vima.c_spotify_id = tjmaa.c_artist_spotify_id
WHERE
    tjmaa.c_suggested_artist_spotify_id IS NOT NULL;

CREATE VIEW v_music_suggested_artists AS
SELECT
    c_id,
    (
        (
            (
                sum(c_original_c) + (sum(c_original_b) * 2) + (sum(c_original_a) * 3) + (sum(c_original_s) * 4)
            ) /(sum(c_original_tracks_number) * 4)
        ) * 100
    ) AS c_original_score,
    (
        (
            (
                sum(c_total_c) + (sum(c_total_b) * 2) + (sum(c_total_a) * 3) + (sum(c_total_s) * 4)
            ) /(sum(c_total_tracks_number) * 4)
        ) * 100
    ) AS c_total_score
FROM
    vi_music_suggested_artists
GROUP BY
    c_id;