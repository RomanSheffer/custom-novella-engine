package novella.service;

import javafx.application.Platform;
import novella.exceptions.WorkException;
import novella.model.Level;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LevelBuilderTest {

    LevelBuilder levelBuilder;
    GameDataLoader gameDataLoader;

    @BeforeAll
    static void initJFX() {
        try {

            Platform.startup(() -> {
            });
        } catch (IllegalStateException e) {
        }
    }

    @BeforeEach
    void startUp() {
        levelBuilder = new LevelBuilder();
        gameDataLoader = Mockito.mock(GameDataLoader.class);
        Mockito.when(gameDataLoader.loadLevelFromJson(Mockito.anyString())).thenReturn(new Level());
    }

    @Test
    @DisplayName("Успешный подсчет файлов и сборка массива уровней")
    void loadCorrectLevelsArray(@TempDir Path tempDir) throws IOException {

        //arrange
        Files.createFile(tempDir.resolve("1.json"));
        Files.createFile(tempDir.resolve("2.json"));
        String pathToTempFolder = tempDir.toAbsolutePath().toString();
        //act
        List<Level> testingArrayOfLevels = levelBuilder.buildLevelsArray(gameDataLoader, pathToTempFolder);
        //assert
        assertEquals(4, testingArrayOfLevels.size(), "два тестовых уровня + 2 вшитых, итого 4");
    }

    @Test
    @DisplayName("если есть уровни с ошибкой в json, загружаем остальные кроме них")
    void ifHasLevelWithJsonError_thenLoadAllWithoutTheseLevels(@TempDir Path tempDir) throws Exception {

        //arrange
        Files.createFile(tempDir.resolve("1.json"));
        Files.createFile(tempDir.resolve("2.json"));
        String pathToTempFolder = tempDir.toAbsolutePath().toString();

        String expectedPathForFirstLevel = pathToTempFolder + 1;
        String expectedPathForSecondLevel = pathToTempFolder + 2;

        Mockito.when(gameDataLoader.loadLevelFromJson(expectedPathForFirstLevel))
                .thenThrow(new WorkException("битый синтаксис json", new RuntimeException()));

        Mockito.when(gameDataLoader.loadLevelFromJson(expectedPathForSecondLevel))
                .thenReturn(new Level());

        //act
        List<Level> testingArrayOfLevels = levelBuilder.buildLevelsArray(gameDataLoader, pathToTempFolder);
        //assert
        assertEquals(3, testingArrayOfLevels.size(), "В списке должно быть 3 уровня ");
    }


}
