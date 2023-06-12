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
    sum(c_original_a) AS c_original_a,
    sum(c_original_b) AS c_original_b,
    sum(c_original_c) AS c_original_c,
    sum(c_original_d) AS c_original_d,
    sum(c_original_s) AS c_original_s,
    (
        (
            (
                sum(c_original_c) + (sum(c_original_b) * 2) + (sum(c_original_a) * 3) + (sum(c_original_s) * 4)
            ) /(sum(c_original_tracks_number) * 4)
        ) * 100
    ) AS c_original_score,
    sum(c_original_tracks_number) AS c_original_tracks_number,
    sum(c_remix_a) AS c_remix_a,
    sum(c_remix_b) AS c_remix_b,
    sum(c_remix_c) AS c_remix_c,
    sum(c_remix_d) AS c_remix_d,
    sum(c_remix_s) AS c_remix_s,
    sum(c_remix_tracks_number) AS c_remix_tracks_number,
    sum(c_total_a) AS c_total_a,
    sum(c_total_b) AS c_total_b,
    sum(c_total_c) AS c_total_c,
    sum(c_total_d) AS c_total_d,
    sum(c_total_s) AS c_total_s,
    (
        (
            (
                sum(c_total_c) + (sum(c_total_b) * 2) + (sum(c_total_a) * 3) + (sum(c_total_s) * 4)
            ) /(sum(c_total_tracks_number) * 4)
        ) * 100
    ) AS c_total_score,
    sum(c_total_tracks_number) AS c_total_tracks_number
FROM
    vi_music_suggested_artists
GROUP BY
    c_id;