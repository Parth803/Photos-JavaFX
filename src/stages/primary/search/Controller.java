package stages.primary.search;

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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    @FXML
    private TilePane photosPane;
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
    private TextField newAlbumName;
    @FXML
    private Text warning;
    @FXML
    private Button createAlbum;
    @FXML
    private Text caption;
    @FXML
    private Text dateTaken;
    @FXML
    private Button display;

    private ArrayList<Photo> searchResults;

    private Photo selectedPhoto;
    public void initialize() {
        String searchQuery = (String) Model.dataTransfer.get(0);
        getSearchedImages(searchQuery);

//        if (!searchResults.isEmpty()) {
//            selectedPhoto = searchResults.get(0);
//            updateDetailDisplay();
//        }

        createElements();

        this.back.setOnAction(actionEvent -> {
            Model.initPreviousScene();
            Photos.changeScene("primary", "/stages/primary/albums/albums.fxml");
        });
        this.logout.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/main/main.fxml"));
        this.search.setOnAction(actionEvent -> searchPhotos());
        this.createAlbum.setOnAction(actionEvent -> addAlbum());
        this.display.setOnAction(actionEvent -> displayPhoto());
    }

    public void createElements() {
        photosPane.getChildren().clear();
        photosPane.setPrefColumns(3);
        photosPane.setHgap(10);
        photosPane.setVgap(10);
        for (Photo p: searchResults) {
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

    public void addAlbum() {
        if (newAlbumName.getText().isEmpty()) {
            return;
        }
        try {
            Model.currentUser.createAlbum(newAlbumName.getText(), searchResults);
            warning.setOpacity(0);
        } catch (Exception e) {
            warning.setOpacity(0.69);
            throw new RuntimeException("error can not add album containing search results");
        }
    }

    public void displayPhoto() {
        if (selectedPhoto == null) {
            return;
        }
        Model.initNextScene(false);
        Album temp = new Album("");
        temp.photos.add(selectedPhoto);
        Model.dataTransfer.add(temp);
        Model.dataTransfer.add(selectedPhoto);
        Photos.changeScene("viewphoto", "/stages/viewphoto/main/main.fxml");
    }

    public void searchPhotos() {
        if (searchField.getText().isEmpty() || searchField.getText().matches("^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} TO \\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}") || searchField.getText().matches("\\S*=\\S*") || searchField.getText().matches("\\S*=\\S* AND \\S*=\\S*") || searchField.getText().matches("\\S*=\\S* OR \\S*=\\S*")) {
            searchWarning.setOpacity(0);
            getSearchedImages(searchField.getText());
            if (!searchResults.isEmpty()) {
                selectedPhoto = searchResults.get(0);
            }
            createElements();
        } else {
            searchWarning.setOpacity(0.69);
        }
    }
    public void getSearchedImages(String searchQuery) {
        if (searchQuery.isEmpty()) {
            searchResults = Model.currentUser.getAllPhotos();
        } else if (searchQuery.matches("^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} TO \\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}")) {
            SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            Calendar start = Calendar.getInstance();
            Calendar end = Calendar.getInstance();
            Pattern p = Pattern.compile("(\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}) TO (\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2})");
            Matcher m = p.matcher(searchQuery);
            if (m.find()) {
                try {
                    start.setTime(formatter.parse(m.group(1)));
                    end.setTime(formatter.parse(m.group(2)));
                    searchResults = Model.currentUser.getPhotosInRange(start, end);
                } catch (Exception e) {
                    throw new RuntimeException("Error parsing searchQuery for date range");
                }
            }
        } else if (searchQuery.matches("\\S*=\\S*")) {
            Pattern p = Pattern.compile("(\\S*)=(\\S*)");
            Matcher m = p.matcher(searchQuery);
            if (m.find()) {
                try {
                    searchResults = Model.currentUser.getPhotosByTag(m.group(1), m.group(2));
                } catch (Exception e) {
                    throw new RuntimeException("Error parsing searchQuery for a tag");
                }
            }
        } else if (searchQuery.matches("\\S*=\\S* AND \\S*=\\S*")) {
            Pattern p = Pattern.compile("(\\S*)=(\\S*) AND (\\S*)=(\\S*)");
            Matcher m = p.matcher(searchQuery);
            if (m.find()) {
                try {
                    searchResults = Model.currentUser.getPhotosByTags(m.group(1), m.group(2), m.group(3),m.group(4), true);
                } catch (Exception e) {
                    throw new RuntimeException("Error parsing searchQuery for tags using AND");
                }
            }
        } else if (searchQuery.matches("\\S*=\\S* OR \\S*=\\S*")) {
            Pattern p = Pattern.compile("(\\S*)=(\\S*) OR (\\S*)=(\\S*)");
            Matcher m = p.matcher(searchQuery);
            if (m.find()) {
                try {
                    searchResults = Model.currentUser.getPhotosByTags(m.group(1), m.group(2), m.group(3),m.group(4), false);
                } catch (Exception e) {
                    throw new RuntimeException("Error parsing searchQuery for tags using OR");
                }
            }
        }
    }
}
