package org.qxpcba.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.qxpcba.utils.Constants;

@CrossOrigin(origins = Constants.frontBaseUrl)
@RequestMapping("/music")
@RestController
public class MusicController {
    Logger logger = LoggerFactory.getLogger(MusicController.class);

    @PostMapping("/postArtist")
    public String postArtist(@RequestBody String spotifyId) {
        logger.info("[POST] /postArtist"); // TODO : Log stuff
        return spotifyId;
    }

    // Mon controller appelle mon service (injecté grâce à ma config) (controller =
    // api &
    // service)
    // Mon service appelle mon DAO (abstrait le modèle) (repository = dao)
    // Mon modèle implémente mon DAO (model)
    // https://openclassrooms.com/fr/courses/6900101-creez-une-application-java-avec-spring-boot/7078013-configurez-et-structurez-votre-api-avec-des-packages
}
