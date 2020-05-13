package com.hub.engineering;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefrence {
    private SharedPreferences sharedPrefrencesss;
    private Context context;

    public SharedPrefrence(Context context){
        this.context = context;
        sharedPrefrencesss = context.getSharedPreferences("Hahaha",Context.MODE_PRIVATE);


    }
    public void add_info(String email,String name,String contactNo, String Address,
                         String logo,String helpno,String lat, String longs,String directorname){
        SharedPreferences.Editor editor = sharedPrefrencesss.edit();
        editor.putString("directorname",directorname);
        editor.putString("email",email);
        editor.putString("name",name);
        editor.putString("contactNo",contactNo);
        editor.putString("Address",Address);
        editor.putString("logo",logo);
        editor.putString("helpno",helpno);
        editor.putString("lat",lat);
        editor.putString("longs",longs);
        editor.apply();


    }
    public void versionChange(String version){
        SharedPreferences.Editor editor = sharedPrefrencesss.edit();
        editor.putString("version",version);
        editor.apply();
    }
    public void addEnquiryDetails(String id,String Enquirystatus){
        SharedPreferences.Editor editor = sharedPrefrencesss.edit();
        editor.putString("id",id);
        editor.putString("Enquirystatus",Enquirystatus);
        editor.apply();
    }
    public void addArea(String area){
        SharedPreferences.Editor editor = sharedPrefrencesss.edit();
        editor.putString("area",area);
        editor.apply();
    }
    public String gethelpno(){
        String emaiId = "";
        emaiId = sharedPrefrencesss.getString("helpno", emaiId);
        return emaiId;
    }
    public Double getlat(){
        String emaiId = "";
        emaiId = sharedPrefrencesss.getString("lat", emaiId);
        return Double.parseDouble(emaiId);
    }
    public Double getlongs(){
        String emaiId = "";
        emaiId = sharedPrefrencesss.getString("longs", emaiId);
        return Double.parseDouble(emaiId);
    }
    public String read_areas() {
        String emaiId = "";
        emaiId = sharedPrefrencesss.getString("area", emaiId);
        return emaiId;
    }
    public String read_directors_name() {
        String emaiId = "";
        emaiId = sharedPrefrencesss.getString("directorname", emaiId);
        return emaiId;
    }
    public String read_id() {
        String emaiId = "";
        emaiId = sharedPrefrencesss.getString("id", emaiId);
        return emaiId;
    }
    public String read_version() {
        String emaiId = "1";
        emaiId = sharedPrefrencesss.getString("version", emaiId);
        return emaiId;
    }

    public String read_enquiry_type() {
        String emaiId = "";
        emaiId = sharedPrefrencesss.getString("Enquirystatus", emaiId);
        return emaiId;
    }
    public String read_email_id() {
        String emaiId = "";
        emaiId = sharedPrefrencesss.getString("email", emaiId);
        return emaiId;
    }
    public String read_name() {
        String emaiId = "";
        emaiId = sharedPrefrencesss.getString("name", emaiId);
        return emaiId;
    }
    public String read_contact_no() {
        String emaiId = "";
        emaiId = sharedPrefrencesss.getString("contactNo", emaiId);
        return emaiId;
    }
    public String read_address() {
        String emaiId = "";
        emaiId = sharedPrefrencesss.getString("Address", emaiId);
        return emaiId;
    }
    public String read_logo_url(){
        String emaiId = "";
        emaiId = sharedPrefrencesss.getString("logo", emaiId);
        return emaiId;
    }
}

