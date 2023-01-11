package com.intersem.sdib.core.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;


import java.io.IOException;
import java.io.InputStream;

public class UI {
    public static void hideSoftKeyboard(Activity activity) {

        try
        {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

        }catch (Exception ex){

        }
    }

    public static ProgressDialog createProgressDialog(Context context, String title, String message){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);

        return progressDialog;
    }

    public static void createDialog(final Context context, String title, String message, DialogInterface.OnClickListener positive){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("Aceptar", positive);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void createDialog(final Context context, String title, String message, DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setNegativeButton("Cancelar", negative);
        builder.setPositiveButton("Aceptar", positive);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static String getJsonFromAssets(Context context, String fileName) {
        String jsonString;
        try {
            InputStream is = context.getAssets().open(fileName);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return jsonString;
    }

    public static void closeActivityWithTimer(final Activity activity) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.finish();
            }
        }, 1500);
    }

}
