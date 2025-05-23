package object;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends SuperObject {
    GamePanel gp;

    public OBJ_Heart(GamePanel gp) {

        this.gp = gp;

        name = "Heart";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart/heart0.png")));
            image2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart/heart1.png")));
            image3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart/heart2.png")));
            image = uTool.scaleImage(image, gp.tileSize / 2,gp.tileSize / 2);
            image2 = uTool.scaleImage(image2, gp.tileSize / 2,gp.tileSize / 2);
            image3 = uTool.scaleImage(image3, gp.tileSize / 2,gp.tileSize / 2);

        } catch (IOException _) {

        }
    }
}
