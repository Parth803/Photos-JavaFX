package stages.primary.admin;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Model;
import model.Photo;
import model.User;
import photos.Photos;

import java.io.IOException;
import java.util.function.Predicate;

public class Controller {
    @FXML
    private Button back;
    @FXML
    private Button logout;
    @FXML
    private Text warning;
    @FXML
    private TextField username;
    @FXML
    private Button addUser;
    @FXML
    private Button deleteUser;
    public void initialize() {
        this.back.setOnAction(actionEvent -> Photos.logOut());
        this.logout.setOnAction(actionEvent -> Photos.logOut());
        this.addUser.setOnAction(actionEvent -> addUser());
        this.deleteUser.setOnAction(actionEvent -> deleteUser());
    }

    public void addUser() {
        try {
            Model.addUser(username.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser() {

    }
}
