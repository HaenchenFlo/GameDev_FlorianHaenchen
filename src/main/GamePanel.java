package main;

import main.Entities.Player;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //funktioniert als GameScreen
    //Screen Einstellungen
    final int originalTileSize = 16; // 16 x 16 tile änderbar für mehr optionen
    final int skalierung = 3;

    public final int tileSize = originalTileSize * skalierung; //48x48 tile
    final int maxScreenCol = 16; // 4:3 Bild
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;    //768 x 576 pixel
    final int screenHeight = tileSize * maxScreenRow;

    //FPS
    int FPS = 60;

    KeyboardHandler keyH = new KeyboardHandler();
    Thread gameThread;
    Player player = new Player(this, keyH);

    // Konstruktor und Default Positionen
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.gray);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    public void run() {

        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime -lastTime);
            lastTime = currentTime;

            if(delta >= 1) {

                update();

                repaint();
                delta--;
                drawCount++;
            }
            if(timer >= 1000000000) {
                System.out.println("FPS: " +  drawCount);
                timer = 0;
                drawCount = 0;
            }

        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        player.draw(g2);

        g2.dispose();
    }
}





//Sleep methode statt delta überlegen ??? ist ein @Override
/*public void run() {

        double drawInterval = (double) 1000000000 /FPS; //0.01666 sekunden
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null) {
            //Update: Spieler position updates
            update();

            //Draw: ScreenDrawer
            repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime/1000000;

                if(remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }*/