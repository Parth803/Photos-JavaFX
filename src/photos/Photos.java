package photos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Model;

public final class Photos extends Application {
    private static Stage primaryStage;

    public static Stage getPrimaryStage() {
        return Photos.primaryStage;
    }

    private static void setPrimaryStage(Stage primaryStage) {
        Photos.primaryStage = primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Model.initializeModel();
        Photos.setPrimaryStage(primaryStage);
        java.net.URL obj = getClass().getResource("/stages/primary/main/main.fxml");
        if (obj == null) {
            // handle this in GUI with alert dialog
            System.out.println("FXML not found");
            throw new NullPointerException();
        }
        Parent root = FXMLLoader.load(obj);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Photos Application");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}


