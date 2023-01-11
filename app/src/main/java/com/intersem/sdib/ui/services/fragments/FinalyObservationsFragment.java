package com.intersem.sdib.ui.services.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.intersem.sdib.R;
import com.intersem.sdib.core.services.ApiBodyService;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.ui.services.models.ResponseObservacionesModel;
import com.intersem.sdib.ui.services.activities.AddServiceActivity;
import com.intersem.sdib.ui.services.models.ServiceRequest;
import com.intersem.sdib.core.utilities.Constants;
import com.intersem.sdib.core.utilities.Network;
import com.intersem.sdib.core.utilities.UI;
import com.itextpdf.text.pdf.TextField;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.util.ArrayList;

import org.json.JSONObject;

public class FinalyObservationsFragment extends Fragment implements Step {

    View view;
    TextInputEditText txtGastosServicio, txtObservaciones;
    TextView lblObservaciones;
    DataBaseService dataBaseService;

    public FinalyObservationsFragment(int option){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.activity_observaciones_finales, container, false);
        iniciarComponentes();
        return view;
    }


    private void iniciarComponentes(){
        dataBaseService = new DataBaseService(getContext());
        txtObservaciones = view.findViewById(R.id.txtObservaciones);
        txtGastosServicio = view.findViewById(R.id.txtGastosServicio);
        lblObservaciones = view.findViewById(R.id.lblObservaciones);
        txtObservaciones.setText(((AddServiceActivity) getActivity()).serviceRequest.getObservaciones_finales());
        txtGastosServicio.setText(((AddServiceActivity) getActivity()).serviceRequest.getGasto());
        if(((AddServiceActivity) getActivity()).serviceRequest.getTipo_servicio() == 3 ){
            lblObservaciones.setText("LUGAR DE SERVICIO");
        }
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        if(!txtGastosServicio.getText().toString().trim().isEmpty()){
            if(!txtObservaciones.getText().toString().trim().isEmpty()){
                ((AddServiceActivity) getActivity()).serviceRequest.setObservaciones_finales(txtObservaciones.getText().toString());
                ((AddServiceActivity) getActivity()).serviceRequest.setGasto(txtGastosServicio.getText().toString());
                dataBaseService.ModificarObservacionesServicio(((AddServiceActivity) getActivity()).serviceRequest);
                return null;
            }else{
                if(((AddServiceActivity) getActivity()).serviceRequest.getTipo_servicio() == 3 ){
                    return new VerificationError("Se debe ingresar el lugar del servicio");
                }else{
                    return new VerificationError("Se debe agregar una observación");
                }
            }
        }else{
            return new VerificationError("Se debe agregar un gasto");
        }
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }
}
