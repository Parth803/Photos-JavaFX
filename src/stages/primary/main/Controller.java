package stages.primary.main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Model;
import model.Photo;
import photos.Photos;

public class Controller {
    @FXML
    private TextField username;
    @FXML
    private Button proceed;
    @FXML
    private Text warning;
    public void initialize() {
        this.proceed.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                continueAction();
            }
        });
    }

    public void continueAction() {
        Model.initializeModel();
        try {
            Model.setCurrentUser(username.getText());
            warning.setOpacity(0);
            Stage primaryStage = Photos.getPrimaryStage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/albums/albums.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Controller controller = loader.getController();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            warning.setOpacity(0.69);
        }
    }
}
