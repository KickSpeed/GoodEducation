package de.lindemann.niklas.mamasapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... params) {
                try{
                    new DataSource(getBaseContext());

                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    finish();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);

            }
        }.execute();
    }


}
