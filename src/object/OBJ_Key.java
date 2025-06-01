package object;

import Entities.Entity;
import main.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;


public class OBJ_Key extends Entity {

    public OBJ_Key(GamePanel gp) {

        super(gp);

        name = "Key";
        icon = setUp("/objects/key");

        description = "[" + name + "]\nÖffnet Türen.";
    }
}
