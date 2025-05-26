package object;

import main.GamePanel;

import Entities.Entity;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends Entity {

    public OBJ_Heart(GamePanel gp) {

        super(gp);

        name = "Heart";
        image = setUp("/objects/heart/heart0");
        image2 = setUp("/objects/heart/heart1");
        image3 = setUp("/objects/heart/heart2");

    }


    @Override
    public BufferedImage setUp(String imageName) {
        Utility uTool = new Utility();
        BufferedImage image;
        BufferedImage scaledImage = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imageName + ".png")));
            scaledImage = uTool.scaleImage(image, gp.tileSize / 2, gp.tileSize / 2);
        } catch (Exception _) {

        }

        return scaledImage;
    }
}
