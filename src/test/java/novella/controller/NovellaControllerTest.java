package novella.controller;

import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.image.ImageView;
import novella.model.Level;
import novella.service.GameDataLoader;
import novella.service.LevelBuilder;
import novella.service.SoundPlayer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.*;

class NovellaControllerTest {

    private NovellaController controller;

    private List<Level> fakeLevels;

    @BeforeAll
    static void initJFX() {
        try {
            Platform.startup(() -> {
            });
        } catch (IllegalStateException e) {
            fail("Критическая ошибка: не удалось инициализировать JavaFX среду", e);
        }
    }

    @BeforeEach
    void setUp() {

         GameDataLoader dataLoaderMock;
         LevelBuilder levelBuilderMock;
         SoundPlayer soundPlayerMock;
         SoundPlayer musicPlayerMock;

        dataLoaderMock = mock(GameDataLoader.class);
        levelBuilderMock = mock(LevelBuilder.class);
        soundPlayerMock = mock(SoundPlayer.class);
        musicPlayerMock = mock(SoundPlayer.class);


        fakeLevels = new ArrayList<>();

        Level lvl0 = new Level();
        lvl0.setLevelId(0);
        lvl0.setLevelText("Начало истории");
        lvl0.setButtonsMap(Map.of("Вперед", 1));

        Level lvl1 = new Level();
        lvl1.setLevelId(1);
        lvl1.setLevelText("Конец демо-версии");
        lvl1.setButtonsMap(Map.of("В начало (Финал)", 999));

        fakeLevels.add(lvl0);
        fakeLevels.add(lvl1);

        when(levelBuilderMock.buildLevelsArray(any(), anyString())).thenReturn(fakeLevels);

        controller = new NovellaController(dataLoaderMock, levelBuilderMock, soundPlayerMock, musicPlayerMock);

        controller.buttonsContainer = new VBox();
        controller.storyText = new Text();
        controller.imageContainer = new ImageView();
    }

    @Test
    @DisplayName("Если кнопка нажата - смена уровня на уровень с указанным levelId ")
    void shouldChangeLevelIdWhenButtonIsPressedAndTextIsNotAnimated() {
        // Arrange
        controller.isAnimated = false;
        Level currentLvl = fakeLevels.getFirst();
        Map.Entry<String, Integer> pressedButton = currentLvl.getButtonsMap().entrySet().iterator().next();

        // Act
        controller.pushGameButton(currentLvl, pressedButton);

        // Assert
        assertEquals(1, controller.currentLevelId, "Контроллер должен переключиться на уровень 1");
    }

    @Test
    @DisplayName(" Если переход на levelId 999 - переход на уровень 0")
    void shouldResetToLevelZeroWhenIdIs999() {

        // Arrange
        controller.isAnimated = false;
        Level currentLvl = fakeLevels.get(1);
        Map.Entry<String, Integer> finalButton = currentLvl.getButtonsMap().entrySet().iterator().next();

        // Act
        controller.pushGameButton(currentLvl, finalButton);

        // Assert
        assertEquals(0, controller.currentLevelId, "При попадании на ID 999 контроллер должен сбросить игру на уровень 0");
    }


}