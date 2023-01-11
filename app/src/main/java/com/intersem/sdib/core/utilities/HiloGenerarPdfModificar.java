package com.intersem.sdib.core.utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.intersem.sdib.ui.services.activities.ServiceUpdateActivity;
import com.intersem.sdib.ui.services.activities.ServiciosActivity;
import com.intersem.sdib.ui.services.models.ServiceRequest;

import java.util.ArrayList;

public class HiloGenerarPdfModificar extends AsyncTask<Void , Integer, Integer> {

    private ProgressDialog progressDialog;
    ArrayList<ServiceRequest.Fotografia> detallesArray;
    ServiceRequest servicio_selecionado;
    Context context;
    int tipo;


    public HiloGenerarPdfModificar(ArrayList<ServiceRequest.Fotografia> detallesArray, ServiceRequest servicio_selecionado, Context context , int tipo) {
        this.detallesArray = detallesArray;
        this.servicio_selecionado = servicio_selecionado;
        this.context = context;
        this.tipo = tipo;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Generando Reporte PDF", "Por favor espere...", true);
        progressDialog.setCancelable(false);
    }
    @Override
    protected Integer doInBackground(Void... voids) {
        try{
            new PdfServices(detallesArray, servicio_selecionado, context);
            new PdfServicesInverse(detallesArray, servicio_selecionado, context);
            //new PdfServiceEvidencia(context,detallesArray,servicio_selecionado);
        }catch (Exception e){
            //Si se devuelve un O significa un error
            return 0;
        }
        return 1;//Si se devuelve un 1 sifnifica correro
    }

    @Override
    protected void onPostExecute(Integer resultado) {
        //Veirficamos si es correcto
        progressDialog.dismiss();
        View view;
        if(tipo == 0){
             view = ((ServiceUpdateActivity) context).findViewById(android.R.id.content);
        }else {
             view = ((ServiciosActivity) context).findViewById(android.R.id.content);
        }

        if(resultado == 1){
            Snackbar.make( view, "PDF generado correctamente", Snackbar.LENGTH_LONG).show();
            //Modificamos el estatus del servicio
            if(tipo == 0){
                ((ServiceUpdateActivity) context).modificarServicio(servicio_selecionado);
            }else {
                ((ServiciosActivity) context).modificarServicio(servicio_selecionado);
            }
        }else{
            Snackbar.make( view, "Error al generar PDF. Contacte con el encargado del sistema", Snackbar.LENGTH_LONG).show();
        }
    }
}
