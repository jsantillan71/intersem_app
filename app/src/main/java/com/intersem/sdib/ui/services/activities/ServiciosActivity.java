package com.intersem.sdib.ui.services.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.print.PrintHelper;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
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
import com.intersem.sdib.core.database.ScriptDataBase;
import com.intersem.sdib.core.services.ApiBodyService;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.ui.services.adapters.PdfDocumentAdapter;
import com.intersem.sdib.ui.services.adapters.ServiciosAdapter;
import com.intersem.sdib.ui.login.models.ResponseLoginModel;
import com.intersem.sdib.ui.services.models.ServiceRequest;
import com.intersem.sdib.core.utilities.Constants;
import com.intersem.sdib.core.utilities.Equipo;
import com.intersem.sdib.core.utilities.Empleado;
import com.intersem.sdib.core.utilities.Network;
import com.intersem.sdib.core.utilities.ResponseEquipos;
import com.intersem.sdib.core.utilities.ResponseEmpleados;
import com.intersem.sdib.core.utilities.UI;

import org.json.JSONObject;

import java.util.ArrayList;

public class ServiciosActivity extends AppCompatActivity {

    //region Atributos
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GridLayoutManager gridLayoutManager;
    private View view;
    private ServiciosAdapter serviciosAdapter;
    private FloatingActionButton fabAgregarServico;
    private DataBaseService dataBaseService;
    private ArrayList<ServiceRequest> responseServiciosArray;
    private ArrayList<ServiceRequest> responseEmpleadosArray;
    private ProgressDialog progressDialog;
    private static final String TAG = "MyActivity";
    //endregion

