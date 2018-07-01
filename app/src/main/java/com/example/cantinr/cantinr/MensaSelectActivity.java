package com.example.cantinr.cantinr;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import cantinrapi.DevcantinrClient;

public class MensaSelectActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

            private ListView mDrawerList;
            private ArrayAdapter<String> mAdapter;
            private ListView mensaListView;
            private ArrayAdapter mensaListAdapter;
            private DevcantinrClient client;
            private String apiUrl;
            Context context;
            Intent intentCity;
            Intent intentMensa;
            Intent intentMain;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_mensa_select);
                context = this;
                getSupportActionBar();
                apiUrl = "https://api.cantinr.de/v1";
                Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMensa);
                toolbar.setTitle("Wähle deine Mensa");
                intentMain = new Intent(this, MainActivity.class);
                intentCity = new Intent(this, CitySelectActivity.class);
                intentMensa = new Intent(this, MensaSelectActivity.class);

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutMensa);
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();

                NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewMensa);
                navigationView.setNavigationItemSelectedListener(this);

                mDrawerList = (ListView)findViewById(R.id.navList);
                addDrawerItems();

                mensaListView = (ListView) findViewById(R.id.mensaListView);
                ArrayList<String> list = new ArrayList<String>();
                mensaListAdapter = new ArrayAdapter(this,
                        android.R.layout.simple_list_item_1, list);
                mensaListView.setAdapter(mensaListAdapter);

                RequestQueue queue = Volley.newRequestQueue(context);
                StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl + "/mensa",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray array = new JSONArray(response);
                                    System.out.println(response);
                                    for (int i = 0; i < array.length(); i++){
                                        String name = array.getJSONObject(i).get("name").toString();
                                        System.out.println(name);
                                        mensaListAdapter.add(name);
                                        mensaListAdapter.notifyDataSetChanged();
                                        System.out.println(mensaListAdapter.getItem(0));

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(stringRequest);

                mensaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (mensaListAdapter.getItem(position).toString() == "Mensaria Metropol") {
                            startActivity(intentMain);
                        } else {
                            Toast.makeText(context, "Kommt bald...", Toast.LENGTH_LONG).show();
                        }
                    }
                });


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

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutMensa);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

    }

