package main;


import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class UI {

    GamePanel gp;
    Font joyStix;
    Graphics2D g2;
    public boolean messageOn = false;
    public String message = "";
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
    }

    public void showMessage(String s) {

        message = s;
        messageOn = true;
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

        }

        if(gp.gameState == gp.pauseState) {
            drawPause();
        }

        if(gp.gameState == gp.dialogState) {
            drawDialogScreen();
        }
    }

    public void drawTitle() {

        g2.setColor(new Color(20, 20, 56));
        g2.fillRect(0,0, gp.screenWidth, gp.screenHeight);

        //titel
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,96f));
        String text = "2D Spiel Florian";
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
}
