package de.lindemann.niklas.mamasapp;


import android.app.Fragment;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity implements MenuFragment.OnFragmentInteractionListener{

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private String[] mMenuItems = new String[]{"Hauptmenü","Einstellungen"};
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_drawer);
        toolbar.setTitle("Hauptmenü");


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);




        final Fragment menuFragment = new MenuFragment();

        final Fragment settingsFragment = new SettingsFragment();

        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

        };
        getFragmentManager().beginTransaction().replace(R.id.content_frame, menuFragment).commit();

        NavigationView nv = (NavigationView) findViewById(R.id.nvView);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.nav_main:
                        getFragmentManager().beginTransaction().replace(R.id.content_frame, menuFragment).commit();
                        break;
                    case R.id.nav_settings:
                        getFragmentManager().beginTransaction().replace(R.id.content_frame, settingsFragment).commit();
                        break;

                }
                menuItem.setChecked(true);
                setTitle(menuItem.getTitle());
                mDrawerLayout.closeDrawers();

                return false;
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }



    @Override
    public void onFragmentInteraction(String id) {

    }
}
