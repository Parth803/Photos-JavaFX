package stages.viewphoto.main;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import model.Album;
import model.Model;
import model.Photo;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

public class Controller {
    @FXML
    private Text caption;
    @FXML
    private Text dateTaken;
    @FXML
    private ImageView displayImage;
    @FXML
    private Button previous;
    @FXML
    private Button next;
    @FXML
    private Text warning;
    @FXML
    private ListView<String> tagsList;

    public void initialize() {
        Album currentAlbum = (Album) Model.dataTransfer.get(0);
        Photo currentPhoto = (Photo) Model.dataTransfer.get(1);
        if ((currentAlbum.photos.indexOf(currentPhoto) - 1) == 0) {
            previous.setDisable(true);
        } else if (currentAlbum.photos.indexOf(currentPhoto) == (currentAlbum.photos.size() - 1)) {
            next.setDisable(true);
        }
        // we cannot clear dataTransfer because if we go back we need the data to load
        // WE NEED TO SOMEHOW STORE THE SEARCH QUERY TOO INCASE WE GO BACK TO SEARCH FROM HERE
        updateDisplay(currentPhoto);

        this.previous.setOnAction(actionEvent -> previousPhoto(currentAlbum, currentPhoto));
        this.next.setOnAction(actionEvent -> nextPhoto(currentAlbum, currentPhoto));

        this.tagsList.setItems(FXCollections.observableList(currentPhoto.tags.stream().map(t -> t.type+"="+t.value).collect(Collectors.toList())));
    }

    public void updateDisplay(Photo currentPhoto) {
        if (!currentPhoto.path.isEmpty()) {
            displayImage.setImage(new Image(currentPhoto.path));
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        this.caption.setText(currentPhoto.caption);
        this.dateTaken.setText(formatter.format(currentPhoto.dateTaken.getTime()));
    }

    public void previousPhoto(Album currentAlbum, Photo currentPhoto) {
        if (currentAlbum.photos.size() == 1) {
            warning.setOpacity(0.69);
            return;
        }
        int index = currentAlbum.photos.indexOf(currentPhoto) - 1;
        if (index >= 0) {
            updateDisplay(currentAlbum.photos.get(index));
            warning.setOpacity(0);
            if (index == 0) {
                previous.setDisable(true);
                next.setDisable(false);
            }
        }
    }

    public void nextPhoto(Album currentAlbum, Photo currentPhoto) {
        if (currentAlbum.photos.size() == 1) {
            warning.setOpacity(0.69);
            return;
        }
        int index = currentAlbum.photos.indexOf(currentPhoto) + 1;
        if (index < currentAlbum.photos.size()) {
            updateDisplay(currentAlbum.photos.get(index));
            warning.setOpacity(0);
            if (index == currentAlbum.photos.size() - 1) {
                previous.setDisable(false);
                next.setDisable(true);
            }
        }
    }
}
