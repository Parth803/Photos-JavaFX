package stages.viewphoto.main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import photos.Photos;

public class Controller {
    @FXML
    private Button back;
    @FXML
    private Button logout;

    public void initialize() {
        this.back.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/photoslist/photoslist.fxml"));
        this.logout.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/main/main.fxml"));
    }
}
