package com.example.cantinr.cantinr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wonderkiln.camerakit.CameraKitError;
import com.wonderkiln.camerakit.CameraKitEvent;
import com.wonderkiln.camerakit.CameraKitEventListener;
import com.wonderkiln.camerakit.CameraKitImage;
import com.wonderkiln.camerakit.CameraKitVideo;
import com.wonderkiln.camerakit.CameraView;

public class PhotoActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, CameraKitEventListener {

    //set up new branch
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    Intent intentCity;
    Intent intentMensa;
    Intent intentMain;
    Intent intentDetail;
    ImageView image;
    Button bt_ok;
    //Button bt_back;
    Button bt_new;
    TextView tv_Action;
    CameraView cameraView;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarPhoto);
        setSupportActionBar(toolbar);
        setTitle("Knips ein Foto");
        intentMain = new Intent(this, MainActivity.class);
        intentDetail = new Intent(this, cardDetail.class);
        image = findViewById(R.id.imageView);
        image.setVisibility(View.GONE);
        intentCity = new Intent(this, CitySelectActivity.class);
        intentMensa = new Intent(this, MensaSelectActivity.class);
        tv_Action = findViewById(R.id.textView3);
        bt_ok = findViewById(R.id.btOK);
        //bt_back = findViewById(R.id.button3);
        bt_new = findViewById(R.id.button2);
        bt_new.setVisibility(View.GONE);
        bt_ok.setVisibility(View.INVISIBLE);
        fab = findViewById(R.id.fab2);
        cameraView = (CameraView) findViewById(R.id.camera);
        cameraView.addCameraKitListener(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutPhoto);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_viewPhoto);
        navigationView.setNavigationItemSelectedListener(this);

        mDrawerList = (ListView)findViewById(R.id.navList);
        addDrawerItems();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraView.captureImage();
            }
        });

        //vorher in Kamera start
        cameraView.start();
        cameraView.setVisibility(View.VISIBLE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        //  cameraView.start();

        //cameraView.start();
        cameraView.setVisibility(View.VISIBLE);
        bt_new.setVisibility(View.INVISIBLE);
        //bt_back.setVisibility(View.VISIBLE);
        image.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPause() {
        //  cameraView.stop();
        super.onPause();
    }

    public void onPhotoClick(View aView){
       // cameraView.start();
       // cameraView.setVisibility(View.VISIBLE);
    }

    public void onPhotoEnd(View aView){
        //cameraView.setVisibility(View.INVISIBLE);
        //cameraView.stop();
       // bt_new.setVisibility(View.INVISIBLE);
       // bt_back.setVisibility(View.VISIBLE);
       // image.setVisibility(View.INVISIBLE);
        startActivity(intentDetail);
    }

    public void photoShot(View aView){

        cameraView.captureImage();

    }

    public void photoOK(View aView){
        //upload photo now
        //after return to Start
        startActivity(intentDetail);
    }

    public  void newPhoto(View aView){
        image.setVisibility(View.INVISIBLE);
        //bt_back.setVisibility(View.VISIBLE);
        bt_new.setVisibility(View.GONE);
        bt_ok.setVisibility(View.GONE);
        fab.setVisibility(View.VISIBLE);
        cameraView.setVisibility(View.VISIBLE);
        tv_Action.setText("Bitte knips ein Foto von deinem Gericht.");
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layoutPhoto);
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
        picture.getJpeg();
        Bitmap pic = picture.getBitmap();
        //Toast.makeText(this, "Wenn dir das Foto gefällt, kehre mit 'ok' zurück", Toast.LENGTH_SHORT).show();
        image.setImageBitmap(pic);
       // bt_back.setVisibility(View.GONE);
        bt_new.setVisibility(View.VISIBLE);
        image.setVisibility(View.VISIBLE);
        bt_ok.setVisibility(View.VISIBLE);
        cameraView.setVisibility(View.GONE);
        fab.setVisibility(View.INVISIBLE);
        tv_Action.setText("Wenn dir das Foto gefällt, teile es mit Klick auf 'ok'");


    }

    @Override
    public void onVideo(CameraKitVideo cameraKitVideo) {

    }
}
