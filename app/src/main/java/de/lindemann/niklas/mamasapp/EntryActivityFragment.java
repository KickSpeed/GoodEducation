package de.lindemann.niklas.mamasapp;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class EntryActivityFragment extends ListFragment {


    private String[] mValues;
    private DataSource mDataSource;
    private int mID;
    private String mValue;
    private OnFragmentInteractionListener mListener;
    List<MainMenuItem> mItemList = new ArrayList<MainMenuItem>();


    public EntryActivityFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mID = getActivity().getIntent().getIntExtra("ID",0);
        mValue = getActivity().getIntent().getStringExtra("Value");
        getActivity().setTitle(mValue);

        mDataSource = new DataSource(getActivity());

        try {
            mDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mItemList = mDataSource.getSubItemsByID(Integer.toString(mID));

        if(mItemList.size()==1){
            MainMenuItem mainMenuItem = mItemList.get(0);
            Intent intent = new Intent(getActivity(),TextActivity.class);
            intent.putExtra("Color",getActivity().getIntent().getIntExtra("Color",0));
            intent.putExtra("ID",mainMenuItem.getId());
            intent.putExtra("Value",mainMenuItem.getValue());
            startActivity(intent);
        }

        setListAdapter(new ArrayAdapter<MainMenuItem>(getActivity(),
                R.layout.list_item_main, R.id.ListViewText, mItemList));


    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (mListener != null){
            MainMenuItem mainMenuItem = (MainMenuItem) l.getItemAtPosition(position);
            Intent intent = new Intent(getActivity(),TextActivity.class);
            intent.putExtra("Color",getActivity().getIntent().getIntExtra("Color",0));
            intent.putExtra("ID",mainMenuItem.getId());
            intent.putExtra("Value",mainMenuItem.getValue());

            //intent.putExtra("DBHelper",mDataSource);
            startActivity(intent);

        }


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);

    }
}
