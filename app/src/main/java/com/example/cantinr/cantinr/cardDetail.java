package com.example.cantinr.cantinr;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

public class cardDetail extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Intent intent;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapterExpandable adapter;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    RatingBar rb;
    Intent intentCity;
    Intent intentMensa;
    Intent intentMain;
    Intent inPhoto;

    Button btChickenGrey;
    //public Context cMain;
    // ImageView image;
    Button photo;
    CameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_detail);


        intent = getIntent();
        int pos;
        pos = intent.getIntExtra("position", 0);
        /*
        String strPos = "Die aktuelle Karte ist " + pos;
        Toast.makeText(this,strPos,
                Toast.LENGTH_LONG).show();
        */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDetail);
        setSupportActionBar(toolbar);
        setTitle("Gerichtdetails");

        rb = (RatingBar) findViewById(R.id.ratingBar4);
        //Drawable stars = (Drawable) rb.getProgressDrawable();
        //Drawable stars = rb.getProgressDrawable();
        //stars.setTint(Color.YELLOW);

        recyclerView =
                (RecyclerView) findViewById(R.id.recycler_view);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapterExpandable();
        recyclerView.setAdapter(adapter);

        intentMain = new Intent(this, MainActivity.class);
        // image = findViewById(R.id.imageView);
        intentCity = new Intent(this, CitySelectActivity.class);
        inPhoto = new Intent(this, PhotoActivity.class);
        intentMensa = new Intent(this, MensaSelectActivity.class);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutDetail);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewDetail);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawerList = (ListView)findViewById(R.id.navList);
        addDrawerItems();

/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cameraView.captureImage();
                startActivity(inPhoto);
            }
        });
*/

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
            startActivity(intentMain);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutDetail);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

