package stages.primary.admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Model;
import model.User;
import photos.Photos;
import javafx.beans.value.*;

import java.util.stream.Collectors;

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
    @FXML
    private ListView<String> usersList;
    public void initialize() {
        this.back.setOnAction(actionEvent -> Photos.logOut());
        this.logout.setOnAction(actionEvent -> Photos.logOut());
        this.addUser.setOnAction(actionEvent -> addUser());
        this.deleteUser.setOnAction(actionEvent -> deleteUser());
        this.usersList.setItems(FXCollections.observableList(Model.users.stream().map(u -> u.username).collect(Collectors.toList())));
    }

    public void addUser() {
        try {
            Model.addUser(username.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteUser() {
        if (this.usersList.getSelectionModel().isEmpty()) {
            return;
        }
        try {
            Model.deleteUser(this.usersList.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
