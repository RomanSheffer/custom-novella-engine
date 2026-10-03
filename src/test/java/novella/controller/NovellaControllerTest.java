package novella.controller;

import javafx.scene.control.Button;
import novella.model.Level;
import novella.service.GameDataLoader;
import novella.service.LevelBuilder;
import novella.service.SoundPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NovellaControllerTest extends ApplicationTest {

    @Mock
    private GameDataLoader gameDataLoader;
    @Mock
    private LevelBuilder levelBuilder;
    @Mock
    private SoundPlayer soundPlayer;
    @Mock
    private SoundPlayer musicPlayer;

    private  NovellaController novellaController;

    @BeforeEach
    void startUp(){
        novellaController = new NovellaController(gameDataLoader, levelBuilder, soundPlayer, musicPlayer);
    }

    }
