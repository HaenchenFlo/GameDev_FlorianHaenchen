package Entities;

import main.GamePanel;
import main.KeyboardHandler;
import object.OBJ_Key;
import object.weapon.OBJ_Shield;
import object.weapon.OBJ_Sword;
import object.weapon.Weapon;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {
    KeyboardHandler keyH;
    BufferedImage up4, up5, up6, up7, up8, down4, down5, down6, down7, down8, left4, left5, left6, left7, left8, right4, right5, right6, right7, right8;

    //Angriff


    public final int screenX;
    public final int screenY;

    /*public int hasKey = 0;*/
    int standCounter = 0;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 30;

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

        setDefaultValues();
        getPlayerImage();
        getPlayerAttack();
        setItems();
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
        level = 1;
        maxHealth = 6;
        health = maxHealth;
        strength = 1;       //stats die mit attack / defense skalieren
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new OBJ_Sword(gp);
        offHand = new OBJ_Shield(gp);
        attack = getAttack();
        defense = getDefense();
    }

    public void setItems() {
        inventory.add(currentWeapon);
        inventory.add(offHand);
        inventory.add(new OBJ_Key(gp));
    }

    public int getAttack() {
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense() {
        return defense = dexterity * offHand.defenseValue;
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

        attackUp1 = setUp("/player/attack/test_up");
        attackDown1 = setUp("/player/attack/test_down");
        attackLeft1 = setUp("/player/attack/test_left");
        attackRight1 = setUp("/player/attack/test_right");

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
                setInvincible();
            }
        }
        if (currentWeapon instanceof Weapon) {
            ((Weapon) currentWeapon).updateEffect();
        }
    }

    public void setInvincible() {
        invincibleCounter++;
        if (invincibleCounter > 120) {
            invincible = false;
            invincibleCounter = 0;
        }
    }

    public void attacking() {
        spriteCounter++;

        if (spriteCounter == 1) {
            if (currentWeapon instanceof Weapon) {
                ((Weapon) currentWeapon).triggerEffekt(worldX, worldY);
            }
            gp.soundEffect(7);
        }
        if (spriteCounter <= 10) {
            spriteNumber = 1;
        } else if (spriteCounter <= 12) {
            spriteNumber = 1;
        } else if(spriteCounter <= 15) {
            spriteNumber = 1; // kein Angriffsbild mehr, zurück zur Idle-Animation
            spriteCounter = 0;
            attacking = false;
        }
            // aktuelle Werte speichern
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int hitBoxWidth = hitBox.width;
            int hitBoxHeight = hitBox.height;

            // attackHitBox anpassen je nach Richtung
            if (direction.equals("up") || direction.equals("down")) {
                attackHitBox.width = gp.tileSize;  // Breiter horizontal
                attackHitBox.height = gp.tileSize;  // Flacher vertikal
            } else if (direction.equals("left") || direction.equals("right")) {
                attackHitBox.width = gp.tileSize;   // Schmal horizontal
                attackHitBox.height = gp.tileSize; // Hoch vertikal
            }

            // Spieler Position und Hitbox anpassen
            switch (direction) {
                case "up":
                    worldX += (hitBox.width - attackHitBox.width) / 2;
                    worldY -= attackHitBox.height;
                    break;
                case "down":
                    worldX += (hitBox.width - attackHitBox.width) / 2;
                    worldY += hitBox.height;
                    break;
                case "left":
                    worldX -= attackHitBox.width;
                    worldY += (hitBox.height - attackHitBox.height) / 2;
                    break;
                case "right":
                    worldX += hitBox.width;
                    worldY += (hitBox.height - attackHitBox.height) / 2;
                    break;
            }

            hitBox.width = attackHitBox.width;
            hitBox.height = attackHitBox.height;

            // Gegnercheck
            ArrayList<Integer> hitMonsters = gp.cCheck.checkEntities(this, gp.monster);
            for (int i : hitMonsters) {
            damageMonster(i);
            }

            // zurücksetzen
            worldX = currentWorldX;
            worldY = currentWorldY;
            hitBox.width = hitBoxWidth;
            hitBox.height = hitBoxHeight;
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
                gp.soundEffect(6);

                int damage = gp.monster[i].attack - defense;
                if(damage < 0) {
                    damage = 0;
                }
                health -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i) {
        if(i != 999) {
            if(gp.monster[i].invincible == false) {
                gp.soundEffect(5);

                int damage = attack - gp.monster[i].defense;
                if(damage < 0) {
                    damage = 0;
                }

                gp.monster[i].health -= damage;
                gp.ui.addMessage(damage + " Schaden!"); // ggf entfernen

                gp.monster[i].invincible = true;
                gp.monster[i].damageReact();

                if(gp.monster[i].health <= 0) {
                    gp.soundEffect(8);
                    gp.monster[i].dying = true;
                    gp.ui.addMessage("test"); //ggf entfernen
                }
            }
        }
    }

    public void draw(Graphics2D g2) {

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        BufferedImage image = null;

        if (attacking) {
            image = switch (direction) {
                case "up" -> attackUp1;
                case "down" -> attackDown1;
                case "left" -> attackLeft1;
                case "right" -> attackRight1;
                default -> image;
            };
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

        if (currentWeapon instanceof Weapon) {
            ((Weapon) currentWeapon).drawEffect(g2);
        }

        //Reset Alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));




        //DEBUG --- Hallo lukas




        //Spieler Hitbox anzeige
        g2.setColor(Color.RED);
        g2.drawRect(screenX + hitBox.x, screenY + hitBox.y, hitBox.width, hitBox.height);

        // Attack-Hitbox (Schlagbereich)
        g2.setColor(Color.BLUE);

        int attackBoxX = hitBox.x;
        int attackBoxY = hitBox.y;

        switch (direction) {
            case "up":
                attackBoxX = hitBox.x + (hitBox.width - attackHitBox.width) / 2;
                attackBoxY = hitBox.y - attackHitBox.height;
                break;
            case "down":
                attackBoxX = hitBox.x + (hitBox.width - attackHitBox.width) / 2;
                attackBoxY = hitBox.y + hitBox.height;
                break;
            case "left":
                attackBoxX = hitBox.x - attackHitBox.width;
                attackBoxY = hitBox.y + (hitBox.height - attackHitBox.height) / 2;
                break;
            case "right":
                attackBoxX = hitBox.x + hitBox.width;
                attackBoxY = hitBox.y + (hitBox.height - attackHitBox.height) / 2;
                break;
        }


        g2.drawRect(screenX + attackBoxX, screenY + attackBoxY, attackHitBox.width, attackHitBox.height);
    }
}
