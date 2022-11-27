package photos;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Model;

public final class Photos extends Application {
    private static Image logo;
    private static Stage primaryStage;
    private static Stage viewPhotoStage;
    private static boolean primaryShowing;
    private static boolean viewPhotoShowing;

    public static Image getLogo() {
        return Photos.logo;
    }

    public static Stage getPrimaryStage() {
        return Photos.primaryStage;
    }

    public static Stage getViewPhotoStage() {
        return Photos.viewPhotoStage;
    }

    public static boolean isPrimaryShowing() {
        return Photos.primaryShowing;
    }

    public static boolean isViewPhotoShowing() {
        return Photos.viewPhotoShowing;
    }

    private static void setLogo() {
        String url = "https://thumbs.dreamstime.com/b/sun-flower-transparent-background-additional-png-file-greeting-cards-holiday-wishes-sun-flower-transparent-129371261.jpg";
        Photos.logo = new Image(url);
    }

    private static void setPrimaryStage(Stage primaryStage) {
        Photos.primaryStage = primaryStage;
    }

    private static void setViewPhotoStage(Stage viewPhotoStage) {
        Photos.viewPhotoStage = viewPhotoStage;
    }

    private static void setPrimaryShowing(boolean primaryShowing) {
        Photos.primaryShowing = primaryShowing;
    }

    private static void setViewPhotoShowing(boolean viewPhotoShowing) {
        Photos.viewPhotoShowing = viewPhotoShowing;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Model.initializeModel();
        setLogo();
        initShowing();
        initPrimaryStage(primaryStage);
        initViewPhotoStage();
        initCloseStageHandlers();
        changeScene("primary", "/stages/primary/main/main.fxml");
    }

    @Override
    public void stop() {
        Model.persist();
    }

    public static void initShowing() {
        setPrimaryShowing(false);
        setViewPhotoShowing(false);
    }

    public static void initPrimaryStage(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        primaryStage.getIcons().add(getLogo());
        primaryStage.setTitle("Photos Application");
        primaryStage.setResizable(false);
        primaryStage.setWidth(1280);
        primaryStage.setHeight(800);
    }

    public static void initViewPhotoStage() {
        Stage viewPhotoStage = new Stage();
        viewPhotoStage.initModality(Modality.NONE);
        setViewPhotoStage(viewPhotoStage);
        viewPhotoStage.getIcons().add(getLogo());
        viewPhotoStage.setTitle("View Photo");
        viewPhotoStage.setResizable(false);
        viewPhotoStage.setWidth(1280);
        viewPhotoStage.setHeight(720);
    }

    public static void initCloseStageHandlers() {
        getPrimaryStage().setOnCloseRequest((event) -> {
            setPrimaryShowing(false);
            Platform.exit();
        });
        getViewPhotoStage().setOnCloseRequest((event) -> {
            getViewPhotoStage().hide();
            setViewPhotoShowing(false);
        });
    }

    public static void showPrimaryStage() {
        if (isPrimaryShowing()) {
            return;
        }
        Stage primaryStage = getPrimaryStage();
        primaryStage.show();
        setPrimaryShowing(true);
    }

    public static void showViewPhotoStage() {
        if (isViewPhotoShowing()) {
            return;
        }
        Stage viewPhotoStage = getViewPhotoStage();
        viewPhotoStage.show();
        setViewPhotoShowing(true);
    }

    public static void changeScene(String stage, String newScene) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Photos.class.getResource(newScene));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            if (stage.equals("primary")) {
                getPrimaryStage().setScene(scene);
                showPrimaryStage();
            } else if (stage.equals("viewphoto")) {
                getViewPhotoStage().setScene(scene);
                showViewPhotoStage();
            } else {
                throw new RuntimeException("incorrect stage");
            }
        } catch (Exception e) {
            throw new RuntimeException("can not change scene");
        }
    }
}


