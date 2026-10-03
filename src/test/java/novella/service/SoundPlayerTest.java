package novella.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.sound.sampled.Clip;

import static org.junit.jupiter.api.Assertions.*;

class SoundPlayerTest {

    SoundPlayer soundPlayer;

    @BeforeEach
    void startUp() {
        soundPlayer = new SoundPlayer();
    }

    @Test
    @DisplayName("Успешная загрузка звука")
    void succsesfullLoadingOfSoundTheme() {

        //arrange
        String pathToSound = "src/main/resources/test-levels/TestLevel.wav";

        //act
        soundPlayer.playSound(pathToSound);
        Clip soundClip = soundPlayer.getSoundClip();

        //assert
        assertNotNull(soundClip);
        assertTrue(soundClip.isOpen());
    }

    @Test
    @DisplayName("Успешная загрузка музыки")
    void succsesfullLoadingOfMusicTheme() {

        //arrange
        String pathToMusic = "src/main/resources/test-levels/TestLeveltheme.wav";

        //act
            soundPlayer.playMusic(pathToMusic);
            Clip musicClip = soundPlayer.getBackstageMusic();

        //assert
        assertNotNull(musicClip);
        assertTrue(musicClip.isOpen());

        }
    }
