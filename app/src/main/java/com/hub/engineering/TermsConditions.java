package com.hub.engineering;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Layout;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hub.engineering.ResponseDataClass.AboutUsResponse;
import com.hub.engineering.permissions.Permission;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsConditions extends AppCompatActivity {
    WebService apiInterface;
    LinearLayout noInternet;
    Dialog dialog;
    ImageView backBtn;

    TextView privacy_policy_text,term_condition_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);
        noInternet = findViewById(R.id.noInternet);
        backBtn = findViewById(R.id.backBtn);
        dialog = new Dialog(Objects.requireNonNull(this), android.R.style.Theme_Translucent_NoTitleBar);
        dialog.setContentView(R.layout.loader_layout);
        dialog.show();
        apiInterface = ApiClient.getClient().create(WebService.class);
        privacy_policy_text = findViewById(R.id.privacy_policy_text);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            privacy_policy_text.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);

        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TermsConditions.super.onBackPressed();
            }
        });

        setCheckConnection();
        noInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = new AlphaAnimation(0.0f, 1.0f);
                anim.setDuration(50); //You can manage the blinking time with this parameter
                anim.setStartOffset(20);
                anim.setRepeatMode(Animation.REVERSE);
                anim.setRepeatCount(2);
                noInternet.startAnimation(anim);
                setCheckConnection();
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext())
                        .setMessage("You need to have Mobile Data or Wifi to access this application.")
                        .setTitle("No internet Connection")
                        .setIcon(R.drawable.ic_no_wifi)
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

        Call<AboutUsResponse> call =apiInterface.aboutUs("4");
        call.enqueue(new Callback<AboutUsResponse>() {
            @Override
            public void onResponse(Call<AboutUsResponse> call, Response<AboutUsResponse> response) {
                AboutUsResponse aboutUsResponse = response.body();
                String lineSep = System.getProperty("line.separator");
                String yourString= aboutUsResponse.data.masterPage.page_long_description.replaceAll("<br>", lineSep);
                privacy_policy_text.setText(yourString);
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<AboutUsResponse> call, Throwable t) {

            }
        });




    }
    public void setCheckConnection() {

        Permission permissions = new Permission();
        if (permissions.checkInternetConnection(this)) {
            noInternet.setVisibility(View.GONE);

        } else {
            noInternet.setVisibility(View.VISIBLE);

        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setCheckConnection();
            }
        }, 1000);
    }
}
