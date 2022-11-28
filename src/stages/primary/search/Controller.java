package stages.primary.search;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import model.Album;
import model.Model;
import model.Photo;
import photos.Photos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
//    @FXML
//    private TilePane photosPane;
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
    public void initialize() {
        String searchQuery = (String) Model.dataTransfer.get(0);
        getSearchedImages(searchQuery);

        // Call when a new tile is selected
        updateDetailDisplay();

        this.back.setOnAction(actionEvent -> {
            Model.initPreviousScene();
            Photos.changeScene("primary", "/stages/primary/albums/albums.fxml");
        });
        this.logout.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/main/main.fxml"));
        this.search.setOnAction(actionEvent -> searchPhotos());
        this.createAlbum.setOnAction(actionEvent -> addAlbum());
        this.display.setOnAction(actionEvent -> displayPhoto());
    }

    public void updateDetailDisplay() {
        // NEEDS TO GET SELECTED Photo AND DISPLAY
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        this.caption.setText(""); // photo.caption
        this.dateTaken.setText(""); // formatter.format(photo.dateTaken.getTime())
    }

    public void addAlbum() {
        if (newAlbumName.getText().isEmpty()) {
            return;
        }
        try {
//            Model.currentUser.createAlbum(newAlbumName.getText(), tilepanePhotos);
            warning.setOpacity(0);
        } catch (Exception e) {
            warning.setOpacity(0.69);
            throw new RuntimeException("can not add album containing search results");
        }
    }

    public void displayPhoto() {
        Model.initNextScene(false);
//        SAVE SELECTED PHOTO IN DataTransfer + ALBUM OBJECT WITH ONLY SELECTED-PHOTO SO IT DOES NOT CAROUSEL
//        Album albumWithPhoto = new Album("");
//        try {
//            albumWithPhoto.addPhoto(selectedPhoto.path);
//        } catch (Exception e) {
//            // exception says photo is already in album
//            // no need to do anything
//        }
//        Model.dataTransfer.add(albumWithPhoto);
//        Model.dataTransfer.add(selectedPhoto);
        Model.dataTransfer.add(Model.currentUser.albums.get(0));
        Model.dataTransfer.add(Model.currentUser.albums.get(0).photos.get(0));
        Photos.changeScene("viewphoto", "/stages/viewphoto/main/main.fxml");
    }

    public void searchPhotos() {
        if (searchField.getText().isEmpty() || searchField.getText().matches("^\\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2} TO \\d{1,2}/\\d{1,2}/\\d{4} \\d{1,2}:\\d{1,2}:\\d{1,2}") || searchField.getText().matches("\\S*=\\S*") || searchField.getText().matches("\\S*=\\S* AND \\S*=\\S*") || searchField.getText().matches("\\S*=\\S* OR \\S*=\\S*")) {
            searchWarning.setOpacity(0);
            getSearchedImages(searchField.getText());
        } else {
            searchWarning.setOpacity(0.69);
        }
    }
    public void getSearchedImages(String searchQuery) {
        if (searchQuery.isEmpty()) {
            Model.currentUser.getAllPhotos(); // NEEDS TO BE STORED IN TILEPANE
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
                    Model.currentUser.getPhotosInRange(start, end); // NEEDS TO BE STORED IN TILEPANE
                } catch (Exception e) {
                    throw new RuntimeException("Error parsing searchQuery for date range");
                }
            }
        } else if (searchQuery.matches("\\S*=\\S*")) {
            Pattern p = Pattern.compile("(\\S*)=(\\S*)");
            Matcher m = p.matcher(searchQuery);
            if (m.find()) {
                try {
                    Model.currentUser.getPhotosByTag(m.group(1), m.group(2)); // NEEDS TO BE STORED IN TILEPANE
                } catch (Exception e) {
                    throw new RuntimeException("Error parsing searchQuery for a tag");
                }
            }
        } else if (searchQuery.matches("\\S*=\\S* AND \\S*=\\S*")) {
            Pattern p = Pattern.compile("(\\S*)=(\\S*) AND (\\S*)=(\\S*)");
            Matcher m = p.matcher(searchQuery);
            if (m.find()) {
                try {
                    Model.currentUser.getPhotosByTags(m.group(1), m.group(2), m.group(3),m.group(4), true); // NEEDS TO BE STORED IN TILEPANE
                } catch (Exception e) {
                    throw new RuntimeException("Error parsing searchQuery for tags using AND");
                }
            }
        } else if (searchQuery.matches("\\S*=\\S* OR \\S*=\\S*")) {
            Pattern p = Pattern.compile("(\\S*)=(\\S*) OR (\\S*)=(\\S*)");
            Matcher m = p.matcher(searchQuery);
            if (m.find()) {
                try {
                    Model.currentUser.getPhotosByTags(m.group(1), m.group(2), m.group(3),m.group(4), false); // NEEDS TO BE STORED IN TILEPANE
                } catch (Exception e) {
                    throw new RuntimeException("Error parsing searchQuery for tags using OR");
                }
            }
        }
    }

}
