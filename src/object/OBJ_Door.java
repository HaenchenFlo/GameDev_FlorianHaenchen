package object;

import main.GamePanel;

import Entities.Entity;

public class OBJ_Door extends Entity{


    public OBJ_Door(GamePanel gp) {

        super(gp);

        name = "Door";
        icon = setUp("/objects/door");
        collision = true;

        hitBox.x = 0;
        hitBox.y = 32;
        hitBox.width = 96;
        hitBox.height = 64;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
    }
}
