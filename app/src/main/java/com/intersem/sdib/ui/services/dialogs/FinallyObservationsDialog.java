package com.intersem.sdib.ui.services.dialogs;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;

import com.google.android.material.snackbar.Snackbar;
import com.intersem.sdib.R;
import com.intersem.sdib.ui.services.activities.AddServiceActivity;

public class FinallyObservationsDialog extends AppCompatDialogFragment {
    private View view;
    private TextView txtFinalizar;
    TextView txtObservaciones;
    CardView btnNoEstaUnidad;
    CardView btnDieronBaja;
    CardView btnNoDisponible;
    CardView btnIncompleto;
    CardView btnCabms;
    CardView btnNoEnciende;
    int observacion_selecionada = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(AppCompatDialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.finally_observations_dialog, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            inicializarComponenetes();
        }
        return view;
    }

    private void inicializarComponenetes() {
        txtFinalizar = view.findViewById(R.id.txtGuardarCambios);
        txtObservaciones = view.findViewById(R.id.txtObservaciones);
        btnNoEstaUnidad =  view.findViewById(R.id.btnNoEstaUnidad);
        btnDieronBaja =  view.findViewById(R.id.btnDieronBaja);
        btnNoDisponible =  view.findViewById(R.id.btnNoDisponible);
        btnIncompleto =  view.findViewById(R.id.btnIncompleto);
        btnCabms =  view.findViewById(R.id.btnCabms);
        btnNoEnciende =  view.findViewById(R.id.btnNoEnciende);
        txtObservaciones.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                btnNoEstaUnidad.setAlpha((float) 0.4);
                btnDieronBaja.setAlpha((float) 0.4);
                btnNoDisponible.setAlpha((float) 0.4);
                btnIncompleto.setAlpha((float) 0.4);
                btnCabms.setAlpha((float) 0.4);
                btnNoEnciende.setAlpha((float) 0.4);
                observacion_selecionada = 0;
            }
        });
        btnNoEstaUnidad.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                btnNoEstaUnidad.setAlpha((float) 1);
                btnDieronBaja.setAlpha((float) 0.4);
                btnNoDisponible.setAlpha((float) 0.4);
                btnIncompleto.setAlpha((float) 0.4);
                btnCabms.setAlpha((float) 0.4);
                btnNoEnciende.setAlpha((float) 0.4);
                observacion_selecionada = 1;
                txtObservaciones.clearFocus();
            }
        });
        btnDieronBaja.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                btnNoEstaUnidad.setAlpha((float) 0.4);
                btnDieronBaja.setAlpha((float) 1);
                btnNoDisponible.setAlpha((float) 0.4);
                btnIncompleto.setAlpha((float) 0.4);
                btnCabms.setAlpha((float) 0.4);
                btnNoEnciende.setAlpha((float) 0.4);
                observacion_selecionada = 2;
                txtObservaciones.clearFocus();
            }
        });
        btnNoDisponible.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                btnNoEstaUnidad.setAlpha((float) 0.4);
                btnDieronBaja.setAlpha((float) 0.4);
                btnNoDisponible.setAlpha((float) 1);
                btnIncompleto.setAlpha((float) 0.4);
                btnCabms.setAlpha((float) 0.4);
                btnNoEnciende.setAlpha((float) 0.4);
                observacion_selecionada = 3;
                txtObservaciones.clearFocus();
            }
        });
        btnIncompleto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                btnNoEstaUnidad.setAlpha((float) 0.4);
                btnDieronBaja.setAlpha((float) 0.4);
                btnNoDisponible.setAlpha((float) 0.4);
                btnIncompleto.setAlpha((float) 1);
                btnCabms.setAlpha((float) 0.4);
                btnNoEnciende.setAlpha((float) 0.4);
                observacion_selecionada = 4;
                txtObservaciones.clearFocus();
            }
        });
        btnCabms.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                btnNoEstaUnidad.setAlpha((float) 0.4);
                btnDieronBaja.setAlpha((float) 0.4);
                btnNoDisponible.setAlpha((float) 0.4);
                btnIncompleto.setAlpha((float) 0.4);
                btnCabms.setAlpha((float) 1);
                btnNoEnciende.setAlpha((float) 0.4);
                observacion_selecionada = 5;
                txtObservaciones.clearFocus();
            }
        });
        btnNoEnciende.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                btnNoEstaUnidad.setAlpha((float) 0.4);
                btnDieronBaja.setAlpha((float) 0.4);
                btnNoDisponible.setAlpha((float) 0.4);
                btnIncompleto.setAlpha((float) 0.4);
                btnCabms.setAlpha((float) 0.4);
                btnNoEnciende.setAlpha((float) 1);
                observacion_selecionada = 6;
                txtObservaciones.clearFocus();
            }
        });

        txtFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String observacion = "";
                if(observacion_selecionada == 0){
                    if(txtObservaciones.getText().toString().trim().isEmpty()){
                        Snackbar.make(view, "Debes escribir el motivo de la cancelación",Snackbar.LENGTH_SHORT).show();
                        return;
                    }else{
                        observacion = txtObservaciones.getText().toString();
                    }
                }else{
                    switch (observacion_selecionada){
                        case 1:
                            observacion = "No está en la unidad";
                            break;
                        case 2:
                            observacion = "Lo dieron de baja";
                            break;
                        case 3:
                            observacion = "No estaba disponible";
                            break;
                        case 4:
                            observacion = "Estaba incompleto/desarmado";
                            break;
                        case 5:
                            observacion = "Serie/Cabms no coindicen";
                            break;
                        case 6:
                            observacion = "No enciende";
                            break;
                    }
                }
                ((AddServiceActivity) getActivity()).finalizarServicioErroneo(observacion.toUpperCase());
            }
        });
    }
}
