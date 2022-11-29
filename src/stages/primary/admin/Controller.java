package stages.primary.admin;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Model;
import photos.Photos;

import java.util.stream.Collectors;

/**
 * @author Parth Patel, Yash Patel
 */
public class Controller {
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
    private Text warning;
    /**
     *
     */
    @FXML
    private TextField username;
    /**
     *
     */
    @FXML
    private Button addUser;
    /**
     *
     */
    @FXML
    private Button deleteUser;
    /**
     *
     */
    @FXML
    private ListView<String> usersList;

    /**
     *
     */
    public void initialize() {
        this.back.setOnAction(actionEvent -> {
            Model.initPreviousScene();
            Photos.changeScene("primary", "/stages/primary/main/main.fxml");
        });
        this.logout.setOnAction(actionEvent ->Photos.changeScene("primary", "/stages/primary/main/main.fxml"));
        this.addUser.setOnAction(actionEvent -> addUser());
        this.deleteUser.setOnAction(actionEvent -> deleteUser());
        updateUsersList();
    }

    /**
     *
     */
    public void updateUsersList() {
        this.usersList.setItems(FXCollections.observableList(Model.users.stream().map(u -> u.username).collect(Collectors.toList())));
    }

    /**
     *
     */
    public void addUser() {
        try {
            if (username.getText().isEmpty()) {
                throw new Exception("empty");
            }
            Model.addUser(username.getText());
            Model.persist();
            updateUsersList();
            warning.setOpacity(0);
        } catch (Exception e) {
            if (e.getMessage().equals("empty")) {
                warning.setText("Please type a username");
            } else {
                warning.setText(e.getMessage());
            }
            warning.setOpacity(0.69);
        }
    }

    /**
     *
     */
    public void deleteUser() {
        if (this.usersList.getSelectionModel().isEmpty()) {
            return;
        }
        try {
            Model.deleteUser(this.usersList.getSelectionModel().getSelectedItem());
            Model.persist();
            updateUsersList();
        } catch (Exception ignored) {}
    }
}
