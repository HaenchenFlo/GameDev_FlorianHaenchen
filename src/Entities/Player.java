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

        //Spielercam
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);


        //hitboxen von Entity
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

        //Spieler Position und speed

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        dWorldX = worldX;
        dWorldY = worldY;
        speed = 4;
        direction = "down";

        //Spieler Status

        maxHealth = 6;
        health = maxHealth;


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

        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed == true) {

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
            if (!collisionOn && !keyH.enterPressed) {
                int dx = 0, dy = 0;
                if (keyH.upPressed) dy -= 1;
                if (keyH.downPressed) dy += 1;
                if (keyH.leftPressed) dx -= 1;
                if (keyH.rightPressed) dx += 1;

                double diagSpeed = speed;
                if (dx != 0 && dy != 0) {
                    diagSpeed = speed / Math.sqrt(2);
                }

                dWorldX += dx * diagSpeed;
                dWorldY += dy * diagSpeed;

                worldX = (int) dWorldX;
                worldY = (int) dWorldY;

                moving = true;
            }

            keyH.enterPressed = false;

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
            if(keyH.enterPressed == true) {
                gp.gameState = gp.dialogState;
                gp.npc[i].speak();
            }
        }
        gp.keyH.enterPressed = false;
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
