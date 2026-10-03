package novella;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import novella.controller.NovellaController;
import novella.service.GameDataLoader;
import novella.service.LevelBuilder;
import novella.service.SoundPlayer;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        SoundPlayer soundPlayer = new SoundPlayer();
        SoundPlayer musicPlayer = new SoundPlayer();
        LevelBuilder levelBuilder = new LevelBuilder();
        GameDataLoader gameDataLoader = new GameDataLoader();

        NovellaController novellaController = new NovellaController(gameDataLoader, levelBuilder,
                soundPlayer, musicPlayer);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/novella_ui.fxml"));
        loader.setControllerFactory(param -> novellaController);

        Scene scene = new Scene(loader.load(),800,600);
        primaryStage.setTitle("Кастомный движок для новеллок");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
