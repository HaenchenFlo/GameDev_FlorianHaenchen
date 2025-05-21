package main;


import java.awt.*;
import java.text.DecimalFormat;

public class UI {

    GamePanel gp;
    Font comic_40, arial_80B;
    Graphics2D g2;
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
    }

    public void showMessage(String s) {

        message = s;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(comic_40);
        g2.setColor(Color.YELLOW);

        if(gp.gameState == gp.playState) {

        }

        if(gp.gameState == gp.pauseState) {
            drawPause();
        }
    }

    public void drawPause() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,160f));
        String text = "Pause";
        int x = centerText(text);
        int y = gp.screenHeight / 2;

        g2.drawString(text, x,y);
    }

    public int centerText(String text) {

        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;

    }
}
