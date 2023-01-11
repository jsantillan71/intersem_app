package com.intersem.sdib.core.utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.intersem.sdib.R;
import com.intersem.sdib.ui.services.models.ArchivoPath;
import com.intersem.sdib.ui.services.activities.AddServiceActivity;
import com.intersem.sdib.ui.services.models.ServiceRequest;

import java.util.ArrayList;

public class HiloSubirArthivo extends AsyncTask<Void , Integer, Integer> {
    private ProgressDialog progressDialog;
    ArrayList<ArchivoPath> files;
    Context context;
    Boolean success;
    ArrayList<ServiceRequest.Fotografia> detallesArray;
    View view;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public HiloSubirArthivo(ArrayList files, Context context, ArrayList<ServiceRequest.Fotografia> detallesArray, View view) {
        this.files = files;
        this.context = context;
        this.detallesArray = detallesArray;
        this.view = view;
    }

    @Override
    protected void onPreExecute() {
        success = true;
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Subiendo archivo al servidor", "(0/"+files.size()+") " + "Por favor espere...", true);
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
        progressDialog.dismiss();
        if(resultado == 1){
            AddServiceActivity addServiceActivity = (AddServiceActivity) context;
            addServiceActivity.subirServicio(detallesArray);
        }else{
            Snackbar.make(view, context.getString(R.string.message_error_ftp), Snackbar.LENGTH_LONG).show();
            ((Activity) context).finish();
        }
    }
}
