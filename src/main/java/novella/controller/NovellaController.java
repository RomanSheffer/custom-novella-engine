package novella.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import novella.model.Level;
import novella.service.GameDataLoader;

import novella.service.LevelBuilder;
import novella.service.SoundPlayer;


import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Slf4j
public class NovellaController {

    @FXML
    VBox buttonsContainer;
    @FXML
    ImageView imageContainer;
    @FXML
    Text storyText;

    private Timeline timeline;
    boolean isAnimated = false;
    int currentLevelId = 0;

    private final SoundPlayer soundPlayer;
    private final SoundPlayer musicPlayer;

    private final List<Level> listOfLevels;


    public NovellaController(GameDataLoader gameDataLoader, LevelBuilder levelBuilder,
                             SoundPlayer soundPlayer, SoundPlayer musicPlayer) {

        this.soundPlayer = soundPlayer;
        this.musicPlayer = musicPlayer;

        String pathToData = Paths.get(System.getProperty("user.dir"), "levels") + File.separator;

        this.listOfLevels = levelBuilder.buildLevelsArray(gameDataLoader, pathToData);
    }

    @FXML
    public void initialize() {
        drawScene();
    }

    private void drawScene() {

        buttonsContainer.getChildren().clear();
        int idOfLevelToGoBackToStart = 999;

        if (currentLevelId == idOfLevelToGoBackToStart) {
            currentLevelId = 0;
        }

        Level curentLevel = listOfLevels.get(currentLevelId);

        printAnimatedText(curentLevel.getLevelText());
        imageContainer.setImage(curentLevel.getImagePath());
        soundPlayer.playSound(curentLevel.getSoundPath());
        musicPlayer.playMusic(curentLevel.getMusicPath());

        for (var entry : listOfLevels.get(currentLevelId).getButtonsMap().entrySet()) {

            Button button = new Button(entry.getKey());
            button.setMaxHeight(Double.MAX_VALUE);

            button.setOnAction(event ->
                    pushGameButton(curentLevel, entry)
            );

            buttonsContainer.getChildren().add(button);
            log.info("запуск уровня{} ", currentLevelId);
        }
    }

    private void printAnimatedText(String fullText) {

        log.info("запуск анимации теста");

        if (timeline != null) {
            timeline.stop();
        }
        isAnimated = true;
        storyText.setText("");

        timeline = new Timeline();

        for (int i = 0; i <= fullText.length(); i++) {

            final int count = i;
            int animatedTextPrintingTimeMs = 20;

            KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(animatedTextPrintingTimeMs * (i + 1.0)),
                    event -> {
                        storyText.setText(fullText.substring(0, count));
                        if (count == fullText.length()) {
                            isAnimated = false;
                        }
                    }
            );
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }

    void pushGameButton(Level curentLevel, Map.Entry<String, Integer> entry) {
        log.info("кнопка нажата");

        if (isAnimated) {
            timeline.stop();
            storyText.setText(curentLevel.getLevelText());
            isAnimated = false;
        } else {
            this.currentLevelId = entry.getValue();
            drawScene();
        }
    }
}
