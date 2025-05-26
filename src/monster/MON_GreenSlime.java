package monster;

import Entities.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MON_GreenSlime extends Entity {

    public MON_GreenSlime(GamePanel gp) {
        super(gp);

        type = 2;
        name = "Green Slime";
        speed = 1;
        maxHealth = 4;
        health = maxHealth;

        hitBox.x = 16;
        hitBox.y = 36;
        hitBox.width = 64;
        hitBox.height = 60;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;

        getImage();

    }

    public void getImage() {
        up1 = setUp("/monster/slime/slime0");
        up2 = setUp("/monster/slime/slime3");
        up3 = setUp("/monster/slime/slime4");
        down1 = setUp("/monster/slime/slime0");
        down2 = setUp("/monster/slime/slime3");
        down3 = setUp("/monster/slime/slime4");
        left1 = setUp("/monster/slime/slime0");
        left2 = setUp("/monster/slime/slime3");
        left3 = setUp("/monster/slime/slime4");
        right1 = setUp("/monster/slime/slime0");
        right2 = setUp("/monster/slime/slime3");
        right3 = setUp("/monster/slime/slime4");

    }


    public void setAction() {

        actionLockCounter++;

        if(actionLockCounter == 240) {

            Random random = new Random();
            int i = random.nextInt(100) + 1; // random nummer zwischen 1 - 100

            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionLockCounter = 0;
        }
    }
}
