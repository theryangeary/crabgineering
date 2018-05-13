package view.estuaryenums; /**
 * Code from:
 * https://www.ntu.edu.sg/home/ehchua/programming/java/J8c_PlayingSound.html
 */

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * This enum encapsulates all the sound effects of a game, so as to separate the sound playing
 * codes from the game codes.
 * 1. Define all your sound effect names and the associated wave file.
 * 2. To play a specific sound, simply invoke EstuarySound.SOUND_NAME.play().
 * 3. You might optionally invoke the static method EstuarySound.init() to pre-load all the
 *    sound files, so that the play is not paused while loading the file for the first time.
 * 4. You can use the static variable EstuarySound.volume to mute the sound.
 */
public enum EstuarySound {
    HIT_GROUND("hit_ground.wav"),
    POINTS("points.wav"),
    THROW("throw.wav"),
    TRASH_HIT("trash_hit.wav"),
    TRASH_WRONG("wrong.wav"),
    GET_TRASH("get_trash.wav"),
    BOUNCE("bounce.wav");

    /**
     * Nested class for specifying volume
      */
    public enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.LOW;

    private final String SOUND_DIR = "resources/soundeffects/";

    // Each sound effect has its own clip, loaded with its own sound file.
    private Clip clip;

    /**
     * Constructor to construct each element of the enum with its own sound file.
      */
    EstuarySound(String soundFileName) {
        try {
            // Use URL (instead of File) to read from disk and JAR.
            //URL url = this.getClass().getClassLoader().getResource(soundFileName);
            URL url = this.getClass().getClassLoader().getResource(SOUND_DIR +soundFileName);

            //this.getClass().getClassLoader().getR
            // Set up an soundeffects input stream piped from the sound file.
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            // Get a clip resource.
            clip = AudioSystem.getClip();
            // Open soundeffects clip and load samples from the soundeffects input stream.
            clip.open(audioInputStream);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    /**
     * Play or Re-play the sound effect from the beginning, by rewinding.
     */
    public void play() {
        if (volume != Volume.MUTE) {
            if (clip.isRunning())
                clip.stop();   // Stop the player if it is still running
            clip.setFramePosition(0); // rewind to the beginning
            clip.start();     // Start playing
        }
    }

    /**
     * Optional static method to pre-load all the sound files.
     */
    public static void init() {
        values(); // calls the constructor for all the elements
    }
}