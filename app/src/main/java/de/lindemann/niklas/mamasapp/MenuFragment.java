package de.lindemann.niklas.mamasapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.ListFragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class MenuFragment extends ListFragment {
    private DataSource mDataSource;

    List<MainMenuItem> mItemList = new ArrayList<MainMenuItem>();

    private OnFragmentInteractionListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MenuFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDataSource =  DataSource.getSingleton(getActivity());

        try {
            mDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mItemList = mDataSource.getAllItems();

        setListAdapter(new BaseAdapter() {
            Bitmap picture;

            @Override
            public int getCount() {
                return mItemList.size();
            }

            @Override
            public Object getItem(int position) {
                return mItemList.get(position);
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View listenZeile = convertView;


                if (listenZeile == null) {
                    listenZeile = getActivity().getLayoutInflater().inflate(R.layout.list_item_main, parent, false);
                }
                MainMenuItem mainMenuItem = mItemList.get(position);

                ImageView imageView = (ImageView) listenZeile.findViewById(R.id.imageViewPuzzle);
                TextView textView = (TextView) listenZeile.findViewById(R.id.ListViewText);

                textView.setText(mainMenuItem.getValue());


                picture = MyResourceManager.getPictureByPosition(getActivity(), mainMenuItem.getId());
                if (picture != null) {
                    imageView.setImageBitmap(picture);
                }

                return listenZeile;
            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.fab_color)));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EmergencyActivity.class);
                startActivity(intent);

            }
        });
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

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent;
        if (null != mListener) {

            MainMenuItem mainMenuItem = (MainMenuItem) l.getItemAtPosition(position);
            if(mainMenuItem.getId()!=1) {
                intent = new Intent(getActivity(), EntryActivity.class);
                //intent.putExtra("Color",getColorByID(mainMenuItem.getId()));
                intent.putExtra("Color", MyResourceManager.getColorByID(getActivity(), mainMenuItem.getId()));
                intent.putExtra("ID", mainMenuItem.getId());
                intent.putExtra("Value", mainMenuItem.getValue());
            }
            else{
                List<EntryMenuItem> SubItemList = new ArrayList<>();
                SubItemList = mDataSource.getSubItemsByID(Integer.toString(mainMenuItem.getId()));
                intent = new Intent(getActivity(), TextActivity.class);
                //intent.putExtra("Color",getColorByID(mainMenuItem.getId()));
                intent.putExtra("Color", MyResourceManager.getColorByID(getActivity(), mainMenuItem.getId()));
                intent.putExtra("ID", SubItemList.get(0).getId());
                intent.putExtra("Value", SubItemList.get(0).getValue());
            }
            startActivity(intent);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String id);

    }

}
