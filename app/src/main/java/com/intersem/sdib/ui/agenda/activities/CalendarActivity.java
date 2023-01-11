package com.intersem.sdib.ui.agenda.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.intersem.sdib.R;
import com.intersem.sdib.core.database.ScriptDataBase;
import com.intersem.sdib.core.services.ApiBodyService;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.ui.agenda.models.NotaModel;
import com.intersem.sdib.ui.login.models.ResponseLoginModel;
import com.intersem.sdib.ui.services.models.ServiceRequest;
import com.intersem.sdib.core.utilities.Constants;
import com.intersem.sdib.core.utilities.Network;
import com.intersem.sdib.core.utilities.ResponseAgendaServicios;
import com.intersem.sdib.core.utilities.UI;

import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    View view;
    DataBaseService dataBaseService;
    ArrayList<ServiceRequest> serviciosAgenda;
    ArrayList<NotaModel> notas;
    ProgressDialog progressDialog;
    public ServiceRequest servicio_selecionado;
    public NotaModel nota_seleccionada;
    CalendarView calendario_bitacora;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        inicilizarComponenetes();
    }

    @Override
    protected void onStart() {
        super.onStart();
        crearCalendario();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    private void inicilizarComponenetes() {
        calendario_bitacora = findViewById(R.id.calendarView);
        dataBaseService = new DataBaseService(getApplicationContext());
        view = findViewById(android.R.id.content);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        calendario_bitacora.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                boolean existe_fecha = false;


                //Buscamos el servicio de la fecha
                servicio_selecionado = null;
                for (ServiceRequest servicio : serviciosAgenda){
                    if(servicio.getFecha_servicio() != null){
                        if(servicio.getFecha_servicio_calendario().compareTo(clickedDayCalendar) == 0){
                            servicio_selecionado = servicio;
                            break;
                        }
                    }
                }

                nota_seleccionada = null;
                for (NotaModel nota_actual : notas){
                    if(nota_actual.getFecha() != null){
                        if(nota_actual.getFecha_nota_calendario().compareTo(clickedDayCalendar) == 0){
                            nota_seleccionada = nota_actual;
                            break;
                        }
                    }
                }


                if(servicio_selecionado != null){
                    Intent intent = new Intent(getApplicationContext(), AgendaActivty.class);
                    intent.putExtra("fecha_servicio",servicio_selecionado.getFecha_servicio());
                    startActivity(intent);
                }else if(nota_seleccionada != null){
                    Intent intent = new Intent(getApplicationContext(), AgendaActivty.class);
                    intent.putExtra("fecha_servicio",nota_seleccionada.getFecha());
                    startActivity(intent);
                }
                else{
                    Snackbar.make(view, "No se tienen eventos asigandos",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_actualizar_servicios:
                if(Network.VerifityConnection(this)){
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Actualizar Agenda");
                    builder.setMessage("¿Comenzar proceso de actuailizar servicios asigandos?");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            descargarServiciosAgenda();
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else{
                    Snackbar.make(view,getString(R.string.meesage_internet),Snackbar.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void descargarServiciosAgenda() {
        //Consultamos los archvios que debemos descargar
        progressDialog = UI.createProgressDialog(this, "Consultando Servicios", "Cargando.....");
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

    private Response.ErrorListener RequestErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Snackbar.make(view, error.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        };
    }

    private void crearCalendario() {
        try {
            //Obtenemos los servicios asigandos
            serviciosAgenda = dataBaseService.GetServiciosAgenda();
            notas = dataBaseService.ConsultarNotas();

            //SERVICIOS ASIGNADOS
            List<EventDay> events = new ArrayList<>();
            ArrayList<ServiceRequest> fechas_agrupadas = new ArrayList<>();
            for (ServiceRequest servicio_actual : serviciosAgenda) {
                if (servicio_actual.getFecha_servicio() != null) {
                    //Revisamos si ya existe una fecha
                    boolean existe = false;
                    for (ServiceRequest servicio_agenda : fechas_agrupadas) {
                        Date fecha = null;
                        fecha = new SimpleDateFormat("yyyy-MM-dd").parse(servicio_actual.getFecha_servicio());
                        Date fecha_agrupada = null;
                        fecha_agrupada = new SimpleDateFormat("yyyy-MM-dd").parse(servicio_agenda.getFecha_servicio());
                        if (fecha.toString() == fecha_agrupada.toString()) {
                            existe = true;
                            break;
                        }
                    }
                    if (!existe) {
                        Date fecha_servicio = null;
                        fecha_servicio = new SimpleDateFormat("yyyy-MM-dd").parse(servicio_actual.getFecha_servicio());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(fecha_servicio);
                        servicio_actual.setFecha_servicio_calendario(calendar);
                        fechas_agrupadas.add(servicio_actual);
                    }
                }
            }
            //Agregamos las fechas al calendario
            if (fechas_agrupadas.size() > 0) {
                //Agregamos las proximas fechas de servicios
                for (ServiceRequest servicio_asiganado : fechas_agrupadas) {
                    events.add(new EventDay(servicio_asiganado.getFecha_servicio_calendario(), R.drawable.ic_mantenimiento_correctivo));
                }
            }


            //NOTAS
            ArrayList<NotaModel> notas_agrupadas_fecha = new ArrayList<>();
            for (NotaModel nota_actual : notas) {
                if (nota_actual.getFecha() != null) {
                    //Revisamos si ya existe una fecha
                    boolean existe = false;
                    for (NotaModel nota_agrupada_actal : notas_agrupadas_fecha) {
                        Date fecha = null;
                        fecha = new SimpleDateFormat("yyyy-MM-dd").parse(nota_actual.getFecha());
                        Date fecha_agrupada = null;
                        fecha_agrupada = new SimpleDateFormat("yyyy-MM-dd").parse(nota_agrupada_actal.getFecha());
                        if (fecha.toString() == fecha_agrupada.toString()) {
                            existe = true;
                            break;
                        }
                    }
                    if (!existe) {
                        Date fecha_nota = null;
                        fecha_nota = new SimpleDateFormat("yyyy-MM-dd").parse(nota_actual.getFecha());
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(fecha_nota);
                        nota_actual.setFecha_nota_calendario(calendar);
                        notas_agrupadas_fecha.add(nota_actual);
                    }
                }
            }

            //Agregamos las fechas de las notas al calendario
            if (notas_agrupadas_fecha.size() > 0) {
                //Agregamos las proximas fechas de servicios

                for (NotaModel nota_actual : notas_agrupadas_fecha) {
                    events.add(new EventDay(nota_actual.getFecha_nota_calendario(), R.drawable.publicalo));
                }
            }
            calendario_bitacora.setEvents(events);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}