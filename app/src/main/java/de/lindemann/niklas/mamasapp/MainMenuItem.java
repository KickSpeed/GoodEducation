package de.lindemann.niklas.mamasapp;

/**
 * Created by Niklas on 17.09.2015.
 */
public class MainMenuItem {
    private String value;
    private int id;

    public MainMenuItem(String value, int id){
        this.value = value;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
