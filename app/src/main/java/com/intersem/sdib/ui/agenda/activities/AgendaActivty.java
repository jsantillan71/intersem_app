package com.intersem.sdib.ui.agenda.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.intersem.sdib.R;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.core.utilities.Cliente;
import com.intersem.sdib.ui.agenda.adapters.ServiciosAgendaAdapter;
import com.intersem.sdib.ui.agenda.dialogs.AgendaDialog;
import com.intersem.sdib.ui.agenda.models.AgendaModel;
import com.intersem.sdib.ui.agenda.models.NotaModel;
import com.intersem.sdib.ui.services.models.ServiceRequest;

import java.util.ArrayList;

import static com.intersem.sdib.core.utilities.Constants.getCurrentTimeStamp;
import static com.intersem.sdib.core.utilities.Constants.obtenerFormato;
import static com.intersem.sdib.core.utilities.Constants.obtenerTipoServicio;

public class AgendaActivty extends AppCompatActivity {

    View view;
    RecyclerView recyclerView;
    GridLayoutManager gridLayoutManager;
    DataBaseService dataBaseService;
    ArrayList<ServiceRequest> serviciosAgenda;
    ArrayList<NotaModel> notasAgenda;
    ArrayList<AgendaModel> elementos_agenda;
    AgendaModel evento_seleccionado;
    ArrayList<ServiceRequest> serviciosselecionados = new ArrayList<>();
    ServiciosAgendaAdapter serviciosAgendaAdapter;
    ProgressDialog progressDialog;
    public ServiceRequest servicio_selecionado;
    String fecha_servicio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_activty);
        inicilizarComponenetes();
    }

    @Override
    public void onStart() {
        super.onStart();
        consultarArchivos();
    }

    private void consultarArchivos() {
        serviciosAgenda = dataBaseService.GetServiciosAgenda();
        notasAgenda = dataBaseService.ConsultarNotas();
        elementos_agenda = new ArrayList<>();
        for (ServiceRequest servicio : serviciosAgenda){
            if(servicio.getFecha_servicio() != null){
                if(servicio.getFecha_servicio().equals(fecha_servicio)){
                    Cliente cliente_selecionado = new Gson().fromJson(servicio.getClientes_datos(), Cliente.class);
                    AgendaModel nuevo_elemento_agenda = new AgendaModel();
                    nuevo_elemento_agenda.setCliente(cliente_selecionado.getRazon_social());
                    nuevo_elemento_agenda.setEquipo(servicio.getEquipo());
                    nuevo_elemento_agenda.setId_registro(servicio.getId_servidor());
                    nuevo_elemento_agenda.setFolio("SERVICIO  " + servicio.getNo_reporte() +" "+ obtenerFormato(servicio.getReporte_subir()));
                    nuevo_elemento_agenda.setTipo_servicio(obtenerTipoServicio(servicio.getTipo_servicio()));
                    nuevo_elemento_agenda.setTipo_id(1);
                    elementos_agenda.add(nuevo_elemento_agenda);
                }
            }
        }

        for (NotaModel nota : notasAgenda){
            if(nota.getFecha() != null){
                if(nota.getFecha().equals(fecha_servicio)){
                    AgendaModel nuevo_elemento_agenda = new AgendaModel();
                    nuevo_elemento_agenda.setCliente(nota.getCliente());
                    nuevo_elemento_agenda.setEquipo(nota.getNota());
                    nuevo_elemento_agenda.setFolio("NOTA");
                    nuevo_elemento_agenda.setTipo_servicio("N/A");
                    nuevo_elemento_agenda.setId_registro(nota.getId());
                    nuevo_elemento_agenda.setTipo_id(2);
                    elementos_agenda.add(nuevo_elemento_agenda);
                }
            }
        }
        crearVista();
    }

    private void crearVista() {
        serviciosAgendaAdapter = new ServiciosAgendaAdapter(new ServiciosAgendaAdapter.ClickListener() {
            @Override
            public void onPositionClicked(int position) {
                evento_seleccionado = elementos_agenda.get(position);
                if(evento_seleccionado.getTipo_id() == 1){
                    servicio_selecionado = null;
                    for (ServiceRequest servicio_actual : serviciosAgenda){
                        if(evento_seleccionado.getId_registro() == servicio_actual.getId_servidor()){
                            servicio_selecionado = servicio_actual;
                        }
                    }
                    AgendaDialog dialog = new AgendaDialog();
                    dialog.show(getSupportFragmentManager(), "");
                }
            }
        }, this, elementos_agenda);
        recyclerView.setAdapter(serviciosAgendaAdapter);
        serviciosAgendaAdapter.notifyDataSetChanged();
    }

    private void inicilizarComponenetes() {
        dataBaseService = new DataBaseService(getApplicationContext());
        view = findViewById(android.R.id.content);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView = findViewById(R.id.rcvDatos);
        gridLayoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setHasFixedSize(true);
        fecha_servicio = getIntent().getExtras().getString("fecha_servicio","");
        setTitle("Eventos " +fecha_servicio);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.agenda_servicios,menu);
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
                if(serviciosAgendaAdapter != null){
                    serviciosAgendaAdapter.getFilter().filter(newText);
                }
                return false;
            }
        };
    }
}