package main;

import javax.swing.*;

public class SpielDrawer {
    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(true);
        window.setTitle("Marcel Stinkt");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        //Spiel Objecte set up
        gamePanel.setUpGame();


        //Game start
        gamePanel.startGameThread();
    }
}
