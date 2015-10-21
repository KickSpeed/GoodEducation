package de.lindemann.niklas.mamasapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.ListFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String[] mValues;
    private DataSource mDataSource;

    List<MainMenuItem> mItemList = new ArrayList<MainMenuItem>();

    private OnFragmentInteractionListener mListener;

    // TODO: Rename and change types of parameters
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

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
        Log.d("MENUFRAGMENT", "ONCREATED");
        mDataSource = new DataSource(getActivity());

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


                picture = MyResourceManager.getPictureByPosition(getActivity(), position);
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
                List<EntryMenuItem> SubItemList = new ArrayList<EntryMenuItem>();
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
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);

    }

}
