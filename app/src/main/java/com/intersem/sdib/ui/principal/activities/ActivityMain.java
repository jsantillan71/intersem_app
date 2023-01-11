package com.intersem.sdib.ui.principal.activities;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.intersem.sdib.R;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.core.utilities.Network;
import com.intersem.sdib.ui.archivos_auxiliares.activities.ArchivosAuxiliaresActivity;
import com.intersem.sdib.ui.agenda.activities.CalendarActivity;
import com.intersem.sdib.ui.services.activities.ServiciosActivity;
import com.intersem.sdib.core.utilities.Seguridad;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityMain extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private View view;
    private TextView txtUsuario;
    private NavigationView navigationView;
    private DataBaseService dataBaseService;
    private ImageView btnServicios , btnAgenda , btnGuias;
    private final static int MY_PERMISSIONS_REQUEST_LOCATION = 1000;
    private static String[] PERMISOS = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actyvity_main);
        inicializarComponentes();
        permisos();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //Recorremos para ver si todos los permisos fueron dados
        boolean completo = true;
        for (int i = 0; i < grantResults.length; i ++){
            if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                completo = false;
                break;
            }
        }
        if(completo){

        }else{
            permisos();
        }
    }

    private void inicializarComponentes() {
        view = findViewById(android.R.id.content);
        dataBaseService = new DataBaseService(getApplicationContext());
        btnAgenda =  findViewById(R.id.btnCalendario);
        btnServicios = findViewById(R.id.btnServicios);
        btnGuias = findViewById(R.id.btnGuias);

        btnServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ServiciosActivity.class);
                startActivity(i);
            }
        });

        btnAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(i);
            }
        });

        btnGuias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ArchivosAuxiliaresActivity.class);
                startActivity(i);
            }
        });

        //Consultamos la bandera de notifiacion
        int notificacion = getIntent().getIntExtra("notificacion",0);
        if(notificacion != 0 && notificacion != 3){
            notificacionServicios(notificacion);
        }
    }

    private void permisos(){
        //Checamos los permisos obligatorios
        int permissionReadExternalCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWriteExternalCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCameraCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA);
        //int permissionInstallCheck = ContextCompat.checkSelfPermission(this,Manifest.permission.REQUEST_INSTALL_PACKAGES);

        if( permissionReadExternalCheck == PackageManager.PERMISSION_GRANTED && permissionWriteExternalCheck == PackageManager.PERMISSION_GRANTED && permissionCameraCheck == PackageManager.PERMISSION_GRANTED){

        }else{
            ActivityCompat.requestPermissions(this,PERMISOS,MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }

    @Override
    public void onBackPressed() {
        Seguridad.closeSession(this);
    }

    private void notificacionServicios(int notificacion){
        btnAgenda.setImageResource(R.drawable.icono_agenda_pendientes);
    }

}