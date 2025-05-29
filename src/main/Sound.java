package main;

import javax.sound.sampled.*;
import java.net.URL;


public class Sound {

    Clip clip;
    URL[] soundURL = new URL[30];

    public Sound() {

        soundURL[0] = getClass().getResource("/sound/zelda.wav");
        soundURL[1] = getClass().getResource("/sound/coin.wav");
        soundURL[2] = getClass().getResource("/sound/powerup.wav");
        soundURL[3] = getClass().getResource("/sound/unlock.wav");
        soundURL[4] = getClass().getResource("/sound/fanfare.wav");
        soundURL[5] = getClass().getResource("/sound/enemy/enemyHurt.wav");
        soundURL[6] = getClass().getResource("/sound/player/playerHurt.wav");
        soundURL[7] = getClass().getResource("/sound/items/sword.wav");
        soundURL[8] = getClass().getResource("/sound/enemy/enemyDeath.wav");

    }

    public void setFile(int i) {
        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);

        } catch (Exception _) {

        }
    }

    public void play() {

        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); //sound leise ggf neue methoden zum sound selber einstellen!!!
        gainControl.setValue(-15.0f);
        clip.start();

    }

    public void loop() {

        clip.loop(Clip.LOOP_CONTINUOUSLY);

    }

    public void stop() {

        clip.stop();

    }
}
