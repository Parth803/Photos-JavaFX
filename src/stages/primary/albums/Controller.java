package stages.primary.albums;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import model.Album;
import model.Model;
import model.Photo;
import photos.Photos;

import java.text.SimpleDateFormat;

public class Controller {
    @FXML
    private Button back;
    @FXML
    private Button logout;
    @FXML
    private TextField searchField;
    @FXML
    private Button search;
    @FXML
    private TilePane albumsPane;
    @FXML
    private Button delete;
    @FXML
    private Button promptAdd;
    @FXML
    private Text nameOfAlbum;
    @FXML
    private Text numPhotos;
    @FXML
    private Text dateRange;
    @FXML
    private Button openAlbum;
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

        // ADD TilePane STUFF SO IT CAN CALL updateDetailDisplay when selecting a tile

        this.back.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/main/main.fxml"));
        this.logout.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/main/main.fxml"));
        this.search.setOnAction(actionEvent -> searchPhotos());
        this.delete.setOnAction(actionEvent -> deleteAlbum());
        this.promptAdd.setOnAction(actionEvent -> promptAdd());
        this.openAlbum.setOnAction(actionEvent -> openAlbum());
        this.sendAdd.setOnAction(actionEvent -> addAlbum());
    }

    public void updateDetailDisplay() {
        // NEEDS TO GET SELECTED ALBUM AND DISPLAY
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        this.nameOfAlbum.setText(""); // album.name
        this.numPhotos.setText(""); // album.photos.size()
        this.dateRange.setText(""); // formatter.format(album.start) + " to " + formatter.format(album.end);
    }

    public void searchPhotos() {
        Model.userData = searchField.getText();
        Photos.changeScene("primary", "/stages/primary/search/search.fxml");
    }

    public void deleteAlbum() {
//        this.albumsPane.getChildren()
        System.out.println("Incomplete");
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

    public void openAlbum() {
        Photos.changeScene("primary", "/stages/primary/photoslist/photoslist.fxml");
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
