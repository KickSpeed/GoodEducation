package de.lindemann.niklas.erziehungstipps;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.sql.SQLException;
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


            mDataSource = DataSource.getSingleton(this);
            try {
                mDataSource.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            mSearchItems = mDataSource.getItemsBySearchValue(query);

            if(mSearchItems.size()==0){
                getFragmentManager().beginTransaction().replace(R.id.notFoundPlaceholder,new NothingFoundFragment()).commit();
            }
            else {
                RecyclerView rvResults = (RecyclerView) findViewById(R.id.rvResuls);
                rvResults.setAdapter(new RVAdaper(mSearchItems, this));
                rvResults.setHasFixedSize(true);
                rvResults.setLayoutManager(new LinearLayoutManager(this));
            }
        }
    }

    public void mOpenTextActivity(EntryMenuItem entryMenuItem){
        Intent intent = new Intent(SearchActivity.this,TextActivity.class);
        intent.putExtra("Color", MyResourceManager.getColorByID(this,entryMenuItem.getHauptID()));
        intent.putExtra("ID",entryMenuItem.getId());
        intent.putExtra("Value", entryMenuItem.getUeberschrift());

        startActivity(intent);
    }

}
