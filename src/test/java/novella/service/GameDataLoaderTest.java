package novella.service;

import javafx.scene.image.Image;
import novella.exceptions.WorkException;
import novella.model.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameDataLoaderTest {

    GameDataLoader gameDataLoader = new GameDataLoader();
    String correctLevel = "src/main/resources/test-levels/TestLevel";
    String uncorrectLevel = "src/main/resources/test-levels/UncorrectLevel";

    @BeforeEach
            void startUp() {
        gameDataLoader = new GameDataLoader();
    }

    @Test
    @DisplayName("загрузка корректного уровня")
         void ifLevelIsCorrectJson_thenGoodLoading(){
        //arrange
        Map<String, Integer> expectedButtons = Map.of(
                "Осмотреться", 2,
                "Продолжать идти", 3
        );
        //act
       Level testLevel = gameDataLoader.loadLevelFromJson(correctLevel);

              //assert
       assertEquals(1, testLevel.getLevelId());
       assertEquals(expectedButtons, testLevel.getButtonsMap());
       assertEquals("тест", testLevel.getLevelText());

       assertNotNull(testLevel.getSoundPath());
       assertNotNull(testLevel.getMusicPath());

         }

    @Test
    @DisplayName("загрузка некорректного уровня - ошибка ")
    void ifLevelIsUncorrectJson_thenLevelIsNull(){

        //assert
        WorkException exception = assertThrows( WorkException.class, () ->{
            gameDataLoader.loadLevelFromJson(uncorrectLevel);
        } );
    }

}