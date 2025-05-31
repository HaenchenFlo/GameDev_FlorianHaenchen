package main;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseHandler implements MouseListener, MouseMotionListener {

    GamePanel gp;

    public int mouseX, mouseY;
    public boolean mousePressed = false;

    public MouseHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Optional: z.B. Angriff starten
        System.out.println("Maus geklickt an: " + e.getX() + ", " + e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Optional
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Optional
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }
}
