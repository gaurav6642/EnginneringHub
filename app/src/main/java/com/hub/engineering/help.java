package com.hub.engineering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hub.engineering.ResponseDataClass.AboutUsResponse;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class help extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView help,contact_no;
    SharedPrefrence sharedPrefrence;
    WebService apiInterface;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    View headerLayout;
    ImageView logo;
    private Toolbar toolbar;
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        apiInterface =ApiClient.getClient().create(WebService.class);
        toolbar = findViewById(R.id.main_toolbar);
        dialog = new Dialog(Objects.requireNonNull(this), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.loader_layout);
        dialog.show();
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        headerLayout = navigationView.getHeaderView(0);
        logo = headerLayout.findViewById(R.id.logo);
        help = findViewById(R.id.help);
        contact_no = findViewById(R.id.contact_no);
        sharedPrefrence = new SharedPrefrence(this);
        contact_no.setText(sharedPrefrence.gethelpno());

        navigationView.getMenu().getItem(3).setChecked(true);
        setSupportActionBar(toolbar);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            help.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }
        Glide.with(logo.getContext())
                .load(sharedPrefrence.read_logo_url())
                .into(logo);

        Call<AboutUsResponse> call = apiInterface.aboutUs("6");
        call.enqueue(new Callback<AboutUsResponse>() {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {
                AboutUsResponse aboutUsResponse = response.body();
                String lineSep = System.getProperty("line.separator");
                String yourString= aboutUsResponse.data.masterPage.page_long_description.replaceAll("<br>", lineSep);
                help.setText(yourString);
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<AboutUsResponse> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(),"Poor Connection...",Toast.LENGTH_LONG).show();
            }
        });

        contact_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPermissionGranted()){
                    call_action();
                }
            }
        });
        navigationView.bringToFront();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,toolbar,R.string.openNavDrawer,
                R.string.claoseNavDrawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

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
    public void call_action(){
        String phnum = contact_no.getText().toString();
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phnum));
        startActivity(callIntent);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
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
        else if(id==R.id.contact_us){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), ContactUs.class);
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
            help.this.finish();
        } catch (Exception e) {

        }

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
}
