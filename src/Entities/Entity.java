package Entities;

import main.GamePanel;
import main.Utility;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Entity {

    protected GamePanel gp;

    //Image
    public BufferedImage up1, up2,up3, down1, down2,down3, left1, left2,left3, right1, right2, right3;
    public BufferedImage attackUp1, attackUp2,attackUp3, attackDown1, attackDown2,attackDown3,
            attackLeft1, attackLeft2,attackLeft3,attackRight1, attackRight2, attackRight3 ,
            attackUp4, attackUp5, attackDown4, attackDown5, attackLeft4, attackLeft5, attackRight4, attackRight5;
    public BufferedImage icon, image, image2,image3;

    //Spieler // NPC Hitbox // OBJ Hitbox
    public Rectangle hitBox = new Rectangle(0,0,96,96);
    public int hitboxDefaultX,hitboxDefaultY;

    public Rectangle attackHitBox = new Rectangle(0,0,0,0);

    //Dialog
    public String[] dialogues = new String[20]; // mehr dialog einstellung
    int dialogIndex = 0;

    //State
    public int worldX, worldY;
    public double dWorldX, dWorldY;
    public String direction = "down";
    public int hitBoxDefaultX, hitBoxDefaultY;
    public boolean collisionOn = false;
    public boolean collision = false;
    public boolean invincible = false;
    public boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;


    //Counter
    public int spriteCounter = 0;
    public int spriteNumber = 1;
    public int actionLockCounter = 0;
    public int invincibleCounter = 0;
    public int dyingCounter = 0;

    //Character Stats
    public String name;
    public int type; //O = player , 1 = npc, 2 = gegner
    public int speed;
    public int maxHealth;
    public int health;

    //Character Attribute
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity offHand;

    //Item Attribute
    public int attackValue;
    public int defenseValue;
    public String description = "";



    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public void setAction() {}
    public void damageReact() {}
    public void speak() {
        if(dialogues[dialogIndex] == null) {
            dialogIndex = 0;
        }
        gp.ui.currentDialog = dialogues[dialogIndex];
        dialogIndex++;

        switch (gp.player.direction) {
            case "up": direction = "down"; spriteNumber = 1; break;
            case "down": direction = "up"; spriteNumber = 1; break;
            case "left": direction = "right"; spriteNumber = 1; break;
            case "right": direction = "left"; spriteNumber = 1; break;
        }
    }

    public void update() {
        setAction();

        collisionOn = false;
        gp.cCheck.checkTile(this);
        gp.cCheck.checkObject(this, false);
        gp.cCheck.checkEntity(this,gp.npc);
        gp.cCheck.checkEntity(this,gp.monster);
        boolean contactPlayer =  gp.cCheck.checkPlayer(this);

        if(this.type == 2 && contactPlayer == true) {
            if(gp.player.invincible == false) {
                gp.soundEffect(6);

                int damage = attack - gp.player.defense;
                if(damage < 0) {
                    damage = 0;
                }

                gp.player.health -= damage;
                gp.player.invincible = true;
            }
        }

        if(collisionOn == false) {
            switch (direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }

        spriteCounter++;
        if(spriteCounter > 18) {
            spriteNumber++;
            if(spriteNumber > 3) {
                spriteNumber = 1;
            }
            spriteCounter = 0;
        }

        if (invincible == true) {
            setInvincible();
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

            // Wenn nix gesetzt wurde, fallback auf image/icon --> I.d.R für Items
            if (image == null && icon != null) {
                image = icon;
            }

            if(dying == true) {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                dyingAnimation(g2,screenX,screenY);
            } else {
                if (invincible == true && invincibleCounter <= 12) {
                    drawImpact(g2,screenX,screenY);
                } else {
                    g2.drawImage(image, screenX, screenY, gp.tileSize,gp.tileSize,null);
                }
            }

            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));


            //hitbox anzeige
            g2.setColor(Color.RED);
            g2.drawRect(screenX + hitBox.x, screenY + hitBox.y, hitBox.width, hitBox.height);

        }
    }

    public void dyingAnimation(Graphics2D g2, int screenX, int screenY) {
        //Überschreiben für jeweilige Monster

    }

    public void drawImpact(Graphics2D g2, int screenX, int screenY) {
        //Überschreiben für jeweilige Monster
    }

    public void setInvincible() {
        //Überschreiben für jeweilige Monster
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
    //Overloaded setUp für nicht 32x32 format Sprites
    public BufferedImage setUp(String imageName, int width, int height) {
        Utility uTool = new Utility();
        BufferedImage image;
        BufferedImage scaledImage = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imageName + ".png")));
            scaledImage = uTool.scaleImage(image, width, height);
        } catch (Exception _) {

        }

        return scaledImage;
    }
}
