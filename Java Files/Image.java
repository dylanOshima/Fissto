package sample;

import java.util.ArrayList;

/**
 * Created by DylanOshima on 8/12/16.
 */

public class Image {
    private String name;
    private ArrayList<String> tags;
    private String comments;
    private String fileLocation;
    private String linkFileLocation;
    private int points;

    //Note: Default tag[] size is 0
    public Image(){
        this.name = "";
        this.tags = new ArrayList<String>();
        this.comments = "";
        this.fileLocation = "";
        this.points = 0;
        this.linkFileLocation = "";
    }
    //Points does not need to be defined
    public Image(String name, ArrayList<String> tags, String comments, String filelocation){
        this.name = name;
        this.tags = tags;
        this.comments = comments;
        this.fileLocation = filelocation;
    }

    //Necessary information is given
    public Image(String name, ArrayList<String> tags, String comments, String filelocation, int points){
        this.name = name;
        this.tags = tags;
        this.comments = comments;
        this.fileLocation = filelocation;
        this.points = points;
    }

    //All inputs given
    public Image(String name, ArrayList<String> tags, String comments, String filelocation, String linkFileLocation, int points){
        this.name = name;
        this.tags = tags;
        this.comments = comments;
        this.fileLocation = filelocation;
        this.linkFileLocation = linkFileLocation;
        this.points = points;
    }

    //Runs through the tag string and puts each tag in an ArrayList
    public static ArrayList<String> getTagOrganizer(Character key, String s){
        //TODO: Tidy up code, kinda messy

        //Removes the BracketsBug
        if(s.charAt(0) == '['){
            s = s.substring(1,(s.length()-1));
        }

        ArrayList<String> list = new ArrayList<String>();
        s = s + ","; //Otherwise it won't get the last tag
        try{
            for(int i=0;i<s.length();i++){
                boolean flag;
                int j = i+1;
                do{
                    flag = true;
                    if(s.charAt(j) == key){
                        list.add(s.substring(i,j));
                        flag = false;
                    }
                    j++;
                }while(flag);
                i=j;
            }
        }catch (IndexOutOfBoundsException e){}
        return list;
    }


    //Getters and Setters for the Photo properties
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getFilelocation() {
        return fileLocation;
    }

    public void setFilelocation(String filelocation) {
        this.fileLocation = filelocation;
    }

    public String getLinkFileLocation() {
        return linkFileLocation;
    }

    public void setLinkFileLocation(String filelocation) {
        this.linkFileLocation = filelocation;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
