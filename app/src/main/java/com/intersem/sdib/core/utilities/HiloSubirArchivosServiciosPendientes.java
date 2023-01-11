package com.intersem.sdib.core.utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.intersem.sdib.ui.services.models.ArchivoPath;
import com.intersem.sdib.ui.services.models.ServiceRequest;
import com.intersem.sdib.ui.splashscreen.activities.SplashScreenActivity;

import java.util.ArrayList;

public class HiloSubirArchivosServiciosPendientes extends AsyncTask<Void , Integer, Integer> {

    private ProgressDialog progressDialog;
    ArrayList<ArchivoPath> files;
    Context context;
    ArrayList<ServiceRequest> servicios_pendientes;
    View view;
    String codigo_error;

    public HiloSubirArchivosServiciosPendientes(ArrayList<ArchivoPath> files, Context context, ArrayList<ServiceRequest> servicios_pendientes, View view) {
        this.files = files;
        this.context = context;
        this.servicios_pendientes = servicios_pendientes;
        this.view = view;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Subiendo archivos al servidor", "(0/"+files.size()+") " + "Este proceso puede tardar unos minutos.....", true);
        progressDialog.setCancelable(false);
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        try{
            if(files.size() > 0){
                for (int i = 0; i < files.size(); i++){
                    new FtpControl(files.get(i).getPath_local(), files.get(i).getPath_server()).SubirArchivo();
                    publishProgress(i);
                }
            }
        }catch (Exception e){
            codigo_error = e.getMessage();
            return 0;
        }
        return 1;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressDialog.setMessage("("+(values[0] + 1)+"/"+files.size()+") " + "Este proceso puede tardar unos minutos.....");
    }

    @Override
    protected void onPostExecute(Integer resultado) {
        try{
            if(resultado == 1){
                SplashScreenActivity splashScreenActivity = (SplashScreenActivity) context;
                progressDialog.dismiss();
                splashScreenActivity.finalizarServiciosPendientes(servicios_pendientes);
            }else{
                SplashScreenActivity splashScreenActivity = (SplashScreenActivity) context;
                progressDialog.dismiss();
                splashScreenActivity.finalizarError(codigo_error);
            }
        }catch (Exception e){

        }
    }
}
