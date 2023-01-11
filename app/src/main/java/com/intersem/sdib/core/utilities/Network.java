package com.intersem.sdib.core.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Network {

    public static boolean VerifityConnection(Context context)
    {
        boolean disponible = false;
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo != null)
        {
                if(activeNetworkInfo.isConnected())
                {
                    disponible = true;
                }
        }
        return disponible;
    }


    public static boolean VerifityWifiConnection(Context context)
    {
        boolean disponible = false;
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if(activeNetworkInfo != null)
        {
            if(activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI){
                if(activeNetworkInfo.isConnected())
                {
                    disponible = true;
                }
            }
        }
        return disponible;
    }

}
