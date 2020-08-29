package com.example.android.chuckchuck;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkConnection {
    private final Context context;

    public NetworkConnection(Context context) {
        this.context = context;
    }

    public boolean checkNetworkConnection() {
        boolean isNetworkConnected = false;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (connectivityManager != null) {
            activeNetwork = connectivityManager.getActiveNetworkInfo();
        }

        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            isNetworkConnected = true;
        }

        return isNetworkConnected;
    }

}
