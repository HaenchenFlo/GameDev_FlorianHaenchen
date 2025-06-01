package object;

import main.GamePanel;

import Entities.Entity;

public class OBJ_Chest extends Entity{

    public OBJ_Chest(GamePanel gp) {

        super(gp);

        name = "Chest";
        icon = setUp("objects/chest");
    }
}
