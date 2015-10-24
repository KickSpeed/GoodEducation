package de.lindemann.niklas.mamasapp;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntryActivity extends AppCompatActivity{

    private String[] mValues;
    private DataSource mDataSource;
    private int mID;
    private String mValue;
    private BaseAdapter mEntryAdapter;
    private ListView mListViewEntry;
    List<EntryMenuItem> mItemList = new ArrayList<EntryMenuItem>();


    private void createAdapter(){
        mEntryAdapter = new BaseAdapter() {
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

                if (listenZeile == null){
                    listenZeile = getLayoutInflater().inflate(R.layout.list_item_entry,parent,false);
                }

                EntryMenuItem entryMenuItem = mItemList.get(position);

                TextView textViewUeberschrift = (TextView) listenZeile.findViewById(R.id.textViewUeberschrift);
                TextView textViewEntry = (TextView) listenZeile.findViewById(R.id.TextViewTextEntry);
                ImageView imageViewEntry = (ImageView) listenZeile.findViewById(R.id.imageViewFigure);

                textViewUeberschrift.setText(entryMenuItem.getUeberschrift());
                textViewUeberschrift.setTextColor(getIntent().getIntExtra("Color",0));
                imageViewEntry.setImageResource(R.drawable.stickfigure1);

                textViewEntry.setText(entryMenuItem.getValue());

                return listenZeile;
            }
        };
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        


        mID = getIntent().getIntExtra("ID",0);
        mValue = getIntent().getStringExtra("Value");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getIntent().getIntExtra("Color", 0)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mValue);

        mDataSource = new DataSource(this);

        try {
            mDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mItemList = mDataSource.getSubItemsByID(Integer.toString(mID));

        if(mItemList.size()==1){
            EntryMenuItem entryMenuItem = mItemList.get(0);
            Intent intent = new Intent(EntryActivity.this,TextActivity.class);
            intent.putExtra("Color",EntryActivity.this.getIntent().getIntExtra("Color",0));
            intent.putExtra("ID",entryMenuItem.getId());
            intent.putExtra("Value",entryMenuItem.getValue());
            startActivity(intent);
        }

        mListViewEntry = (ListView) findViewById(R.id.listViewEntry);

        createAdapter();

        mListViewEntry.setAdapter(mEntryAdapter);

        mListViewEntry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EntryMenuItem entryMenuItem = (EntryMenuItem) mListViewEntry.getItemAtPosition(position);
                Intent intent = new Intent(EntryActivity.this,TextActivity.class);
                intent.putExtra("Color", getIntent().getIntExtra("Color", 0));
                intent.putExtra("ID",entryMenuItem.getId());
                intent.putExtra("Value", entryMenuItem.getUeberschrift());

                startActivity(intent);
            }
        });




    }

    @Override
    protected void onPostResume() {

        createAdapter();
        mListViewEntry.setAdapter(mEntryAdapter);
        super.onPostResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
