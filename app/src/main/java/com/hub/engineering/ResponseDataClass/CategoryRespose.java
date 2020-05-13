package com.hub.engineering.ResponseDataClass;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoryRespose {
    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public dataClass data;



    public class dataClass{
        @SerializedName("base_url")
        public String base_url;
        @SerializedName("category_image")
        public String category_image;
        @SerializedName("app_logo")
        public String app_logo;
        @SerializedName("account_setting")
        public AccountSetting accountSetting;
        @SerializedName("arr_category")
        public List<ArrayCategory> arr_category;


        public class AccountSetting{
            @SerializedName("account_setting_id")
            public String account_setting_id;
            @SerializedName("director_name")
            public String director_name;
            @SerializedName("site_name")
            public String site_name;
            @SerializedName("site_logo")
            public String site_logo;
            @SerializedName("site_number")
            public String site_number;
            @SerializedName("site_help_number")
            public String site_help_number;
            @SerializedName("latitude")
            public String latitude;
            @SerializedName("longitude")
            public String longitude;
            @SerializedName("site_url")
            public String site_url;
            @SerializedName("site_email")
            public String site_email;
            @SerializedName("site_address")
            public String site_address;
            @SerializedName("app_version")
            public String app_version;
            @SerializedName("app_version_status")
            public String app_version_status;
            @SerializedName("delivery_area")
            public String delivery_area;
            @SerializedName("facebook_link")
            public String facebook_link;
            @SerializedName("twitter_link")
            public String twitter_link;
            @SerializedName("google_plus_link")
            public String google_plus_link;
            @SerializedName("linkdin_link")
            public String linkdin_link;
            @SerializedName("short_description")
            public String short_description;
            @SerializedName("created_at")
            public String created_at;
            @SerializedName("updated_at")
            public String updated_at;
        }

        public class ArrayCategory{
            @SerializedName("category_id")
            public String category_id;
            @SerializedName("category_root_id")
            public String category_root_id;
            @SerializedName("category_name")
            public String category_name;
            @SerializedName("category_slug")
            public String category_slug;
            @SerializedName("category_image")
            public String category_image;
            @SerializedName("category_servicing")
            public String category_servicing;
            @SerializedName("category_manufacturing")
            public String category_manufacturing;
            @SerializedName("sub_category")
            public List<SubCategory> sub_category;

            public class SubCategory{
                @SerializedName("category_id")
                public String category_id;
                @SerializedName("category_root_id")
                public String category_root_id;
                @SerializedName("category_name")
                public String category_name;
                @SerializedName("category_slug")
                public String category_slug;
                @SerializedName("category_image")
                public String category_image;
                @SerializedName("category_servicing")
                public String category_servicing;
                @SerializedName("category_manufacturing")
                public String category_manufacturing;
                @SerializedName("servicing_text")
                public String servicing_text;
                @SerializedName("manufacturing_text")
                public String manufacturing_text;
            }
        }

    }

}
