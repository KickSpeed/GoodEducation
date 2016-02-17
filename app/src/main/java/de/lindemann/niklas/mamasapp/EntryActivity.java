package de.lindemann.niklas.mamasapp;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntryActivity extends AppCompatActivity{

    private String[] mValues;
    private DataSource mDataSource;
    private int mID;
    private String mValue;
    private BaseAdapter mEntryAdapter;
    private RecyclerView mListViewEntry;
    private CardView mCardView;
    List<EntryMenuItem> mItemList = new ArrayList<>();




    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        mID = getIntent().getIntExtra("ID", 0);
        mValue = getIntent().getStringExtra("Value");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getIntent().getIntExtra("Color", 0)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(mValue);

        mDataSource = DataSource.getSingleton(this);


        try {
            mDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mItemList = mDataSource.getSubItemsByID(Integer.toString(mID));

        if (mItemList.size() == 1) {
            EntryMenuItem entryMenuItem = mItemList.get(0);
            Intent intent = new Intent(EntryActivity.this, TextActivity.class);
            intent.putExtra("Color", EntryActivity.this.getIntent().getIntExtra("Color", 0));
            intent.putExtra("ID", entryMenuItem.getId());
            intent.putExtra("Value", entryMenuItem.getValue());
            startActivity(intent);
        }

        mListViewEntry = (RecyclerView) findViewById(R.id.rvEntry);
        mListViewEntry.setHasFixedSize(true);



        mListViewEntry.setAdapter(new RVAdaper(mItemList,this));

        mListViewEntry.setLayoutManager(new LinearLayoutManager(this));

    }


    public void mOpenTextActivity(EntryMenuItem entryMenuItem){
        Intent intent = new Intent(EntryActivity.this,TextActivity.class);
        intent.putExtra("Color", getIntent().getIntExtra("Color", 0));
        intent.putExtra("ID",entryMenuItem.getId());
        intent.putExtra("Value", entryMenuItem.getUeberschrift());

        startActivity(intent);
    }

    @Override
    protected void onPostResume() {

        mListViewEntry.setAdapter(new RVAdaper(mItemList,this));

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
