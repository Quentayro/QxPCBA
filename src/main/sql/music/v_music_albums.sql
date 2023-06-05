CREATE VIEW vi_music_albums AS
SELECT
    c_id,
    (
        SELECT
            count(*)
        FROM
            t_music_tracks
        WHERE
            c_rank = 'A'
            AND c_album_spotify_id = tma.c_spotify_id
    ) AS c_a,
    (
        SELECT
            count(*)
        FROM
            t_music_tracks
        WHERE
            c_rank = 'B'
            AND c_album_spotify_id = tma.c_spotify_id
    ) AS c_b,
    (
        SELECT
            count(*)
        FROM
            t_music_tracks
        WHERE
            c_rank = 'C'
            AND c_album_spotify_id = tma.c_spotify_id
    ) AS c_c,
    (
        SELECT
            count(*)
        FROM
            t_music_tracks
        WHERE
            c_rank = 'D'
            AND c_album_spotify_id = tma.c_spotify_id
    ) AS c_d,
    (
        SELECT
            sum(c_duration)
        FROM
            t_music_tracks
        WHERE
            c_album_spotify_id = tma.c_spotify_id
    ) AS c_duration,
    (
        SELECT
            sum(tmt.c_duration)
        FROM
            t_music_tracks tmt
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tjmtt.c_track_spotify_id = tmt.c_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_album_spotify_id = tma.c_spotify_id
    ) AS c_effective_duration,
    (
        SELECT
            count(*)
        FROM
            t_music_tracks
        WHERE
            c_rank = 'S'
            AND c_album_spotify_id = tma.c_spotify_id
    ) AS c_s
FROM
    t_music_albums tma;

CREATE VIEW v_music_albums AS
SELECT
    tma.c_id,
    (
        (
            (
                vima.c_c + (vima.c_b * 2) + (vima.c_a * 3) + (vima.c_s * 4)
            ) /(tma.c_tracks_number * 4)
        ) * 100
    ) AS c_score
FROM
    t_music_albums tma
    INNER JOIN vi_music_albums vima ON tma.c_id = vima.c_id;