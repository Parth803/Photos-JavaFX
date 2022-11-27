package stages.primary.edit;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.Album;
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
    private ChoiceBox<String> tagType;
    @FXML
    private TextField newTagType;
    @FXML
    private ChoiceBox<String> tagProperty;
    @FXML
    private TextField tagValue;
    @FXML
    private Text tagWarning;
    @FXML
    private Button addTag;
    @FXML
    private TextField destinationAlbum;
    @FXML
    private Text warning;
    @FXML
    private Button copyTo;
    @FXML
    private Button moveTo;
    public void initialize() {
        newTagType.setDisable(true);
        tagType.getItems().addAll("Location", "Person", "Other");
        tagProperty.getItems().addAll("Single", "Multi");

        Album currentAlbum = (Album) Model.dataTransfer.get(0);
        Photo selectedPhoto = (Photo) Model.dataTransfer.get(1);
        Model.dataTransfer.clear();

        tagType.setOnAction(this::selectTagType);

        this.back.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/photoslist/photoslist.fxml"));
        this.logout.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/main/main.fxml"));
        this.deleteTag.setOnAction(actionEvent -> deleteTag(selectedPhoto));
        this.updateCaption.setOnAction(actionEvent -> updateCaption(selectedPhoto));
        this.addTag.setOnAction(actionEvent -> addTag(selectedPhoto));
        this.copyTo.setOnAction(actionEvent -> copyTo(selectedPhoto));
        this.moveTo.setOnAction(actionEvent -> moveTo(currentAlbum, selectedPhoto));

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

    public void selectTagType(Event event) {
        if (tagType.getValue().equals("Other")) {
            newTagType.setDisable(false);
            newTagType.setOpacity(1);
        } else {
            newTagType.setDisable(true);
            newTagType.setOpacity(0);
        }
    }

    public void addTag(Photo selectedPhoto) {
        if (tagType.getValue() == null || (!newTagType.isDisabled() && newTagType.getText() == null) || tagProperty.getValue() == null || tagValue.getText() == null) {
            tagWarning.setOpacity(1);
            tagWarning.setText("Fill in all the details to add a tag.");
            return;
        }
        tagWarning.setOpacity(0);
        tagWarning.setText("Tag Value already exists.");

        try {
            if (tagProperty.getValue().equals("Single")) {
                if (tagType.getValue().equals("Other")) {
                    selectedPhoto.addTag(newTagType.getText(), tagValue.getText(), true);
                    tagWarning.setOpacity(0);
                } else {
                    selectedPhoto.addTag(tagType.getValue(), tagValue.getText(), true);
                    tagWarning.setOpacity(0);
                }
            } else {
                if (tagType.getValue().equals("Other")) {
                    selectedPhoto.addTag(newTagType.getText(), tagValue.getText(), false);
                    tagWarning.setOpacity(0);
                } else {
                    selectedPhoto.addTag(tagType.getValue(), tagValue.getText(), false);
                    tagWarning.setOpacity(0);
                }
            }
        } catch (Exception e) {
            tagWarning.setOpacity(0.69);
            throw new RuntimeException("error adding multi tag");
        }
    }

    public void copyTo(Photo selectedPhoto) {
        try {
            Model.currentUser.albums.get(Model.currentUser.albums.indexOf(new Album(destinationAlbum.getText()))).addPhoto(selectedPhoto.path, selectedPhoto.caption);
            warning.setOpacity(0);
        } catch (Exception e) {
            warning.setOpacity(0.69);
            throw new RuntimeException("error when copying photo");
        }
    }

    public void moveTo(Album currentAlbum, Photo selectedPhoto) {
        try {
            Model.currentUser.albums.get(Model.currentUser.albums.indexOf(new Album(destinationAlbum.getText()))).addPhoto(selectedPhoto.path, selectedPhoto.caption);
            currentAlbum.removePhoto(selectedPhoto.path);
            warning.setOpacity(0);
        } catch (Exception e) {
            warning.setOpacity(0.69);
            throw new RuntimeException("error when moving photo");
        }
    }


}
