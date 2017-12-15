package com.sajroz.ai.restaurantwebapp.services;

import com.sajroz.ai.restaurantwebapp.dto.ImageData;
import com.sajroz.ai.restaurantwebapp.returnMessages.JSONMessageGenerator;
import com.sajroz.ai.restaurantwebapp.returnMessages.ResponseMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.UUID;

@Service
public class ImageService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private JSONMessageGenerator jsonMessageGenerator;

    public String convertImageToBase64(String imagePath) {
        byte[] imageBytes;
        try {
            Path fileLocation = Paths.get("./images/" + imagePath);
            imageBytes = Files.readAllBytes(fileLocation);
        } catch (IOException e) {
            logger.info("No file found, filePath={}", imagePath);
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.NO_FILE).toString();
        }

        imageBytes = Base64.getMimeEncoder().encode(imageBytes);
        return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.OK, "image", new String(imageBytes)).toString();
    }

    public String convertBase64ToImage(ImageData userImage) {
        byte[] decodedImage;
        try {
            decodedImage = Base64.getMimeDecoder().decode(userImage.getImage());
        }  catch (IllegalArgumentException e) {
            logger.info("No data to save, decode error, e={}", e.getMessage());
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.BASE64_ERROR).toString();
        }

        if (decodedImage != null) {
            String filename = storeImage(decodedImage);
            if(filename != null) {
                logger.info("Saving user image successful, filepath={}", filename);
                return jsonMessageGenerator.createResponseWithAdditionalInfo(ResponseMessages.IMAGE_SAVED, "filename", filename).toString();
            } else {
                logger.warn("Saving user image failed");
                return jsonMessageGenerator.createSimpleRespons(ResponseMessages.IMAGE_SAVING_ERROR).toString();
            }
        } else {
            logger.info("No data to save");
            return jsonMessageGenerator.createSimpleRespons(ResponseMessages.EMPTY_IMAGE_DATA).toString();
        }
    }

    private String storeImage(byte[] userImage) {
        String filename = UUID.randomUUID().toString() + ".jpg";
        File file = new File("./images/" + filename);
        while (file.isFile()) {
            filename = UUID.randomUUID().toString() + ".jpg";
            file = new File("./images/" + filename);
        }

        boolean isDirectoryCreated= file.getParentFile().exists();
        if (!isDirectoryCreated) {
            isDirectoryCreated= file.getParentFile().mkdirs();
        }

        if(isDirectoryCreated) {
            try {
                FileOutputStream out = new FileOutputStream(file);
                out.write(userImage);
                out.close();
                return (filename);

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
