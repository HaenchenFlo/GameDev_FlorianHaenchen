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
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart/heart00.png")));
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart/heart01.png")));
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/heart/heart02.png")));
            uTool.scaleImage(image, gp.tileSize,gp.tileSize);

        } catch (IOException _) {

        }
    }
}
