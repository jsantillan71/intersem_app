package com.intersem.sdib.ui.archivos_auxiliares.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.intersem.sdib.R;
import com.intersem.sdib.core.services.ApiBodyService;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.ui.archivos_auxiliares.adapters.ArchivosAuxiliaresAdapter;
import com.intersem.sdib.ui.archivos_auxiliares.models.ArchivoAuxialiarModel;
import com.intersem.sdib.ui.archivos_auxiliares.models.ResponseArchivosAuxiliares;
import com.intersem.sdib.ui.login.models.ResponseLoginModel;
import com.intersem.sdib.core.utilities.Constants;
import com.intersem.sdib.core.utilities.HiloDescargarArchivosFTP;
import com.intersem.sdib.core.utilities.Network;
import com.intersem.sdib.core.utilities.UI;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

public class ArchivosAuxiliaresActivity extends AppCompatActivity {

    //Elementos de la vista
    View view;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    DataBaseService dataBaseService;
    ArrayList<ArchivoAuxialiarModel> archivoAuxialiarArrayList;
    ArchivosAuxiliaresAdapter archivosAuxiliaresAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archivos_auxiliares);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        view = findViewById(android.R.id.content);
        recyclerView = findViewById(R.id.rcvDatos);
        gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        dataBaseService =  new DataBaseService(this);
        if(Network.VerifityWifiConnection(getApplicationContext())){
            if(dataBaseService.GetServiciosAgenda().size() > 0)
                descargaArchivos();
        }else{
            consultarArchivos();
        }
    }

    public void consultarArchivos() {
        archivoAuxialiarArrayList = dataBaseService.ConsultarArchivosAuxialiar();
        if(archivoAuxialiarArrayList.size() == 0)
            Snackbar.make(view,"No se tienen archivos almacenados",Snackbar.LENGTH_SHORT);

        crearVista();
    }

    private void crearVista() {
        archivosAuxiliaresAdapter = new ArchivosAuxiliaresAdapter(addClickButtonAdapterListener(),this,archivoAuxialiarArrayList);
        recyclerView.setAdapter(archivosAuxiliaresAdapter);
        archivosAuxiliaresAdapter.notifyDataSetChanged();
    }

    private ArchivosAuxiliaresAdapter.ClickListener addClickButtonAdapterListener() {
        return new ArchivosAuxiliaresAdapter.ClickListener() {
            @Override
            public void onPositionClicked(int position) {
                ArchivoAuxialiarModel archvio_selecionado = archivoAuxialiarArrayList.get(position);
                openFile(archvio_selecionado);
            }
        };
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.archivos_auxiliares_menu,menu);
        SearchView searchView = (SearchView)menu.findItem(R.id.search_list).getActionView();
        searchView.setOnQueryTextListener(addQueryTextToSearchView());
        return true;
    }

    private SearchView.OnQueryTextListener addQueryTextToSearchView(){
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(archivosAuxiliaresAdapter != null){
                    archivosAuxiliaresAdapter.getFilter().filter(newText);
                }
                return false;
            }
        };
    }



    public void descargaArchivos(){
        //Consultamos los archvios que debemos descargar
        progressDialog = UI.createProgressDialog(this,"Consultando archivios auxiliares","Cargando.....");
        progressDialog.show();
        //Agregamos el usuaio de creacion
        ResponseLoginModel.Response user_save = dataBaseService.GetUsuarios();
        //Creamos json que se enviara
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("usuario_creacion",user_save.getUser_id());
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            ApiBodyService apiBodyService =new ApiBodyService(
                    Request.Method.POST,
                    Constants.URL_API_DOCUMENTOS_AUXILIARES,
                    jsonBody.toString(),
                    RequestSuccessListener(),RequestErrorListener());
            requestQueue.add(apiBodyService);
        } catch (Exception e) {
            progressDialog.dismiss();
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
                    progressDialog.dismiss();
                    int response_flag = response.getInt("response_flag");
                    if(response_flag == 1){
                        ResponseArchivosAuxiliares response_archivos  = new Gson().fromJson(response.toString(), ResponseArchivosAuxiliares.class);
                        if(response_archivos.getResponse().size() > 0){
                            HiloDescargarArchivosFTP hilo = new HiloDescargarArchivosFTP(ArchivosAuxiliaresActivity.this,response_archivos);
                            hilo.execute();
                        }else{
                            Snackbar.make(view,"No se tienen archivos auxiliares", Snackbar.LENGTH_LONG).show();
                        }
                    }else{
                        Snackbar.make(view,getString(R.string.message_error_api), Snackbar.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        };
    }

    public void openFile(ArchivoAuxialiarModel archivo){
        File file = new File(archivo.getRuta_archivo());
        Uri uri_prueba = FileProvider.getUriForFile(this,
                "com.intersem.sdib.fileprovider",
                file);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri_prueba, MimeTypeMap.getSingleton().getMimeTypeFromExtension(archivo.getTipo()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No existe una aplicación para abrir el PDF", Toast.LENGTH_SHORT).show();
        }
    }
}