package com.example.cantinr.cantinr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
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
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class galleryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapterGallery adapter;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ArrayList<Bitmap> networkImages;
    private String globalResponse;
    private ArrayList<Bitmap> updatedNetworkImages = new ArrayList<>();
    private Context context;
    private RequestQueue queue;

    Intent intentCity;
    Intent intentMensa;
    Intent intentMain;
    Intent inPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarGallery);
        setSupportActionBar(toolbar);
        setTitle("Bildergalerie");
        networkImages = RecyclerAdapterExpandable.networkImages;
        context = this;

        recyclerView =
                (RecyclerView) findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapterGallery();
        recyclerView.setAdapter(adapter);
        Log.d("LENGTH", String.valueOf(networkImages.size()));
        System.out.println(String.valueOf(networkImages.size()));

        adapter.setImages(networkImages);
        adapter.notifyDataSetChanged();

        intentMain = new Intent(this, MainActivity.class);
        // image = findViewById(R.id.imageView);
        intentCity = new Intent(this, CitySelectActivity.class);
        inPhoto = new Intent(this, PhotoActivity.class);
        intentMensa = new Intent(this, MensaSelectActivity.class);
        //cMain = this;
        // photo = findViewById(R.id.bt_photo);
        //cameraView = (CameraView) findViewById(R.id.camera);
        // cameraView.addCameraKitListener(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutGallery);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewGallery);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawerList = (ListView)findViewById(R.id.navList);
        addDrawerItems();

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutGallery);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void updateImages(String id) {
        String apiUrl = "https://api.cantinr.de/v1";
        String uri = apiUrl + "/meals/" + id;

        final AtomicInteger requestsCounter = new AtomicInteger(0);

        queue = Volley.newRequestQueue(context);

        updatedNetworkImages.clear();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            globalResponse = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                        } catch (UnsupportedEncodingException e) {

                        }

                        try {
                            JSONArray array = new JSONArray(globalResponse);

                            System.out.println("RESPONSE IS: ");
                            System.out.println(globalResponse);
                            System.out.println("ARRAY LENGTH IS " + array.length());
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                Log.d("i", String.valueOf(i));
                                Log.d("OBJECT", obj.toString());
                                System.out.println(obj.get("pic").toString());
                                if (obj.get("pic").toString() == "null") {
                                    updatedNetworkImages.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.img1));
                                } else {
                                    //fetch images
                                    for (int j = 0; j < obj.getJSONArray("pic").length(); j++) {
                                        requestsCounter.incrementAndGet();
                                        ImageRequest imageRequest = new ImageRequest(obj.getJSONArray("pic").getString(j), new Response.Listener<Bitmap>() {
                                            @Override
                                            public void onResponse(Bitmap bitmap) {
                                                updatedNetworkImages.add(bitmap);
                                            }
                                        }, 1024, 1024, null, null);
                                        queue.add(imageRequest);
                                    }
                                }
                            }
                            Log.d("IMAGES", updatedNetworkImages.toString());

                            queue.addRequestFinishedListener(request -> {
                                requestsCounter.decrementAndGet();

                                if (requestsCounter.get() == 0) {
                                    networkImages = updatedNetworkImages;
                                    adapter.setImages(updatedNetworkImages);
                                    adapter.notifyDataSetChanged();
                                }
                            });

                        } catch (JSONException e) {
                            Log.d("ERR", e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

    }

}

