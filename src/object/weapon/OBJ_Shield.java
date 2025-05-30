package object.weapon;

import Entities.Entity;
import main.GamePanel;

public class OBJ_Shield extends Entity {

    public OBJ_Shield(GamePanel gp) {
        super(gp);

        name = "Shield";
        down1 = setUp("/objects/shield");
        defenseValue = 2;
    }
}
