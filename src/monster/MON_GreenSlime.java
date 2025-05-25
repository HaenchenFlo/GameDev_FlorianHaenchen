package monster;

import Entities.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MON_GreenSlime extends Entity {

    public MON_GreenSlime(GamePanel gp) {
        super(gp);

        name = "Green Slime";
        speed = 1;
        maxHealth = 4;
        health = maxHealth;

        hitBox.x = 6;
        hitBox.y = 36;
        hitBox.width = 84;
        hitBox.height = 60;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;

        getImage();

    }

    public void getImage() {
        up1 = setUp("/monster/slime/slime0");
        up2 = setUp("/monster/slime/slime1");
        up3 = setUp("/monster/slime/slime2");
        down1 = setUp("/monster/slime/slime0");
        down2 = setUp("/monster/slime/slime1");
        down3 = setUp("/monster/slime/slime2");
        left1 = setUp("/monster/slime/slime0");
        left2 = setUp("/monster/slime/slime1");
        left3 = setUp("/monster/slime/slime2");
        right1 = setUp("/monster/slime/slime0");
        right2 = setUp("/monster/slime/slime1");
        right3 = setUp("/monster/slime/slime2");

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

    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up":
                    if(spriteNumber == 1) {image = up1;}
                    if(spriteNumber == 2) {image = up2;}
                    if(spriteNumber == 3) {image = up3;}
                    break;
                case "down":
                    if(spriteNumber == 1) {image = down1;}
                    if(spriteNumber == 2) {image = down2;}
                    if(spriteNumber == 3) {image = down3;}
                    break;
                case "left":
                    if(spriteNumber == 1) {image = left1;}
                    if(spriteNumber == 2) {image = left2;}
                    if(spriteNumber == 3) {image = left3;}
                    break;
                case "right":
                    if(spriteNumber == 1) {image = right1;}
                    if(spriteNumber == 2) {image = right2;}
                    if(spriteNumber == 3) {image = right3;}
                    break;

            }

            g2.drawImage(image, screenX, screenY, gp.tileSize,gp.tileSize,null);
            //hitbox anzeige
            g2.drawRect(screenX + hitBox.x, screenY + hitBox.y, hitBox.width, hitBox.height);
        }
    }
}
