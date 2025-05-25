package object;

import main.GamePanel;

import Entities.Entity;

public class OBJ_Boots extends Entity {

    public OBJ_Boots(GamePanel gp) {

        super(gp);

        name = "Boots";
        down1 = setUp("/objects/boots");
    }
}
