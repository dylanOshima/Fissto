package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LibraryController implements Initializable{
    private Model model;
    private Image selectedImage;

    public void initLibraryModel(Model model){
        //Checks to make sure that there is only one model instance
        if(this.model != null){
            System.out.println("Error: More then one model");
        }

        this.model = model;

        //Load the file
        model.loadFromFile();
        updateImageLibrary();
        System.out.println("File Loaded");
    }

    @FXML Label imageWindowTitle;
    @FXML ImageView imageWindowImage;
    @FXML Label imageWindowComment;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Listens for a row selection in the library TableView
        library.getSelectionModel().selectedItemProperty().addListener( (v, oldValue, newValue) -> {
            selectedImage = newValue;
            imageWindowTitle.setText(selectedImage.getName());
            imageWindowComment.setText(selectedImage.getComments());
            try {
                imageWindowImage.setImage(new javafx.scene.image.Image("file:" + selectedImage.getFilelocation()));
            } catch (Exception e){
                System.out.println("Uh oh: " + e);
            }

            
        });

        library.getSelectionModel();

        System.out.println("LibraryPage Loaded");
    }

    //Library TableView Controllers
    @FXML public TableView<Image> library;
    @FXML private TableColumn<Image, String> NameColumn  = new TableColumn<>();
    @FXML private TableColumn<Image, ArrayList<String>> TagsColumn  = new TableColumn<>();
    @FXML private TableColumn<Image, String> CommentsColumn  = new TableColumn<>();
    @FXML private TableColumn<Image, String> FileLocationColumn  = new TableColumn<>();
    @FXML private TableColumn<Image, Integer> PointsColumn = new TableColumn<>();

    private void updateImageLibrary(){
        library.getItems().clear();
        for(int i = 0; i < model.getSize(); i++){
            library.getItems().add(model.getImage(i));
            addDataToColumns();
        }
    }

    public void addDataToColumns(){
        NameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TagsColumn.setCellValueFactory(new PropertyValueFactory<>("tags")); //TODO Convert to String format
        CommentsColumn.setCellValueFactory(new PropertyValueFactory<>("comments"));
        FileLocationColumn.setCellValueFactory(new PropertyValueFactory<>("filelocation"));
        PointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
    }

    //Creates the window for adding pictures
    public void setAddPicStage() throws Exception{
        display();
        updateImageLibrary();
    }

    public static Stage addImageWindow;

    public void display() throws Exception{
        addImageWindow = new Stage();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddImagePage.fxml"));
        Parent root = (Parent) loader.load();
        AddImage addImage = loader.getController();
        Scene scene = new Scene(root);

        //Initialize Model
        addImage.initAddImageModel(model);

        addImageWindow.initModality(Modality.APPLICATION_MODAL); //Makes it so that the screen behind isn't accessible while the add image screen is open
        addImageWindow.setTitle("Add an image to Fissto library");
        addImageWindow.setScene(scene);
        addImageWindow.showAndWait();
    }

    //Removing images from the library
    public void handleDeletePressed(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.BACK_SPACE){
            ObservableList<Image> imageSelected, allImages;
            allImages = library.getItems();
            imageSelected = library.getSelectionModel().getSelectedItems();

            System.out.println(library.getSelectionModel().getSelectedItems());
            model.deleteItem(imageSelected);
            imageSelected.forEach(allImages::remove);
        }
    }

    //Searches for a certain key which is equal to the searchBar
    @FXML private TextField SearchBar;
    public void handleSearch(KeyEvent event){
        String key = SearchBar.getText();

        if(event.getCode() == KeyCode.ENTER){
            if(key.equals("")){                 //To show all files in the library
                library.getItems().clear();
                SearchBar.clear();

                updateImageLibrary();
                model.test(key);

            }else {
                library.getItems().clear();
                SearchBar.clear();

                ArrayList<Image> search = model.searchList(key);
                for(int i=0;i<search.size();i++){
                    library.getItems().add(search.get(i));
                    addDataToColumns();
                }
                model.test(key);
            }
            model.test("Searched");
        }

    }

    //Listens for when the window closes
    public void handleExitButton(){
        //Updates the save.txt file
        model.saveToFile();
        System.out.println("File Saved");

        Main.primaryStage.close();
    }

    //Opens the image using the default image launching application
    public void imageButton(){
        try{
            Desktop.getDesktop().open(new File(selectedImage.getFilelocation()));
        } catch(Exception e){
            System.out.println("Uh oh: " + e);
        }
    }

    //Updates the image to the linked image
    public void changeLinkImageButton(){
        //TODO implement the image switch button so that it reads an image from the other source when this button is selected
        //imageWindowImage.setImage(new javafx.scene.image.Image("file:" + selectedImage.getLinkFileLocation()));
    }

}
