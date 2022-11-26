package stages.primary.edit;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import photos.Photos;

public class Controller {
    @FXML
    private Button back;
    @FXML
    private Button logout;

    public void initialize() {
        this.back.setOnAction(actionEvent -> Photos.changeScene("/stages/primary/photoslist/photoslist.fxml"));
        this.logout.setOnAction(actionEvent -> Photos.changeScene("/stages/primary/main/main.fxml"));
    }

    public void goBack() {

    }
}
