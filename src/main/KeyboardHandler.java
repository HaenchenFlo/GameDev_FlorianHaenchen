package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener {
    GamePanel gp;

    public boolean upPressed, downPressed, leftPressed,rightPressed, enterPressed;

    boolean checkDrawTime = false;


    public KeyboardHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyInput = e.getKeyCode();

        //Titel
        if(gp.gameState == gp.titleState) {
            if(keyInput == KeyEvent.VK_W) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if(keyInput == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }

            if(keyInput == KeyEvent.VK_ENTER) {
                switch(gp.ui.commandNum) {
                    case 0: gp.gameState = gp.playState;
                            gp.playMusic(0);
                            break;
                    case 1:
                            break;
                    case 2: System.exit(0);
                            break;
                }
            }
        }

        //player movement
        if(gp.gameState == gp.playState) {
            if(keyInput == KeyEvent.VK_W) {
                upPressed = true;
            }
            if(keyInput == KeyEvent.VK_S) {
                downPressed = true;
            }

            if(keyInput == KeyEvent.VK_A) {
                leftPressed = true;
            }

            if(keyInput == KeyEvent.VK_D) {
                rightPressed = true;
            }

            if(keyInput == KeyEvent.VK_P) {
                gp.gameState = gp.pauseState;
            }
            if(keyInput == KeyEvent.VK_ENTER) {
               enterPressed = true;
            }

        }
        //pause
        else if(gp.gameState == gp.pauseState) {
            if(keyInput == KeyEvent.VK_P) {
                gp.gameState = gp.playState;
            }
        }
        //dialog
        else if(gp.gameState == gp.dialogState) {
                if(keyInput == KeyEvent.VK_ENTER) {
                    gp.gameState = gp.playState;
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {
            upPressed = false;
        }
        if(code == KeyEvent.VK_S) {
            downPressed = false;
        }

        if(code == KeyEvent.VK_A) {
            leftPressed = false;
        }

        if(code == KeyEvent.VK_D) {
            rightPressed = false;
        }

        //debug wie lange zum draw von frames und tiles
        // test1: 60000 - 160000;
        if(code == KeyEvent.VK_T) {
            if(checkDrawTime == false) {
                checkDrawTime = true;
            } else if (checkDrawTime == true) {
                checkDrawTime = false;
            }
        }

    }
}
