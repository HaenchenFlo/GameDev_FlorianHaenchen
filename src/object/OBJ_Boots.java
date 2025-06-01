package object;

import main.GamePanel;

import Entities.Entity;

public class OBJ_Boots extends Entity {

    public OBJ_Boots(GamePanel gp) {

        super(gp);

        name = "Boots";
        icon = setUp("/objects/boots");
    }
}
