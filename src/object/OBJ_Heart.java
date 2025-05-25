package object;

import main.GamePanel;

import Entities.Entity;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class OBJ_Heart extends Entity {

    public OBJ_Heart(GamePanel gp) {

        super(gp);

        name = "Heart";
        image = setUp("/objects/heart/heart0.png");
        image2 = setUp("/objects/heart/heart1.png");
        image3 = setUp("/objects/heart/heart2.png");

    }
}
