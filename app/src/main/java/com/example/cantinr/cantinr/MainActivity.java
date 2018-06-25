package com.example.cantinr.cantinr;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CameraKitEventListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private String currentDate;
    private String apiUrl;
    private Context context;
    private ArrayList<String> titles;
    private ArrayList<String> ingrediences;
    private ArrayList<Integer> images;

    Intent intentCity;
    Intent intentMensa;
    Intent intentMain;
    Intent inPhoto;
    //public Context cMain;
   // ImageView image;
    Button photo;
    CameraView cameraView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Mensaria Metropol");

        context = this;
        apiUrl = "https://api.cantinr.de/v1";
        System.out.println(apiUrl);

        recyclerView =
                (RecyclerView) findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        titles = new ArrayList<String>();
        ingrediences= new ArrayList<>();
        images = new ArrayList<>();

        currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String uri = null;
        uri = String.format(apiUrl + "/meals?date=%1$s&mensa=%2$s", "2018-06-20", "DHBW Mannheim Mensaria Metropol".replace(" ", "%20"));
        System.out.println(uri);

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            System.out.println("RESPONSE IS: ");
                            System.out.println(response);
                            for (int i = 0; i < array.length(); i++){
                                JSONObject obj = array.getJSONObject(i);
                                JSONArray responeIngrediences = obj.getJSONArray("ingrediences");
                                String name = ingrediences.get(0).toString();
                                String ingredience = responeIngrediences.get(1).toString();
                                ingrediences.add(ingredience);
                                titles.add(name);
                                System.out.println(name);

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

        adapter.setDataSet(titles, ingrediences, images);
        adapter.notifyDataSetChanged();

        intentMain = getIntent();
       // image = findViewById(R.id.imageView);
        intentCity = new Intent(this, CitySelectActivity.class);
        inPhoto = new Intent(this, PhotoActivity.class);
        intentMensa = new Intent(this, MensaSelectActivity.class);
        //cMain = this;
       // photo = findViewById(R.id.bt_photo);
        //cameraView = (CameraView) findViewById(R.id.camera);
       // cameraView.addCameraKitListener(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawerList = (ListView)findViewById(R.id.navList);
        addDrawerItems();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cameraView.captureImage();
                startActivity(inPhoto);
            }
        });


    }

    @Override
    protected void onResume() {
      super.onResume();
      //  cameraView.start();
    }

    @Override
    protected void onPause() {
        //  cameraView.stop();
       super.onPause();
    }

    public void onPhotoClick(View aView){
       //cameraView.start();
    }

    public void onPhotoEnd(View aView){
       // cameraView.stop();
    }

    public void photoShot(View aView){
       // cameraView.captureImage();
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
            
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onEvent(CameraKitEvent cameraKitEvent) {

    }

    @Override
    public void onError(CameraKitError cameraKitError) {

    }

    @Override
    public void onImage(CameraKitImage picture) {
       // picture.getJpeg();
       // Bitmap pic = picture.getBitmap();
      //  Toast.makeText(MainActivity.this, "Here!", Toast.LENGTH_SHORT).show();
      //  image.setImageBitmap(pic);
    }

    @Override
    public void onVideo(CameraKitVideo cameraKitVideo) {

    }
}
