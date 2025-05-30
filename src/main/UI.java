package main;


import Entities.Entity;
import object.OBJ_Heart;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class UI {

    GamePanel gp;
    Font joyStix;
    Graphics2D g2;
    BufferedImage healthFull, healthHalf, healthNull;
    public boolean messageOn = false;
    /*public String message = "";*/
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialog = "";
    public int commandNum = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        InputStream is = getClass().getResourceAsStream("/font/joystix.otf");
        try {
            assert is != null;
            joyStix = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            throw new RuntimeException(e);
        }

        //Spieler HUD
        Entity heart = new OBJ_Heart(gp);
        healthFull = heart.image;
        healthHalf = heart.image2;
        healthNull = heart.image3;

    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(joyStix);
        /* g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON); */ //Antialiasing für schönschrift
        g2.setColor(Color.YELLOW);

        if(gp.gameState == gp.titleState) {
            drawTitle();
        }

        if(gp.gameState == gp.playState) {
            drawPlayerHealth();
            drawMessage();
        }

        if(gp.gameState == gp.pauseState) {
            drawPlayerHealth();
            drawPause();
        }

        if(gp.gameState == gp.dialogState) {
            drawPlayerHealth();
            drawDialogScreen();
        }

        if(gp.gameState == gp.characterState) {
            drawCharacterScreen();
        }
    }

    public void drawPlayerHealth() {

        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;


        //Leben 0
        while(i < gp.player.maxHealth/2) {
            g2.drawImage(healthNull,x,y,null);
            i++;
            x += gp.tileSize / 2 + gp.tileSize / 8;
        }

        // Reset
        x = gp.tileSize / 2;
        i = 0;


        //Lebendig
        while(i < gp.player.health) {
            g2.drawImage(healthHalf,x,y,null);
            i++;
            if(i < gp.player.health) {
                g2.drawImage(healthFull,x,y,null);
            }
            i++;
            x += gp.tileSize / 2 + gp.tileSize / 8;
        }


    }

    public void drawMessage() {
        int messageX = gp.tileSize / 2;
        int messageY = gp.tileSize * 3;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,32f));

        for(int i = 0; i < message.size();  i++) {
            if(message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i),messageX+4,messageY+4);
                g2.setColor(Color.white);
                g2.drawString(message.get(i),messageX,messageY);

                int counter = messageCounter.get(i) + 1; // MessageCounter++
                messageCounter.set(i, counter);
                messageY += 64;

                if(messageCounter.get(i) > 240) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawTitle() {

        g2.setColor(new Color(20, 20, 56));
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

        //titel
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96f));
        String text = "Florian 2D-Game";
        int x = centerTextX(text);
        int y = gp.tileSize * 2;

        //Schatten
        g2.setColor(Color.gray);
        g2.drawString(text,x + 10,y + 10);

        //Titeltext
        g2.setColor(Color.white);
        g2.drawString(text,x,y);

        //TitelBild
        x = gp.screenWidth / 2 - gp.tileSize/2;
        y += gp.tileSize * 2;
        try {
            g2.drawImage(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/title/alec.png"))),x,y,gp.tileSize,gp.tileSize,null);
        } catch (Exception _) {}

        //Menu

        g2.setFont(g2.getFont().deriveFont(Font.BOLD,48f));

        text = "NEW GAME";
        x = centerTextX(text);
        y += gp.tileSize * 2;
        g2.drawString(text,x,y);
        if(commandNum == 0) {
            g2.drawString(">",x- gp.tileSize / 2,y);
        }

        text = "LOAD";
        x = centerTextX(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if(commandNum == 1) {
            g2.drawString(">",x- gp.tileSize / 2,y);
        }

        text = "QUIT";
        x = centerTextX(text);
        y += gp.tileSize;
        g2.drawString(text,x,y);
        if(commandNum == 2) {
            g2.drawString(">",x- gp.tileSize / 2,y);
        }

    }

    public void drawPause() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,160f));
        String text = "Pause";
        int x = centerTextX(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x,y);
    }

    public void drawDialogScreen() {

        //dialog fenster
        int x = gp.tileSize * 2;
        int y = gp.tileSize * 7;
        int width = gp.screenWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 2;

        drawSubWindow(x,y,width,height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,32f));
        x += (gp.tileSize / 2);
        y += gp.tileSize / 2 + gp.tileSize / 4;

        for(String line : currentDialog.split("\n")) {
            g2.drawString(line,x,y);
            y += 40;
        }
    }

    public void drawCharacterScreen() {

        //Subframe
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize / 2;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 9;
        drawSubWindow(frameX,frameY,frameWidth,frameHeight);

        //Text
        g2.setColor(Color.WHITE);
        g2.setFont(g2.getFont().deriveFont(32f));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 68;

        //
        g2.drawString("Level",textX,textY);
        textY += lineHeight;
        g2.drawString("HP",textX,textY);
        textY += lineHeight;
        g2.drawString("Stärke",textX,textY);
        textY += lineHeight;
        g2.drawString("Dex",textX,textY);
        textY += lineHeight;
        g2.drawString("Attack",textX,textY);
        textY += lineHeight;
        g2.drawString("Defense",textX,textY);
        textY += lineHeight;
        g2.drawString("XP",textX,textY);
        textY += lineHeight;
        g2.drawString("New Level",textX,textY);
        textY += lineHeight;
        g2.drawString("Coins",textX,textY);
        textY += lineHeight + 20;
        g2.drawString("Weapon",textX,textY);
        textY += lineHeight + 30;
        g2.drawString("Offhand",textX,textY);

        // Werte
        int tailX = (frameX + frameWidth) - 30;
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = alignRightTextX(value,tailX);
        g2.drawString(value,textX,textY); textY += lineHeight;

        value = String.valueOf(gp.player.health);
        textX = alignRightTextX(value,tailX);
        g2.drawString(value,textX,textY); textY += lineHeight;

        value = String.valueOf(gp.player.strength + " / " + gp.player.maxHealth);
        textX = alignRightTextX(value,tailX);
        g2.drawString(value,textX,textY); textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = alignRightTextX(value,tailX);
        g2.drawString(value,textX,textY); textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = alignRightTextX(value,tailX);
        g2.drawString(value,textX,textY); textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = alignRightTextX(value,tailX);
        g2.drawString(value,textX,textY); textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = alignRightTextX(value,tailX);
        g2.drawString(value,textX,textY); textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = alignRightTextX(value,tailX);
        g2.drawString(value,textX,textY); textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = alignRightTextX(value,tailX);
        g2.drawString(value,textX,textY); textY += lineHeight;

        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize / 2 - gp.tileSize / 4,textY - 45, null);
        textY += gp.tileSize;
        g2.drawImage(gp.player.offHand.down1, tailX - gp.tileSize / 2 - gp.tileSize / 4,textY - 45, null);
    }

    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0,0,0,220);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5, width-10, height-10, 25, 25);
    }

    public int centerTextX(String text) {

        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;

    }

    public int alignRightTextX(String text, int tailX) {
        int length = (int) g2.getFontMetrics().getStringBounds(text,g2).getWidth();
        int x = tailX - length;
        return x;
    }
}
