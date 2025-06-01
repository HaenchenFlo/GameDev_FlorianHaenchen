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
        attack = 5;
        defense = 0;

        hitBox.x = 26;
        hitBox.y = 36;
        hitBox.width = 48;
        hitBox.height = 64;
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
    @Override
    public void dyingAnimation(Graphics2D g2, int screenX, int screenY) {

        dyingCounter++;

        BufferedImage dyingImage1 = setUp("/monster/slime/slimedeath0");
        BufferedImage dyingImage2 = setUp("/monster/slime/slimedeath1");
        BufferedImage dyingImage3 = setUp("/monster/slime/slimedeath2");
        BufferedImage dyingImage4 = setUp("/monster/slime/slimedeath3");
        BufferedImage dyingImage5 = setUp("/monster/slime/slimedeath4");

        int i = 10;

        if(dyingCounter <= i) {}
        if(dyingCounter > i && dyingCounter <= i * 2) {g2.drawImage(dyingImage1, screenX, screenY, gp.tileSize, gp.tileSize, null);}
        if(dyingCounter > i * 2 && dyingCounter <= i * 3) {g2.drawImage(dyingImage2, screenX, screenY, gp.tileSize, gp.tileSize, null);}
        if(dyingCounter > i * 3 && dyingCounter <= i * 4) {g2.drawImage(dyingImage3, screenX, screenY, gp.tileSize, gp.tileSize, null);}
        if(dyingCounter > i * 4 && dyingCounter <= i * 5) {g2.drawImage(dyingImage4, screenX, screenY, gp.tileSize, gp.tileSize, null);}
        if(dyingCounter > i * 5 && dyingCounter <= i * 8) {g2.drawImage(dyingImage5, screenX, screenY, gp.tileSize, gp.tileSize, null);}
        if(dyingCounter > i * 8) {
            dying = false;
            alive = false;
        }
    }
    @Override
    public void drawImpact(Graphics2D g2,int screenX,int screenY) {

        BufferedImage impactFrame = setUp("/monster/slime/slime_iframe");
        g2.drawImage(impactFrame, screenX, screenY, gp.tileSize, gp.tileSize, null);

    }

    public void damageReact() {
        //einfache AI weg vom spieler laufen
        actionLockCounter = 0;
        direction = gp.player.direction;
    }

    public void setInvincible() {
        invincibleCounter++;
        if (invincibleCounter > 20) {
            invincible = false;
            invincibleCounter = 0;
        }
    }
}
