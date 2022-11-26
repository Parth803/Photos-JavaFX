package stages.primary.albums;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import photos.Photos;

public class Controller {
    @FXML
    private Button back;
    @FXML
    private Button logout;

    public void initialize() {
        this.back.setOnAction(actionEvent -> Photos.logOut());
        this.logout.setOnAction(actionEvent -> Photos.logOut());
    }
}
