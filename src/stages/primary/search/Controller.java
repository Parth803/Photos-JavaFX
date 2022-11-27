package stages.primary.search;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import model.Model;
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
        Model.dataTransfer.clear();
        getSearchedImages(searchQuery);

        // Call when a new tile is selected
        updateDetailDisplay();

        this.back.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/albums/albums.fxml"));
        this.logout.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/main/main.fxml"));
        this.search.setOnAction(actionEvent -> searchPhotos());
        this.createAlbum.setOnAction(actionEvent -> addAlbum());
        this.display.setOnAction(actionEvent -> displayPhoto());
    }

    public void updateDetailDisplay() {
        // NEEDS TO GET SELECTED Photo AND DISPLAY
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        this.caption.setText(""); // photo.caption
        this.dateTaken.setText(""); // formatter.format(photo.dateTaken)
    }

    public void addAlbum() {
        if (newAlbumName.getText().isEmpty()) {
            return;
        }
        try {
            Model.currentUser.createAlbum(newAlbumName.getText());
            warning.setOpacity(0);
        } catch (Exception e) {
            e.printStackTrace();
            warning.setOpacity(0.69);
        }
    }

    public void displayPhoto() {
//        SAVE SELECTED PHOTO IN DATA + ALBUM OBJECT WITH ONLY SELECTED-PHOTO SO IT DOES NOT CAROUSEL
//        Model.dataTransfer.add(1, selectedPhoto);
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
