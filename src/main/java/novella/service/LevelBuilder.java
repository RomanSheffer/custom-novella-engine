package novella.service;

import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;
import novella.exceptions.WorkException;
import novella.model.Level;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
public class LevelBuilder {

    String startLevelImg = "/images/logo.jpg";

    List<String> brokenLevels = new ArrayList<>();

    public List<Level> buildLevelsArray(GameDataLoader gameDataLoader, String pathToFile) {

        Level startLevel = new Level();
        startLevel.setLevelId(0);
        String startLevelData = "========UI движок для ваших кастомных новеллок=======";

        startLevel.setLevelText(startLevelData);
        startLevel.setImagePath( new Image(startLevelImg));

        startLevel.setMusicPath("snds/main-theme-music.wav");
        startLevel.setSoundPath("snds/button-sound.wav");

        Level infoLevel = new Level();
        infoLevel.setLevelText("""
                Информация о проекте:
                CNEngine
                Автор : Roman Sheffer
                
                p.s. Данный движок не является дизайн-проектом, а я не являюсь дизайнером, 
                его главная цель -
                дать вам возможность создать свою собственную историю и обмениваться ими 
                """);
        infoLevel.setImagePath( new Image(startLevelImg));
        infoLevel.setButtonsMap(Map.of("на главную!", 0));
        infoLevel.setSoundPath("src/main/resources/sounds/button-sound.wav");

        List<Level> levelList = new ArrayList<>();
        levelList.add(startLevel);

        try {
            log.info("собираем массив уровней");

            long fileCounter = Files.list(Paths.get(pathToFile))
                    .filter(file -> !Files.isDirectory(file))
                    .filter(file -> file.toString().endsWith(".json"))
                    .count();

            for (int i = 1; i <= fileCounter; i++) {

                try {
                    Level level = gameDataLoader.loadLevelFromJson(pathToFile + i);
                    levelList.add(level);
                }catch (WorkException e){
                    log.error("не удалось загрузить уровень {}", i);
                    brokenLevels.add("ошибка загрузки уровня" + i +" детали: " + e.getMessage());
                }
            }
            if(!brokenLevels.isEmpty()){
                startLevel.setLevelText(startLevelData +"\n"+ "\n" + brokenLevels.toString());
            }
            //добавляем здесь,чтобы четко попасть уровнем - инструкцией в конец списка
            infoLevel.setLevelId(levelList.size());
            int indexOfInfoLevel = infoLevel.getLevelId();
            startLevel.setButtonsMap(Map.of("Поехали!", 1, "Информация о проекте", indexOfInfoLevel));
            levelList.add(infoLevel);

            log.info("массив уровней собран, уровней: {} ", levelList.size());
            return levelList;

        } catch (IOException e) {

            log.error("ошибка сборки массива уровней", e);
            return null;
        }
    }
}
