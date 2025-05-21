package Entities;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Entity {

    GamePanel gp;
    public int worldX, worldY;
    public int speed;
    public BufferedImage up1, up2,up3, down1, down2,down3, left1, left2,left3, right1, right2, right3;
    public String direction;


    public int spriteCounter = 0;
    public int spriteNumber = 1;

    public int actionLockCounter = 0;

    //Spieler Hitbox
    public Rectangle hitBox = new Rectangle(0,0,96,96);
    //Object Hitbox checker
    public int hitBoxDefaultX, hitBoxDefaultY;
    public boolean collisionOn = false;

    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {

    }

    public void update() {
        setAction();

        collisionOn = false;
        gp.cCheck.checkTile(this);

        if(collisionOn == false) {
            switch (direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }

        spriteCounter++;
        if(spriteCounter > 15) {
            spriteNumber++;
            if(spriteNumber > 3) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
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
        }
    }

    public BufferedImage setUp(String imageName) {
        Utility uTool = new Utility();
        BufferedImage image;
        BufferedImage scaledImage = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imageName + ".png")));
            scaledImage = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        } catch (Exception _) {

        }

        return scaledImage;
    }
}
