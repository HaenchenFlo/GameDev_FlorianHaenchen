package main.Entities;

import main.GamePanel;
import main.KeyboardHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Entity {
    GamePanel gp;
    KeyboardHandler keyH;

    public Player(GamePanel gp, KeyboardHandler keyboardHandler) {
        this.gp = gp;
        this.keyH = keyboardHandler;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {

        x = 100;
        y = 100;
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
                y -= speed;
            } else if (keyH.downPressed) {
                direction = "down";
                y += speed;
            } else if (keyH.rightPressed) {
                direction = "right";
                x += speed;
            } else if (keyH.leftPressed) {
                direction = "left";
                x -= speed;
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
        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }
}
