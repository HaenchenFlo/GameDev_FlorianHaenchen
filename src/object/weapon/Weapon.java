package object.weapon;

import Entities.Entity;
import main.GamePanel;

import java.awt.image.BufferedImage;

public abstract class Weapon extends Entity {

    public Weapon(GamePanel gp) {
        super(gp);
    }

}
