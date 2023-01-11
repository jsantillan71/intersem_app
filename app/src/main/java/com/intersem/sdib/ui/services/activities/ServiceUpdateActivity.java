package com.intersem.sdib.ui.services.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.intersem.sdib.R;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.ui.services.adapters.FotografiaAdapter;
import com.intersem.sdib.ui.services.helpers.MyItemTouchHelperCallback;
import com.intersem.sdib.ui.services.helpers.OnStartDragListener;
import com.intersem.sdib.ui.services.models.ServiceRequest;
import com.intersem.sdib.core.utilities.Constants;
import com.intersem.sdib.core.utilities.HiloGenerarPdfModificar;
import com.intersem.sdib.core.utilities.UI;

import java.util.ArrayList;

public class ServiceUpdateActivity extends AppCompatActivity {

    private View view;
    private RecyclerView rcvViewAllImages;
    private FotografiaAdapter fotografiaAdapter;
    private ImageButton imgCerrar;
    ArrayList<ServiceRequest.Fotografia> fotografias;
    private GridLayoutManager gridLayoutManager;
    ItemTouchHelper itemTouchHelper;
    private TextView txtGuadarCambios;
    private DataBaseService dataBaseService;
    private ProgressDialog progressDialog;
    ServiceRequest servicio_selecionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_update);
        inicializarComponentes();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.modificar_servicio,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.modificarServicio:
                generarPdf();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void generarPdf() {
        //Modificamos el orden de las fotos
        ArrayList<ServiceRequest.Fotografia> fotografias_nuevo_orden =  fotografiaAdapter.obtenerFotografias();
        int orden = 0;
        for(ServiceRequest.Fotografia fotografia : fotografias_nuevo_orden){
            fotografia.setOrden(orden);
            dataBaseService.ModificarDetalleServicio(fotografia);
            orden += 1;
        }

        fotografias = dataBaseService.GetDetalles(servicio_selecionado.getId());
        //generamos el PDF de nuevo
        HiloGenerarPdfModificar hilo = new HiloGenerarPdfModificar(fotografias , servicio_selecionado ,ServiceUpdateActivity.this , 0);
        hilo.execute();
    }

    private void inicializarComponentes() {
        //txtGuadarCambios = findViewById(R.id.txtGuardarCambios);
        rcvViewAllImages =  findViewById(R.id.rcvFotografias);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        rcvViewAllImages.setLayoutManager(gridLayoutManager);
        rcvViewAllImages.setHasFixedSize(true);
        dataBaseService = new DataBaseService(this);
        cargarInformacion();
    }

    private void cargarInformacion() {
        progressDialog = UI.createProgressDialog(this,"Cargando Imagenes","Cargando.....");
        progressDialog.show();
        int servicio_id = getIntent().getExtras().getInt("id_servicio",0);
        servicio_selecionado = dataBaseService.GetServicio(servicio_id);
        setTitle(servicio_selecionado.getNo_reporte());
        fotografias = dataBaseService.GetDetalles(servicio_id);
        fotografiaAdapter = new FotografiaAdapter(this,fotografias, new OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                itemTouchHelper.startDrag(viewHolder);
            };
        });
        rcvViewAllImages.setAdapter(fotografiaAdapter);
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(fotografiaAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rcvViewAllImages);

        progressDialog.dismiss();
    }

    public void modificarServicio(ServiceRequest servicio_selecionado){
        dataBaseService.ModificarServicioSubido(servicio_selecionado);
        vistaPreviaPdf(servicio_selecionado);
    }

    public void vistaPreviaPdf(ServiceRequest servicio){
        Intent intent = new Intent(getApplicationContext(), ViewPdfActivity.class);
        String reporte_selecionado = "";
        switch (servicio.getReporte_subir()){
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
}