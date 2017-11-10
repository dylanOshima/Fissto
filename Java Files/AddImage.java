package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by DylanOshima on 8/11/16.
 * This is the class for the AlertBox that pops to add images to the library.
 * Accessed: Clicking the '+' button on the menubar
 */

public class AddImage {
    private Model model;

    public void initAddImageModel(Model model){
        //Checks to make sure that there is only one model
        if(this.model != null) {
            System.out.println("Error: More then one model");
        }

        this.model = model;
    }

    //Handler for the main image source
    private String fileLocation;
    public void imageSelectionHandler(ActionEvent event){
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("."));
        chooser.setTitle("Pick an Image");
        File selectedFile = chooser.showOpenDialog(null);

        if (selectedFile != null) {
            fileLocation = selectedFile.getAbsolutePath();
        } else {
            System.out.println("No Selection");
            fileLocation = null;
        }
    }

    //Handler for the image source handler
    private String childLocation;
    public void imageLinkSelectionHandler(ActionEvent event){
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("."));
        chooser.setTitle("Pick an Image");
        File selectedFile = chooser.showOpenDialog(null);

        if (selectedFile != null) {
            childLocation = selectedFile.getAbsolutePath();
        } else {
            System.out.println("No Selection");
            childLocation = null;
        }
    }

    //Submitting an image to the library from the AddImagePage
    public TextField nameInput;
    public TextField tagsInput;
    public TextField commentInput;
    public TextField pointsInput;
    public Label errorMessage;

    public void submitImage(){
        if(!(nameInput.getText().trim().isEmpty()) && !(tagsInput.getText().trim().isEmpty()) && !(pointsInput.getText().trim().isEmpty())) {
            if (isInt(pointsInput) && fileLocation != null) {
                ArrayList<String> s = Image.getTagOrganizer(',', tagsInput.getText().toUpperCase()); //organizes tags and puts them into an ArrayList

                //TODO implement the child location so that image switching is available
                model.addImage(new Image(nameInput.getText(),s,commentInput.getText(), fileLocation /*, childLocation*/, Integer.parseInt(pointsInput.getText())));
                System.out.println("Image added to model array");

                LibraryController.addImageWindow.close();
            }
        }else {
            errorMessage.setText("Fill every field");
        }
    }

    //Submission format verifiers, checks that the point input is actually an integer
    private boolean isInt(TextField input){
        try{
            int i = Integer.parseInt(input.getText());
            errorMessage.setText("");
            return true;
        }catch (NumberFormatException e){
            System.out.println("Oh shit: " + input.getText() + " is not an integer");
            errorMessage.setText("Points must be a number");
            return false;
        }
    }

    public void cancelButtonHandler(){
        LibraryController.addImageWindow.close();
    }

}
