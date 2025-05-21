package Entities;

import main.GamePanel;
import main.KeyboardHandler;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gp;
    KeyboardHandler keyH;

    public final int screenX;
    public final int screenY;

    public int hasKey = 0;
    int standCounter = 0;

    public Player(GamePanel gp, KeyboardHandler keyboardHandler) {
        this.gp = gp;
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
        up1 = setUp("player_up1");
        up2 = setUp("player_up2");
        up3 = setUp("player_up3");
        down1 = setUp("player_down1");
        down2 = setUp("player_down2");
        down3 = setUp("player_down3");
        left1 = setUp("player_left1");
        left2 = setUp("player_left2");
        left3 = setUp("player_left3");
        right1 = setUp("player_right1");
        right2 = setUp("player_right2");
        right3 = setUp("player_right3");
    }

    public BufferedImage setUp(String imageName) {
        Utility uTool = new Utility();
        BufferedImage image;
        BufferedImage scaledImage = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/" + imageName + ".png")));
            scaledImage = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception _) {

        }

        return scaledImage;
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
            collisionOn = false;
            gp.cCheck.checkTile(this);
            int objIndex = gp.cCheck.checkObject(this, true);
            pickup(objIndex);

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
                    worldX += (int) (dx * (speed / Math.sqrt(2)));
                    worldY += (int) (dy * (speed / Math.sqrt(2)));
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
            String objectname = gp.obj[i].name;

            switch (objectname) {
                case "Key":
                    gp.soundEffect(1);
                    hasKey++;
                    gp.obj[i] = null;
                    gp.ui.showMessage("Du hast ein Schlüssel!");
                    break;
                case "Door":
                    if(hasKey > 0) {
                        gp.soundEffect(3);
                        gp.obj[i] = null;
                        hasKey--;
                        gp.ui.showMessage("Du hast eine Tür geöffnet!");
                    } else {
                        gp.ui.showMessage("Du benötigst einen Schlüssel");
                    }
                    break;
                case "Boots":
                    gp.soundEffect(2);
                    speed += 2;
                    gp.obj[i] = null;
                    gp.ui.showMessage("SpeedUP!");
                    break;
                case "Chest":
                    gp.ui.gameFinished = true;
                    gp.stopMusic();
                    gp.soundEffect(4);
                    break;
            }
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
