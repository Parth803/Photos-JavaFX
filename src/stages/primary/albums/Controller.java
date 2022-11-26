package stages.primary.albums;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import model.Album;
import model.Model;
import photos.Photos;

public class Controller {
    @FXML
    private Button back;
    @FXML
    private Button logout;
    @FXML
    private TilePane albumsPane;
    @FXML
    private Button promptAdd;
    @FXML
    private Text newAlbumLabel;
    @FXML
    private TextField albumName;
    @FXML
    private Text warning;
    @FXML
    private Button sendAdd;

    public void initialize() {
        sendAdd.setDisable(true);
        albumName.setDisable(true);
        this.back.setOnAction(actionEvent -> Photos.logOut());
        this.logout.setOnAction(actionEvent -> Photos.logOut());
        this.promptAdd.setOnAction(actionEvent -> promptAdd());
        this.sendAdd.setOnAction(actionEvent -> addAlbum());

        for (Album i: Model.currentUser.albums) {
            ImageView imageView = new ImageView(new Image(i.photos.get(0).path));
            this.albumsPane.getChildren().add(imageView);
        }

    }

    public void promptAdd() {
        if (promptAdd.getText().equals("Add")) {
            promptAdd.setText("Close");
            sendAdd.setDisable(false);
            newAlbumLabel.setOpacity(1);
            albumName.setOpacity(1);
            albumName.setDisable(false);
            sendAdd.setOpacity(1);
        } else {
            promptAdd.setText("Add");
            sendAdd.setDisable(true);
            newAlbumLabel.setOpacity(0);
            albumName.setOpacity(0);
            albumName.setDisable(true);
            sendAdd.setOpacity(0);
        }
    }

    public void addAlbum() {
        if (albumName.getText().isEmpty()) {
            return;
        }
        try {
            Model.currentUser.createAlbum(albumName.getText());
            promptAdd.setText("Add");
            newAlbumLabel.setOpacity(0);
            albumName.clear();
            albumName.setOpacity(0);
            albumName.setDisable(true);
            warning.setOpacity(0);
            sendAdd.setOpacity(0);
            sendAdd.setDisable(true);
        } catch (Exception e) {
            e.printStackTrace();
            warning.setOpacity(0.69);
        }
    }
}
