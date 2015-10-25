package de.lindemann.niklas.mamasapp;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private DataSource mDataSource;

    private List<EntryMenuItem> mSearchItems;

    private DialogInterface.OnClickListener mDialogListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        handleIntent(getIntent());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("Suchergebnisse");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDialogListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(getBaseContext(),
                                MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
                        suggestions.clearHistory();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.verlauf_loeschen).setPositiveButton(R.string.Ja, mDialogListener)
                    .setNegativeButton(R.string.Nein, mDialogListener).show();


            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,MySuggestionProvider.AUTHORITY,MySuggestionProvider.MODE);
            suggestions.saveRecentQuery(query,null);


            mDataSource = new DataSource(this);
            try {
                mDataSource.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            mSearchItems = mDataSource.getItemsBySearchValue(query);

            BaseAdapter adapter = new BaseAdapter() {
                @Override
                public int getCount() {
                    return mSearchItems.size();
                }

                @Override
                public Object getItem(int position) {
                    return mSearchItems.get(position);
                }

                @Override
                public long getItemId(int position) {
                    return 0;
                }

                @Override
                public View getView(int position, View convertView, ViewGroup parent) {

                    View listenZeile = convertView;

                    if (listenZeile == null){
                        listenZeile = getLayoutInflater().inflate(R.layout.list_item_entry,parent,false);
                    }

                    EntryMenuItem entryMenuItem = mSearchItems.get(position);

                    TextView textViewUeberschrift = (TextView) listenZeile.findViewById(R.id.textViewUeberschrift);
                    TextView textViewEntry = (TextView) listenZeile.findViewById(R.id.TextViewTextEntry);
                    ImageView imageViewEntry = (ImageView) listenZeile.findViewById(R.id.imageViewFigure);

                    textViewUeberschrift.setText(entryMenuItem.getUeberschrift());
                    textViewUeberschrift.setTextColor(MyResourceManager.getColorByID(SearchActivity.this,entryMenuItem.getHauptID()));
                    imageViewEntry.setImageResource(R.drawable.stickfigure1);

                    textViewEntry.setText(entryMenuItem.getValue());

                    return listenZeile;
                }
            };

            final ListView listView = (ListView) findViewById(R.id.listViewResults);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    EntryMenuItem searchItem = (EntryMenuItem) listView.getItemAtPosition(position);

                    Intent intent = new Intent(SearchActivity.this,TextActivity.class);
                    intent.putExtra("ID",searchItem.getId());
                    intent.putExtra("Color",MyResourceManager.getColorByID(SearchActivity.this, mDataSource.getHauptIDByUnterpunktID(searchItem.getHauptID())));
                    intent.putExtra("Value", searchItem.getValue());
                    startActivity(intent);
                }
            });


        }
    }

}
