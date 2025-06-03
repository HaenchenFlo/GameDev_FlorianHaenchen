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
        attackCooldown = 5; // spätere implementierung von Hit Cooldown

        description = "[" + name + "]\nEin altes Schwert.";
    }

    @Override
    protected void loadEffectFrames() {
        for(int i = 0; i < 4; i++) {
            effectFrames.add(setUp("/weapons/sword/effects/slash_" + i,gp.tileSize * 3,gp.tileSize * 3));
            effectFrameDurations.add(6);
        }
    }

    @Override
    public void drawEffect(Graphics2D g2) {
        if (effectPlaying && effectFrameIndex < effectFrames.size()) {
            int effectSkalierung = 3;
            int effectSize = gp.tileSize * effectSkalierung;

            // Basisposition berechnen
            int screenX = effectX - gp.player.worldX + gp.player.screenX - (effectSize / effectSkalierung);
            int screenY = effectY - gp.player.worldY + gp.player.screenY - (effectSize / effectSkalierung);

            // Richtungsabhängiger Offset
            int offset = gp.tileSize / 5;
            switch (gp.player.direction) {
                case "up":
                    screenY -= offset;
                    screenX -= 20;
                    break;
                case "down":
                    screenY += offset;
                    screenX += 20;
                    break;
                case "left":
                    screenX -= offset;
                    screenY += 20;
                    break;
                case "right":
                    screenX += offset;
                    break;
            }

            // Bild & Mittelpunkt holen
            BufferedImage frame = effectFrames.get(effectFrameIndex);
            int centerX = screenX + effectSize / 2;
            int centerY = screenY + effectSize / 2;

            // Rotation + Zeichnung
            double rotation = getRotationFromDirection(gp.player.direction);
            AffineTransform originalTransform = g2.getTransform();
            g2.rotate(rotation, centerX, centerY);
            g2.drawImage(frame, screenX, screenY, effectSize, effectSize, null);
            g2.setTransform(originalTransform);
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
