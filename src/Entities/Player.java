package Entities;

import main.GamePanel;
import main.KeyboardHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyboardHandler keyH;

    public final int screenX;
    public final int screenY;

    /*public int hasKey = 0;*/
    int standCounter = 0;

    public Player(GamePanel gp, KeyboardHandler keyboardHandler) {

        super(gp);

        this.keyH = keyboardHandler;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        hitBox = new Rectangle();
        hitBox.x = 32;
        hitBox.y = 32;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        hitBox.width = 32;
        hitBox.height = 48;

        setDefaultValues();

        getPlayerImage();
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

    }

    public void getPlayerImage() {
        up1 = setUp("/player/player_up1");
        up2 = setUp("/player/player_up2");
        up3 = setUp("/player/player_up3");
        down1 = setUp("/player/player_down1");
        down2 = setUp("/player/player_down2");
        down3 = setUp("/player/player_down3");
        left1 = setUp("/player/player_left1");
        left2 = setUp("/player/player_left2");
        left3 = setUp("/player/player_left3");
        right1 = setUp("/player/player_right1");
        right2 = setUp("/player/player_right2");
        right3 = setUp("/player/player_right3");
    }


    public void update() {
        boolean moving = false;

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {

            // Setze Richtung für Animation
            if (keyH.upPressed) direction = "up";
            if (keyH.downPressed) direction = "down";
            if (keyH.leftPressed) direction = "left";
            if (keyH.rightPressed) direction = "right";

            // Collision prüfen

            //Tile Collision
            collisionOn = false;
            gp.cCheck.checkTile(this);
            //Object Collision
            int objIndex = gp.cCheck.checkObject(this, true);
            pickup(objIndex);
            //NPC Collision
            int npcIndex = gp.cCheck.checkEntity(this, gp.npc);
            interactNPC(npcIndex);


            // Bewegung
            if (!collisionOn) {
                int dx = 0;
                int dy = 0;

                if (keyH.upPressed) dy -= 1;
                if (keyH.downPressed) dy += 1;
                if (keyH.leftPressed) dx -= 1;
                if (keyH.rightPressed) dx += 1;

                // Diagonal normalisieren
                if (dx != 0 && dy != 0) {
                    worldX += (int) (dx * (speed / Math.sqrt(3)));
                    worldY += (int) (dy * (speed / Math.sqrt(3)));
                } else {
                    worldX += dx * speed;
                    worldY += dy * speed;
                }

                moving = true;
            }

            // Animation
            if (moving) {
                spriteCounter++;
                if (spriteCounter > 18) {
                    spriteNumber++;
                    if(spriteNumber > 3) {
                        spriteNumber = 1;
                    }
                    spriteCounter = 0;
                }
            }
        } else {
            standCounter++;
            if (standCounter == 30) {
                spriteNumber = 1;
                standCounter = 0;
            }
        }
    }


    public void pickup(int i) {
        if(i != 999) {

        }
    }

    public void interactNPC(int i) {
        if(i != 999) {
            System.out.println("test test test");
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

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
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        /*g2.drawRect(screenX + hitBox.x, screenY + hitBox.y, hitBox.width, hitBox.height);  //hitbox anzeige  */
    }
}
