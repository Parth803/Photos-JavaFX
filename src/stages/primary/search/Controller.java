package stages.primary.search;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.Model;
import photos.Photos;

public class Controller {
    @FXML
    private Button back;
    @FXML
    private Button logout;

    public void initialize() {
        this.back.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/albums/albums.fxml"));
        this.logout.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/main/main.fxml"));
        String searchQuery = (String) Model.dataTransfer.get(0);
        Model.dataTransfer.clear();
        // parse searchQuery and extract variables
        // pass those variables into one of the methods below

//        Model.currentUser.getPhotosByTags();
//        Model.currentUser.getPhotosInRange();
//        Model.currentUser.getPhotosByTag();
//        Model.currentUser.getPhotosAtTime();
    }

}
