package com.hub.engineering;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieOnCompositionLoadedListener;
import com.hub.engineering.ResponseDataClass.EnquireStatus;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnquireForm extends AppCompatActivity {
    TextView username, emailId, phoneNo, pinCode, companyName, companyAddress, country, state, city, message;
    Button submit;
    Dialog dialog;
    long duration=0;
    String id,category_name,enquiry_type;
    WebService apiInterface;
    Intent intent;
    Dialog dialog_loader;
    SharedPrefrence sharedPrefrence;
    LottieAnimationView animationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enquire_form);

        sharedPrefrence = new SharedPrefrence(this);
        username = findViewById(R.id.username);
        emailId = findViewById(R.id.emailId);
        phoneNo = findViewById(R.id.phoneNo);
        pinCode = findViewById(R.id.pinCode);
        companyName = findViewById(R.id.companyName);
        companyAddress = findViewById(R.id.companyAddress);
        country = findViewById(R.id.country);
        state = findViewById(R.id.state);
        city = findViewById(R.id.city);
        message = findViewById(R.id.message);
        submit = findViewById(R.id.submit);
        apiInterface = ApiClient.getClient().create(WebService.class);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.on_submit_dialog);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog_loader = new Dialog(Objects.requireNonNull(this), android.R.style.Theme_Translucent_NoTitleBar);
        dialog_loader.setContentView(R.layout.loader_layout);

        animationView = dialog.findViewById(R.id.animation);
        intent =getIntent();

        id = intent.getStringExtra("id");
        category_name = intent.getStringExtra("category_name");
        enquiry_type = intent.getStringExtra("enquiry_type");


        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(username.getText().toString().length()>0 && emailId.getText().toString().length()>0 && phoneNo.getText().toString().length()>0 && pinCode.getText().toString().length()>0 && companyName.getText().toString().length()>0 && companyAddress.getText().toString().length()>0 && country.getText().toString().length()>0
                        && state.getText().toString().length()>0 && city.getText().toString().length()>0 && message.getText().toString().length()>0){
                    submit.setTextColor(getResources().getColor(R.color.colorText));
                    submit.setBackgroundResource(R.drawable.confirm_btn);
                }
                else {
                    submit.setTextColor(getResources().getColor(R.color.fadeColor));
                    submit.setBackgroundResource(R.drawable.disable_confirm_btn);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        emailId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isEmailValid(emailId.getText().toString())&&username.getText().toString().length()>0 && emailId.getText().toString().length()>0 && phoneNo.getText().toString().length()>0 && pinCode.getText().toString().length()>0 && companyName.getText().toString().length()>0 && companyAddress.getText().toString().length()>0 && country.getText().toString().length()>0
                        && state.getText().toString().length()>0 && city.getText().toString().length()>0 && message.getText().toString().length()>0){
                    submit.setTextColor(getResources().getColor(R.color.colorText));
                    submit.setBackgroundResource(R.drawable.confirm_btn);
                }
                else {
                    submit.setTextColor(getResources().getColor(R.color.fadeColor));
                    submit.setBackgroundResource(R.drawable.disable_confirm_btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        phoneNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(username.getText().toString().length()>0 && emailId.getText().toString().length()>0 && phoneNo.getText().toString().length()>0 && pinCode.getText().toString().length()>0 && companyName.getText().toString().length()>0 && companyAddress.getText().toString().length()>0 && country.getText().toString().length()>0
                        && state.getText().toString().length()>0 && city.getText().toString().length()>0 && message.getText().toString().length()>0){
                    submit.setTextColor(getResources().getColor(R.color.colorText));
                    submit.setBackgroundResource(R.drawable.confirm_btn);
                }
                else {
                    submit.setTextColor(getResources().getColor(R.color.fadeColor));
                    submit.setBackgroundResource(R.drawable.disable_confirm_btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pinCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(username.getText().toString().length()>0 && emailId.getText().toString().length()>0 && phoneNo.getText().toString().length()>0 && pinCode.getText().toString().length()>0 && companyName.getText().toString().length()>0 && companyAddress.getText().toString().length()>0 && country.getText().toString().length()>0
                        && state.getText().toString().length()>0 && city.getText().toString().length()>0 && message.getText().toString().length()>0){
                    submit.setTextColor(getResources().getColor(R.color.colorText));
                    submit.setBackgroundResource(R.drawable.confirm_btn);
                }
                else {
                    submit.setTextColor(getResources().getColor(R.color.fadeColor));
                    submit.setBackgroundResource(R.drawable.disable_confirm_btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        companyAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(username.getText().toString().length()>0 && emailId.getText().toString().length()>0 && phoneNo.getText().toString().length()>0 && pinCode.getText().toString().length()>0 && companyName.getText().toString().length()>0 && companyAddress.getText().toString().length()>0 && country.getText().toString().length()>0
                        && state.getText().toString().length()>0 && city.getText().toString().length()>0 && message.getText().toString().length()>0){
                    submit.setTextColor(getResources().getColor(R.color.colorText));
                    submit.setBackgroundResource(R.drawable.confirm_btn);
                }
                else {
                    submit.setTextColor(getResources().getColor(R.color.fadeColor));
                    submit.setBackgroundResource(R.drawable.disable_confirm_btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        companyName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(username.getText().toString().length()>0 && emailId.getText().toString().length()>0 && phoneNo.getText().toString().length()>0 && pinCode.getText().toString().length()>0 && companyName.getText().toString().length()>0 && companyAddress.getText().toString().length()>0 && country.getText().toString().length()>0
                        && state.getText().toString().length()>0 && city.getText().toString().length()>0 && message.getText().toString().length()>0){
                    submit.setTextColor(getResources().getColor(R.color.colorText));
                    submit.setBackgroundResource(R.drawable.confirm_btn);
                }
                else {
                    submit.setTextColor(getResources().getColor(R.color.fadeColor));
                    submit.setBackgroundResource(R.drawable.disable_confirm_btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        country.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(username.getText().toString().length()>0 && emailId.getText().toString().length()>0 && phoneNo.getText().toString().length()>0 && pinCode.getText().toString().length()>0 && companyName.getText().toString().length()>0 && companyAddress.getText().toString().length()>0 && country.getText().toString().length()>0
                        && state.getText().toString().length()>0 && city.getText().toString().length()>0 && message.getText().toString().length()>0){
                    submit.setTextColor(getResources().getColor(R.color.colorText));
                    submit.setBackgroundResource(R.drawable.confirm_btn);
                }
                else {
                    submit.setTextColor(getResources().getColor(R.color.fadeColor));
                    submit.setBackgroundResource(R.drawable.disable_confirm_btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        state.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(username.getText().toString().length()>0 && emailId.getText().toString().length()>0 && phoneNo.getText().toString().length()>0 && pinCode.getText().toString().length()>0 && companyName.getText().toString().length()>0 && companyAddress.getText().toString().length()>0 && country.getText().toString().length()>0
                        && state.getText().toString().length()>0 && city.getText().toString().length()>0 && message.getText().toString().length()>0){
                    submit.setTextColor(getResources().getColor(R.color.colorText));
                    submit.setBackgroundResource(R.drawable.confirm_btn);
                }
                else {
                    submit.setTextColor(getResources().getColor(R.color.fadeColor));
                    submit.setBackgroundResource(R.drawable.disable_confirm_btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(username.getText().toString().length()>0 && emailId.getText().toString().length()>0 && phoneNo.getText().toString().length()>0 && pinCode.getText().toString().length()>0 && companyName.getText().toString().length()>0 && companyAddress.getText().toString().length()>0 && country.getText().toString().length()>0
                        && state.getText().toString().length()>0 && city.getText().toString().length()>0 && message.getText().toString().length()>0){
                    submit.setTextColor(getResources().getColor(R.color.colorText));
                    submit.setBackgroundResource(R.drawable.confirm_btn);
                }
                else {
                    submit.setTextColor(getResources().getColor(R.color.fadeColor));
                    submit.setBackgroundResource(R.drawable.disable_confirm_btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(username.getText().toString().length()>0 && emailId.getText().toString().length()>0 && phoneNo.getText().toString().length()>0 && pinCode.getText().toString().length()>0 && companyName.getText().toString().length()>0 && companyAddress.getText().toString().length()>0 && country.getText().toString().length()>0
                        && state.getText().toString().length()>0 && city.getText().toString().length()>0 && message.getText().toString().length()>0){
                    submit.setTextColor(getResources().getColor(R.color.colorText));
                    submit.setBackgroundResource(R.drawable.confirm_btn);
                }
                else {
                    submit.setTextColor(getResources().getColor(R.color.fadeColor));
                    submit.setBackgroundResource(R.drawable.disable_confirm_btn);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_loader.show();
                final float[] dur = new float[1];
                if(username.getText().toString().length()>0 && emailId.getText().toString().length()>0 && phoneNo.getText().toString().length()>0 && pinCode.getText().toString().length()>0 && companyName.getText().toString().length()>0 && companyAddress.getText().toString().length()>0 && country.getText().toString().length()>0
                        && state.getText().toString().length()>0 && city.getText().toString().length()>0 && message.getText().toString().length()>0){
                    if(isEmailValid(emailId.getText().toString())){
                        Call<EnquireStatus> call = apiInterface.sendEnquiry(sharedPrefrence.read_id(),sharedPrefrence.read_enquiry_type(),username.getText().toString(),emailId.getText().toString(),
                                phoneNo.getText().toString(),pinCode.getText().toString(),companyName.getText().toString(),
                                companyAddress.getText().toString(),country.getText().toString(),country.getText().toString(),
                                city.getText().toString(),message.getText().toString());
                        call.enqueue(new Callback<EnquireStatus>() {
                            @Override
                            public void onResponse(Call<EnquireStatus> call, Response<EnquireStatus> response) {
                                dialog_loader.dismiss();
                                EnquireStatus enquireStatus = response.body();

                                if(enquireStatus.status.equals("true")){
                                    sharedPrefrence.addEnquiryDetails("","");
                                    dialog.show();
                                    animationView.addLottieOnCompositionLoadedListener(new LottieOnCompositionLoadedListener() {
                                        @Override
                                        public void onCompositionLoaded(LottieComposition composition) {
                                            dur[0] = composition.getDuration();
                                            duration = (long) dur[0];
                                        }
                                    });
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            dialog.dismiss();
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }
                                    }, duration);

                                }
                                else{
                                    dialog_loader.dismiss();
                                    Toast.makeText(getApplicationContext(),"Poor Connection...Please Try Again."+enquireStatus.status+sharedPrefrence.read_id()+sharedPrefrence.read_enquiry_type(),Toast.LENGTH_LONG).show();

                                }
                            }

                            @Override
                            public void onFailure(Call<EnquireStatus> call, Throwable t) {
                                dialog_loader.dismiss();
                                Toast.makeText(getApplicationContext(),"Poor Connection...Please Try Again.",Toast.LENGTH_LONG).show();
                            }
                        });
                    }else{
                        dialog_loader.dismiss();
                        Toast.makeText(getApplicationContext(),"Enter Valid Email Id",Toast.LENGTH_LONG).show();
                    }





                }
                else{
                    dialog_loader.dismiss();
                    Toast.makeText(getApplicationContext(),"Please fill the details...",Toast.LENGTH_LONG).show();
                }


            }
        });



    }
    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
