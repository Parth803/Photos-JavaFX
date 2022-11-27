package stages.primary.edit;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Model;
import model.Photo;
import photos.Photos;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Controller {
    @FXML
    private Button back;
    @FXML
    private Button logout;
    @FXML
    private TextField caption;
    @FXML
    private Button updateCaption;
    @FXML
    private Button deleteTag;
    @FXML
    private ListView<String> tagsList;
    @FXML
    private TextField destinationAlbum;
    @FXML
    private Button copyTo;
    @FXML
    private Button moveTo;
    public void initialize() {
        Photo selectedPhoto = (Photo) Model.dataTransfer.get(1);
        Model.dataTransfer.clear();

        this.back.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/photoslist/photoslist.fxml"));
        this.logout.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/main/main.fxml"));
        this.deleteTag.setOnAction(actionEvent -> deleteTag(selectedPhoto));
        this.updateCaption.setOnAction(actionEvent -> updateCaption(selectedPhoto));
        this.copyTo.setOnAction(actionEvent -> copyTo());
        this.moveTo.setOnAction(actionEvent -> moveTo());

        this.tagsList.setItems(FXCollections.observableList(selectedPhoto.tags.stream().map(t -> t.type+"="+t.value).collect(Collectors.toList())));

    }

    public void deleteTag(Photo selectedPhoto) {
        if (this.tagsList.getSelectionModel().isEmpty()) {
            return;
        }
        Pattern p = Pattern.compile("(\\S*)=(\\S*)");
        Matcher m = p.matcher(this.tagsList.getSelectionModel().getSelectedItem());
        if (m.find()) {
            try {
                Model.currentUser.uniquePhotos.get(selectedPhoto.path).removeTag(m.group(1), m.group(2));
                this.tagsList.setItems(FXCollections.observableList(selectedPhoto.tags.stream().map(t -> t.type+"="+t.value).collect(Collectors.toList())));
            } catch (Exception e) {
                throw new RuntimeException("Error removing tag from photo");
            }
        }
    }

    public void updateCaption(Photo selectedPhoto) {
        selectedPhoto.caption = caption.getText();
    }

    public void copyTo() {

    }

    public void moveTo() {

    }
}
