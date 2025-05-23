package object;

import main.GamePanel;
import main.Utility;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public BufferedImage image, image2,image3;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle hitbox = new Rectangle(0,0,96,96);
    public int hitboxDefaultX = 0;
    public int hitboxDefaultY = 0;
    Utility uTool = new Utility();

    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
        && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
        && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
        && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
            g2.drawImage(image, screenX, screenY, gp.tileSize,gp.tileSize,null);
        }
    }
}
