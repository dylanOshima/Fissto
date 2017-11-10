package sample;

import javafx.collections.ObservableList;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Created by DylanOshima on 10/20/16.
 *
 * Contains the main storage class here as well as the reading and writing of the file.
 *
 */
public class Model {
    private ArrayList<Image> library = new ArrayList<>();

    public void addImage(Image image){
        System.out.println("Here");
        library.add(image);
    }

    public Image getImage(int i){
        return library.get(i);
    }

    public int getSize(){return library.size();}

    //Searches the array for the ones that match the key, currently using BINARY SEARCH
    public ArrayList<Image> searchList(String key){
        ArrayList<Image> list = new ArrayList<>();

        for(int i = 0; i < getSize(); i++){
            if (getImage(i).getTags().contains(key.toUpperCase())) {
                list.add(getImage(i));
            }
        }

        return list;
    }

    //Runs through each item in the library, and checks if any of them are equal to the imageSelected, and if they are it removes them.
    public void deleteItem(ObservableList<Image> imageSelected){
        for(int i=0;i<library.size();i++){
            for(int j=0;j<imageSelected.size();j++) {
                if (library.get(i).equals(imageSelected.get(j))) {
                    library.remove(i);
                }
            }
        }
    }

    /* File Access methods
    */

    //Saves the library arraylist
    private String saveLocation;
    private File file;
    public void saveToFile(){
        try{
            PrintWriter output = new PrintWriter(file);

            for(Image i : library){
                output.println(i.getName());
                output.println(i.getTags());
                output.println(i.getComments());
                output.println(i.getFilelocation());
                output.println(i.getPoints());
            }

            //Tells the program to stop reading
            output.println("END");

            output.close();
        } catch (IOException e){
            System.out.println("Uh oh: " + e);
        }
    }

    //Updates the library arraylist
    public void loadFromFile(){
        saveLocation = getFileLocation();
        if(saveLocation == null){
            saveLocation = getSaveLocation();
        }

        file = new File(saveLocation);
        try {
            Scanner input = new Scanner(file);
            do{
                String name = input.nextLine();
                String tagsString = input.nextLine();
                String comments = input.nextLine();
                String fileLocation = input.nextLine();
                String points = input.nextLine();

                ArrayList<String> tags = Image.getTagOrganizer(',',tagsString);
                Image i = new Image(name, tags, comments, fileLocation, Integer.parseInt(points));
                library.add(i);
            } while (!input.hasNext("END"));

        } catch (FileNotFoundException e){
            System.out.println("Uh oh: " + e);
        } catch (NoSuchElementException e){
            System.out.println("Uh oh: " + e);
        }
    }

    //Returns the path of the selected directory
    public String getFileLocation(){
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("."));
        chooser.setTitle("Fissto - Pick a save file");
        File selectedFile = chooser.showOpenDialog(null);

        if (selectedFile != null) {
            return selectedFile.getAbsolutePath();
        } else {
            return null;
        }
    }

    //Returns the directory of the location for the save file
    public String getSaveLocation(){
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File("."));
        chooser.setTitle("Fissto - Pick a directory to save in");
        File selectedDirectory = chooser.showSaveDialog(null);

        if (selectedDirectory != null) {
             return selectedDirectory.getAbsolutePath();
        } else {
            return ".";
        }
    }

    public void test(String s){
        System.out.println("Model: " + s);
    }

}
