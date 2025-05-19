package Entities;

import main.GamePanel;
import main.KeyboardHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyboardHandler keyH;

    public final int screenX;
    public final int screenY;

    public Player(GamePanel gp, KeyboardHandler keyboardHandler) {
        this.gp = gp;
        this.keyH = keyboardHandler;

        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        hitBox = new Rectangle();
        hitBox.x = 16;
        hitBox.y = 32;
        hitBox.width = 64;
        hitBox.height = 64;

        setDefaultValues();

        getPlayerImage();
    }

    public void setDefaultValues() {

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down";

    }
    @SuppressWarnings("DataFlowIssue")
    public void getPlayerImage() {

        try {
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/alec_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/alec_up_1.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/alec_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/alec_down_1.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/alec_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/alec_left_1.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/alec_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/alec_right_2.png"));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if(keyH.upPressed  || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            if(keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.rightPressed) {
                direction = "right";
            } else if (keyH.leftPressed) {
                direction = "left";
            }

            //Tile Collision checken
            collisionOn = false;
            gp.cCheck.checkTile(this);

            //wenn collision false - kann sich spieler bewegen
            if(collisionOn == false) {
                switch (direction) {
                    case ("up"):
                        worldY -= speed;
                        break;
                    case ("down"):
                        worldY += speed;
                        break;
                    case ("left"):
                        worldX -= speed;
                        break;
                    case ("right"):
                        worldX += speed;
                        break;

                }
            }

            spriteCounter++;

            if(spriteCounter > 20) {
                if(spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        switch (direction) {
            case "up":
                if(spriteNumber == 1) {
                    image = up1;
                }
                if(spriteNumber == 2) {
                    image = up2;
                }
                break;
            case "down":
                if(spriteNumber == 1) {
                    image = down1;
                }
                if(spriteNumber == 2) {
                    image = down2;
                }
                break;
            case "left":
                if(spriteNumber == 1) {
                    image = left1;
                }
                if(spriteNumber == 2) {
                    image = left2;
                }
                break;
            case "right":
                if(spriteNumber == 1) {
                    image = right1;
                }
                if(spriteNumber == 2) {
                    image = right2;
                }
                break;

        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
    }
}
