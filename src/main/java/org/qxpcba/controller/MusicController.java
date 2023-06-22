package org.qxpcba.controller;

import java.util.HashSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.qxpcba.model.music.MusicGenre;
import org.qxpcba.model.music.MusicGetArtistResponse;
import org.qxpcba.model.music.MusicGetArtistsResponse;
import org.qxpcba.service.MusicService;
import org.qxpcba.utils.Constants;

@CrossOrigin(origins = Constants.frontBaseUrl)
@RequestMapping("/music")
@RestController
public class MusicController {
    private Logger logger = LoggerFactory.getLogger(MusicController.class);
    private MusicService musicService;

    public MusicController(MusicService musicService) {
        this.musicService = musicService;
    }

    @GetMapping("/getArtist")
    public ResponseEntity<MusicGetArtistResponse> getArtist(@RequestParam String spotifyId) {
        logger.info("[GET] /music/getArtist");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.musicService.getArtist(spotifyId));
        } catch (Exception e) {
            logger.error("MusicController - getArtists() failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/getArtists")
    public ResponseEntity<MusicGetArtistsResponse> getArtists() {
        logger.info("[GET] /music/getArtists");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.musicService.getArtists());
        } catch (Exception e) {
            logger.error("MusicController - getArtists() failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/postArtist")
    public ResponseEntity<MusicGetArtistsResponse> postArtist(@RequestBody String artistSpotifyId) {
        logger.info("[POST] /music/postArtist");
        try {
            this.musicService.postArtist(artistSpotifyId);
            return ResponseEntity.status(HttpStatus.OK).body(this.musicService.getArtists());
        } catch (Exception e) {
            logger.error("MusicController - postArtist(artistSpotifyId) failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/postGenreDisplayText")
    public ResponseEntity<HashSet<MusicGenre>> postMusicGenreDisplayText(@RequestBody MusicGenre genre) {
        logger.info("[POST] /music/postArtist");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(this.musicService.postGenreDisplayText(genre));
        } catch (Exception e) {
            logger.error("MusicController - postArtist(genre) failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
