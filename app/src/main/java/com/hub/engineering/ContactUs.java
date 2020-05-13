package com.hub.engineering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

public class ContactUs extends AppCompatActivity implements OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener {
    ImageView logo;
    View headerLayout;
    private Toolbar toolbar;
    LinearLayout autoCall,autoMail;
    TextView contact_no, companyAddress, name, emailId;
    SharedPrefrence sharedPrefrence;
    SharedPreferences sharedPreferences;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    RecyclerViewCategory recyclerViewCategory;
    RecyclerView recyclerView;
    CircularProgressBar progressBar;
    TextView call,companyName,director_name_txt;
    GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        sharedPrefrence = new SharedPrefrence(this);
        director_name_txt = findViewById(R.id.director_name_txt);
        toolbar = findViewById(R.id.main_toolbar);
        emailId = findViewById(R.id.emailId);
        autoCall = findViewById(R.id.autoCall);
        autoMail = findViewById(R.id.autoMail);
        name = findViewById(R.id.name);
        contact_no = findViewById(R.id.contact_no);
        companyAddress =findViewById(R.id.companyAddress);



        emailId.setText(sharedPrefrence.read_email_id().toString());
        name.setText(sharedPrefrence.read_name());
        contact_no.setText(sharedPrefrence.read_contact_no());
        companyAddress.setText(sharedPrefrence.read_address());

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(2).setChecked(true);
        setSupportActionBar(toolbar);

        navigationView.bringToFront();
        headerLayout = navigationView.getHeaderView(0);
        logo = headerLayout.findViewById(R.id.logo);
        companyName = headerLayout.findViewById(R.id.companyName);
       /* companyName.setText(sharedPrefrence.read_name());*/
        Glide.with(logo.getContext())
                .load(sharedPrefrence.read_logo_url())
                .into(logo);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,toolbar,R.string.openNavDrawer,
                R.string.claoseNavDrawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        director_name_txt.setText(sharedPrefrence.read_directors_name());
        contact_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPermissionGranted()){
                    call_action();
                }
            }
        });
        autoMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { emailId.getText().toString() });

                startActivity(Intent.createChooser(intent, ""));

            }
        });



    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        drawerLayout.closeDrawers();
        if(id==R.id.about_us){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), AboutUs.class);
                    startActivity(intent);
                }
            }, 200);

        }
        else if(id==R.id.term_condition) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), TermsConditions.class);
                    startActivity(intent);
                }
            }, 200);

        }
        else if(id==R.id.privacy_policy) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), PrivacyPolicy.class);
                    startActivity(intent);
                }
            }, 200);
        }
        else if(id==R.id.help){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), help.class);
                    startActivity(intent);
                }
            }, 200);
        }

        else if(id==R.id.home){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }, 200);
        }

        return true;

    }
    public void finishs() {
        try {
            ContactUs.this.finish();
        } catch (Exception e) {

        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng sydney = new LatLng(sharedPrefrence.getlat(), sharedPrefrence.getlongs());
        mMap.addMarker(new MarkerOptions().position(sydney).title("Office Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f));
    }


    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else
            super.onBackPressed();
    }

    public  boolean isPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {

                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {

            case 1: {

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), "Permission granted", Toast.LENGTH_SHORT).show();
                    call_action();
                } else {
                    Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    public void call_action(){
        String phnum = contact_no.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phnum));
        startActivity(callIntent);
    }
}