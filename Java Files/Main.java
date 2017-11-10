package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static Stage primaryStage;
    public final Model MODEL = new Model();

    @Override
    public void start(Stage window) throws Exception{
        primaryStage = new Stage();
        window = primaryStage;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LibraryView.fxml"));
        Parent root = (Parent) loader.load();
        LibraryController libCont = loader.getController();

        Scene scene = new Scene(root);
        window.setTitle("Fissto - the File Storage App!");

        //Initialize Model
        libCont.initLibraryModel(MODEL);

        window.setScene(scene);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
