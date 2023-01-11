package com.intersem.sdib.core.services;

import android.content.Context;
import android.content.SharedPreferences;

import com.intersem.sdib.core.utilities.Constants;

public class SharedPreferencesService {
    private Context context;

    public SharedPreferencesService(Context context){
        this.context = context;
    }

    public void addCredentials(String user_id, String user, String token){
        SharedPreferences sharedPreferences = context.getSharedPreferences("credentials", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("user_id", user_id);
        editor.putString("user", user);
        editor.putString("token", token);
        editor.commit();
    }

    public String getCredentialParameter(Constants.CREDENTIALS param){
        SharedPreferences sharedPreferences = context.getSharedPreferences("credentials", Context.MODE_PRIVATE);

        return sharedPreferences.getString(param.name(), "");
    }
}
