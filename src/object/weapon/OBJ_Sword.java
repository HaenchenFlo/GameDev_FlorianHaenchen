package object.weapon;

import Entities.Entity;
import main.GamePanel;

public class OBJ_Sword extends Entity {

    public OBJ_Sword(GamePanel gp) {
        super(gp);

        name = "Normal Sword";
        down1 = setUp("/objects/sword");
        attackValue = 1;
    }
}
