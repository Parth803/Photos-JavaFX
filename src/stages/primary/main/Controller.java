package stages.primary.main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.Model;
import photos.Photos;

public class Controller {
    @FXML
    private Button back;
    @FXML
    private Button logout;
    @FXML
    private TextField username;
    @FXML
    private Button proceed;
    @FXML
    private Text warning;
    public void initialize() {
        this.back.setDisable(true);
        this.logout.setDisable(true);
        this.proceed.setOnAction(actionEvent -> continueAction());
    }

    public void continueAction() {
        try {
            Model.setCurrentUser(username.getText());
            warning.setOpacity(0);

            if (username.getText().equals("admin")) {
                Photos.changeScene("/stages/primary/admin/admin.fxml");
            }
            else {
                Photos.changeScene("/stages/primary/albums/albums.fxml");
            }
        } catch (Exception e) {
            warning.setOpacity(0.69);
        }
    }
}
