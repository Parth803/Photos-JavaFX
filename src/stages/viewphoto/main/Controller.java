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

/**
 * @author Parth Patel, Yash Patel
 */
public class Controller {
    /**
     *
     */
    @FXML
    private Text caption;
    /**
     *
     */
    @FXML
    private Text dateTaken;
    /**
     *
     */
    @FXML
    private ImageView displayImage;
    /**
     *
     */
    @FXML
    private Button previous;
    /**
     *
     */
    @FXML
    private Button next;
    /**
     *
     */
    @FXML
    private ListView<String> tagsList;
    /**
     *
     */
    private Album currentAlbum;
    /**
     *
     */
    private Photo currentPhoto;

    /**
     *
     */
    public void initialize() {
        currentAlbum = (Album) Model.dataTransfer.get(0);
        currentPhoto = (Photo) Model.dataTransfer.get(1);
        updatePrevNext();
        updateDisplay();
        updateTagsList();
        this.previous.setOnAction(actionEvent -> previousPhoto());
        this.next.setOnAction(actionEvent -> nextPhoto());
    }

    /**
     *
     */
    public void updateTagsList() {
        this.tagsList.setItems(FXCollections.observableList(currentPhoto.tags.stream().map(t -> t.type+"="+t.value).collect(Collectors.toList())));
    }

    /**
     *
     */
    public void updatePrevNext() {
        this.previous.setDisable((currentAlbum.photos.indexOf(currentPhoto)) == 0);
        this.next.setDisable(currentAlbum.photos.indexOf(currentPhoto) == (currentAlbum.photos.size() - 1));
    }

    /**
     *
     */
    public void updateDisplay() {
        if (!currentPhoto.path.isEmpty()) displayImage.setImage(new Image("file:" + currentPhoto.path));
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        this.caption.setText(currentPhoto.caption);
        this.dateTaken.setText(formatter.format(currentPhoto.dateTaken.getTime()));
    }

    /**
     *
     */
    public void previousPhoto() {
        int index = currentAlbum.photos.indexOf(currentPhoto) - 1;
        currentPhoto = currentAlbum.photos.get(index);
        updatePrevNext();
        updateDisplay();
        updateTagsList();
    }

    /**
     *
     */
    public void nextPhoto() {
        int index = currentAlbum.photos.indexOf(currentPhoto) + 1;
        currentPhoto = currentAlbum.photos.get(index);
        updatePrevNext();
        updateDisplay();
        updateTagsList();
    }
}
