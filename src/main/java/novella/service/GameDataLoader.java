package novella.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.image.Image;
import lombok.extern.slf4j.Slf4j;
import novella.exceptions.WorkException;
import novella.model.Level;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
public class GameDataLoader {

    private String commonPathToFile;

    public Level loadLevelFromJson(String pathToFile) throws WorkException {

        String levelFormat = ".json";
        commonPathToFile = pathToFile;
        ObjectMapper mapper = new ObjectMapper();

        try {
            log.info("загружаем уровень{} ", pathToFile);

            File jsonLevel = new File(pathToFile + levelFormat);
            Level level = mapper.readValue(jsonLevel, Level.class);

            level.setLevelText(loadTextForLevel());
            level.setImagePath(imageOfLevel());
            level.setSoundPath(loadSoundOfLevel());
            level.setMusicPath(loadMusicOfLevel());

            return level;

        } catch (Exception e) {
            log.error("ошибка чтения json уровня", e);
            throw new WorkException("ошибка в структуре json для уровня", e);
        }
    }

    private String loadTextForLevel() throws WorkException {

        log.info("загружаем текст к уровню");
        String storyFileFormat = ".txt";
        try {

            Path pathToTxt = Paths.get(commonPathToFile + storyFileFormat);
            List<String> linesOfText = Files.readAllLines(pathToTxt);

            return String.join(System.lineSeparator(), linesOfText);

        } catch (Exception e) {
            log.error("не удалось прочитать текст уровня", e);
            throw new WorkException("ошибка загрузки текста", e);
        }
    }

    private Image imageOfLevel() {

        log.info("загружаем картинку к уровню");
        String imageFileFormat = ".jpg";

        try {
            return new Image("file:/" + commonPathToFile + imageFileFormat);
        } catch (Exception e) {
            log.error("не удалось загрузить картинку", e);
            return null;
        }
    }

    private String loadSoundOfLevel() {

        log.info("загружаем звук к уровню");
        String soundFileFormat = ".wav";

        if (!Files.exists(Paths.get(commonPathToFile + soundFileFormat))) {
            return null;
        } else {
            return commonPathToFile + soundFileFormat;
        }
    }

    private String loadMusicOfLevel() {

        log.info("загружаем музыку");
        String musicFileFormat = "theme.wav";

        if (!Files.exists(Paths.get(commonPathToFile + musicFileFormat))) {
            return null;
        }
        return commonPathToFile + musicFileFormat;
    }

}

