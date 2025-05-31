package object.weapon;

import Entities.Entity;
import main.GamePanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Weapon extends Entity {

    public List<BufferedImage> effectFrames = new ArrayList<>();
    public List<Integer> effectFrameDurations = new ArrayList<>();
    public int effectFrameIndex = 0;
    public int effectFrameDelay = 5;
    public int effectCounter = 0;
    public boolean effectPlaying = false;
    public int effectX, effectY;

    public Weapon(GamePanel gp) {
        super(gp);
        loadEffectFrames();
    }

    protected abstract void loadEffectFrames();

    public void triggerEffekt(int x, int y) {
        effectPlaying = true;
        effectFrameIndex = 0;
        effectCounter = 0;
        effectX = x;
        effectY = y;
    }

    public void updateEffect() {
        if(effectPlaying) {
            effectCounter++;

            int currentFrameDelay = effectFrameDurations.get(effectFrameIndex); // falss die frames länger / langsamer gehen sollten

            if(effectCounter >= currentFrameDelay) {
                effectFrameIndex++;
                effectCounter = 0;

                if(effectFrameIndex >= effectFrames.size()) {
                    effectPlaying = false;
                }
            }
        }
    }

    public abstract void drawEffect(Graphics2D g2);
}
