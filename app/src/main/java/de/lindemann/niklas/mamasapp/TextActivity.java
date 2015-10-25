package de.lindemann.niklas.mamasapp;

import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.SQLException;

public class TextActivity extends AppCompatActivity {


    private String mValue;
    private int mUnterpunktID;
    private DataSource mDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        String[] text = new String[2];
        super.onCreate(savedInstanceState);

        DBHelper dbOpenHelper = new DBHelper(this, "Ina.sqlite3");

        setContentView(R.layout.activity_text);

        mValue = getIntent().getStringExtra("Value");
        mUnterpunktID = getIntent().getIntExtra("ID", 0);
        setTitle(mValue);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getIntent().getIntExtra("Color", 0)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button btText = (Button) findViewById(R.id.btTextAction);

        btText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TextActivity.this,"Blaaaa wir fahren nach Sillian!",Toast.LENGTH_SHORT).show();
            }
        });

        mDataSource = new DataSource(this);

        try {
            mDataSource.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        text = mDataSource.getTextByUnterpunktID(mUnterpunktID);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(Html.fromHtml(text[0]));



        btText.setText(text[1]);

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
        if(id == android.R.id.home){
            onBackPressed();
        }


        return super.onOptionsItemSelected(item);
    }



}
