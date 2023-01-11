package com.intersem.sdib.ui.splashscreen.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.intersem.sdib.R;
import com.intersem.sdib.core.database.ScriptDataBase;
import com.intersem.sdib.core.services.ApiBodyService;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.core.utilities.ResponseAgendaServicios;
import com.intersem.sdib.ui.agenda.models.NotaModel;
import com.intersem.sdib.ui.principal.activities.ActivityMain;
import com.intersem.sdib.ui.login.activities.LoginActivity;
import com.intersem.sdib.ui.services.models.ArchivoPath;
import com.intersem.sdib.ui.login.models.ResponseLoginModel;
import com.intersem.sdib.ui.services.models.PendantServiceRequest;
import com.intersem.sdib.ui.services.models.ServiceRequest;
import com.intersem.sdib.core.utilities.Constants;
import com.intersem.sdib.core.utilities.HiloSubirArchivosServiciosPendientes;
import com.intersem.sdib.core.utilities.Network;
import com.intersem.sdib.core.utilities.UI;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SplashScreenActivity extends AppCompatActivity {

    DataBaseService dataBaseService = null;
    View view;
    boolean sesion_valida;
    ArrayList<ServiceRequest> servicios_sin_guardar;
    ArrayList<ServiceRequest> servicios_sin_guardar_new;
    ArrayList<ServiceRequest> servicios_pendientes;
    boolean sesion_activa;
    ResponseLoginModel.Response usuario;
    ProgressDialog progressDialog;
    int bandera_notifiacion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        File file = new File(Constants.CARPETA_PDFS);
        if(!file.exists()) {
            file.mkdirs();
        }
        getSupportActionBar().hide();
        view = findViewById(android.R.id.content);
        dataBaseService = new DataBaseService(getApplicationContext());
        usuario = dataBaseService.GetUsuarios();
        dataBaseService.InsertUsuario(usuario);
        sesion_activa = (usuario.getToken() == null) ? false : true;
        servicios_sin_guardar = new ArrayList();
        if(sesion_activa){
            Calendar c = Calendar.getInstance();

            Date now = new Date();
            Date date_create = new Date();
            try {
                date_create = new SimpleDateFormat("yyyy-MM-dd").parse(usuario.getDate_create());
            }catch (Exception e){
                Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
            }
            c.setTime(date_create);
            c.add(Calendar.DATE, 7);
            date_create = c.getTime();

            int comparetion_date = now.compareTo(date_create);
            sesion_valida = (comparetion_date < 0);
            if(sesion_valida){
                if(Network.VerifityConnection(getApplicationContext())) {
                    consultarServiciosAsigandos();
                }else{
                    startActivityWithTimer(1);
                }
            }else{
                startActivityWithTimer(2);
            }
        }
        else{
            startActivityWithTimer(2);
        }
    }

    public void startActivityWithTimer(final int opcion) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(opcion == 1){
                    Intent i = new Intent(getApplicationContext(), ActivityMain.class);
                    i.putExtra("notificacion",bandera_notifiacion);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                    i.putExtra("notificacion",bandera_notifiacion);
                    startActivity(i);
                    finish();
                }
            }
        }, 2500);
    }

    public void ejecutarAccionTiempo(final int opcion) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(opcion == 1){
                    progressDialog.dismiss();
                    comprobarServicios();
                }else{

                }
            }
        }, 2500);
    }

    public void finalizarServiciosPendientes(ArrayList<ServiceRequest> servicios_pendientes){
        //Realizamos la peticion a la API para generar el gaurdado de los servicios
        progressDialog = UI.createProgressDialog(this,"Finalizando Servicio","Cargando.....");
        progressDialog.show();
        //Creamos json que se enviara
        try {
            PendantServiceRequest pendantServiceRequest = new PendantServiceRequest();
            pendantServiceRequest.setServicios(servicios_pendientes);
            String body = new Gson().toJson(pendantServiceRequest,PendantServiceRequest.class);
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            ApiBodyService apiBodyService =new ApiBodyService(
                    Request.Method.POST,
                    Constants.URL_API_SERVICIO_PENDIENTES,
                    body,
                    RequestSuccessCompleteListener(servicios_pendientes),RequestErrorListener());
            requestQueue.add(apiBodyService);
        } catch (Exception e) {
            progressDialog.dismiss();
            Snackbar.make(view, "Error al finalizar el servicio", Snackbar.LENGTH_LONG).show();
        }
    }

    public void finalizarError(String mensaje){
        Snackbar.make(view, mensaje, Snackbar.LENGTH_LONG).show();
        startActivityWithTimer(1);
    }

    public void comprobarServicios(){
        ResponseLoginModel.Response  usuario = dataBaseService.GetUsuarios();
        ArrayList<ServiceRequest> servicios = dataBaseService.GetServicios();
        servicios_pendientes = new ArrayList<>();
        //Consultamos los servicios pendientes terminados
        for (ServiceRequest servicio_actual : servicios) {
            if (servicio_actual.getSubido() != 1 && servicio_actual.getTerminado() == 1) {
                servicio_actual.setUsuario_creacion(usuario.getUser_id());
                servicios_pendientes.add(servicio_actual);
            }
        }
        //Consultamos los servicios cancelados pendientes
        for (ServiceRequest servicio_actual : servicios) {
            if (servicio_actual.getSubido() != 1 && servicio_actual.getTerminado() == -1) {
                servicio_actual.setUsuario_creacion(usuario.getUser_id());
                servicios_pendientes.add(servicio_actual);
            }
        }

        for (ServiceRequest servicio_actual : servicios) {
            if (servicio_actual.getSubido() != 1 && servicio_actual.getTerminado() == -2) {
                servicio_actual.setUsuario_creacion(usuario.getUser_id());
                servicios_pendientes.add(servicio_actual);
            }
        }

        if (servicios_pendientes.size() > 0) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            subirArchviosServicio();
                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            startActivityWithTimer(1);
                            break;
                    }
                }
            };
            AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenActivity.this);
            builder.setMessage(Html.fromHtml("<p style=\"text-align: center;\"><center><strong> " +servicios_pendientes.size()+ "  Servicios sin Sincronizar</strong></center></p><br><br> \n" +
                    "Se cuenta con servicios que no han sido sincronizados\n" +
                    "con el servidor\n" +
                    "<p>&iquest;Quiere iniciar con el proceso de sincronizaci&oacute;n?</p>"))
                    .setPositiveButton("Comenzar", dialogClickListener)
                    .setNegativeButton("Cancelar", dialogClickListener).show();
        }else{
            startActivityWithTimer(1);
        }
    }

    public void subirArchviosServicio(){
        ArrayList<ArchivoPath> archivos = new ArrayList();
        ServiceRequest servicio_actual = servicios_pendientes.get(0);
        ArrayList<ServiceRequest.Fotografia> fotografias = dataBaseService.GetDetalles(servicio_actual.getId());
        int contador_fotos = 0;
        for (ServiceRequest.Fotografia fotografia : fotografias) {
            //Creamos el archivo
            archivos.add(new ArchivoPath(fotografia.getRuta_fotografia(),
                    Constants.generar_path_imagen_server(fotografia.getTipo_fotografia(),
                            servicio_actual.getNumero_reporte(),contador_fotos,servicio_actual.getReporte_subir())));
            //Lo agregamos a la fotografia
            fotografia.setRuta_servidor(Constants.generar_path_imagen_server(fotografia.getTipo_fotografia(),
                    servicio_actual.getNumero_reporte(),contador_fotos,servicio_actual.getReporte_subir()));
            servicio_actual.getFotografias().add(fotografia);
            contador_fotos ++;
        }
        if(servicio_actual.getTerminado() == 1){
            if(servicio_actual.getTipo_servicio() != 3){
                switch (servicio_actual.getReporte_subir()){
                    case 1:
                        archivos.add(new ArchivoPath(Constants.generar_path_reporte(1,
                                servicio_actual.getNumero_reporte()),
                                Constants.generar_path_reporte_server(1, servicio_actual.getNumero_reporte())));

                        //archivos.add(new ArchivoPath(Constants.generar_path_reporte_inverse(1,
                        //        servicio_actual.getNumero_reporte()),
                        //        Constants.generar_path_reporte_server_inverse(1, servicio_actual.getNumero_reporte())));


                        servicio_actual.setPath_pdf_1(Constants.generar_path_reporte_server(1, servicio_actual.getNumero_reporte()));
                        servicio_actual.setPath_pdf_2("");
                        servicio_actual.setPath_pdf_3("");
                        break;
                    case 2:
                        archivos.add(new ArchivoPath(Constants.generar_path_reporte(2,
                                servicio_actual.getNumero_reporte()),
                                Constants.generar_path_reporte_server(2, servicio_actual.getNumero_reporte())));
                        //archivos.add(new ArchivoPath(Constants.generar_path_reporte_inverse(2,
                        //        servicio_actual.getNumero_reporte()),
                        //        Constants.generar_path_reporte_server_inverse(2, servicio_actual.getNumero_reporte())));
                        servicio_actual.setPath_pdf_2(Constants.generar_path_reporte_server(2, servicio_actual.getNumero_reporte()));
                        servicio_actual.setPath_pdf_1("");
                        servicio_actual.setPath_pdf_3("");
                        break;
                    case 3:
                        archivos.add(new ArchivoPath(Constants.generar_path_reporte(3,
                                servicio_actual.getNumero_reporte()),
                                Constants.generar_path_reporte_server(3, servicio_actual.getNumero_reporte())));
                        //archivos.add(new ArchivoPath(Constants.generar_path_reporte_inverse(3,
                        //        servicio_actual.getNumero_reporte()),
                        //        Constants.generar_path_reporte_server_inverse(3, servicio_actual.getNumero_reporte())));
                        servicio_actual.setPath_pdf_3(Constants.generar_path_reporte_server(3, servicio_actual.getNumero_reporte()));
                        servicio_actual.setPath_pdf_2("");
                        servicio_actual.setPath_pdf_1("");
                        break;
                }
            }

            //servicio_actual.setPath_pdf_inverso(Constants.generar_path_reporte_server_inverse(servicio_actual.getReporte_subir(), servicio_actual.getNumero_reporte()));
        }
        ArrayList<ServiceRequest> servicios_subir = new ArrayList<>();
        servicios_subir.add(servicio_actual);
        //Iniciamos el hilo para subir los archivos al servidor
        HiloSubirArchivosServiciosPendientes hilo = new HiloSubirArchivosServiciosPendientes(
                archivos,
                SplashScreenActivity.
                        this, servicios_subir, view);
        //Executemaos el hilo para subir los archivos
        hilo.execute();
    }

    private Response.Listener<JSONObject> RequestSuccessCompleteListener(final ArrayList<ServiceRequest> servicios_pendientes) {
        return  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    progressDialog.dismiss();
                    int response_flag = response.getInt("response_flag");
                    if(response_flag == 1){
                        //Recorremos los servicios y los actualizamos en la base de datos
                        for (ServiceRequest servicio_actual : servicios_pendientes){
                            dataBaseService.TerminadoServicio(servicio_actual);
                        }
                        comprobarServicios();
                    }else{
                        progressDialog.dismiss();
                        Snackbar.make(view, "Error al finalizar el servicio", Snackbar.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    progressDialog.dismiss();
                    Snackbar.make(view, "Error al finalizar el servicio", Snackbar.LENGTH_LONG).show();
                }
            }
        };
    }

    private Response.ErrorListener RequestErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Snackbar.make(view, error.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        };
    }

    private void consultarServiciosAsigandos(){
        //Consultamos los archvios que debemos descargar
        progressDialog = UI.createProgressDialog(this, "Consultando Servicios y Notas", "Espere un momento");
        progressDialog.show();
        //Agregamos el usuaio de creacion
        ResponseLoginModel.Response user_save = dataBaseService.GetUsuarios();
        //Creamos json que se enviara
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("usuario_creacion", user_save.getUser_id());
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            ApiBodyService apiBodyService = new ApiBodyService(
                    Request.Method.POST,
                    Constants.URL_API_SERVICIO_ASIGNADOS,
                    jsonBody.toString(),
                    RequestSuccessListener(), RequestErrorListener());
            requestQueue.add(apiBodyService);
        } catch (Exception e) {
            progressDialog.dismiss();
        }
    }

    private Response.Listener<JSONObject> RequestSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int response_flag = response.getInt("response_flag");
                    if (response_flag == 1) {
                        ResponseAgendaServicios response_servicios = new Gson().fromJson(response.toString(), ResponseAgendaServicios.class);
                        int servicios_anterirores_length = dataBaseService.GetServiciosAgenda().size();
                        int notas_anteriores_length = dataBaseService.ConsultarNotas().size();
                        dataBaseService.cleanTable(ScriptDataBase.Servicio.TABLE_NAME_AGENDA, null, null);
                        dataBaseService.cleanTable(ScriptDataBase.Nota.TABLE_NAME, null, null);
                            for (ServiceRequest servicio_agenda : response_servicios.getResponse().getServicios_asignados()) {
                                JSONObject jsonBody = new JSONObject();
                                jsonBody.put("razon_social", servicio_agenda.getNombre());
                                jsonBody.put("codigo_postal", servicio_agenda.getCodigo_postal());
                                jsonBody.put("calle", servicio_agenda.getCalle());
                                jsonBody.put("numero", servicio_agenda.getNumero());
                                jsonBody.put("numero_int", servicio_agenda.getNumero_int());
                                jsonBody.put("colonia", servicio_agenda.getColonia());
                                jsonBody.put("municipio", servicio_agenda.getMunicipio());
                                jsonBody.put("estado", servicio_agenda.getEstado());
                                servicio_agenda.setClientes_datos(jsonBody.toString());
                                dataBaseService.InsertServicioAgenda(servicio_agenda);
                            }

                        for (NotaModel nota : response_servicios.getResponse().getNotas()) {
                            dataBaseService.InsertNota(nota);
                        }
                            //Tomamos la medida del nuevo arreglo
                            int servicios_nuevos_length = dataBaseService.GetServiciosAgenda().size();
                            //Validamos las medidas
                            if(servicios_nuevos_length > servicios_anterirores_length){
                                bandera_notifiacion = 1; //Bandera de nuevos servicios asignados
                            }else if (servicios_nuevos_length < servicios_anterirores_length){
                                bandera_notifiacion = 2; //Bandera de que se han eliminado servicios
                            }else{
                                bandera_notifiacion = 0;//No hay servicios nuevos
                            }

                            //Totamos la medida del arreglo de notas
                        int notas_nuevas_length = dataBaseService.ConsultarNotas().size();
                            if (bandera_notifiacion == 0){
                                if(notas_nuevas_length > notas_anteriores_length){
                                    bandera_notifiacion = 1; //Bandera de nuevos servicios asignados
                                }else if (notas_nuevas_length < notas_anteriores_length){
                                    bandera_notifiacion = 2; //Bandera de que se han eliminado servicios
                                }else{
                                    bandera_notifiacion = 0;//No hay servicios nuevos
                                }
                            }
                        ejecutarAccionTiempo(1);
                    } else {
                        progressDialog.dismiss();
                        Snackbar.make(view, getString(R.string.message_error_api), Snackbar.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_SHORT).show();
                }
            }
        };
    }

}