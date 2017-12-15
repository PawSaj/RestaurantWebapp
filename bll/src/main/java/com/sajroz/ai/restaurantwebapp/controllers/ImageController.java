package com.sajroz.ai.restaurantwebapp.controllers;

import com.sajroz.ai.restaurantwebapp.dto.ImageData;
import com.sajroz.ai.restaurantwebapp.services.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ImageController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    ImageService imageService;

    @RequestMapping(value = "/sendImage", method = RequestMethod.POST, produces = "application/json")
    public String downloadImage(@RequestBody ImageData userImage){
        logger.info("Received data->, image={}", userImage.getImage());
        return imageService.convertBase64ToImage(userImage);
    }

    @RequestMapping(value = "/getImage/{imageName:.+}", method = RequestMethod.GET, produces = "application/json")
    public String sendImage(@PathVariable String imageName) {
        logger.info("Loading image, imageName={}", imageName);
        return imageService.convertImageToBase64(imageName).replaceAll("\\\\r\\\\n", "");
    }
}
