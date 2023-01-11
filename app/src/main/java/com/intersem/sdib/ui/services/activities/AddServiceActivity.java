package com.intersem.sdib.ui.services.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.intersem.sdib.R;
import com.intersem.sdib.core.services.ApiBodyService;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.core.utilities.Network;
import com.intersem.sdib.ui.login.models.ResponseLoginModel;
import com.intersem.sdib.ui.services.adapters.StepperAdapter;
import com.intersem.sdib.ui.services.dialogs.ViewAllImageDialog;
import com.intersem.sdib.ui.services.dialogs.ViewImageDialog;
import com.intersem.sdib.ui.services.fragments.ServiceAfterFragment;
import com.intersem.sdib.ui.services.fragments.ServiceBeforeFragment;
import com.intersem.sdib.ui.services.fragments.ServiceCambsFragment;
import com.intersem.sdib.ui.services.fragments.ServiceGafetFragment;
import com.intersem.sdib.ui.services.fragments.ServiceGeneralFragment;
import com.intersem.sdib.ui.services.fragments.ServiceIdFragment;
import com.intersem.sdib.ui.services.fragments.ServicePendigFragment;
import com.intersem.sdib.ui.services.fragments.ServiceRepairFragment;
import com.intersem.sdib.ui.services.fragments.ServiceTagFragment;
import com.intersem.sdib.ui.services.fragments.FinalyObservationsFragment;
import com.intersem.sdib.ui.services.models.ServiceRequest;
import com.intersem.sdib.core.utilities.Constants;
import com.intersem.sdib.core.utilities.HiloGenerarPdf;
import com.intersem.sdib.core.utilities.UI;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class AddServiceActivity extends AppCompatActivity implements StepperLayout.StepperListener {

    //region Atributos
    private StepperLayout stepperLayout;
    private View view;
    private FloatingActionButton fabViewService;
    public ServiceRequest serviceRequest = new ServiceRequest();
    private ProgressDialog progressDialog;
    DataBaseService dataBaseService =  null;
    public int orden_fotografico = 1;
    private int servicio_id = 0;
    public boolean servicio_imcompleto = false;//Bandera que nos dice si es un servicio viejo
    public static final String CARPETA_PRINCIPAL = "intersem/";//Directorio Principal
    public static final String CARPETA_IMAGEN_ANTES =  CARPETA_PRINCIPAL + "antes";//Carpeta donde se guarda las fotos de antes
    public static final String CARPETA_IMAGEN_DURANTE = CARPETA_PRINCIPAL +"durante";//Carpeta donde se guarda las fotos de durante
    public static final String CARPETA_IMAGEN_DESPUES = CARPETA_PRINCIPAL + "despues";//Carpeta donde se guarda las fotos de despues
    public static final String CARPETA_IMAGEN_ETIQUETA = CARPETA_PRINCIPAL +"etiqueta";//Carpeta donde se guarda las fotos de etiqueta
    public static final String CARPETA_IMAGEN_CAMBS = CARPETA_PRINCIPAL +"cambs";//Carpeta donde se guarda las fotos de cambs
    public static final String CARPETA_IMAGEN_ID = CARPETA_PRINCIPAL + "serie";//Carpeta donde se guarda las fotos de id
    public static final String CARPETA_IMAGEN_BINNACLE = CARPETA_PRINCIPAL + "bitacora";//Carpeta donde se guarda las fotos de id
    public static final String CARPETA_IMAGEN_REPAIR = CARPETA_PRINCIPAL + "refaccion";//Carpeta donde se guarda las fotos de id
    public static final String CARPETA_IMAGEN_GAFETE = CARPETA_PRINCIPAL + "gafete";//Carpeta donde se guarda las fotos de id
    private boolean servicio_extra = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case  R.id.ic_help:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Ayuda");
                builder.setMessage(Html.fromHtml("Las fotografías  de las fases: <br><br>\n" +
                        "<big><b>\n" +
                        "Refacción<br>\n" +
                        "Gafete<br>\n" +
                        "</b></big><br>\n" +
                        "Deben ser <b>seleccionadas</b> en la pantalla <br>\n" +
                        "de vista previa de fotografías,\n" +
                        "sino estas no <b>aparecerán en los reportes</b><br><br>\n" +
                        "<b>*Es necesario solo selecionar una foto de cada fase mencionada</b>"));
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            case  R.id.ic_view_images:
                //Creamos un solo arreglo de fotografias
                ArrayList<ServiceRequest.Fotografia> fotografias =  dataBaseService.GetDetalles(serviceRequest.getId());
                if(fotografias.size() > 0){
                    ViewAllImageDialog dialog_image = new ViewAllImageDialog(fotografias);
                    dialog_image.show(getSupportFragmentManager(),"Hola");
                }else{
                    Snackbar.make(view, "No se tienen fotografias tomadas", Snackbar.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
       UI.createDialog(this, "Cerrar Servicio", "¿Estas seguro de salir del servicio previamente iniciado, el servicio se eliminara?", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
               dataBaseService.EliminarServicio(serviceRequest);
                finish();
           }
       });
    }

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_service);
            initComponents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initComponents() {
        dataBaseService = new DataBaseService(getApplicationContext());
        setTitle("Servicio Nuevo");
        view = findViewById(android.R.id.content);
        stepperLayout = view.findViewById(R.id.stepperLayout);
        //Obtenemos el servicio imcompleto
        servicio_id = getIntent().getExtras().getInt("id_servicio",0);
        if(servicio_id > 0){

            consultarServicioImcompleto();
        }else if (servicio_id == -1){
            servicio_extra = true;
            int contador_servicios_extras = 0;
            for (ServiceRequest servicio :  dataBaseService.GetServicios()){
                if(servicio.getTipo_servicio() == 5){
                    contador_servicios_extras ++;
                }
            }
            serviceRequest.setNumero_reporte("SERVICIO EXTRA " + (contador_servicios_extras + 1));
            serviceRequest.setTipo_servicio(5);
            agregarServicio();
        }
        StepperAdapter stepperAdapter = new StepperAdapter(getSupportFragmentManager(), this);
        if(!servicio_extra){
            stepperAdapter.addFragment(new ServiceGeneralFragment(0), "Información");
        }
        stepperAdapter.addFragment(new ServiceBeforeFragment(0), "Antes");
        stepperAdapter.addFragment(new ServicePendigFragment(0), "Durante");
        stepperAdapter.addFragment(new ServiceAfterFragment(0), "Despues");
        stepperAdapter.addFragment(new ServiceTagFragment(0), "Etiqueta");
        stepperAdapter.addFragment(new ServiceIdFragment(0), "Serie");
        stepperAdapter.addFragment(new ServiceCambsFragment(0), "Cambs");
        //stepperAdapter.addFragment(new ServiceBinnacleFragment(0), "Bitacora");
        stepperAdapter.addFragment(new ServiceRepairFragment(0), "Refacción");
        stepperAdapter.addFragment(new ServiceGafetFragment(0), "Gafete");
        stepperAdapter.addFragment(new FinalyObservationsFragment(0), "Finalizar Servicio");
        stepperLayout.setAdapter(stepperAdapter);
        stepperLayout.setListener(this);
    }

    @Override
    public void onCompleted(View completeButton) {
        createDialogFinalizarServicio();
    }
    @Override
    public void onError(VerificationError verificationError) {
        Snackbar.make(view, verificationError.getErrorMessage(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onStepSelected(int newStepPosition) {
    }

    @Override
    public void onReturn() {

    }

    //region Metodos de servicios
    //region Fotos Antes
    public void EliminarFotografiaAntes(ServiceRequest.Fotografia fotografia){
        dataBaseService.DeleteDetalleServicio(fotografia);
        serviceRequest.getFotografias_antes().remove(fotografia);
        File file = new File(fotografia.getRuta_fotografia());
        file.delete();
    };

    public ArrayList<ServiceRequest.Fotografia> agregarFotografiaAntes(ServiceRequest.Fotografia fotografia){
        fotografia.setTipo_fotografia(1);
        fotografia.setServicio_id(serviceRequest.getId());
        fotografia.setOrden(orden_fotografico);
        orden_fotografico += 1;
        fotografia.setServicio_detalle_id(dataBaseService.InsertDetalleServicio(fotografia));
        serviceRequest.getFotografias_antes().add(fotografia);
        return serviceRequest.getFotografias_antes();
    };
    public ArrayList<ServiceRequest.Fotografia> consultarFotografiasAntes(){
        return serviceRequest.getFotografias_antes();
    };
    //endregion

    //region Fotos Durante
    public ArrayList<ServiceRequest.Fotografia> agregarFotografiaDurante(ServiceRequest.Fotografia fotografia){
        fotografia.setTipo_fotografia(2);
        fotografia.setServicio_id(serviceRequest.getId());
        fotografia.setOrden(orden_fotografico);
        orden_fotografico += 1;
        fotografia.setServicio_detalle_id(dataBaseService.InsertDetalleServicio(fotografia));
        serviceRequest.getFotografias_durante().add(fotografia);
        return serviceRequest.getFotografias_durante();
    };

    public ArrayList<ServiceRequest.Fotografia> consultarFotografiasDurante(){
        return serviceRequest.getFotografias_durante();
    };

    public void EliminarFotografiaDurante(ServiceRequest.Fotografia fotografia){
        dataBaseService.DeleteDetalleServicio(fotografia);
        serviceRequest.getFotografias_durante().remove(fotografia);
        File file = new File(fotografia.getRuta_fotografia());
        file.delete();
    };
    //endregion

    //region Fotos Despues
    public ArrayList<ServiceRequest.Fotografia> agregarFotografiaDespues(ServiceRequest.Fotografia fotografia){
        fotografia.setTipo_fotografia(3);
        fotografia.setServicio_id(serviceRequest.getId());
        fotografia.setOrden(orden_fotografico);
        orden_fotografico += 1;
        fotografia.setServicio_detalle_id(dataBaseService.InsertDetalleServicio(fotografia));
        serviceRequest.getFotografias_despues().add(fotografia);
        return serviceRequest.getFotografias_despues();
    };

    public ArrayList<ServiceRequest.Fotografia> consultarFotografiasDespues(){
        return serviceRequest.getFotografias_despues();
    };

    public void EliminarFotografiaDespues(ServiceRequest.Fotografia fotografia){
        dataBaseService.DeleteDetalleServicio(fotografia);
        serviceRequest.getFotografias_despues().remove(fotografia);
        File file = new File(fotografia.getRuta_fotografia());
        file.delete();
    };

    //endregion

    //region Fotos  Etiqueta
    public ArrayList<ServiceRequest.Fotografia> agregarFotografiaTag(ServiceRequest.Fotografia fotografia){
        fotografia.setTipo_fotografia(4);
        fotografia.setServicio_id(serviceRequest.getId());
        fotografia.setOrden(orden_fotografico);
        orden_fotografico += 1;
        fotografia.setServicio_detalle_id(dataBaseService.InsertDetalleServicio(fotografia));
        serviceRequest.getFotografias_etiqueta().add(fotografia);
        return serviceRequest.getFotografias_etiqueta();
    };

    public ArrayList<ServiceRequest.Fotografia> consultarFotografiaTag(){
        return serviceRequest.getFotografias_etiqueta();
    };

    public void EliminarFotografiaTag(ServiceRequest.Fotografia fotografia){
        dataBaseService.DeleteDetalleServicio(fotografia);
        serviceRequest.getFotografias_etiqueta().remove(fotografia);
        File file = new File(fotografia.getRuta_fotografia());
        file.delete();
    };

    //endregion

    //region Fotos CAMBS
    public ArrayList<ServiceRequest.Fotografia> agregarFotografiaCambs(ServiceRequest.Fotografia fotografia){
        fotografia.setTipo_fotografia(6);
        fotografia.setServicio_id(serviceRequest.getId());
        fotografia.setOrden(orden_fotografico);
        orden_fotografico += 1;
        fotografia.setServicio_detalle_id(dataBaseService.InsertDetalleServicio(fotografia));
        serviceRequest.getFotografias_cambs().add(fotografia);
        return serviceRequest.getFotografias_cambs();
    };

    public ArrayList<ServiceRequest.Fotografia> consultarFotografiaCambs(){
        return serviceRequest.getFotografias_cambs();
    };

    public void EliminarFotografiaCambs(ServiceRequest.Fotografia fotografia){
        dataBaseService.DeleteDetalleServicio(fotografia);
        serviceRequest.getFotografias_cambs().remove(fotografia);
        File file = new File(fotografia.getRuta_fotografia());
        file.delete();
    };

    //endregion

    //region Fotos Serie
    public ArrayList<ServiceRequest.Fotografia> agregarFotografiaId(ServiceRequest.Fotografia fotografia){
        fotografia.setTipo_fotografia(5);
        fotografia.setServicio_id(serviceRequest.getId());
        fotografia.setOrden(orden_fotografico);
        orden_fotografico += 1;
        fotografia.setServicio_detalle_id(dataBaseService.InsertDetalleServicio(fotografia));
        serviceRequest.getFotografias_id().add(fotografia);
        return serviceRequest.getFotografias_id();
    };

    public ArrayList<ServiceRequest.Fotografia> consultarFotografiaId(){
        return serviceRequest.getFotografias_id();
    };

    public void EliminarFotografiaId(ServiceRequest.Fotografia fotografia){
        dataBaseService.DeleteDetalleServicio(fotografia);
        serviceRequest.getFotografias_id().remove(fotografia);
        File file = new File(fotografia.getRuta_fotografia());
        file.delete();
    };

    //endregion

    //region Fotos Bitacora
    public ArrayList<ServiceRequest.Fotografia> agregarFotografiaBitacora(ServiceRequest.Fotografia fotografia){
        fotografia.setTipo_fotografia(7);
        fotografia.setServicio_id(serviceRequest.getId());
        fotografia.setOrden(orden_fotografico);
        orden_fotografico += 1;
        fotografia.setServicio_detalle_id(dataBaseService.InsertDetalleServicio(fotografia));
        serviceRequest.getFotografias_bitacora().add(fotografia);
        return serviceRequest.getFotografias_bitacora();
    };

    public ArrayList<ServiceRequest.Fotografia> consultarFotografiaBitacora(){
        return serviceRequest.getFotografias_bitacora();
    };

    public void EliminarFotografiaBitacora(ServiceRequest.Fotografia fotografia){
        dataBaseService.DeleteDetalleServicio(fotografia);
        serviceRequest.getFotografias_bitacora().remove(fotografia);
        File file = new File(fotografia.getRuta_fotografia());
        file.delete();
    };

    //endregion

    //region Fotos Refaccion
    public ArrayList<ServiceRequest.Fotografia> agregarFotografiaRefaccion(ServiceRequest.Fotografia fotografia){
        fotografia.setTipo_fotografia(8);
        fotografia.setServicio_id(serviceRequest.getId());
        fotografia.setOrden(orden_fotografico);
        orden_fotografico += 1;
        fotografia.setServicio_detalle_id(dataBaseService.InsertDetalleServicio(fotografia));
        serviceRequest.getFotografias_refaccion().add(fotografia);
        return serviceRequest.getFotografias_refaccion();
    };

    public ArrayList<ServiceRequest.Fotografia> consultarFotografiaRefaccion(){
        return serviceRequest.getFotografias_refaccion();
    };

    public void EliminarFotografiaRefaccion(ServiceRequest.Fotografia fotografia){
        dataBaseService.DeleteDetalleServicio(fotografia);
        serviceRequest.getFotografias_refaccion().remove(fotografia);
        File file = new File(fotografia.getRuta_fotografia());
        file.delete();
    };

    //endregion
    //region Fotos Gafete
    public ArrayList<ServiceRequest.Fotografia> agregarFotografiaGafete(ServiceRequest.Fotografia fotografia){
        fotografia.setTipo_fotografia(9);
        fotografia.setServicio_id(serviceRequest.getId());
        fotografia.setOrden(orden_fotografico);
        orden_fotografico += 1;
        fotografia.setServicio_detalle_id(dataBaseService.InsertDetalleServicio(fotografia));
        serviceRequest.getFotografias_gafete().add(fotografia);
        return serviceRequest.getFotografias_gafete();
    };

    public ArrayList<ServiceRequest.Fotografia> consultarFotografiaGafete(){
        return serviceRequest.getFotografias_gafete();
    };

    public void EliminarFotografiaGafete(ServiceRequest.Fotografia fotografia){
        dataBaseService.DeleteDetalleServicio(fotografia);
        serviceRequest.getFotografias_gafete().remove(fotografia);
        File file = new File(fotografia.getRuta_fotografia());
        file.delete();
    };
    //endregion
    //endregion

    public void verImagen(ServiceRequest.Fotografia fotografia,int tipo_fotografia){
        ViewImageDialog dialog = new ViewImageDialog(fotografia,tipo_fotografia);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(),"Hola");
    }

    public void finalizarGuardado() {
        dataBaseService.TerminadoServicio(serviceRequest);
        dataBaseService.ModificarServicio(serviceRequest);
        vistaPreviaPdf(serviceRequest);
    }

    public void finalizarErroneoGuardado() {
        dataBaseService.ModificarServicioErroneo(serviceRequest);
    }

    public void finalizarNoRealizadoGuardado() {
        dataBaseService.ModificarServicioNoRealizado(serviceRequest);
        finish();
    }

    public void finalizarErroneoGuardadoWifi() {
        dataBaseService.TerminadoServicio(serviceRequest);
        dataBaseService.ModificarServicioErroneo(serviceRequest);
    }

    public void finalizarSinGuardado(ServiceRequest servicio) {
        dataBaseService.ModificarServicio(serviceRequest);
        if(servicio.getTipo_servicio() != 5){
            vistaPreviaPdf(servicio);
        }else {
            finish();
        }
    }

    public void vistaPreviaPdf(ServiceRequest servicio){
        Intent intent = new Intent(getApplicationContext(), ViewPdfActivity.class);
        String reporte_selecionado = "";
        switch (serviceRequest.getReporte_subir()){
            case 1:
                reporte_selecionado = Constants.generar_path_reporte(1, servicio.getNo_reporte());
                break;
            case 2:
                reporte_selecionado = Constants.generar_path_reporte(2, servicio.getNo_reporte());
                break;
            case 3:
                reporte_selecionado = Constants.generar_path_reporte(3, servicio.getNo_reporte());
                break;
        }
        intent.putExtra("servicio_pdf", reporte_selecionado);
        intent.putExtra("servicio_id", servicio.getId());
        startActivity(intent);
        finish();
    }

    public void agregarServicio(){
        try {
            serviceRequest.setId(dataBaseService.InsertServicio(serviceRequest));
            setTitle(serviceRequest.getNumero_reporte());
        }catch (Exception e){
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    public  void modificarEstatus(int id_estatus , String observaciones){
        //Creamos el dialog
        progressDialog = UI.createProgressDialog(this,"Cambiando de Fase","Cargando.....");
        progressDialog.show();
        //Creamos json que se enviara
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("id_estatus",id_estatus);
            jsonBody.put("observaciones",observaciones);
            progressDialog.dismiss();
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            ApiBodyService apiBodyService =new ApiBodyService(
                    Request.Method.PUT,
                    Constants.URL_API_SERVICIO + "/"+ serviceRequest.getId_servidor(),
                    jsonBody.toString(),
                    RequestSuccessListener(),RequestErrorListener());

            requestQueue.add(apiBodyService);
        } catch (Exception e) {
            progressDialog.dismiss();
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    public void finalizarServicioErroneo(String observaciones){
        serviceRequest.setObservaciones_finales(observaciones);
        //Verificamos si tenemos conexion de internet
        if(Network.VerifityConnection(this)){
            progressDialog = UI.createProgressDialog(this,"Finalizando Servicio","Cargando.....");
            progressDialog.show();
            //Agregamos el usuaio de creacion
            ResponseLoginModel.Response user_save = dataBaseService.GetUsuarios();
            serviceRequest.setUsuario_creacion(user_save.getUser_id());
            //Creamos json que se enviara
            try {
                String body = new Gson().toJson(serviceRequest,ServiceRequest.class);
                RequestQueue requestQueue = Volley.newRequestQueue(this);

                ApiBodyService apiBodyService =new ApiBodyService(
                        Request.Method.POST,
                        Constants.URL_API_SERVICIO_ERRONEO,
                        body,
                        RequestSuccessCompleteServicioErroneoListener(),RequestErrorListener());
                requestQueue.add(apiBodyService);
            } catch (Exception e) {
                progressDialog.dismiss();
            }
        }else{
            finalizarErroneoGuardado();
            finish();
        }
    }

    public void finalizarServicioNoEncontrado(){
        //Verificamos si tenemos conexion de internet
        if(Network.VerifityConnection(this)){
            progressDialog = UI.createProgressDialog(this,"Finalizando Servicio","Cargando.....");
            progressDialog.show();
            //Agregamos el usuaio de creacion
            ResponseLoginModel.Response user_save = dataBaseService.GetUsuarios();
            serviceRequest.setUsuario_creacion(user_save.getUser_id());
            //Creamos json que se enviara
            try {
                String body = new Gson().toJson(serviceRequest,ServiceRequest.class);
                RequestQueue requestQueue = Volley.newRequestQueue(this);

                ApiBodyService apiBodyService =new ApiBodyService(
                        Request.Method.POST,
                        Constants.URL_API_SERVICIO_NO_ENCONTRADO,
                        body,
                        RequestSuccessCompleteServicioNoRealizadoListener(),RequestErrorListener());
                requestQueue.add(apiBodyService);
            } catch (Exception e) {
                progressDialog.dismiss();
            }
        }else{
            finalizarNoRealizadoGuardado();
            finish();
        }
    }

    private Response.ErrorListener RequestErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(view, error.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        };
    }

    private Response.Listener<JSONObject> RequestSuccessListener() {
        return  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int response_flag = response.getInt("response_flag");
                    if(response_flag == 1){

                    }else{
                        Snackbar.make(view,"Ocurrio un error", Snackbar.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        };
    }

    public  void subirServicio(ArrayList<ServiceRequest.Fotografia> fotografias){
        progressDialog = UI.createProgressDialog(this,"Finalizando Servicio","Cargando.....");
        progressDialog.show();
        serviceRequest.setFotografias(new ArrayList<ServiceRequest.Fotografia>());
        serviceRequest.setFotografias(fotografias);
        switch (serviceRequest.getReporte_subir()){
            case 1:
                serviceRequest.setPath_pdf_1(Constants.generar_path_reporte_server(1,serviceRequest.getNo_reporte()));
                break;
            case 2:
                serviceRequest.setPath_pdf_2(Constants.generar_path_reporte_server(2,serviceRequest.getNo_reporte()));
                break;
            case 3:
                serviceRequest.setPath_pdf_3(Constants.generar_path_reporte_server(3,serviceRequest.getNo_reporte()));
                break;
        }
        //serviceRequest.setPath_pdf_inverso(Constants.generar_path_reporte_server_inverse(serviceRequest.getReporte_subir(), serviceRequest.getNumero_reporte()));

        //Insertamos el usuario de creacion del servicio
        ResponseLoginModel.Response user_save = dataBaseService.GetUsuarios();
        serviceRequest.setUsuario_creacion(user_save.getUser_id());
        //Creamos json que se enviara
        try {
            String body = new Gson().toJson(serviceRequest,ServiceRequest.class);
            RequestQueue requestQueue = Volley.newRequestQueue(this);

            ApiBodyService apiBodyService =new ApiBodyService(
                    Request.Method.POST,
                    Constants.URL_API_SERVICIO,
                    body,
                    RequestSuccessCompleteListener(),RequestErrorListener());

            requestQueue.add(apiBodyService);
        } catch (Exception e) {
            progressDialog.dismiss();
        }
    }



    private Response.Listener<JSONObject> RequestSuccessCompleteListener() {
        return  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int response_flag = response.getInt("response_flag");
                    if(response_flag == 1){
                        finalizarGuardado();
                        progressDialog.dismiss();
                    }else{
                        Snackbar.make(view, "Error al finalizar el servicio", Snackbar.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Snackbar.make(view, "Error al finalizar el servicio", Snackbar.LENGTH_LONG).show();
                }
            }
        };
    }

    private Response.Listener<JSONObject> RequestSuccessCompleteServicioErroneoListener() {
        return  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int response_flag = response.getInt("response_flag");
                    if(response_flag == 1){
                        finalizarErroneoGuardadoWifi();
                        progressDialog.dismiss();
                        finish();
                    }else{
                        Snackbar.make(view, "Error al finalizar el servicio", Snackbar.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Snackbar.make(view, "Error al finalizar el servicio", Snackbar.LENGTH_LONG).show();
                }
            }
        };
    }

    private Response.Listener<JSONObject> RequestSuccessCompleteServicioNoRealizadoListener() {
        return  new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int response_flag = response.getInt("response_flag");
                    if(response_flag == 1){
                        finalizarNoRealizadoGuardado();
                        dataBaseService.TerminadoServicio(serviceRequest);
                        progressDialog.dismiss();
                        finish();
                    }else{
                        Snackbar.make(view, "Error al finalizar el servicio", Snackbar.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Snackbar.make(view, "Error al finalizar el servicio", Snackbar.LENGTH_LONG).show();
                }
            }
        };
    }


    private void createDialogFinalizarServicio(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Finalizar Servicio");
        builder.setMessage("¿Estas seguro de finalizar el servicio?");
        builder.setPositiveButton("Finalizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    //Validamos si es un servicio extra
                    if(serviceRequest.getTipo_servicio() != 5){
                        // Se insertan las fotos
                        ArrayList<ServiceRequest.Fotografia> fotografias =  dataBaseService.GetDetalles(serviceRequest.getId());
                        //Se generan los pdf
                        String [] path_reportes_pdf = {
                                Constants.generar_path_reporte(1, serviceRequest.getNo_reporte()),
                                Constants.generar_path_reporte(2, serviceRequest.getNo_reporte()),
                                Constants.generar_path_reporte(3, serviceRequest.getNo_reporte()),
                        };
                        switch (serviceRequest.getReporte_subir()){
                            case 1:
                                serviceRequest.setPath_pdf_1(path_reportes_pdf[0]);
                                serviceRequest.setPath_pdf_2("");
                                serviceRequest.setPath_pdf_3("");
                                break;
                            case 2:
                                serviceRequest.setPath_pdf_2(path_reportes_pdf[1]);
                                serviceRequest.setPath_pdf_1("");
                                serviceRequest.setPath_pdf_3("");
                                break;
                            case 3:
                                serviceRequest.setPath_pdf_3(path_reportes_pdf[2]);
                                serviceRequest.setPath_pdf_2("");
                                serviceRequest.setPath_pdf_1("");
                                break;
                        }
                        //Generamos el hilo para generar los pdfs
                        //Mostramos la alerta
                        HiloGenerarPdf hiloGenerarPdf = new HiloGenerarPdf(fotografias, serviceRequest, AddServiceActivity.this, path_reportes_pdf,view);
                        hiloGenerarPdf.execute();
                    }else{
                        finalizarSinGuardado(serviceRequest);
                    }
                }catch (Exception e){
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void consultarServicioImcompleto(){
        //Abrimos bandera
        servicio_imcompleto = true;
        //Buscamos el servicio selecionado
        serviceRequest = dataBaseService.GetServicio(servicio_id);
        if(serviceRequest.getTipo_servicio() == 5){
            servicio_extra = true;
            setTitle(serviceRequest.getNumero_reporte());
        }
        //Consultamos las fotografias utilizadas
        ArrayList<ServiceRequest.Fotografia> fotografias =  dataBaseService.GetDetalles(servicio_id);
        //Recorremos las fotografias para agregarlas
        //Obtenemos el ultimo orden establecido en las fotografias
        int orden = 0;
        for (int i = 0; i < fotografias.size() ; i++){
            ServiceRequest.Fotografia fotografia_actual = fotografias.get(i);
            switch (fotografia_actual.getTipo_fotografia()){
                case 1:
                    serviceRequest.getFotografias_antes().add(fotografia_actual);
                    break;
                case 2:
                    serviceRequest.getFotografias_durante().add(fotografia_actual);
                    break;
                case 3:
                    serviceRequest.getFotografias_despues().add(fotografia_actual);
                    break;
                case 4:
                    serviceRequest.getFotografias_etiqueta().add(fotografia_actual);
                    break;
                case 5:
                    serviceRequest.getFotografias_id().add(fotografia_actual);
                    break;
                case 6:
                    serviceRequest.getFotografias_cambs().add(fotografia_actual);
                    break;
                case 7:
                    serviceRequest.getFotografias_bitacora().add(fotografia_actual);
                    break;
                case 8:
                    serviceRequest.getFotografias_refaccion().add(fotografia_actual);
                    break;
                case 9:
                    serviceRequest.getFotografias_gafete().add(fotografia_actual);
                    break;
            }
            if(i == fotografias.size() -1){
                orden_fotografico = (fotografia_actual.getOrden() + 1);
            }
        }
    }
}