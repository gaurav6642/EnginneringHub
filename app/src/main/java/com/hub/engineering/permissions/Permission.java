package com.hub.engineering.permissions;

import android.content.Context;
import android.net.ConnectivityManager;

public class Permission {
    int RESULT_OK = 0;
    private final int SPLASH_DISPLAY_LENGHT = 3000;


    public boolean checkInternetConnection(final Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        // ARE WE CONNECTED TO THE NET

        if (conMgr.getActiveNetworkInfo() != null
                && conMgr.getActiveNetworkInfo().isAvailable()
                && conMgr.getActiveNetworkInfo().isConnected()) {
            return true;

        }

        return false;
    }

}

