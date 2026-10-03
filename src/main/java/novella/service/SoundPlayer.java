package novella.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

@Slf4j
public class SoundPlayer {


    @Getter
    private Clip backstageMusic;
    @Getter
    private Clip soundClip;

    public void playSound(String pathToFile){

        if(pathToFile == null){
            return ;
        }
        try {
            if(soundClip != null){
                soundClip.close();
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(pathToFile));
            soundClip = AudioSystem.getClip();
            soundClip.open(audioInputStream);
            soundClip.start();

        }catch (Exception e){
            log.error("ошибка воспроизведения звука", e);
        }
    }

    public void playMusic(String pathToFile){

        if(pathToFile == null){
            return ;
        }
        try {
            if(backstageMusic != null){
                backstageMusic.close();
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(pathToFile));
            backstageMusic= AudioSystem.getClip();
            backstageMusic.open(audioInputStream);
            backstageMusic.loop(Clip.LOOP_CONTINUOUSLY);

        }catch (Exception e){
            log.error("ошибка воспроизведения музыки", e);
        }
    }
}
