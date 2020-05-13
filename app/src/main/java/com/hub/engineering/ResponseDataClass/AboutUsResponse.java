package com.hub.engineering.ResponseDataClass;

import com.google.gson.annotations.SerializedName;

public class AboutUsResponse {
    @SerializedName("status")
    public String status;
    @SerializedName("message")
    public String message;
    @SerializedName("data")
    public dataClass data;

    public class dataClass {
        @SerializedName("masterPage")
        public masterPage masterPage;

        public class masterPage{
            @SerializedName("master_page_id")
            public String master_page_id;
            @SerializedName("page_name")
            public String page_name;
            @SerializedName("page_name_slug")
            public String page_name_slug;
            @SerializedName("page_sort_description")
            public String page_sort_description;
            @SerializedName("page_long_description")
            public String page_long_description;
            @SerializedName("image")
            public String image;
            @SerializedName("page_status")
            public String page_status;
        }
    }

}
