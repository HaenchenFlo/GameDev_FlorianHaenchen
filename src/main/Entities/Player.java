package main.Entities;

import main.GamePanel;
import main.KeyboardHandler;

import java.awt.*;

public class Player extends Entity {
    GamePanel gp;
    KeyboardHandler keyH;

    public Player(GamePanel gp, KeyboardHandler keyboardHandler) {
        this.gp = gp;
        this.keyH = keyboardHandler;

        setDefaultValues();

    }

    public void setDefaultValues() {

        x = 100;
        y = 100;
        speed = 4;

    }

    public void update() {
        if(keyH.upPressed == true) {
            y -= speed;
        } else if (keyH.downPressed == true) {
            y += speed;
        } else if (keyH.rightPressed == true) {
            x += speed;
        } else if (keyH.leftPressed == true) {
            x -= speed;
        }
    }

    public void draw(Graphics2D g2) {
        g2.setColor(Color.white);

        g2.fillRect(x, y, gp.tileSize, gp.tileSize);
    }
}
