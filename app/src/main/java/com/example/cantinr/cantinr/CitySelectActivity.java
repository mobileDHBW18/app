package com.example.cantinr.cantinr;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import cantinrapi.DevcantinrClient;

public class CitySelectActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private EditText cityEdit;
    private Button searchButton;
    private ListView cityListView;
    private ArrayAdapter adapter;
    Intent intentCity;
    Intent intentMensa;
    Intent intentMain;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);

        getSupportActionBar();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCity);
        toolbar.setTitle("Wähle deine Stadt");
        context = this;
        intentMain = new Intent(this, MainActivity.class);
        intentCity = new Intent(this, CitySelectActivity.class);
        intentMensa = new Intent(this, MensaSelectActivity.class);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutCity);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewCity);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawerList = (ListView)findViewById(R.id.navList);
        addDrawerItems();

        cityEdit = (EditText) findViewById(R.id.cityEdit);
        searchButton = (Button) findViewById(R.id.searchButton);

        cityListView = (ListView) findViewById(R.id.cityListView);
        ArrayList<String> list = new ArrayList<String>();
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        cityListView.setAdapter(adapter);

        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (id == 0) {
                    startActivity(intentMensa);
                } else {
                    Toast.makeText(context, "Bisher nur Mannheim - sorry!", Toast.LENGTH_LONG).show();
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityEdit.setText("Mannheim");
                adapter.add("Mannheim");
                adapter.add("Deine Stadt, bald hier");
                adapter.notifyDataSetChanged();

            }
        });
        //Check for cities with these names

        // if nothing is found, display a message



    }

    private void addDrawerItems() {
        String[] osArray = { "Android", "iOS", "Windows", "OS X", "Linux", "FreeBSD" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_city) {
            // Handle the camera action
            //Toast.makeText(MainActivity.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();
            startActivity(intentCity);

        } else if (id == R.id.nav_mensa) {
            //Toast.makeText(MainActivity.this, "Jasmin", Toast.LENGTH_SHORT).show();
            startActivity(intentMensa);

        } else if (id == R.id.nav_last) {
            //Toast.makeText(MainActivity.this, "Here!", Toast.LENGTH_SHORT).show();
            startActivity(intentMain);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutCity);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

