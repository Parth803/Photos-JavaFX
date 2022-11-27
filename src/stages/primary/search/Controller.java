package stages.primary.search;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.Model;
import photos.Photos;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    @FXML
    private Button back;
    @FXML
    private Button logout;

    public void initialize() {
        String searchQuery = (String) Model.dataTransfer.get(0);
        Model.dataTransfer.clear();
        getSearchedImages(searchQuery);

        this.back.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/albums/albums.fxml"));
        this.logout.setOnAction(actionEvent -> Photos.changeScene("primary", "/stages/primary/main/main.fxml"));
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
