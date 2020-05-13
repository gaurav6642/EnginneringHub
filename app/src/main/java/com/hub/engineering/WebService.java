package com.hub.engineering;

import com.hub.engineering.ResponseDataClass.AboutUsResponse;
import com.hub.engineering.ResponseDataClass.CategoryRespose;
import com.hub.engineering.ResponseDataClass.EnquireStatus;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebService {

    @GET("/api/dashboard")
    Call<CategoryRespose> getBrand();

    @GET("/api/dashboard")
    Call<CategoryRespose> getSubCategory(@Query("category_id") String id);

    @GET("/api/submit-enquiry")
    Call<EnquireStatus> sendEnquiry(@Query("category_id") String id, @Query("enquiry_type") String enquire_type,
                                    @Query("name") String name, @Query("email") String email,
                                    @Query("phone_number") String phone_number, @Query("pincode") String pincode,
                                    @Query("company_name") String company_name, @Query("company_address") String company_address,
                                    @Query("country") String country, @Query("state") String state,
                                    @Query("city") String city, @Query("message") String message);

    @GET("/api/master-pages")
    Call<AboutUsResponse> aboutUs(@Query("page_id") String id);


}

//http://engineeringhub.in/api/submit-enquiry?category_id=2&enquiry_type=&name=dss&email=sad&phone_number=sad&pincode=&company_name=&company_address=&country=&state=&city=&message=
