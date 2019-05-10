package com.etudes.combat.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ResourceLoader {

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(ResourceLoader.class.getClassLoader().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
