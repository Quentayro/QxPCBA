CREATE VIEW vi_music_artists AS
SELECT
    tma.c_id,
    (
        SELECT
            count(*)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND NOT c_is_remix
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
            AND c_rank = 'A'
    ) AS c_original_a,
    (
        SELECT
            count(*)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND NOT c_is_remix
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
            AND c_rank = 'B'
    ) AS c_original_b,
    (
        SELECT
            count(*)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND NOT c_is_remix
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
            AND c_rank = 'C'
    ) AS c_original_c,
    (
        SELECT
            count(*)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND NOT c_is_remix
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
            AND c_rank = 'D'
    ) AS c_original_d,
    (
        SELECT
            count(*)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND NOT c_is_remix
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
            AND c_rank = 'S'
    ) AS c_original_s,
    (
        SELECT
            sum(tmt.c_duration)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND NOT c_is_remix
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
    ) AS c_original_tracks_duration,
    (
        SELECT
            count(*)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND NOT c_is_remix
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
    ) AS c_original_tracks_number,
    (
        SELECT
            count(*)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND c_is_remix
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
            AND c_rank = 'A'
    ) AS c_remix_a,
    (
        SELECT
            count(*)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND c_is_remix
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
            AND c_rank = 'B'
    ) AS c_remix_b,
    (
        SELECT
            count(*)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND c_is_remix
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
            AND c_rank = 'C'
    ) AS c_remix_c,
    (
        SELECT
            count(*)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND c_is_remix
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
            AND c_rank = 'D'
    ) AS c_remix_d,
    (
        SELECT
            count(*)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND c_is_remix
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
            AND c_rank = 'S'
    ) AS c_remix_s,
    (
        SELECT
            sum(tmt.c_duration)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND c_is_remix
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
    ) AS c_remix_tracks_duration,
    (
        SELECT
            count(*)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND c_is_remix
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
    ) AS c_remix_tracks_number,
    (
        SELECT
            count(*)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
            AND c_rank = 'A'
    ) AS c_total_a,
    (
        SELECT
            count(*)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
            AND c_rank = 'B'
    ) AS c_total_b,
    (
        SELECT
            count(*)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
            AND c_rank = 'C'
    ) AS c_total_c,
    (
        SELECT
            count(*)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
            AND c_rank = 'D'
    ) AS c_total_d,
    (
        SELECT
            count(*)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
            AND c_rank = 'S'
    ) AS c_total_s,
    (
        SELECT
            sum(tmt.c_duration)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
    ) AS c_total_tracks_duration,
    (
        SELECT
            count(*)
        FROM
            tj_music_artists_tracks tjmat
            INNER JOIN t_music_tracks tmt ON tjmat.c_track_spotify_id = tmt.c_spotify_id
            LEFT JOIN tj_music_tracks_tracks tjmtt ON tmt.c_spotify_id = tjmtt.c_track_spotify_id
        WHERE
            tjmtt.c_track_spotify_id IS NULL
            AND c_is_available
            AND tjmat.c_artist_spotify_id = tma.c_spotify_id
    ) AS c_total_tracks_number
FROM
    t_music_artists tma
    LEFT JOIN tj_music_artists_artists tjmaa ON tma.c_spotify_id = tjmaa.c_suggested_artist_spotify_id
WHERE
    tjmaa.c_suggested_artist_spotify_id IS NULL;

CREATE VIEW v_music_artists AS
SELECT
    c_id,
    (
        (
            (
                c_original_c + (c_original_b * 2) + (c_original_a * 3) + (c_original_s * 4)
            ) /(c_original_tracks_number * 4)
        ) * 100
    ) AS c_original_score,
    (
        (
            (
                c_total_c + (c_total_b * 2) + (c_total_a * 3) + (c_total_s * 4)
            ) /(c_total_tracks_number * 4)
        ) * 100
    ) AS c_total_score
FROM
    vi_music_artists;