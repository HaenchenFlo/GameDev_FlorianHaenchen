package npc;

import Entities.Entity;
import main.GamePanel;

import java.util.Random;

public class NPC_Oldman extends Entity {

    public NPC_Oldman(GamePanel gp) {

        super(gp);

        direction = "down";
        speed = 1;

        getImage();
        setDialog();

        hitBox.x = 32;
        hitBox.y = 32;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        hitBox.width = 32;
        hitBox.height = 48;

    }

    public void getImage() {
        up1 = setUp("/npc/oldman/oldman_up1");
        up2 = setUp("/npc/oldman/oldman_up2");
        up3 = setUp("/npc/oldman/oldman_up3");
        down1 = setUp("/npc/oldman/oldman_down1");
        down2 = setUp("/npc/oldman/oldman_down2");
        down3 = setUp("/npc/oldman/oldman_down3");
        left1 = setUp("/npc/oldman/oldman_left1");
        left2 = setUp("/npc/oldman/oldman_left2");
        left3 = setUp("/npc/oldman/oldman_left3");
        right1 = setUp("/npc/oldman/oldman_right1");
        right2 = setUp("/npc/oldman/oldman_right2");
        right3 = setUp("/npc/oldman/oldman_right3");
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

    public void setDialog() {
        dialogues[0] = "Oy vey";
        dialogues[1] = "Willst du ein Make-Shift Dildo bauen? :)";
        dialogues[2] = "Ich liebe 3 Finger in meinem Arsch";
        dialogues[3] = "Kys, Alec";
    }

    public void speak() {

        super.speak();

    }
}
