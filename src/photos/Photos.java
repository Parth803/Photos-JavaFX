package photos;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Model;

import java.io.IOException;

public final class Photos extends Application {
    private static Stage primaryStage;
    private static Stage viewPhotoStage;
    private static boolean primaryShowing;
    private static boolean viewPhotoShowing;

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
    public void start(Stage primaryStage) throws Exception {
        Model.initializeModel();
        initShowing();
        initPrimaryStage(primaryStage);
        initViewPhotoStage();
        initCloseStageHandlers();
    }

    @Override
    public void stop() {
        Model.persist();
    }

    public static void initPrimaryStage(Stage primaryStage) throws Exception {
        Photos.setPrimaryStage(primaryStage);
        java.net.URL obj = Photos.class.getResource("/stages/primary/main/main.fxml");
        if (obj == null) {
            throw new NullPointerException();
        }
        Parent root = FXMLLoader.load(obj);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Photos Application");
        primaryStage.setResizable(false);
        primaryStage.setWidth(1280);
        primaryStage.setHeight(800);
        primaryStage.show();
        setPrimaryShowing(true);
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

    public static void initShowing() {
        setPrimaryShowing(false);
        setViewPhotoShowing(false);
    }

    public static void initViewPhotoStage() throws Exception {
        Stage viewPhotoStage = new Stage();
        viewPhotoStage.initModality(Modality.NONE);
        Photos.setViewPhotoStage(viewPhotoStage);
        java.net.URL obj = Photos.class.getResource("/stages/viewphoto/main/main.fxml");
        if (obj == null) {
            throw new NullPointerException();
        }
        Parent root = FXMLLoader.load(obj);
        Scene scene = new Scene(root);
        viewPhotoStage.setScene(scene);
        viewPhotoStage.setTitle("View Photo");
        viewPhotoStage.setResizable(false);
        viewPhotoStage.setWidth(1280);
        viewPhotoStage.setHeight(720);
    }

    public static void showViewPhotoStage() {
        if (isViewPhotoShowing()) {
            return;
        }
        Stage viewPhotoStage = Photos.getViewPhotoStage();
        viewPhotoStage.show();
        setViewPhotoShowing(true);
    }

    public static void changeScene(String newScene) {
        try {
            java.net.URL obj = Photos.class.getResource(newScene);
            if (obj == null) {
                // handle this in GUI with alert dialog
                System.out.println("FXML not found");
                throw new NullPointerException();
            }
            Parent root = FXMLLoader.load(obj);
            Scene scene = new Scene(root);
            Photos.getPrimaryStage().setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


