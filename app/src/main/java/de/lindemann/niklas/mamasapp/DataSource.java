package de.lindemann.niklas.mamasapp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niklas on 18.09.2015.
 */
public class DataSource {

    private SQLiteDatabase mDatabase;
    private DBHelper mDBHelper;

    private String[] mColumnsMain = {"_id","Label"};
    private String[] mColumnsUnterpunkt = {"_id","Label","Text","Ueberschrift","BtnLabel","HauptpunktID"};


    public DataSource(Context context){
        mDBHelper = new DBHelper(context,"Ina.sqlite3");

    }

    public void open() throws SQLException {
        mDatabase = mDBHelper.getWritableDatabase();
    }

    public void close(){
        mDatabase.close();
    }

    public List<MainMenuItem> getAllItems(){
        List<MainMenuItem> itemList = new ArrayList<MainMenuItem>();
        Cursor cursor = mDatabase.query("Hauptpunkt",
                mColumnsMain,null,null,null,null,null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            MainMenuItem mainMenuItem = populateItem(cursor);
            itemList.add(mainMenuItem);
            cursor.moveToNext();

        }

        cursor.close();
        return itemList;
    }

    public List<EntryMenuItem> getSubItemsByID(String ID){
        List<EntryMenuItem> itemList = new ArrayList<EntryMenuItem>();
        Cursor cursor = mDatabase.query("Unterpunkt",
                mColumnsUnterpunkt,"HauptpunktID = " + ID,null,null,null,null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()){
            EntryMenuItem entryMenuItem = populateEntryItem(cursor);
            itemList.add(entryMenuItem);
            cursor.moveToNext();

        }

        cursor.close();
        return itemList;
    }

    public String getTextByUnterpunktID(int unterpunktID){
        String Text;
        Cursor cursor = mDatabase.query("Unterpunkt",mColumnsUnterpunkt,"_id = " + Integer.toString(unterpunktID),null,null,null,null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()){
            Text = cursor.getString(cursor.getColumnIndex("Text"));
            return Text;
        }
        else return  "";
    }

    public List<EntryMenuItem> getItemsBySearchValue(String searchValue){
        String text;
        List<EntryMenuItem> resultList = new ArrayList<>();

        Cursor cursor = mDatabase.rawQuery("SELECT * FROM Unterpunkt WHERE " +
                "Label LIKE '%" + searchValue + "%' " +
                "OR Text LIKE '%" + searchValue + "%' " +
                "OR Ueberschrift LIKE '%" + searchValue + "%'",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            EntryMenuItem searchItem = populateEntryItem(cursor);
            resultList.add(searchItem);
            cursor.moveToNext();
        }
        cursor.close();

        return resultList;
    }

    public int getHauptIDByUnterpunktID(int UnterpunktID){

        Cursor cursor = mDatabase.query("Hauptpunkt",mColumnsMain,"_id = " + Integer.toString(UnterpunktID),null,null,null,null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            return cursor.getInt(cursor.getColumnIndex("_id"));
        }
        return 0;
    }




    private MainMenuItem populateItem(Cursor cursor){
        int idIndex = cursor.getColumnIndex("_id");
        int LabelIndex = cursor.getColumnIndex("Label");

        MainMenuItem item = new MainMenuItem(cursor.getString(LabelIndex),cursor.getInt(idIndex));

        return item;

    }

    private EntryMenuItem populateEntryItem(Cursor cursor){
        int idIndex = cursor.getColumnIndex("_id");
        int LabelIndex = cursor.getColumnIndex("Label");
        int UeberschriftIndex = cursor.getColumnIndex("Ueberschrift");
        int BtnLabelIndex = cursor.getColumnIndex("BtnLabel");
        int HauptIDIndex= cursor.getColumnIndex("HauptpunktID");

        EntryMenuItem item = new EntryMenuItem(cursor.getString(LabelIndex),
                cursor.getInt(idIndex),
                cursor.getString(UeberschriftIndex),
                cursor.getString(BtnLabelIndex),
                cursor.getInt(HauptIDIndex));

        return item;

    }







}
