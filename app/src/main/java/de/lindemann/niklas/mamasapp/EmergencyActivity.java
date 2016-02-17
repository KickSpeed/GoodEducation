package de.lindemann.niklas.mamasapp;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

public class EmergencyActivity extends AppCompatActivity {

    private TextView mTextView;
    private MediaPlayer mMediaPlayer;
    private CountDownTimer mTimer;
    private ListView mListViewEmergency;
    private BaseAdapter mEmergencyAdapter;


    private void mCreateAdapter(){
        mEmergencyAdapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return null;
            }
        };
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.fab_color)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        mTextView = (TextView) findViewById(R.id.textViewTimer);
        mListViewEmergency = (ListView) findViewById(R.id.listViewEmergency);

        //mListViewEmergency.

        if(prefs.getBoolean("checkbox_gong",true)) {
            mTimer = new CountDownTimer(20000, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {
                    mTextView.setText("Bitte noch " + millisUntilFinished / 1000 + " Sekunden ruhig bleiben!");
                }

                @Override
                public void onFinish() {

                    mTextView.setText("Gong!");
                    Uri path = Uri.parse("android.resource://de.lindemann.niklas.mamasapp/" + R.raw.gong);
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mMediaPlayer.setDataSource(getApplicationContext(), path);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    try {
                        mMediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mMediaPlayer.start();
                }
            }.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTimer!=null)
        mTimer.cancel();
        if(mMediaPlayer!=null)
        mMediaPlayer.reset();
    }
}
