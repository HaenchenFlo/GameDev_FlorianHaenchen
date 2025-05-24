package main;

import java.awt.*;

public class EventHandler {

    GamePanel gp;
    Rectangle eventBox;
    int eventDefaultX, eventDefaultY;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventBox = new Rectangle();
        eventBox.x = 46;
        eventBox.y = 46;
        eventBox.width = 4;
        eventBox.height = 4;
        eventDefaultX = eventBox.x;
        eventDefaultY = eventBox.y;

    }

    public void checkEvent() {

        if(hit(27,16,"right") == true) {damagePit(gp.dialogState);}
        if(hit(23,12,"up") == true) {healingWater(gp.dialogState);}
        if(hit(24,12,"up") == true) {teleport(gp.dialogState);}
    }

    public boolean hit(int eventCol, int eventRow, String reqDirection) {

        boolean hit = false;

        gp.player.hitBox.x = gp.player.worldX + gp.player.hitBox.x;
        gp.player.hitBox.y = gp.player.worldY + gp.player.hitBox.y;
        eventBox.x = eventCol*gp.tileSize + eventBox.x;
        eventBox.y = eventRow*gp.tileSize + eventBox.y;

        if(gp.player.hitBox.intersects(eventBox)) {
            if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
                hit = true;
            }
        }

        gp.player.hitBox.x = gp.player.hitBoxDefaultX;
        gp.player.hitBox.y = gp.player.hitBoxDefaultY;
        eventBox.x = eventDefaultX;
        eventBox.y = eventDefaultY;


        return hit;
    }

    public void teleport(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialog = "Du teleportierst dich!";
        gp.player.dWorldX = gp.tileSize * 37;
        gp.player.dWorldY = gp.tileSize * 10;
    }

    public void damagePit(int gameState) {
        gp.gameState = gameState;
        gp.ui.currentDialog = "Du bist gefallen!";
        gp.player.health -= 1;
    }

    public void healingWater(int gameState) {
        if(gp.keyH.enterPressed == true) {
            gp.gameState = gameState;
            gp.ui.currentDialog = "Du heilst im Wasser";
            gp.player.health = gp.player.maxHealth;
        }
    }
}
