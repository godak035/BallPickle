/**
 * Sound.java
 * @author Avishan
 * 2024/01/23
 * Allows game to play and stop background music
 */

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

    // Variables
    private Clip music, soundEffect;
    private URL soundURL[] = new URL[30];
    
    // Constructor
    public Sound() {
        //Sound collection
        soundURL[0] = getClass().getResource("Sound/MainMenu.wav");
        soundURL[1] = getClass().getResource("Sound/levelOdd.wav");
        soundURL[2] = getClass().getResource("Sound/levelEven.wav");
        soundURL[3] = getClass().getResource("Sound/lastLevel.wav");
        soundURL[4] = getClass().getResource("Sound/soundEffect.wav");
    }
    
    /**
     * Setting which audio clip to play
     * @param i : the audio clip to play (1 or 0), as per above
     */
    public void setFileM(int i) {
        //Try Catch Statement to keep the music running
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            music = AudioSystem.getClip();
            music.open(ais);
        } catch(Exception e) {
            System.out.print(e);
        }
    }
    
    public void setFileS(int i) {
        //Try Catch Statement to keep the music running
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            soundEffect = AudioSystem.getClip();
            soundEffect.open(ais);
        } catch(Exception e) {
            System.out.print(e);
        }
    }
    
    /**
     * Called to play music
     */
    public void play() { music.start(); }

    /**
     * Called to play the sound effect
     */
    public void playSoundEffect() { soundEffect.start(); }

    /**
     * Called to loop the music forever
     */
    public void loop() { music.loop(Clip.LOOP_CONTINUOUSLY); }

    /**
     * Called to stop music
     */
    public void stop() { music.stop(); }
}