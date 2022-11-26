package stages.primary.main;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import model.Model;
import photos.Photos;


public class Controller {
    @FXML
    private TextField username;
    @FXML
    private Button proceed;
    @FXML
    private Text warning;
    public void initialize() {
        this.proceed.setOnAction(actionEvent -> continueAction());
    }

    public void continueAction() {
        try {
            Model.setCurrentUser(username.getText());
            warning.setOpacity(0);

            if (username.getText().equals("admin")) {
                Photos.changeScene("primary", "/stages/primary/admin/admin.fxml");
            }
            else {
                Photos.changeScene("primary", "/stages/primary/albums/albums.fxml");
            }
        } catch (Exception e) {
            warning.setOpacity(0.69);
        }
    }
}
