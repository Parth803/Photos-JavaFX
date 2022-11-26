package stages.primary.search;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import photos.Photos;

public class Controller {
    @FXML
    private Button back;
    @FXML
    private Button logout;

    public void initialize() {
        this.back.setOnAction(actionEvent -> Photos.changeScene("/stages/primary/albums/albums.fxml"));
        this.logout.setOnAction(actionEvent -> Photos.changeScene("/stages/primary/main/main.fxml"));
    }
}
