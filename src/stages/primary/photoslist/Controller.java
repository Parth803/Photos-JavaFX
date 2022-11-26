package stages.primary.photoslist;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import model.Model;
import photos.Photos;

public class Controller {
    @FXML
    private Button back;
    @FXML
    private Button logout;
//    @FXML
//    private TilePane photosPane;
    @FXML
    private Button delete;
    @FXML
    private Button promptAdd;
    @FXML
    private Text caption;
    @FXML
    private Text dateRange;
    @FXML
    private Button edit;
    @FXML
    private Button display;
    @FXML
    private Text photoPathLabel;
    @FXML
    private TextField photoPath;
    @FXML
    private Text warning;
    @FXML
    private Button sendAdd;
    public void initialize() {
        sendAdd.setDisable(true);
        photoPath.setDisable(true);

        this.back.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/albums/albums.fxml"));
        this.logout.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/main/main.fxml"));
        this.delete.setOnAction(actionEvent -> deletePhoto());
        this.promptAdd.setOnAction(actionEvent -> promptAdd());
        this.edit.setOnAction(actionEvent -> editPhoto());
        this.display.setOnAction(actionEvent -> displayPhoto());
        this.sendAdd.setOnAction(actionEvent -> addPhoto());
    }

    public void deletePhoto() {
        // photosPane delete stuff
        System.out.println("Incomplete");
    }

    public void promptAdd() {
        if (promptAdd.getText().equals("Add")) {
            promptAdd.setText("Close");
            sendAdd.setDisable(false);
            photoPathLabel.setOpacity(1);
            photoPath.setOpacity(1);
            photoPath.setDisable(false);
            sendAdd.setOpacity(1);
        } else {
            promptAdd.setText("Add");
            sendAdd.setDisable(true);
            photoPathLabel.setOpacity(0);
            photoPath.setOpacity(0);
            photoPath.setDisable(true);
            sendAdd.setOpacity(0);
        }
    }

    public void editPhoto() {
        // pass data to next scene to edit the data
        Photos.changeScene("primary", "/stages/primary/edit/edit.fxml");
    }

    public void displayPhoto() {
        // pass current photo and full album for carousel
        Photos.changeScene("viewphoto", "/stages/viewphoto/main/main.fxml");
    }


    public void addPhoto() {
        if (photoPath.getText().isEmpty()) {
            return;
        }

        try {
            // NEED TO GET CURRENT ALBUM THAT IS PASSED FROM ALBUMS SCENE (PERHAPS USING MODEL LIKE THE USERDATA THINGY), CHECK IF PHOTO ALREADY IN ALBUM, AND THEN ADD A PHOTO TO IT
//            Model.currentUser.createAlbum(albumName.getText());
            promptAdd.setText("Add");
            photoPathLabel.setOpacity(0);
            photoPath.clear();
            photoPath.setOpacity(0);
            photoPath.setDisable(true);
            warning.setOpacity(0);
            sendAdd.setOpacity(0);
            sendAdd.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
            warning.setOpacity(0.69);
        }
    }
}
