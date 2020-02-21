package com.example.demo.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Component
public class ImageUtil {
    @Value("${image.origin.path}")
    private String originPath;

    @Value("${image.imageToUse.path}")
    private String imageToUsePath;

    @Value("${image.imageToUse.width}")
    private double imageToUseWidth;

    @Value("${image.imageToUse.height}")
    private double imageToUseHeight;

    /**
     * save user uploaded origin image ,
     * and generate a cutted and compressed image and save it.
     * @param originImage
     *        user uploaded image
     * @return image name ,named by ObjectId, suffix is jpg.
     */
    public String saveImage(BufferedImage originImage) throws IOException {
        double heightRatio = imageToUseHeight / originImage.getHeight();
        double widthRatio = imageToUseWidth / originImage.getWidth();
        System.out.println(heightRatio);
        double ratioDatum = heightRatio > widthRatio ? heightRatio:widthRatio;


        String imageId = new ObjectId().toString();

        Thumbnails.of(originImage)
                .scale(1f)
                .toFile(originPath+imageId+".jpg");

        Thumbnails.of(originImage)
                .scale(ratioDatum)
                .sourceRegion(Positions.CENTER , (int)(imageToUseHeight/ratioDatum) , (int)(imageToUseWidth/ratioDatum))
                .toFile(imageToUsePath+imageId+".jpg");

        return imageId+".jpg";
    }


}
