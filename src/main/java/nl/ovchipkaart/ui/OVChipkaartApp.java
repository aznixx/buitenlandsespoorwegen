package nl.ovchipkaart.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OVChipkaartApp extends Application {

    private static Stage mainStage;
    private static boolean darkMode = false;
    private static boolean dutch = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainStage = primaryStage;
        showLoginScreen();
    }

    public static void showLoginScreen() throws Exception {
        FXMLLoader loader = new FXMLLoader(OVChipkaartApp.class.getResource("login-view.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 450, 550);
        String css = darkMode ? "style-dark.css" : "style.css";
        scene.getStylesheets().add(OVChipkaartApp.class.getResource(css).toExternalForm());
        mainStage.setTitle("Binnenlandse Spoorwegen - Login");
        mainStage.setScene(scene);
        mainStage.show();
    }

    public static void showMainScreen(nl.ovchipkaart.model.Account account) throws Exception {
        FXMLLoader loader = new FXMLLoader(OVChipkaartApp.class.getResource("main-view.fxml"));
        Parent root = loader.load();
        MainController controller = loader.getController();
        controller.setAccount(account);
        Scene scene = new Scene(root, 900, 700);
        String css = darkMode ? "style-dark.css" : "style.css";
        scene.getStylesheets().add(OVChipkaartApp.class.getResource(css).toExternalForm());
        mainStage.setTitle("Binnenlandse Spoorwegen - " + account.getName());
        mainStage.setScene(scene);
    }

    public static boolean isDarkMode() { return darkMode; }
    public static void setDarkMode(boolean dark) { darkMode = dark; }
    public static boolean isDutch() { return dutch; }
    public static void setDutch(boolean nl) { dutch = nl; }
    public static Stage getStage() { return mainStage; }

    public static void main(String[] args) {
        launch(args);
    }
}