    //region Ciclo de Vida
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios_p);
        inicializarComponentes();
        actualizarEmpleados();
    }

    @Override
    public void onStart() {
        super.onStart();
        consultarServicios();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.services_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addServicio:
                Intent i = new Intent(getApplicationContext(), AddServiceActivity.class);
                i.putExtra("id_servicio", 0);
                startActivity(i);
                return true;
            /*
            case R.id.item_servicio_extra:
                Intent intent = new Intent(getApplicationContext(), AddServiceActivity.class);
                intent.putExtra("id_servicio", -1);
                startActivity(intent);
                return true;
                */
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    //endregion

    //region Funciones
    public void consultarServicios(){
        responseServiciosArray = dataBaseService.GetServicios();
        //Revisamos respuesta
        serviciosAdapter = new ServiciosAdapter(addClickButtonAdapterListener(), this, responseServiciosArray);
        recyclerView.setAdapter(serviciosAdapter);
        serviciosAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    private void inicializarComponentes(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        view = findViewById(android.R.id.content);
        dataBaseService = new DataBaseService(this);
        recyclerView = findViewById(R.id.rcvDatos);
        swipeRefreshLayout = findViewById(R.id.swipeServicios);
        gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        fabAgregarServico = findViewById(R.id.fabAddServicio);
        fabAgregarServico.setImageTintList(null);
        fabAgregarServico.setOnClickListener(OnCLickFabAgregarServicioListenter());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                consultarServicios();
            }
        });
    }
    //endregion

    //region Eventos
    private View.OnClickListener OnCLickFabAgregarServicioListenter() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
    }
    private ServiciosAdapter.ClickListener addClickButtonAdapterListener() {
        return new ServiciosAdapter.ClickListener() {
            @Override
            public void onPositionClicked(int position) {
                //Tomamos el servicio selecionado
                ServiceRequest servicio_selecionado = responseServiciosArray.get(position);
                //Verificamos si esta terminado
                if(servicio_selecionado.getTerminado() != -1 && servicio_selecionado.getTerminado() != -2){
                    if(servicio_selecionado.getTerminado() == 1){
                        //Verificamos si el servicio tiene bitacora
                        if(servicio_selecionado.getBitacora() == 1){
                            createRadioListDialog(servicio_selecionado).show();
                        }else{
                            String path = Constants.generar_path_reporte(servicio_selecionado.getReporte_subir(),servicio_selecionado.getNo_reporte());
                            doDocumentPrint(path,servicio_selecionado);
                        }
                    }else{
                        //Si no esta terminado, lo mandamos a la vista de servicios
                        Intent i = new Intent(getApplicationContext(), AddServiceActivity.class);
                        i.putExtra("id_servicio", servicio_selecionado.getId());
                        startActivity(i);
                    }
                }
            }
        };
    }

    public AlertDialog createRadioListDialog(final ServiceRequest servicio_selecionado) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        final CharSequence[] items = new CharSequence[2];
        items[0] = Constants.generar_name_reporte(servicio_selecionado.getReporte_subir(),servicio_selecionado.getNo_reporte());
        items[1] = "B" + servicio_selecionado.getNumero_reporte() + "bitacora.pdf";
        final int[] seleccionado = {0};
        builder.setTitle("¿Qué PDF quieres imprimir?")
                .setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        seleccionado[0] = (int)which;
                    }
                }).setPositiveButton("Imprimir", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String path_pdf = "";
                switch (seleccionado[0]){
                    case 0:
                        path_pdf = Constants.obtener_path_reporte(servicio_selecionado);
                        break;
                    case 1:
                        path_pdf = Constants.generar_path_reporte(4, servicio_selecionado.getNumero_reporte());
                        break;
                }
                if(seleccionado[0] == 1){
                    //Consultamos las fotografias y buscamos la bitacora
                    boolean existe = false;
                    ArrayList<ServiceRequest.Fotografia> fotografias = dataBaseService.GetDetalles(servicio_selecionado.getId());
                    for (ServiceRequest.Fotografia fotografia : fotografias){
                        if(fotografia.getTipo_fotografia() == 7 && fotografia.getCheck() == 1){
                            path_pdf = fotografia.getRuta_fotografia();
                            existe = true;
                        }
                    }
                    if(existe){
                        Bitmap bitmap = BitmapFactory.decodeFile(path_pdf);
                        doPhotoPrint(bitmap,servicio_selecionado);
                        return;
                    }else{
                        Snackbar.make(view,"No se cuenta con documeto de bitacora", Snackbar.LENGTH_SHORT).show();
                    }
                }else{
                    doDocumentPrint(path_pdf,servicio_selecionado);
                }
            }
        });
        return builder.create();
    }

    private void doPhotoPrint(Bitmap bigbitmap,ServiceRequest servicio)
    {
        PrintHelper photoPrinter = new PrintHelper(getApplicationContext());
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        photoPrinter.printBitmap("Bitacora-" + servicio.getNo_reporte(), bigbitmap);
        servicio.setPrinted(1);
        dataBaseService.ModificarServicio(servicio);
        consultarServicios();
    }

    private void doDocumentPrint(String path_pdf,ServiceRequest servicio_selecionado){
        try {
            PrintManager printManager = (PrintManager)getSystemService(Context.PRINT_SERVICE);
            PrintDocumentAdapter printDocumentAdapter = new PdfDocumentAdapter(getApplicationContext(), path_pdf,servicio_selecionado);
            printManager.print("Document", printDocumentAdapter, null);
        }catch (Exception e){
            Snackbar.make(view,e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
    }

    private void actualizarEquipos(){
        //Consultamos los archvios que debemos descargar

        progressDialog = UI.createProgressDialog(this,"Consultando equipos del servidor","Cargando.....");
        progressDialog.show();
        //Agregamos el usuaio de creacion
        ResponseLoginModel.Response user_save = dataBaseService.GetUsuarios();
        //Creamos json que se enviara
        try {
            JSONObject jsonBody = new JSONObject();
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            ApiBodyService apiBodyService =new ApiBodyService(
                    Request.Method.GET,
                    Constants.URL_API_EQUIPOS,
                    jsonBody.toString(),
                    RequestSuccessListener(),RequestErrorListener());
            requestQueue.add(apiBodyService);
        } catch (Exception e) {
            progressDialog.dismiss();
        }
    }

    private void actualizarEmpleados(){
        //Consultamos los archvios que debemos descargar

        progressDialog = UI.createProgressDialog(this,"Consultando empleados del servidor","Cargando.....");
        progressDialog.show();
        //Agregamos el usuaio de creacion
        ResponseLoginModel.Response user_save = dataBaseService.GetUsuarios();
        //Creamos json que se enviara
        try {
            JSONObject jsonBody = new JSONObject();
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            ApiBodyService apiBodyService =new ApiBodyService(
                    Request.Method.GET,
                    Constants.URL_API_Empleado,
                    jsonBody.toString(),
                    RequestSuccessListenerEmpleado(),RequestErrorListener());
            requestQueue.add(apiBodyService);
        } catch (Exception e) {
            progressDialog.dismiss();
        }
    }

    private Response.ErrorListener RequestErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(view, getText(R.string.message_error_api), Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        };
    }

    private Response.Listener<JSONObject> RequestSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseEquipos responseEquipos = new Gson().fromJson(response.toString(), ResponseEquipos.class);
                dataBaseService.cleanTable(ScriptDataBase.Equipo.TABLE_NAME,null, null);
                if(responseEquipos.getResponse_flag() == 1){
                    for (Equipo equipo : responseEquipos.getResponse()){
                        dataBaseService.InsertarEquipo(equipo);
                    }
                    progressDialog.dismiss();
                    Snackbar.make(view, "Equipos actualizados correctamente", Snackbar.LENGTH_LONG).show();
                }else{
                    Snackbar.make(view, getText(R.string.message_error_api), Snackbar.LENGTH_LONG).show();
                }
            }
        };
    }

    private Response.Listener<JSONObject> RequestSuccessListenerEmpleado() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                ResponseEmpleados responseEmpleados = new Gson().fromJson(response.toString(), ResponseEmpleados.class);
                dataBaseService.cleanTable(ScriptDataBase.Empleado.TABLE_NAME,null, null);
                if(responseEmpleados.getResponse_flag() == 1){
                    for (Empleado empleado : responseEmpleados.getResponse()){
                        dataBaseService.InsertarEmpleado(empleado);
                    }
                    progressDialog.dismiss();
                    Snackbar.make(view, "Equipos actualizados correctamente", Snackbar.LENGTH_LONG).show();
                }else{
                    Snackbar.make(view, getText(R.string.message_error_api), Snackbar.LENGTH_LONG).show();
                }
            }
        };
    }

    public void modificarServicio(ServiceRequest servicio_selecionado) {
        Intent intent = new Intent(getApplicationContext(), ViewPdfActivity.class);
        String reporte_selecionado = "";
        switch (servicio_selecionado.getReporte_subir()){
            case 1:
                reporte_selecionado = Constants.generar_path_reporte(1, servicio_selecionado.getNumero_reporte());
                break;
            case 2:
                reporte_selecionado = Constants.generar_path_reporte(2, servicio_selecionado.getNumero_reporte());
                break;
            case 3:
                reporte_selecionado = Constants.generar_path_reporte(3, servicio_selecionado.getNumero_reporte());
                break;
        }
        intent.putExtra("servicio_pdf", reporte_selecionado);
        intent.putExtra("servicio_id", servicio_selecionado.getId());
        startActivity(intent);
        finish();
    }
    //endregion
}