package Entities;

import main.GamePanel;
import main.KeyboardHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {
    KeyboardHandler keyH;
    BufferedImage up4, up5, up6, up7, up8, down4, down5, down6, down7, down8, left4, left5, left6, left7, left8, right4, right5, right6, right7, right8;

    //Angriff

    public int attackCounter = 0;
    public int attackFrame = 0;


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
        hitBox.width = 32;
        hitBox.height = 48;

        attackHitBox.width = 72;
        attackHitBox.height = 72;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttack();
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

        attackUp1 = setUp("/player/attack/player_attack_up0", gp.tileSize, gp.tileSize * 2);
        attackUp2 = setUp("/player/attack/player_attack_up1", gp.tileSize, gp.tileSize * 2);
        attackUp3 = setUp("/player/attack/player_attack_up2", gp.tileSize, gp.tileSize * 2);
        attackUp4 = setUp("/player/attack/player_attack_up3", gp.tileSize, gp.tileSize * 2);
        attackUp5 = setUp("/player/attack/player_attack_up4", gp.tileSize, gp.tileSize * 2);

        attackDown1 = setUp("/player/attack/player_attack_down0", gp.tileSize, gp.tileSize * 2);
        attackDown2 = setUp("/player/attack/player_attack_down1", gp.tileSize, gp.tileSize * 2);
        attackDown3 = setUp("/player/attack/player_attack_down2", gp.tileSize, gp.tileSize * 2);
        attackDown4 = setUp("/player/attack/player_attack_down3", gp.tileSize, gp.tileSize * 2);
        attackDown5 = setUp("/player/attack/player_attack_down4", gp.tileSize, gp.tileSize * 2);

        attackLeft1 = setUp("/player/attack/player_attack_left0", gp.tileSize * 2, gp.tileSize);
        attackLeft2 = setUp("/player/attack/player_attack_left1", gp.tileSize * 2, gp.tileSize);
        attackLeft3 = setUp("/player/attack/player_attack_left2", gp.tileSize * 2, gp.tileSize);
        attackLeft4 = setUp("/player/attack/player_attack_left3", gp.tileSize * 2, gp.tileSize);
        attackLeft5 = setUp("/player/attack/player_attack_left4", gp.tileSize * 2, gp.tileSize);

        attackRight1 = setUp("/player/attack/player_attack_right0", gp.tileSize * 2, gp.tileSize);
        attackRight2 = setUp("/player/attack/player_attack_right1", gp.tileSize * 2, gp.tileSize);
        attackRight3 = setUp("/player/attack/player_attack_right2", gp.tileSize * 2, gp.tileSize);
        attackRight4 = setUp("/player/attack/player_attack_right3", gp.tileSize * 2, gp.tileSize);
        attackRight5 = setUp("/player/attack/player_attack_right4", gp.tileSize * 2, gp.tileSize);

    }


    public void update() {
        boolean moving = false;

        if (attacking) {
            attacking();
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
                int monsterIndex = gp.cCheck.checkEntity(this, gp.monster);
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
                        if (spriteNumber > 8) {
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
            if (invincible == true) {
                invincibleCounter++;
                if (invincibleCounter > 120) {
                    invincible = false;
                    invincibleCounter = 0;
                }
            }
        }
    }

    public void attacking() {
        spriteCounter++;

        if(spriteCounter <= 5) {
            spriteNumber = 1;
        }

        if(spriteCounter > 5 && spriteCounter <= 25) {
            spriteNumber = 2;
        }

        if(spriteCounter > 25 && spriteCounter <= 35) {
            spriteNumber = 3;


            //curren save
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int hitBoxWidth = hitBox.width;
            int hitBoxHeight = hitBox.height;


            //spieler anpassen
            switch (direction) {
                case "up": worldY -= hitBoxHeight; break;
                case "down": worldY += hitBoxHeight; break;
                case "left": worldX -= hitBoxWidth; break;
                case "right": worldX += hitBoxWidth; break;

            }

            hitBox.width = attackHitBox.width;
            hitBox.height = attackHitBox.height;
            // gegner Check
            int monsterIndex = gp.cCheck.checkEntity(this, gp.monster);
            damageMonster(monsterIndex);

            worldX = currentWorldX;
            worldY = currentWorldY;
            hitBox.width = hitBoxWidth;
            hitBox.height = hitBoxHeight;
        }

        if(spriteCounter > 35 && spriteCounter <= 45) {
            spriteNumber = 4;
        }

        if(spriteCounter > 45) {
            spriteNumber = 5;
            spriteCounter = 0;
            attacking = false;
        }
    }



    public void pickup(int i) {
        if (i != 999) {

        }
    }

    public void interactNPC(int i) {
        if (i != 999) {
            if (keyH.enterPressed == true) {
                gp.gameState = gp.dialogState;
                gp.npc[i].speak();
            }
        }
    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (invincible == false) {
                health -= 1;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i) {
        if(i != 999) {

            if(gp.monster[i].invincible == false) {

                gp.monster[i].health -= 1;
                gp.monster[i].invincible = true;

                if(gp.monster[i].health <= 0) {
                    gp.monster[i].dying = true;
                }
            }
        }
    }

    public void draw(Graphics2D g2) {

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        BufferedImage image = null;

        if (attacking) {
            switch (direction) {
                case "up":
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNumber == 1) {
                        image = attackUp1;
                    }
                    if (spriteNumber == 2) {
                        image = attackUp2;
                    }
                    if (spriteNumber == 3) {
                        image = attackUp3;
                    }
                    if (spriteNumber == 4) {
                        image = attackUp4;
                    }
                    if (spriteNumber == 5) {
                        image = attackUp5;
                    }
                    break;
                case "down":
                    if (spriteNumber == 1) {
                        image = attackDown1;
                    }
                    if (spriteNumber == 2) {
                        image = attackDown2;
                    }
                    if (spriteNumber == 3) {
                        image = attackDown3;
                    }
                    if (spriteNumber == 4) {
                        image = attackDown4;
                    }
                    if (spriteNumber == 5) {
                        image = attackDown5;
                    }
                    break;
                case "left":
                    tempScreenX = screenX - gp.tileSize;
                    if (spriteNumber == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNumber == 2) {
                        image = attackLeft2;
                    }
                    if (spriteNumber == 3) {
                        image = attackLeft3;
                    }
                    if (spriteNumber == 4) {
                        image = attackLeft4;
                    }
                    if (spriteNumber == 5) {
                        image = attackLeft5;
                    }
                    break;
                case "right":
                    if (spriteNumber == 1) {
                        image = attackRight1;
                    }
                    if (spriteNumber == 2) {
                        image = attackRight2;
                    }
                    if (spriteNumber == 3) {
                        image = attackRight3;
                    }
                    if (spriteNumber == 4) {
                        image = attackRight4;
                    }
                    if (spriteNumber == 5) {
                        image = attackRight5;
                    }
                    break;
            }
        } else {
            switch (direction) {
                case "up":
                    if (spriteNumber == 1) {
                        image = up1;
                    }
                    if (spriteNumber == 2) {
                        image = up2;
                    }
                    if (spriteNumber == 3) {
                        image = up3;
                    }
                    if (spriteNumber == 4) {
                        image = up4;
                    }
                    if (spriteNumber == 5) {
                        image = up5;
                    }
                    if (spriteNumber == 6) {
                        image = up6;
                    }
                    if (spriteNumber == 7) {
                        image = up7;
                    }
                    if (spriteNumber == 8) {
                        image = up8;
                    }
                    break;
                case "down":
                    if (spriteNumber == 1) {
                        image = down1;
                    }
                    if (spriteNumber == 2) {
                        image = down2;
                    }
                    if (spriteNumber == 3) {
                        image = down3;
                    }
                    if (spriteNumber == 4) {
                        image = down4;
                    }
                    if (spriteNumber == 5) {
                        image = down5;
                    }
                    if (spriteNumber == 6) {
                        image = down6;
                    }
                    if (spriteNumber == 7) {
                        image = down7;
                    }
                    if (spriteNumber == 8) {
                        image = down8;
                    }
                    break;
                case "left":
                    if (spriteNumber == 1) {
                        image = left1;
                    }
                    if (spriteNumber == 2) {
                        image = left2;
                    }
                    if (spriteNumber == 3) {
                        image = left3;
                    }
                    if (spriteNumber == 4) {
                        image = left4;
                    }
                    if (spriteNumber == 5) {
                        image = left5;
                    }
                    if (spriteNumber == 6) {
                        image = left6;
                    }
                    if (spriteNumber == 7) {
                        image = left7;
                    }
                    if (spriteNumber == 8) {
                        image = left8;
                    }
                    break;
                case "right":
                    if (spriteNumber == 1) {
                        image = right1;
                    }
                    if (spriteNumber == 2) {
                        image = right2;
                    }
                    if (spriteNumber == 3) {
                        image = right3;
                    }
                    if (spriteNumber == 4) {
                        image = right4;
                    }
                    if (spriteNumber == 5) {
                        image = right5;
                    }
                    if (spriteNumber == 6) {
                        image = right6;
                    }
                    if (spriteNumber == 7) {
                        image = right7;
                    }
                    if (spriteNumber == 8) {
                        image = right8;
                    }
                    break;
            }
        }
        if (invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY,null);

        //Reset Alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //hitbox anzeige
        /*g2.drawRect(screenX + hitBox.x, screenY + hitBox.y, hitBox.width, hitBox.height);*/
    }
}
