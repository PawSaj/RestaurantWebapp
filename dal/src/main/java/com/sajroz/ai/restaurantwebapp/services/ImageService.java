package com.sajroz.ai.restaurantwebapp.services;

import com.sajroz.ai.restaurantwebapp.dto.ImageData;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public String convertImageToBase64(String imagePath) {
        JSONObject returnMessage = new JSONObject();
        byte[] imageBytes;
        try {
            Path fileLocation = Paths.get("./images/" + imagePath);
            imageBytes = Files.readAllBytes(fileLocation);
        } catch (IOException e) {
            logger.info("No file found, filePath={}", imagePath);
            returnMessage.put("status", -1);
            returnMessage.put("message", "No file found");
            return returnMessage.toString();
        }

        imageBytes = Base64.getMimeEncoder().encode(imageBytes);
        returnMessage.put("status", 0);
        returnMessage.put("image", new String(imageBytes));

        return returnMessage.toString();
    }

    public String convertBase64ToImage(ImageData userImage) {
        JSONObject returnMessage = new JSONObject();

        byte[] decodedImage;
        try {
            decodedImage = Base64.getMimeDecoder().decode(userImage.getImage());
        }  catch (IllegalArgumentException e) {
            logger.info("No data to save, decode error, e={}", e.getMessage());
            returnMessage.put("status", -3);
            returnMessage.put("message", "Wrong encode");
            returnMessage.put("filepath", "");
            return returnMessage.toString();
        }
        if (decodedImage != null) {
            String filepath = storeImage(decodedImage);
            if(filepath != null) {
                logger.info("Saving user image successful, filepath={}", filepath);
                returnMessage.put("status", 0);
                returnMessage.put("message", "Image saved successfully");
                returnMessage.put("filepath", filepath);
                return returnMessage.toString();
            } else {
                logger.warn("Saving user image failed");
                returnMessage.put("status", -1);
                returnMessage.put("message", "Image saving failure");
                returnMessage.put("filepath", "");
                return returnMessage.toString();
            }
        } else {
            logger.info("No data to save");
            returnMessage.put("status", -2);
            returnMessage.put("message", "No data to save or data is corrupted");
            returnMessage.put("filepath", "");
            return returnMessage.toString();
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
