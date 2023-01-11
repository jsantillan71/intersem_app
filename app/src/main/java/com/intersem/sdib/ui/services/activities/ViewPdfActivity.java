package com.intersem.sdib.ui.services.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
import com.intersem.sdib.R;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.ui.services.models.ServiceRequest;
import com.intersem.sdib.core.utilities.Constants;
import com.intersem.sdib.core.utilities.UI;

import java.io.File;

public class ViewPdfActivity extends AppCompatActivity {
    private PDFView pdfView;
    private View view;
    private String pdf;
    private int servicio_id;
    private DataBaseService dataBaseService;
    private ServiceRequest serviceRequest;
    private boolean bitacora = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_d_f_view);
        initComponents();
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }

    @Override
    public void onBackPressed() {
        if(serviceRequest != null){
            if(serviceRequest.getBitacora() == 1){
                if(!bitacora){
                    UI.createDialog(this, "Bitacora", "Usted cuenta con un PDF de bitacora ¿Desea ver la vista previa? ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            pdf = Constants.generar_path_reporte(4, serviceRequest.getNo_reporte());
                            setTitle("Vista Previa Bitacora");
                            bitacora = true;
                            visualizarPDF();
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                }else{
                    finish();
                }
            }else{
                finish();
            }
        }
    }



    private void initComponents() {
        setTitle("Vista Previa Reporte");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dataBaseService =  new DataBaseService(getApplicationContext());
        view = findViewById(android.R.id.content);
        pdfView = findViewById(R.id.pdfViewer);
        pdf = getIntent().getStringExtra("servicio_pdf");
        servicio_id = getIntent().getIntExtra("servicio_id",0);
        if(servicio_id > 0){
            serviceRequest = dataBaseService.GetServicio(servicio_id);
        }
        visualizarPDF();
    }

    private void visualizarPDF(){
        File folder = new File(pdf);
        if(folder.exists()){
            pdfView.fromFile(folder).load();
        }
    }
}