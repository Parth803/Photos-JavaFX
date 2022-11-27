package stages.viewphoto.main;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import model.Album;
import model.Model;
import model.Photo;

import java.text.SimpleDateFormat;

public class Controller {
    @FXML
    private Text caption;
    @FXML
    private Text dateTaken;
    @FXML
    private Button next;
    @FXML
    private Button previous;

    public void initialize() {
        Album currentAlbum = (Album) Model.dataTransfer.get(0);
        Photo currentPhoto = (Photo) Model.dataTransfer.get(1);
        // we cannot clear dataTransfer because if we go back we need the data to load
        // WE NEED TO SOMEHOW STORE THE SEARCH QUERY TOO INCASE WE GO BACK TO SEARCH FROM HERE
        updateDetailDisplay(currentPhoto);

        this.next.setOnAction(actionEvent -> nextPhoto(currentAlbum, currentPhoto));
        this.previous.setOnAction(actionEvent -> previousPhoto(currentAlbum, currentPhoto));
    }

    public void updateDetailDisplay(Photo currentPhoto) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        this.caption.setText(currentPhoto.caption);
        this.dateTaken.setText(formatter.format(currentPhoto.dateTaken.getTime()));
    }

    public void nextPhoto(Album currentAlbum, Photo currentPhoto) {
        // check if album is of size 1 (we displayed photo for search result) or not (we displayed photo for album, so we can go to next photo in album)

    }

    public void previousPhoto(Album currentAlbum, Photo currentPhoto) {
        // check if album is of size 1 (we displayed photo for search result) or not (we displayed photo for album, so we can go to prev photo in album)

    }
}
