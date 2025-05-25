package main;

import monster.MON_GreenSlime;
import npc.NPC_Oldman;
import object.OBJ_Door;
import object.OBJ_Key;


public class ObjectHandler {

    GamePanel gp;

    public ObjectHandler(GamePanel gp) {
        this.gp = gp;
    }

    public void setObject() {

    }

    public void setNpc() {

        gp.npc[0] = new NPC_Oldman(gp);
        gp.npc[0].worldX = gp.tileSize * 21;
        gp.npc[0].worldY = gp.tileSize * 21;


    }

    public void setMonster() {
        gp.monster[0] = new MON_GreenSlime(gp);
        gp.monster[0].worldX = gp.tileSize * 23;
        gp.monster[0].worldY = gp.tileSize * 36;

        gp.monster[1] = new MON_GreenSlime(gp);
        gp.monster[1].worldX = gp.tileSize * 23;
        gp.monster[1].worldY = gp.tileSize * 37;
    }
}
