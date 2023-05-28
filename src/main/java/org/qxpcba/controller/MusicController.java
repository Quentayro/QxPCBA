package org.qxpcba.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/postArtist")
    public ResponseEntity<String> postArtist(@RequestBody String artistSpotifyId) {
        logger.info("[POST] /postArtist");
        try {
            return ResponseEntity.status(HttpStatus.OK).body(musicService.postArtist(artistSpotifyId));
        } catch (Exception e) {
            logger.error("MusicController - postArtist(" + artistSpotifyId + ") failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.toString());
        }
    }
}
