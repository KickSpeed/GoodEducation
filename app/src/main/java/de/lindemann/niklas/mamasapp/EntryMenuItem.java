package de.lindemann.niklas.mamasapp;

/**
 * Created by Niklas on 21.10.2015.
 */
public class EntryMenuItem {
    private String value;
    private int id;
    private String ueberschrift;
    private String btnlabel;

    public EntryMenuItem(String value, int id, String ueberschrift, String btnlabel){
        this.value = value;
        this.id = id;
        this.ueberschrift = ueberschrift;
        this.btnlabel = btnlabel;
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

    public String getUeberschrift() {
        return ueberschrift;
    }

    public void setUeberschrift(String ueberschrift) {
        this.ueberschrift = ueberschrift;
    }

    public String getBtnlabel() {
        return btnlabel;
    }

    public void setBtnlabel(String btnlabel) {
        this.btnlabel = btnlabel;
    }
}
