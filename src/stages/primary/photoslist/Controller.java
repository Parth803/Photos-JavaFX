package stages.primary.photoslist;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import model.Album;
import model.Model;
import model.Photo;
import photos.Photos;

import java.text.SimpleDateFormat;

public class Controller {
    @FXML
    private TilePane photosPane;
    @FXML
    private Button back;
    @FXML
    private Button logout;
    @FXML
    private Text albumName;
    @FXML
    private Button delete;
    @FXML
    private Button promptAdd;
    @FXML
    private Text caption;
    @FXML
    private Text dateTaken;
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
    private Album currentAlbum;
    private Photo selectedPhoto;

    public void initialize() {
        sendAdd.setDisable(true);
        photoPath.setDisable(true);
        currentAlbum = (Album) Model.dataTransfer.get(0);
        albumName.setText("Album: " + currentAlbum.name);
        if (!currentAlbum.photos.isEmpty()) {
            selectedPhoto = currentAlbum.photos.get(0);
            updateDetailDisplay();
        }
        createElements();
        this.back.setOnAction(actionEvent -> {
            Model.initPreviousScene();
            Photos.changeScene("primary", "/stages/primary/albums/albums.fxml");
        });
        this.logout.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/main/main.fxml"));
        this.delete.setOnAction(actionEvent -> deletePhoto());
        this.promptAdd.setOnAction(actionEvent -> promptAdd());
        this.edit.setOnAction(actionEvent -> editPhoto());
        this.display.setOnAction(actionEvent -> displayPhoto());
        this.sendAdd.setOnAction(actionEvent -> addPhoto());
    }

    public void createElements() {
        photosPane.getChildren().clear();
        photosPane.setPrefColumns(3);
        photosPane.setHgap(10);
        photosPane.setVgap(10);
        for (Photo p: currentAlbum.photos) {
            photosPane.getChildren().add(createElement(p));
        }
    }

    public VBox createElement(Photo p) {
        ImageView img = new ImageView();
        img.setImage(new Image("file:" + p.path));
        img.setFitWidth(175);
        img.setFitHeight(175);

        VBox element = new VBox();
        element.getChildren().add(img);
        element.setOnMouseClicked(mouseEvent -> {
            selectedPhoto = p;
            updateDetailDisplay();
        });
        return element;
    }

    public void updateDetailDisplay() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        this.caption.setText(selectedPhoto.caption);
        this.dateTaken.setText(formatter.format(selectedPhoto.dateTaken.getTime()));
    }

    public void deletePhoto() {
        try {
            currentAlbum.photos.remove(selectedPhoto);
            selectedPhoto = null;
            this.caption.setText("N/A");
            this.dateTaken.setText("N/A");
            createElements();

        } catch (Exception e) {
            throw new RuntimeException("error deleting selected album");
        }
    }

    public void promptAdd() {
        if (promptAdd.getText().equals("Add")) {
            promptAdd.setText("Close");
            sendAdd.setDisable(false);
            photoPathLabel.setOpacity(1);
            photoPath.setOpacity(1);
            photoPath.setDisable(false);
            sendAdd.setOpacity(1);
            return;
        }
        promptAdd.setText("Add");
        sendAdd.setDisable(true);
        photoPathLabel.setOpacity(0);
        photoPath.setOpacity(0);
        photoPath.setDisable(true);
        sendAdd.setOpacity(0);
        warning.setOpacity(0);
    }

    public void editPhoto() {
        Model.initNextScene(true);
        Model.dataTransfer.add(currentAlbum);
        Model.dataTransfer.add(selectedPhoto);
        Photos.changeScene("primary", "/stages/primary/edit/edit.fxml");
    }

    public void displayPhoto() {
        Model.initNextScene(false);
        Model.dataTransfer.add(currentAlbum);
        Model.dataTransfer.add(selectedPhoto);
        Photos.changeScene("viewphoto", "/stages/viewphoto/main/main.fxml");
    }


    public void addPhoto() {
        if (photoPath.getText().isEmpty()) {
            return;
        }
        try {
            currentAlbum.addPhoto(photoPath.getText());
            createElements();
            promptAdd.setText("Add");
            photoPathLabel.setOpacity(0);
            photoPath.clear();
            photoPath.setOpacity(0);
            photoPath.setDisable(true);
            warning.setOpacity(0);
            sendAdd.setOpacity(0);
            sendAdd.setDisable(true);
        } catch (Exception e) {
            warning.setOpacity(0.69);
            throw new RuntimeException("error adding photo to album.");
        }
    }
}
