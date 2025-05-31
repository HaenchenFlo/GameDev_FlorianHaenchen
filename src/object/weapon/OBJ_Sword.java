package object.weapon;

import main.GamePanel;

public class OBJ_Sword extends Weapon {

    public OBJ_Sword(GamePanel gp) {
        super(gp);

        name = "Normal Sword";
        icon = setUp("/objects/sword");
        attackValue = 1;
    }
}
