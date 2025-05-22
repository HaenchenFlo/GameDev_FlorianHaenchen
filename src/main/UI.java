package main;


import java.awt.*;

public class UI {

    GamePanel gp;
    Font arial_40, arial_80B;
    Graphics2D g2;
    public boolean messageOn = false;
    public String message = "";
    public String currentDialog = "";

    public UI(GamePanel gp) {
        this.gp = gp;
        arial_40 = new Font("Arial", Font.PLAIN, 40);
        arial_80B = new Font("Arial", Font.BOLD, 80);
    }

    public void showMessage(String s) {

        message = s;
        messageOn = true;
    }

    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(arial_40);
        g2.setColor(Color.YELLOW);

        if(gp.gameState == gp.playState) {

        }

        if(gp.gameState == gp.pauseState) {
            drawPause();
        }

        if(gp.gameState == gp.dialogState) {
            drawDialogScreen();
        }
    }

    public void drawPause() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,160f));
        String text = "Pause";
        int x = centerText(text);
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

    public void drawSubWindow(int x, int y, int width, int height) {

        Color c = new Color(0,0,0,220);
        g2.setColor(c);
        g2.fillRoundRect(x,y,width,height,35,35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5,y+5, width-10, height-10, 25, 25);
    }

    public int centerText(String text) {

        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;

    }
}
