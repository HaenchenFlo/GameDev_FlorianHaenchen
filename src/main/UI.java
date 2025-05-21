package main;

import object.OBJ_Key;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Font comic_40, arial_80B;
    BufferedImage keyImage;

    public boolean messageOn = false;
    public String message = "";
    int messageZ = 0;
    public boolean gameFinished = false;

    double playTime;
    DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public UI(GamePanel gp) {
        this.gp = gp;
        comic_40 = new Font("Comic Sans MS", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
        OBJ_Key key = new OBJ_Key();
        keyImage = key.image;
    }

    public void showMessage(String s) {

        message = s;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {


        //Game Win
        if(gameFinished == true) {

            String text;
            int textLength;
            int x;
            int y;

            g2.setFont(comic_40);
            g2.setColor(Color.white);
            text = "Du hast dein Spielzeug gefunden!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);


            g2.setFont(arial_80B);
            g2.setColor(Color.MAGENTA);
            text = "KYS ALEC";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize);
            g2.drawString(text, x, y);


            g2.setFont(comic_40);
            g2.setColor(Color.blue);
            text = "Deine Zeit: " + decimalFormat.format(playTime) + "!";
            textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
            x = gp.screenWidth / 2 - textLength / 2;
            y = gp.screenHeight / 2 + (gp.tileSize * 2);
            g2.drawString(text, x, y);

            gp.gameThread = null;

        } else {

            g2.setFont(comic_40);
            g2.setColor(Color.white);
            g2.drawImage(keyImage, gp.tileSize / 4, gp.tileSize / 4, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 100, 100);

            //Spielzeit
            playTime += (double) 1/120;
            g2.drawString("Zeit: " + decimalFormat.format(playTime), gp.tileSize * 14, 100);

            // Nachricht ausgeben und verschwinden lassen
            if (messageOn == true) {

                g2.setFont(g2.getFont().deriveFont(30f));
                g2.drawString(message, gp.tileSize / 4, gp.tileSize * 5);

                messageZ++;


                // 2 sekunden message
                if (messageZ > 2 * gp.FPS) {
                    messageZ = 0;
                    messageOn = false;
                }
            }
        }
    }
}
