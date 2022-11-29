package stages.primary.photoslist;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import model.Album;
import model.Model;
import model.Photo;
import photos.Photos;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Parth Patel, Yash Patel
 */
public class Controller {
    /**
     *
     */
    @FXML
    private TilePane photosPane;
    /**
     *
     */
    @FXML
    private Button back;
    /**
     *
     */
    @FXML
    private Button logout;
    /**
     *
     */
    @FXML
    private Text albumName;
    /**
     *
     */
    @FXML
    private Button delete;
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
    private Button edit;
    /**
     *
     */
    @FXML
    private Button display;
    /**
     *
     */
    @FXML
    private Text warning;
    /**
     *
     */
    @FXML
    private Button sendAdd;
    /**
     *
     */
    private Album currentAlbum;
    /**
     *
     */
    private VBox selectedPhotoBox;
    /**
     *
     */
    private Photo selectedPhoto;

    /**
     *
     */
    public void initialize() {
        currentAlbum = (Album) Model.dataTransfer.get(0);
        if (Model.dataTransfer.size() == 2) {
            selectedPhoto = (Photo) Model.dataTransfer.get(1);
        }
        albumName.setText("Album: " + currentAlbum.name);

        createElements();

        this.back.setOnAction(actionEvent -> {
            Model.initPreviousScene();
            Photos.changeScene("primary", "/stages/primary/albums/albums.fxml");
        });
        this.logout.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/main/main.fxml"));
        this.delete.setOnAction(actionEvent -> deletePhoto());
        this.edit.setOnAction(actionEvent -> editPhoto());
        this.display.setOnAction(actionEvent -> displayPhoto());
        this.sendAdd.setOnAction(actionEvent -> addPhoto());
    }

    /**
     *
     */
    public void createElements() {
        photosPane.getChildren().clear();
        photosPane.setPrefColumns(3);
        photosPane.setHgap(10);
        photosPane.setVgap(10);
        for (Photo p: currentAlbum.photos) {
            photosPane.getChildren().add(createElement(p));
        }
    }

    /**
     *
     * @param p
     * @return
     */
    public VBox createElement(Photo p) {
        ImageView img = new ImageView();
        img.setImage(new Image("file:" + p.path));
        img.setFitWidth(175);
        img.setFitHeight(175);

        VBox element = new VBox();
        element.getChildren().add(img);

        Border b = new Border(new BorderStroke(Paint.valueOf("#4285F4"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));

        if (selectedPhoto != null && selectedPhoto.equals(p)) {
            selectedPhotoBox = element;
            selectedPhotoBox.setBorder(b);
            updateDetailDisplay();
        }

        element.setOnMouseClicked(mouseEvent -> {
            if (selectedPhotoBox != null) {
                selectedPhotoBox.setBorder(Border.stroke(Paint.valueOf("white")));
            }
            selectedPhoto = p;
            selectedPhotoBox = element;
            selectedPhotoBox.setBorder(b);
            Model.dataTransfer.clear();
            Model.dataTransfer.add(currentAlbum);
            Model.dataTransfer.add(selectedPhoto);
            updateDetailDisplay();
        });
        return element;
    }

    /**
     *
     */
    public void updateDetailDisplay() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        this.caption.setText(selectedPhoto.caption);
        this.dateTaken.setText(formatter.format(selectedPhoto.dateTaken.getTime()));
    }

    /**
     *
     */
    public void deletePhoto() {
        if (selectedPhoto == null) {
            return;
        }
        try {
            currentAlbum.removePhoto(selectedPhoto.path);
            Model.persist();
            selectedPhoto = null;
            this.caption.setText("N/A");
            this.dateTaken.setText("N/A");
            createElements();
        } catch (Exception ignored) {}
    }

    /**
     *
     */
    public void editPhoto() {
        if (selectedPhoto == null) {
            return;
        }
        Model.initNextScene(true);
        Model.dataTransfer.add(currentAlbum);
        Model.dataTransfer.add(selectedPhoto);
        Photos.changeScene("primary", "/stages/primary/edit/edit.fxml");
    }

    /**
     *
     */
    public void displayPhoto() {
        if (selectedPhoto == null) {
            return;
        }
        Model.initNextScene(false);
        Model.dataTransfer.add(currentAlbum);
        Model.dataTransfer.add(selectedPhoto);
        Photos.changeScene("viewphoto", "/stages/viewphoto/main/main.fxml");
    }

    /**
     *
     */
    public void addPhoto() {
        List<String> extensions = new ArrayList<>();
        extensions.add("*.bmp");
        extensions.add("*.gif");
        extensions.add("*.jpeg");
        extensions.add("*.jpg");
        extensions.add("*.png");
        String description = "Image Extensions";
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(description, extensions));
        File file = fileChooser.showOpenDialog(Photos.getPrimaryStage());
        try {
            if (file == null) {
                throw new Exception("Select an image file");
            }
            Image img = new Image("file:" + file.getAbsolutePath());
            if (img.isError()) {
                throw new Exception("Image can not be loaded. Please try again.");
            }
            currentAlbum.addPhoto(file.getAbsolutePath());
            Model.persist();
            createElements();
            warning.setOpacity(0);
        } catch (SecurityException e) {
            warning.setText("Need permission to access file. Please try again.");
            warning.setOpacity(0.69);
        } catch (Exception e) {
            warning.setText(e.getMessage());
            warning.setOpacity(0.69);
        }
    }
}

