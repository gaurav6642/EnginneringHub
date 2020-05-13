package com.hub.engineering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.circularprogressbar.CircularProgressBar;

import com.bumptech.glide.Glide;
import com.hub.engineering.ResponseDataClass.AboutUsResponse;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUs extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener {
    ImageView logo;
    View headerLayout;
    Dialog dialog;
    private Toolbar toolbar;
    SharedPrefrence sharedPrefrence;
    WebService apiInterface;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    RecyclerViewCategory recyclerViewCategory;
    RecyclerView recyclerView;
    TextView textView,companyName;
    CircularProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        sharedPrefrence = new SharedPrefrence(this);

        toolbar = findViewById(R.id.main_toolbar);
        textView = findViewById(R.id.about_us_text);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(1).setChecked(true);
        dialog = new Dialog(Objects.requireNonNull(this), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.loader_layout);
        dialog.show();

        navigationView.bringToFront();
        headerLayout = navigationView.getHeaderView(0);
        logo = headerLayout.findViewById(R.id.logo);
        companyName = headerLayout.findViewById(R.id.companyName);
        apiInterface = ApiClient.getClient().create(WebService.class);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,toolbar,R.string.openNavDrawer,
                R.string.claoseNavDrawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        /*companyName.setText(sharedPrefrence.read_name());*/

        Glide.with(logo.getContext())
                .load(sharedPrefrence.read_logo_url())
                .into(logo);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            textView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
        }
        Call<AboutUsResponse> call = apiInterface.aboutUs("1");
        call.enqueue(new Callback<AboutUsResponse>() {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {
                AboutUsResponse aboutUsResponse = response.body();
                dialog.dismiss();
                String lineSep = System.getProperty("line.separator");
                String yourString= aboutUsResponse.data.masterPage.page_long_description.replaceAll("<br>", lineSep);
                textView.setText(yourString);

            }

            @Override
            public void onFailure(Call<AboutUsResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Poor Connection...",Toast.LENGTH_LONG).show();
            }
        });




    }




    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        drawerLayout.closeDrawers();
        if(id==R.id.contact_us){
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
            AboutUs.this.finish();
        } catch (Exception e) {

        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

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
