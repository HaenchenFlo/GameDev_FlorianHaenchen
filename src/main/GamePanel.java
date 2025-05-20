package main;

import Entities.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    //funktioniert als GameScreen
    //Screen Einstellungen
    final int originalTileSize = 32; // 32 x 32 tile änderbar für mehr optionen
    final int skalierung = 3;

    public final int tileSize = originalTileSize * skalierung; //96x96 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 10;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //World Settings
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //FPS
    int FPS = 120;

    //System
    TileManager tileM = new TileManager(this);
    KeyboardHandler keyH = new KeyboardHandler();
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionCheck cCheck = new CollisionCheck(this);
    public ObjectHandler oSetter = new ObjectHandler(this);
    Thread gameThread;

    //Entity und Objecte
    public Player player = new Player(this, keyH);
    public SuperObject[] obj = new SuperObject[10];

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.gray);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame() {
        oSetter.setObject();

        /*playMusic(0);*/ //Einschalten wenn musik für hintergrund

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


        //Tile
        tileM.draw(g2);

        //Object
        for(int i = 0; i < obj.length; i++) {
            if(obj[i] != null) { //gegen Nullpointer
                obj[i].draw(g2, this);
            }
        }

        //Tile
        player.draw(g2);

        g2.dispose();
    }

    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();

    }

    public void stopMusic() {

        music.stop();

    }

    public void soundEffect(int i) {

        se.setFile(i);
        se.play();

    }
}