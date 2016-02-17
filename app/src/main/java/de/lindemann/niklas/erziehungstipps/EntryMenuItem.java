package de.lindemann.niklas.erziehungstipps;

/**
 * Created by Niklas on 21.10.2015.
 */
public class EntryMenuItem {
    private String value;
    private int id;
    private String ueberschrift;
    private String btnlabel;
    private int hauptID;



    public EntryMenuItem(String value, int id, String ueberschrift, String btnlabel, int hauptID){
        this.value = value;
        this.id = id;
        this.ueberschrift = ueberschrift;
        this.btnlabel = btnlabel;
        this.hauptID = hauptID;
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

    public int getHauptID() {
        return hauptID;
    }

    public void setHauptID(int hauptID) {
        this.hauptID = hauptID;
    }
}
