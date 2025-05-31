package main;

import Entities.Entity;
import Entities.Player;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.util.*;

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
    public int FPS = 120;

    //System
    TileManager tileM = new TileManager(this);
    public KeyboardHandler keyH = new KeyboardHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();
    public CollisionCheck cCheck = new CollisionCheck(this);
    public ObjectHandler oSetter = new ObjectHandler(this);
    public EventHandler eHandler = new EventHandler(this);
    public MouseHandler mouseH = new MouseHandler(this);

    //UI
    public UI ui = new UI(this);
    Thread gameThread;

    //Entity und Objecte
    public Player player = new Player(this, keyH);
    public Entity[] obj = new Entity[10];// erhöhen für mehr obj
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    //Game State

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogState = 3;
    public final int characterState = 4;



    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.addMouseListener(mouseH);
        this.addMouseListener(mouseH);
    }

    public void setUpGame() {
        oSetter.setObject();
        oSetter.setNpc();
        oSetter.setMonster();
        /*playMusic(0); //Einschalten wenn musik für hintergrund*/
        gameState = titleState;

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

        if(gameState == playState) {
            player.update();

            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    npc[i].update();
                }
            }
            for(int i = 0; i < monster.length; i++) {
                if(monster[i] != null) {
                    if(monster[i].alive == true && monster[i].dying == false) {
                        monster[i].update();
                    }
                    if(monster[i].alive == false) {
                        monster[i] = null;
                    }
                }
            }
        }

        if(gameState == pauseState) {
            //pausieren
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        //debug
        long drawStart = System.nanoTime();

        //Hauptemenü
        if(gameState == titleState) {
            ui.draw(g2);
        }
        else {

            //Tile
            tileM.draw(g2);

            entityList.add(player);

            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }

            for(int i = 0; i < obj.length; i++) {
                if(obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }

            for(int i = 0; i < monster.length; i++) {
                if(monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }

            entityList.sort(new Comparator<Entity>() {
                @Override
                public int compare(Entity o1, Entity o2) {
                    return Integer.compare(o1.worldY, o2.worldY);
                }
            });


            //Entity draw
            for(int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }

            entityList.clear();

            //UI
            ui.draw(g2);
        }

        if(keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Drawtime: " + passed, 20, 400);
            System.out.println(passed);
        }
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