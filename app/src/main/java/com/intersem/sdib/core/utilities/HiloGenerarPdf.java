package com.intersem.sdib.core.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.intersem.sdib.R;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.ui.services.models.ArchivoPath;
import com.intersem.sdib.ui.services.activities.AddServiceActivity;
import com.intersem.sdib.ui.services.models.ServiceRequest;

import java.util.ArrayList;

public class HiloGenerarPdf extends AsyncTask<Void , Integer, Integer> {
    private static final String TAG = "PDF";
    private ProgressDialog progressDialog;
    ArrayList<ServiceRequest.Fotografia> detallesArray;
    ServiceRequest servicio_selecionado;
    Context context;
    DataBaseService dataBaseService;
    String[] path_reportes_pdf;
    View view;

    public HiloGenerarPdf(ArrayList<ServiceRequest.Fotografia> detallesArray, ServiceRequest servicio_selecionado, Context context, String[] path_reportes_pdf , View view) {
        this.dataBaseService = new DataBaseService(context);
        this.servicio_selecionado = servicio_selecionado;
        this.detallesArray = detallesArray;
        this.context = context;
        this.path_reportes_pdf = path_reportes_pdf;
        this.view = view;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Generando Reportes PDF", "Por favor espere...", true);
        progressDialog.setCancelable(false);
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        try{
            new PdfServices(detallesArray, servicio_selecionado, context);
            //new PdfServicesInverse(detallesArray, servicio_selecionado, context);
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
        if(resultado == 1){
            progressDialog.dismiss();
            boolean bitacora = false;
            //Guadamos fotografias en el objecto
            ArrayList<ArchivoPath> archivos = new ArrayList();
            int contador_fotos = 0;
            for(ServiceRequest.Fotografia fotografia : detallesArray){
                archivos.add(new ArchivoPath(fotografia.getRuta_fotografia(),Constants.generar_path_imagen_server(fotografia.getTipo_fotografia(),servicio_selecionado.getNo_reporte(),contador_fotos,servicio_selecionado.getReporte_subir())));
                fotografia.setRuta_servidor(Constants.generar_path_imagen_server(fotografia.getTipo_fotografia(),servicio_selecionado.getNo_reporte(),contador_fotos,servicio_selecionado.getReporte_subir()));
                servicio_selecionado.getFotografias().add(fotografia);
                contador_fotos ++;
                if(fotografia.getCheck() == 1 && fotografia.getTipo_fotografia() == 7){
                    bitacora = true;
                }
            }
            if(bitacora) servicio_selecionado.setBitacora(1);
            //Guardamos el regsitro de PDFS en sqlite
            dataBaseService.ModificarServicio(servicio_selecionado);
            switch (servicio_selecionado.getReporte_subir()){
                case 1:
                    archivos.add(new ArchivoPath(Constants.generar_path_reporte(1,servicio_selecionado.getNo_reporte()),Constants.generar_path_reporte_server(1,servicio_selecionado.getNo_reporte())));
                    //archivos.add(new ArchivoPath(Constants.generar_path_reporte_inverse(1,servicio_selecionado.getNo_reporte()),Constants.generar_path_reporte_server_inverse(1,servicio_selecionado.getNo_reporte())));
                    break;
                case 2:
                    archivos.add(new ArchivoPath(Constants.generar_path_reporte(2,servicio_selecionado.getNo_reporte()),Constants.generar_path_reporte_server(2,servicio_selecionado.getNo_reporte())));
                    //archivos.add(new ArchivoPath(Constants.generar_path_reporte_inverse(2,servicio_selecionado.getNo_reporte()),Constants.generar_path_reporte_server_inverse(2,servicio_selecionado.getNo_reporte())));
                    break;
                case 3:
                    archivos.add(new ArchivoPath(Constants.generar_path_reporte(3,servicio_selecionado.getNo_reporte()),Constants.generar_path_reporte_server(3,servicio_selecionado.getNo_reporte())));
                    //archivos.add(new ArchivoPath(Constants.generar_path_reporte_inverse(3,servicio_selecionado.getNo_reporte()),Constants.generar_path_reporte_server_inverse(3,servicio_selecionado.getNo_reporte())));
                    break;
            }
            if(bitacora) archivos.add(new ArchivoPath(Constants.generar_path_reporte(4,servicio_selecionado.getNo_reporte()),Constants.generar_path_reporte_server(4,servicio_selecionado.getNo_reporte())));
            //Verificamos si tenemos conexion
            if(Network.VerifityConnection(context)){
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Subir Archivo Servidor");
                builder.setMessage("¿Quieres subir los archivos al servidor?");
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Generamos hilo para subir los arhivos y pdfs a servidor
                        dialogInterface.dismiss();
                        HiloSubirArthivo hiloSubirArthivo = new HiloSubirArthivo(archivos, context, detallesArray,view);
                        hiloSubirArthivo.execute();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        AddServiceActivity addServiceActivity = (AddServiceActivity) context;
                        addServiceActivity.finalizarSinGuardado(servicio_selecionado);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }else{
                AddServiceActivity addServiceActivity = (AddServiceActivity) context;
                addServiceActivity.finalizarSinGuardado(servicio_selecionado);
            }
        }else{
            Snackbar.make(view,context.getString(R.string.meesage_error_pdf), Snackbar.LENGTH_LONG).show();
            ((Activity) context).finish();
        }
    }
}
