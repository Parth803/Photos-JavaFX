package stages.primary.main;
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
                java.net.URL obj = Photos.class.getResource("/stages/primary/admin/admin.fxml");
                if (obj == null) {
                    // handle this in GUI with alert dialog
                    System.out.println("FXML not found");
                    throw new NullPointerException();
                }
                Parent root = FXMLLoader.load(obj);
                Scene scene = new Scene(root);
                Photos.getPrimaryStage().setScene(scene);
            }
            else {
                java.net.URL obj = Photos.class.getResource("/stages/primary/albums/albums.fxml");
                if (obj == null) {
                    // handle this in GUI with alert dialog
                    System.out.println("FXML not found");
                    throw new NullPointerException();
                }
                Parent root = FXMLLoader.load(obj);
                Scene scene = new Scene(root);
                Photos.getPrimaryStage().setScene(scene);
            }
        } catch (Exception e) {
            warning.setOpacity(0.69);
        }
    }
}
