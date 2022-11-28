package stages.primary.albums;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.Album;
import model.Model;
import photos.Photos;

import java.text.SimpleDateFormat;

public class Controller {
    @FXML
    private TilePane albumsPane;
    @FXML
    private Button back;
    @FXML
    private Button logout;
    @FXML
    private TextField searchField;
    @FXML
    private Button search;
    @FXML
    private Text searchWarning;
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
    private VBox selectedAlbumBox;
    private Album selectedAlbum;

    public void initialize() {
        if (!Model.dataTransfer.isEmpty()) {
            selectedAlbum = (Album) Model.dataTransfer.get(0);
        }
        sendAdd.setDisable(true);
        albumName.setDisable(true);

        createElements();

        this.back.setOnAction(actionEvent -> {
            Model.initPreviousScene();
            Photos.changeScene("primary", "/stages/primary/main/main.fxml");
        });
        this.logout.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/main/main.fxml"));
        this.search.setOnAction(actionEvent -> searchPhotos());
        this.delete.setOnAction(actionEvent -> deleteAlbum());
        this.promptAdd.setOnAction(actionEvent -> promptAdd());
        this.openAlbum.setOnAction(actionEvent -> openAlbum());
        this.sendAdd.setOnAction(actionEvent -> addAlbum());
    }

    public void createElements() {
        albumsPane.getChildren().clear();
        albumsPane.setPrefColumns(3);
        albumsPane.setHgap(10);
        albumsPane.setVgap(10);
        for (Album a: Model.currentUser.albums) {
            albumsPane.getChildren().add(createElement(a));
        }
    }

    public VBox createElement(Album a) {
        ImageView img = new ImageView();
        if (a.photos.size() == 0) {
            // placeholder image when we create new album
            img.setImage(Photos.getLogo());
        } else {
            img.setImage(new Image("file:" + a.photos.get(a.photos.size() - 1).path));
        }
        img.setFitWidth(175);
        img.setFitHeight(175);

        Text albumName = new Text(a.name);
        albumName.setFont(Font.font(18));

        VBox element = new VBox();
        element.getChildren().add(img);
        element.getChildren().add(albumName);
        element.setAlignment(Pos.CENTER);

        Border b = new Border(new BorderStroke(Paint.valueOf("#4285F4"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(3)));

        if (selectedAlbum != null && selectedAlbum.equals(a)) {
            selectedAlbumBox = element;
            selectedAlbumBox.setBorder(b);
            updateDetailDisplay();
        }

        element.setOnMouseClicked(mouseEvent -> {
            if (selectedAlbumBox != null) {
                selectedAlbumBox.setBorder(Border.stroke(Paint.valueOf("white")));
            }
            selectedAlbum = a;
            selectedAlbumBox = element;
            selectedAlbumBox.setBorder(b);
            Model.dataTransfer.clear();
            Model.dataTransfer.add(selectedAlbum);
            updateDetailDisplay();
        });

        return element;
    }

    public void updateDetailDisplay() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        this.nameOfAlbum.setText(selectedAlbum.name);
        this.numPhotos.setText(String.valueOf(selectedAlbum.photos.size()));
        this.dateRange.setText(formatter.format(selectedAlbum.start.getTime()) + " TO " + formatter.format(selectedAlbum.end.getTime()));
    }

    public void searchPhotos() {
        if (searchField.getText().isEmpty() || searchField.getText().matches("^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} TO \\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}") || searchField.getText().matches("\\S*=\\S*") || searchField.getText().matches("\\S*=\\S* AND \\S*=\\S*") || searchField.getText().matches("\\S*=\\S* OR \\S*=\\S*")) {
            searchWarning.setOpacity(0);
            Model.initNextScene(true);
            Model.dataTransfer.add(searchField.getText());
            Photos.changeScene("primary", "/stages/primary/search/search.fxml");
        } else {
            searchWarning.setOpacity(0.69);
        }
    }

    public void deleteAlbum() {
        if (selectedAlbum == null) {
            return;
        }
        try {
            Model.currentUser.deleteAlbum(selectedAlbum.name);
            Model.persist();
            selectedAlbum = null;
            this.nameOfAlbum.setText("N/A");
            this.numPhotos.setText("N/A");
            this.dateRange.setText("N/A TO N/A");
            createElements();
        } catch (Exception ignored) {}
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
            warning.setOpacity(0);
        }
    }

    public void openAlbum() {
        if (selectedAlbum == null) {
            return;
        }
        Model.initNextScene(true);
        Model.dataTransfer.add(selectedAlbum);
        Photos.changeScene("primary", "/stages/primary/photoslist/photoslist.fxml");
    }

    public void addAlbum() {
        try {
            if (albumName.getText().isEmpty()) {
                throw new Exception("Enter album name");
            }
            Model.currentUser.createAlbum(albumName.getText());
            Model.persist();
            createElements();
            promptAdd.setText("Add");
            newAlbumLabel.setOpacity(0);
            albumName.clear();
            albumName.setOpacity(0);
            albumName.setDisable(true);
            warning.setOpacity(0);
            sendAdd.setOpacity(0);
            sendAdd.setDisable(true);
        } catch (Exception e) {
            warning.setText(e.getMessage());
            warning.setOpacity(0.69);
        }
    }
}


