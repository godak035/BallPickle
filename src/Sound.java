/**
 * Sound.java
 * @author Avishan
 * 2024/01/23
 * Allows game to play and stop background music
 */

 import java.io.File;
 import java.net.URL;
 import javax.sound.sampled.AudioInputStream;
 import javax.sound.sampled.AudioSystem;
 import javax.sound.sampled.Clip;
 
 public class Sound {
 
     // Variables
     Clip clip;
     URL soundURL[] = new URL[30];
     File file1;
     
     // Constructor
     public Sound() {
         
         //Sound collection
         soundURL[0] = getClass().getResource("Sound/MainMenu.wav");
         soundURL[1] = getClass().getResource("Sound/MainMenu.wav");
         soundURL[2] = getClass().getResource("Sound/MainMenu.wav");
         soundURL[3] = getClass().getResource("Sound/MainMenu.wav");
     }
     
     /**
      * Setting which audio clip to play
      * @param i : the audio clip to play (1 or 0), as per above
      */
     public void setFile(int i) {
 
         //Try Catch Statement to keep the music running
         try {
             AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
             clip = AudioSystem.getClip();
             clip.open(ais);
             
         }
         //Catches Errors
         catch(Exception e) {
             System.out.print(e);
         }
         
     }
     
     /**
      * Called to play music
      */
     public void play() {
         clip.start();
     }
 
     /**
      * Called to loop the music forever
      */
     public void loop() {
         clip.loop(Clip.LOOP_CONTINUOUSLY);
         
     }
 
     /**
      * Called to stop music
      */
     public void stop() {
         clip.stop();
         
     }


 }
 