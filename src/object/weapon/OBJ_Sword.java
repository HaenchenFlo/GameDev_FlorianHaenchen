package object.weapon;

import main.GamePanel;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class OBJ_Sword extends Weapon {

    public OBJ_Sword(GamePanel gp) {
        super(gp);

        name = "Normal Sword";
        icon = setUp("/objects/sword");
        attackValue = 1;
    }

    @Override
    protected void loadEffectFrames() {
        for(int i = 0; i < 4; i++) {
            effectFrames.add(setUp("/weapons/sword/effects/slash_" + i,gp.tileSize * 3,gp.tileSize * 3));
            if(i == 1) {
                effectFrameDurations.add(5);
            } else {
                effectFrameDurations.add(5);
            }
        }
    }

    @Override
    public void drawEffect(Graphics2D g2) {
        if(effectPlaying && effectFrameIndex < effectFrames.size()) {
            int effectSkalierung = 3;
            int effectSize = gp.tileSize * effectSkalierung;
            int screenX = effectX - gp.player.worldX + gp.player.screenX - (effectSize / effectSkalierung);
            int screenY = effectY - gp.player.worldY + gp.player.screenY - (effectSize / effectSkalierung);


            BufferedImage frame = effectFrames.get(effectFrameIndex);

            // Mittelpunkt des Bildes
            int centerX = screenX + effectSize / 2;
            int centerY = screenY + effectSize / 2;

            // Rotation abhängig von Spieler-Richtung
            double rotation = getRotationFromDirection(gp.player.direction);

            // Transformation vorbereiten
            AffineTransform originalTransform = g2.getTransform(); // Zustand speichern
            g2.rotate(rotation, centerX, centerY);
            g2.drawImage(frame, screenX, screenY, effectSize, effectSize, null);
            g2.setTransform(originalTransform); // Zustand zurücksetzen
        }
    }

    @Override
    public void drawWeapon(Graphics2D g2) {
        
    }

    private double getRotationFromDirection(String direction) {
        switch (direction) {
            case "right": return 0;
            case "down": return Math.PI / 2;
            case "left": return Math.PI;
            case "up": return 3 * Math.PI / 2;
            default: return 0;
        }
    }
}
