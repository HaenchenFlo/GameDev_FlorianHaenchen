package Entities;

import main.GamePanel;
import main.KeyboardHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyboardHandler keyH;
    BufferedImage up4, up5, up6,up7,up8,down4,down5,down6,down7,down8,left4,left5,left6,left7,left8,right4,right5,right6,right7,right8;

    //Angriff
    public boolean attacking = false;
    public int attackCounter = 0;
    public int attackFrame = 0;

    //Angriff animationen
    BufferedImage[][] weaponSprites = new BufferedImage[4][5];
    //[Richtung][Frames]

    public final int screenX;
    public final int screenY;

    /*public int hasKey = 0;*/
    int standCounter = 0;

    public Player(GamePanel gp, KeyboardHandler keyboardHandler) {

        super(gp);

        this.keyH = keyboardHandler;

        //Spielercam
        screenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        screenY = gp.screenHeight / 2 - (gp.tileSize / 2);


        //hitboxen von Player
        hitBox = new Rectangle();
        hitBox.x = 32;
        hitBox.y = 32;
        hitBoxDefaultX = hitBox.x;
        hitBoxDefaultY = hitBox.y;
        hitBox.width = 24;
        hitBox.height = 48;

        setDefaultValues();

        loadWeaponSprite();

        getPlayerImage();
    }

    public void setDefaultValues() {

        //Spieler Position und speed

        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        dWorldX = worldX;
        dWorldY = worldY;
        speed = 4;
        direction = "down";

        //Spieler Status

        maxHealth = 6;
        health = maxHealth;


    }

    public void getPlayerImage() {

        up1 = setUp("/player/up/player_up0");
        up2 = setUp("/player/up/player_up1");
        up3 = setUp("/player/up/player_up2");
        up4 = setUp("/player/up/player_up3");
        up5 = setUp("/player/up/player_up4");
        up6 = setUp("/player/up/player_up5");
        up7 = setUp("/player/up/player_up6");
        up8 = setUp("/player/up/player_up7");

        down1 = setUp("/player/right/player_right0");
        down2 = setUp("/player/right/player_right1");
        down3 = setUp("/player/right/player_right2");
        down4 = setUp("/player/right/player_right3");
        down5 = setUp("/player/right/player_right4");
        down6 = setUp("/player/right/player_right5");
        down7 = setUp("/player/right/player_right6");
        down8 = setUp("/player/right/player_right7");


        left1 = setUp("/player/left/player_left0");
        left2 = setUp("/player/left/player_left1");
        left3 = setUp("/player/left/player_left2");
        left4 = setUp("/player/left/player_left3");
        left5 = setUp("/player/left/player_left4");
        left6 = setUp("/player/left/player_left5");
        left7 = setUp("/player/left/player_left6");
        left8 = setUp("/player/left/player_left7");

        right1 = setUp("/player/right/player_right0");
        right2 = setUp("/player/right/player_right1");
        right3 = setUp("/player/right/player_right2");
        right4 = setUp("/player/right/player_right3");
        right5 = setUp("/player/right/player_right4");
        right6 = setUp("/player/right/player_right5");
        right7 = setUp("/player/right/player_right6");
        right8 = setUp("/player/right/player_right7");

    }

    public void getPlayerAttack() {

        attackUp1 = setUp("/player/attack/player_attack_");
        attackUp2 = setUp("/player/attack/player_attack_");
        attackUp3 = setUp("/player/attack/player_attack_");

        attackDown1 = setUp("/player/attack/player_attack_");
        attackDown2 = setUp("/player/attack/player_attack_");
        attackDown3 = setUp("/player/attack/player_attack_");

        attackLeft1 = setUp("/player/attack/player_attack_");
        attackLeft2 = setUp("/player/attack/player_attack_");
        attackLeft3 = setUp("/player/attack/player_attack_");

        attackRight1 = setUp("/player/attack/player_attack_");
        attackRight2 = setUp("/player/attack/player_attack_");
        attackRight3 = setUp("/player/attack/player_attack_");

    }

    public void loadWeaponSprite() {
        for(int i = 0; i < 5; i++) {
            weaponSprites[0][i] = setUp("/weapons/sword/sword_right" + i,gp.tileSize / 2,gp.tileSize / 2);
            weaponSprites[1][i] = setUp("/weapons/sword/sword_right" + i,gp.tileSize / 2,gp.tileSize / 2);
            weaponSprites[2][i] = setUp("/weapons/sword/sword_right" + i,gp.tileSize / 2,gp.tileSize / 2);
            weaponSprites[3][i] = setUp("/weapons/sword/sword_right" + i,gp.tileSize / 2,gp.tileSize / 2);
        }
    }


    public void update() {
        boolean moving = false;

        if (attacking) {
            attackCounter++;
            if (attackCounter % 10 == 0) {
                attackFrame++;
                if (attackFrame >= 5) {
                    attacking = false;
                    attackFrame = 0;
                    attackCounter = 0;
                }
            }
        } else {
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed == true) {

                // Setze Richtung für Animation
                if (keyH.upPressed) direction = "up";
                if (keyH.downPressed) direction = "down";
                if (keyH.leftPressed) direction = "left";
                if (keyH.rightPressed) direction = "right";

                // Collision prüfen

                //Tile Collision
                collisionOn = false;
                gp.cCheck.checkTile(this);

                //Object Collision
                int objIndex = gp.cCheck.checkObject(this, true);
                pickup(objIndex);

                //NPC Collision
                int npcIndex = gp.cCheck.checkEntity(this, gp.npc);
                interactNPC(npcIndex);

                //Gegner / Monster Collision
                int monsterIndex = gp.cCheck.checkEntity(this,gp.monster);
                contactMonster(monsterIndex);

                //Check Event
                gp.eHandler.checkEvent();

                //Nach jedem check
                gp.keyH.enterPressed = false;


                // Bewegung
                if (!collisionOn && !keyH.enterPressed) {
                    int dx = 0, dy = 0;
                    if (keyH.upPressed) dy -= 1;
                    if (keyH.downPressed) dy += 1;
                    if (keyH.leftPressed) dx -= 1;
                    if (keyH.rightPressed) dx += 1;

                    double diagSpeed = speed;
                    if (dx != 0 && dy != 0) {
                        diagSpeed = speed / Math.sqrt(2);
                    }

                    dWorldX += dx * diagSpeed;
                    dWorldY += dy * diagSpeed;

                    worldX = (int) dWorldX;
                    worldY = (int) dWorldY;

                    moving = true;
                }

                keyH.enterPressed = false;

                // Animation
                if (moving) {
                    spriteCounter++;
                    if (spriteCounter > 10) {
                        spriteNumber++;
                        if(spriteNumber > 8) {
                            spriteNumber = 1;
                        }
                        spriteCounter = 0;
                    }
                }
            } else {
                standCounter++;
                if (standCounter == 24) {
                    spriteNumber = 1;
                    standCounter = 0;
                }
            }

            //I-Frames
            if(invincible == true) {
                invincibleCounter++;
                if(invincibleCounter > 120) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
        }

     }


    public void pickup(int i) {
        if(i != 999) {

        }
    }

    public void interactNPC(int i) {
        if(i != 999) {
            if(keyH.enterPressed == true) {
                gp.gameState = gp.dialogState;
                gp.npc[i].speak();
            }
        }
    }

    public void contactMonster(int i) {
        if(i != 999) {
            if(invincible == false) {
                health -= 1;
                invincible = true;
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        if (attacking) {
            int directionIndex = 0; // up

            switch (direction) {
                case "up": directionIndex = 0; break;
                case "down": directionIndex = 1; break;
                case "left": directionIndex = 2; break;
                case "right": directionIndex = 3; break;
            }

            BufferedImage weaponImage = weaponSprites[directionIndex][attackFrame];

            // Offset berechnen (abhängig von Blickrichtung)
            int weaponOffsetX = 0;
            int weaponOffsetY = 0;

            switch (direction) {
                case "up":    weaponOffsetY = -gp.tileSize / 2; break;
                case "down":  weaponOffsetY = gp.tileSize / 2;  break;
                case "left":  weaponOffsetX = -gp.tileSize / 2; break;
                case "right": weaponOffsetX = gp.tileSize / 2;  break;
            }

            g2.drawImage(
                    weaponImage,
                    screenX + weaponOffsetX,
                    screenY + weaponOffsetY,
                    gp.tileSize,
                    gp.tileSize,
                    null
            );
        }

        switch (direction) {
            case "up":
                if(spriteNumber == 1) {image = up1;}
                if(spriteNumber == 2) {image = up2;}
                if(spriteNumber == 3) {image = up3;}
                if(spriteNumber == 4) {image = up4;}
                if(spriteNumber == 5) {image = up5;}
                if(spriteNumber == 6) {image = up6;}
                if(spriteNumber == 7) {image = up7;}
                if(spriteNumber == 8) {image = up8;}
                break;
            case "down":
                if(spriteNumber == 1) {image = down1;}
                if(spriteNumber == 2) {image = down2;}
                if(spriteNumber == 3) {image = down3;}
                if(spriteNumber == 4) {image = down4;}
                if(spriteNumber == 5) {image = down5;}
                if(spriteNumber == 6) {image = down6;}
                if(spriteNumber == 7) {image = down7;}
                if(spriteNumber == 8) {image = down8;}
                break;
            case "left":
                if(spriteNumber == 1) {image = left1;}
                if(spriteNumber == 2) {image = left2;}
                if(spriteNumber == 3) {image = left3;}
                if(spriteNumber == 4) {image = left4;}
                if(spriteNumber == 5) {image = left5;}
                if(spriteNumber == 6) {image = left6;}
                if(spriteNumber == 7) {image = left7;}
                if(spriteNumber == 8) {image = left8;}
                break;
            case "right":
                if(spriteNumber == 1) {image = right1;}
                if(spriteNumber == 2) {image = right2;}
                if(spriteNumber == 3) {image = right3;}
                if(spriteNumber == 4) {image = right4;}
                if(spriteNumber == 5) {image = right5;}
                if(spriteNumber == 6) {image = right6;}
                if(spriteNumber == 7) {image = right7;}
                if(spriteNumber == 8) {image = right8;}
                break;

        }

        if(invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f));
        }
        g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
        //Reset Alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));

        //hitbox anzeige
        g2.drawRect(screenX + hitBox.x, screenY + hitBox.y, hitBox.width, hitBox.height);

        //I-frame test
        /*g2.setFont(new Font("Arial", Font.PLAIN,26));
        g2.setColor(Color.white);
        g2.drawString("Invincible Counter: " + invincibleCounter, 10, 400);*/
    }
}
