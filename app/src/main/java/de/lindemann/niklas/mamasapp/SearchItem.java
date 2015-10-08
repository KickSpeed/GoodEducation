package de.lindemann.niklas.mamasapp;

/**
 * Created by Niklas on 29.09.2015.
 */
public class SearchItem {

    private String label;
    private String text;
    private int id;

    public int getHauptID() {
        return hauptID;
    }

    public void setHauptID(int hauptID) {
        this.hauptID = hauptID;
    }

    private int hauptID;

    public SearchItem(int id,String label,String text, int hauptID){
        this.id = id;
        this.label = label;
        this.text = text;
        this.hauptID = hauptID;
    }

    public String getlabel() {
        return label;
    }

    public void setlabel(String label) {
        this.label = label;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.label;
    }
}
