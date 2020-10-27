import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * This class is used to read and play wav format music files for music and sound effects.
 */
public abstract class PlayMusic {

    /**
     * The only method needed just opens up a given file and plays it beginning to end.  If it is the background music,
     * loops 1000 times(Nobody is going to play this for more than 60 hours.  Can change later if necessary.)
     * @param file - .wav file to be played
     */
    public static void play(File file) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            if(file == Assets.backgroundMusic){
                clip.loop(1000);
            }else{
                clip.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
