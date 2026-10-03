package novella.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javafx.scene.image.Image;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class Level {

    int levelId;

    Map<String, Integer> buttonsMap;

    @JsonIgnore
    String levelText;

    @JsonIgnore
    Image imagePath;

    @JsonIgnore
    String soundPath;

    @JsonIgnore
    String musicPath;

}
