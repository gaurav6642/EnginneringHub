package com.hub.engineering;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.hub.engineering.permissions.Permission;

public class SplashScreen extends Activity {
    TextView noInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        noInternet = findViewById(R.id.noInternet);
        setCheckConnection();


    }

    public void setCheckConnection() {

        Permission permissions = new Permission();
        if (!permissions.checkInternetConnection(this)) {
            noInternet.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setCheckConnection();
                }
            }, 1000);

        } else {

            new Handler().postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    noInternet.setVisibility(View.GONE);
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
            }, 1000);

        }


    }
}
