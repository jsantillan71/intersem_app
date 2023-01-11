package com.intersem.sdib.core.utilities;

import android.content.Context;
import com.intersem.sdib.core.services.SharedPreferencesService;
import java.util.HashMap;
import java.util.Map;

public class Api {
    public static Map<String, String> getHeaders(Context context){
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        ///El JWT es para la aplicacion de zend
        ///En larabel se manda como bear token jajaja

        headers.put("Authorization ", "Bearer " +  new SharedPreferencesService(context).getCredentialParameter(Constants.CREDENTIALS.token));
        return headers;
    }
}
