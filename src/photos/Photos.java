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

/**
 * @author Parth Patel, Yash Patel
 */
public final class Photos extends Application {
    /**
     *
     */
    private static Image logo;
    /**
     *
     */
    private static Stage primaryStage;
    /**
     *
     */
    private static Stage viewPhotoStage;
    /**
     *
     */
    private static boolean primaryShowing;
    /**
     *
     */
    private static boolean viewPhotoShowing;

    /**
     *
     * @return
     */
    public static Image getLogo() {
        return Photos.logo;
    }

    /**
     *
     * @return
     */
    public static Stage getPrimaryStage() {
        return Photos.primaryStage;
    }

    /**
     *
     * @return
     */
    public static Stage getViewPhotoStage() {
        return Photos.viewPhotoStage;
    }

    /**
     *
     * @return
     */
    public static boolean isPrimaryShowing() {
        return Photos.primaryShowing;
    }

    /**
     *
     * @return
     */
    public static boolean isViewPhotoShowing() {
        return Photos.viewPhotoShowing;
    }

    /**
     *
     */
    private static void setLogo() {
        Photos.logo = new Image("file:data/resources/logo.jpg");
    }

    /**
     *
     * @param primaryStage
     */
    private static void setPrimaryStage(Stage primaryStage) {
        Photos.primaryStage = primaryStage;
    }

    /**
     *
     * @param viewPhotoStage
     */
    private static void setViewPhotoStage(Stage viewPhotoStage) {
        Photos.viewPhotoStage = viewPhotoStage;
    }

    /**
     *
     * @param primaryShowing
     */
    private static void setPrimaryShowing(boolean primaryShowing) {
        Photos.primaryShowing = primaryShowing;
    }

    /**
     *
     * @param viewPhotoShowing
     */
    private static void setViewPhotoShowing(boolean viewPhotoShowing) {
        Photos.viewPhotoShowing = viewPhotoShowing;
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        Model.init();
        setLogo();
        initShowing();
        initPrimaryStage(primaryStage);
        initViewPhotoStage();
        initCloseStageHandlers();
        changeScene("primary", "/stages/primary/main/main.fxml");
    }

    /**
     *
     */
    @Override
    public void stop() {
        Model.persist();
    }

    /**
     *
     */
    public static void initShowing() {
        setPrimaryShowing(false);
        setViewPhotoShowing(false);
    }

    /**
     *
     * @param primaryStage
     */
    public static void initPrimaryStage(Stage primaryStage) {
        setPrimaryStage(primaryStage);
        primaryStage.getIcons().add(getLogo());
        primaryStage.setTitle("Photos Application");
        primaryStage.setResizable(true);
        primaryStage.setWidth(1280);
        primaryStage.setHeight(800);
    }

    /**
     *
     */
    public static void initViewPhotoStage() {
        Stage viewPhotoStage = new Stage();
        viewPhotoStage.initModality(Modality.NONE);
        setViewPhotoStage(viewPhotoStage);
        viewPhotoStage.getIcons().add(getLogo());
        viewPhotoStage.setTitle("View Photo");
        viewPhotoStage.setResizable(true);
        viewPhotoStage.setWidth(1280);
        viewPhotoStage.setHeight(720);
    }

    /**
     *
     */
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

    /**
     *
     */
    public static void showPrimaryStage() {
        if (isPrimaryShowing()) {
            return;
        }
        getPrimaryStage().show();
        setPrimaryShowing(true);
    }

    /**
     *
     */
    public static void showViewPhotoStage() {
        if (isViewPhotoShowing()) {
            return;
        }
        getViewPhotoStage().show();
        setViewPhotoShowing(true);
    }

    /**
     *
     */
    public static void closeViewPhotoStage() {
        if (!isViewPhotoShowing()) {
            return;
        }
        getViewPhotoStage().hide();
        setViewPhotoShowing(false);
    }

    /**
     *
     * @param stage
     * @param newScene
     */
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


