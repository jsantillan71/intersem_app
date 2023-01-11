package com.intersem.sdib.ui.services.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.intersem.sdib.R;
import com.intersem.sdib.core.services.ApiBodyService;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.ui.services.models.ResponseObservacionesModel;
import com.intersem.sdib.ui.services.activities.AddServiceActivity;
import com.intersem.sdib.ui.services.dialogs.FinallyObservationsDialog;
import com.intersem.sdib.ui.services.models.ServiceRequest;
import com.intersem.sdib.core.utilities.Constants;
import com.intersem.sdib.core.utilities.Equipo;
import com.intersem.sdib.core.utilities.Network;
import com.intersem.sdib.core.utilities.UI;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import org.json.JSONObject;

import java.util.ArrayList;

import fr.ganfra.materialspinner.MaterialSpinner;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class ServiceGeneralFragment extends Fragment  implements Step {
    private int option;
    private View view;
    private TextInputEditText txtNumeroReporte, txtCambs, txtEquipo,txtMarca,txtModelo,txtSerie;
    private ImageView btnBuscar ,btnNuevo ,btnPreventivo,btnCorrectivo,btnInt,btnTec,btnRet,btnOk ,btnNe , btnNR;
    private int tipo_servicio = 0;
    private int tipo_reporte;
    private int estado_servicio;
    Equipo equipo_selecionado;
    SpinnerDialog spinnerDialog;
    SpinnerDialog spinnerDialogServicio;
    ArrayList<Equipo> equiposArrayList;
    DataBaseService dataBaseService;
    Snackbar snackbar;
    boolean nuevo_servicio = false;
    ArrayList<ServiceRequest> servicios_asignados;

    //region Events

    public ServiceGeneralFragment(int option) {
        this.option = option;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_service_general_information, container, false);
        iniciarComponentes();
        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerDialogServicio.showSpinerDialog();
                /*
                if(Network.VerifityConnection(getContext())){
                        if(tipo_servicio != 0 ){
                            if(txtNumeroReporte.getText().toString().trim().length() > 0){
                                consultarServicio(txtNumeroReporte.getText().toString());
                            }else{
                                Snackbar.make(view, getString(R.string.message_consultar_reporte), Snackbar.LENGTH_LONG).show();
                            }
                        }else{
                            Snackbar.make(view, "Se debe selecionar el tipo de servicio", Snackbar.LENGTH_LONG).show();
                        }
                }else{
                    Snackbar.make(view,getString(R.string.meesage_internet),Snackbar.LENGTH_SHORT).show();
                }*/
            }
        });
        return  view;
    }

    private void iniciarComponentes() {

        dataBaseService = new DataBaseService(getContext());
        txtNumeroReporte = view.findViewById(R.id.txtNumeroReporte);
        txtCambs = view.findViewById(R.id.txtCambs);
        txtMarca = view.findViewById(R.id.txtMarca);
        txtEquipo =  view.findViewById(R.id.txtEquipo);
        txtModelo =  view.findViewById(R.id.txtModelo);
        txtSerie = view.findViewById(R.id.txtSerie);
        btnBuscar = view.findViewById(R.id.btnBuscar);
        btnNuevo = view.findViewById(R.id.btnNuevo);
        btnCorrectivo =  view.findViewById(R.id.btnCorrectivo);
        btnPreventivo =  view.findViewById(R.id.btnPreventivo);
        btnInt =  view.findViewById(R.id.btnInt);
        btnTec =  view.findViewById(R.id.btnTec);
        btnRet =  view.findViewById(R.id.btnRet);
        btnNR = view.findViewById(R.id.btnNR);

        btnOk =  view.findViewById(R.id.btnOk);
        btnNe =  view.findViewById(R.id.btnNe);

        //region Botones Tipo Servicio
        btnCorrectivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipo_servicio = 1;
                btnCorrectivo.setAlpha((float) 1);
                btnPreventivo.setAlpha((float) 0.4);
            }
        });

        btnPreventivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipo_servicio = 2;
                btnCorrectivo.setAlpha((float) 0.4);
                btnPreventivo.setAlpha((float) 1);
            }
        });

        //Botones Tipo de Reporte
        btnInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipo_reporte = 1;
                btnInt.setAlpha((float)1);
                btnTec.setAlpha((float) 0.4);
                btnRet.setAlpha((float) 0.4);
            }
        });


        btnTec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipo_reporte = 2;
                btnInt.setAlpha((float)0.4);
                btnTec.setAlpha((float) 1);
                btnRet.setAlpha((float) 0.4);
            }
        });


        btnRet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tipo_reporte = 3;
                btnInt.setAlpha((float)0.4);
                btnTec.setAlpha((float) 0.4);
                btnRet.setAlpha((float) 1);
            }
        });

        //Botones Estado Servicio

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estado_servicio = 1;
                btnOk.setAlpha((float)1);
                btnNe.setAlpha((float) 0.4);
                btnNR.setAlpha((float) 0.4);
            }
        });

        btnNe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estado_servicio = 2;
                btnOk.setAlpha((float)0.4);
                btnNe.setAlpha((float) 1);
                btnNR.setAlpha((float) 0.4);
            }
        });

        btnNR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estado_servicio = 3;
                btnOk.setAlpha((float)0.4);
                btnNe.setAlpha((float) 0.4);
                btnNR.setAlpha((float) 1);
            }
        });

        //endregion
        if(((AddServiceActivity) getActivity()).servicio_imcompleto) llenarInformacionServicio();
        equiposArrayList = dataBaseService.GetEquipos();
        //Creamos arreglo de equipos
        ArrayList<String> items = new ArrayList<>();
        for (Equipo equipo : equiposArrayList){
            items.add(equipo.getEquipo());
        }
        spinnerDialog=new SpinnerDialog(getActivity(),items,"Selecionar Equipo de Servicio",R.style.DialogAnimations_SmileWindow,"Cerrar");
        spinnerDialog.setCancellable(false); // for cancellable
        spinnerDialog.setShowKeyboard(false);// for open keyboard by default
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                equipo_selecionado = equiposArrayList.get(position);
                txtEquipo.setText(equipo_selecionado.getEquipo());
                txtMarca.setText(equipo_selecionado.getMarca());
                txtModelo.setText(equipo_selecionado.getModelo());
            }
        });
        txtEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinnerDialog.showSpinerDialog();
                txtSerie.setEnabled(true);
                txtCambs.setEnabled(true);
            }
        });

        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Network.VerifityConnection(getContext())){
                    if(tipo_servicio != 0){
                        consultarFolio();
                    }else{
                        Snackbar.make(view,"Se debe selecionar un tipo de servicio",Snackbar.LENGTH_SHORT).show();
                    }
                }else{
                    Snackbar.make(view,getString(R.string.meesage_internet),Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        //Consultamos los servicios disponibles
        servicios_asignados = dataBaseService.GetServiciosAgenda();
        ArrayList<ServiceRequest>  servicio_asignados_temp = new ArrayList<>();
        //Consultamos los servicios realizados
        ArrayList<ServiceRequest>  servicio_realizados = dataBaseService.GetServicios();
        //Generamos el arreglo de Strings
        ArrayList<String> servicios_cadenas = new ArrayList<>();
        boolean bandera_terminado = false;
        for (ServiceRequest servicio : servicios_asignados){
            for (ServiceRequest servicio_realizado : servicio_realizados){
                if(servicio.getNumero_reporte().equals(servicio_realizado.getNumero_reporte()) && servicio_realizado.getTerminado() == 1){
                    bandera_terminado = true;
                }
            }
            if(!bandera_terminado){
                servicio_asignados_temp.add(servicio);
                String tipo_servicio = (servicio.getTipo_servicio() == 1) ? "PREVENTVO" : "CORRECTIVO";
                String tipo_reporte = (servicio.getReporte_subir() == 1) ? "INT" : "TEC";
                servicios_cadenas.add(tipo_reporte +"-"+ servicio.getNumero_reporte() +"  " + tipo_servicio);
            }
            bandera_terminado = false;
        }

        servicios_asignados = servicio_asignados_temp;
        spinnerDialogServicio=new SpinnerDialog(getActivity(),servicios_cadenas,"Selecionar Servicio",R.style.DialogAnimations_SmileWindow,"Cerrar");
        spinnerDialogServicio.setCancellable(false); // for cancellable
        spinnerDialogServicio.setShowKeyboard(false);// for open keyboard by default
        spinnerDialogServicio.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                llenarInformacionServicioSelecionado(servicios_asignados.get(position));
            }
        });
    }


    @Nullable
    @Override
    public VerificationError verifyStep() {
        if(validarInformacion() == false){
            return new  VerificationError("Faltan datos que llenar");
        }else{
            //Validamos si ya se creo el servicio
            if(estado_servicio != 1 ){

                    if(!verificarServicioRealizado()){
                        //Se almacena el servicio
                        if(((AddServiceActivity) getActivity()).serviceRequest.getId() == 0){
                            int equipo_id = (equipo_selecionado != null ) ? equipo_selecionado.getEquipo_id() : 0;
                            ((AddServiceActivity)getActivity()).serviceRequest.setNumero_reporte(txtNumeroReporte.getText().toString());
                            ((AddServiceActivity)getActivity()).serviceRequest.setTipo_servicio(tipo_servicio);
                            ((AddServiceActivity)getActivity()).serviceRequest.setEquipo(txtEquipo.getText().toString());
                            ((AddServiceActivity)getActivity()).serviceRequest.setNo_reporte(txtNumeroReporte.getText().toString());
                            ((AddServiceActivity)getActivity()).serviceRequest.setMarca(txtMarca.getText().toString());
                            ((AddServiceActivity)getActivity()).serviceRequest.setModelo(txtModelo.getText().toString());
                            ((AddServiceActivity)getActivity()).serviceRequest.setSerie(txtSerie.getText().toString());
                            ((AddServiceActivity)getActivity()).serviceRequest.setImprimir(1);
                            ((AddServiceActivity)getActivity()).serviceRequest.setReporte_subir(tipo_reporte);
                            ((AddServiceActivity)getActivity()).serviceRequest.setCabms(txtCambs.getText().toString());
                            ((AddServiceActivity)getActivity()).serviceRequest.setEquipo_id(equipo_id);
                            ((AddServiceActivity)getActivity()).agregarServicio();
                        }

                        //Validamos el tipo de estado de servicio
                        if(estado_servicio == 3){
                            //Se abre la el dialog para escoger la observacion de finalización
                            FinallyObservationsDialog finallyObservationsDialog = new FinallyObservationsDialog();
                            finallyObservationsDialog.setCancelable(true);
                            finallyObservationsDialog.show(getParentFragmentManager(),"Hola");
                            return new  VerificationError("Finalizar Servicio");
                        }else{
                            mostrarMensajeServicioNoEncontrado();
                            return new  VerificationError("Finalizar Servicio");
                        }
                    }else {
                        return new  VerificationError("No. Reporte ya fue registrado");
                    }
            }else{
                if(((AddServiceActivity) getActivity()).serviceRequest.getId() == 0){
                    int equipo_id = (equipo_selecionado != null ) ? equipo_selecionado.getEquipo_id() : 0;
                    ((AddServiceActivity)getActivity()).serviceRequest.setNumero_reporte(txtNumeroReporte.getText().toString());
                    ((AddServiceActivity)getActivity()).serviceRequest.setTipo_servicio(tipo_servicio);
                    ((AddServiceActivity)getActivity()).serviceRequest.setEquipo(txtEquipo.getText().toString());
                    ((AddServiceActivity)getActivity()).serviceRequest.setNo_reporte(txtNumeroReporte.getText().toString());
                    ((AddServiceActivity)getActivity()).serviceRequest.setMarca(txtMarca.getText().toString());
                    ((AddServiceActivity)getActivity()).serviceRequest.setModelo(txtModelo.getText().toString());
                    ((AddServiceActivity)getActivity()).serviceRequest.setSerie(txtSerie.getText().toString());
                    ((AddServiceActivity)getActivity()).serviceRequest.setImprimir(1);
                    ((AddServiceActivity)getActivity()).serviceRequest.setReporte_subir(tipo_reporte);
                    ((AddServiceActivity)getActivity()).serviceRequest.setCabms(txtCambs.getText().toString());
                    ((AddServiceActivity)getActivity()).serviceRequest.setEquipo_id(equipo_id);
                    if(!verificarServicioRealizado()){
                        ((AddServiceActivity)getActivity()).agregarServicio();
                    }else {
                        return new  VerificationError("No. Reporte ya fue registrado");
                    }
                }
            }
            return null;
        }
    }

    private boolean validarInformacion() {
        boolean result = true;
        if(txtNumeroReporte.getText().toString().length() > 0){
            txtNumeroReporte.setError(null);
        }else{
            txtNumeroReporte.setError("Campo Requerido");
            result = false;
        }
        if(nuevo_servicio){
            if(equipo_selecionado == null){
                txtEquipo.setError("Campo Requerido");
                result = false;
            }

            if(txtSerie.getText().toString().trim().isEmpty()){
                txtSerie.setError("Campo Requerido");
                result = false;
            }

            if(txtCambs.getText().toString().trim().isEmpty()){
                txtCambs.setError("Campo Requerido");
                result = false;
            }
        }

        if(tipo_reporte == 0 || estado_servicio == 0){
            result = false;
        }
        return result;
    }


    @Override
    public void onSelected() {
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    private void consultarServicio(String numero_reporte){
        //Creamos el dialog
        snackbar = Snackbar.make(view,"Buscando "+numero_reporte+ "...",BaseTransientBottomBar.LENGTH_INDEFINITE);
        snackbar.show();
        //Creamos json que se enviara
        JSONObject jsonBody = new JSONObject();
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            ApiBodyService apiBodyService =new ApiBodyService(
                    Request.Method.GET,
                    Constants.URL_API_SERVICIO + "/" + numero_reporte,
                    jsonBody.toString(),
                    this.RequestSuccessListenerSerch(0),this.RequestErrorListenerSerch());

            requestQueue.add(apiBodyService);
        } catch (Exception e) {
           snackbar.dismiss();
           Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    private void consultarFolio(){
        //Creamos el dialog
        snackbar = Snackbar.make(view,"Consultado Folio...",BaseTransientBottomBar.LENGTH_INDEFINITE);
        snackbar.show();
        //Creamos json que se enviara
        JSONObject jsonBody = new JSONObject();
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            jsonBody.put("tipo_servicio",tipo_servicio);
            ApiBodyService apiBodyService =new ApiBodyService(
                    Request.Method.GET,
                    Constants.URL_API_SERVICIO_FOLIO ,
                    jsonBody.toString(),
                    this.RequestSuccessListenerSerch(1),this.RequestErrorListenerSerch());

            requestQueue.add(apiBodyService);
        } catch (Exception e) {
            snackbar.dismiss();
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    private Response.ErrorListener RequestErrorListenerSerch() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                snackbar.dismiss();
                Snackbar.make(view, error.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        };
    }

    private Response.Listener<JSONObject> RequestSuccessListenerSerch(int option) {
        //Aqui siempre va a entrar
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Aqui esta donde entrar si fue correctar
                try {
                    snackbar.dismiss();
                    int response_flag = response.getInt("response_flag");
                    if(response_flag == 1){
                        switch (option){
                            case 0:
                                //Parceamos la respuesta del servidor a un modelo para su manipulacio
                                ServiceRequest serviceRequest = new Gson().fromJson(response.getString("response").toString(), ServiceRequest.class);
                                txtNumeroReporte.setText(serviceRequest.getNo_reporte());
                                txtNumeroReporte.setEnabled(false);
                                txtMarca.setText(serviceRequest.getMarca());
                                txtEquipo.setText(serviceRequest.getEquipo());
                                txtModelo.setText(serviceRequest.getModelo());
                                txtSerie.setText(serviceRequest.getSerie());
                                txtCambs.setText(serviceRequest.getCabms());
                                tipo_reporte = serviceRequest.getReporte_subir();
                                tipo_servicio = serviceRequest.getId_tipo_servicio();
                                btnBuscar.setEnabled(false);
                                btnNuevo.setEnabled(false);
                                btnNuevo.setAlpha((float) 0.4);
                                ((AddServiceActivity) getActivity()).serviceRequest.setId_servidor(serviceRequest.getId());
                                ((AddServiceActivity) getActivity()).serviceRequest.setNo_reporte(serviceRequest.getNo_reporte());
                                switch (tipo_servicio){
                                    case 1:
                                        btnCorrectivo.setAlpha((float) 1);
                                        btnPreventivo.setAlpha((float) 0.4);
                                        break;
                                    case 2:
                                        btnCorrectivo.setAlpha((float) 0.4);
                                        btnPreventivo.setAlpha((float) 1);
                                        break;
                                }
                                switch (tipo_reporte){
                                    case 1:
                                        btnInt.setAlpha((float)1);
                                        btnTec.setAlpha((float) 0.4);
                                        btnRet.setAlpha((float) 0.4);
                                        break;
                                    case 2:
                                        btnInt.setAlpha((float)0.4);
                                        btnTec.setAlpha((float) 1);
                                        btnRet.setAlpha((float) 0.4);
                                        break;
                                    case 3:
                                        btnInt.setAlpha((float)0.4);
                                        btnTec.setAlpha((float) 0.4);
                                        btnRet.setAlpha((float) 1);
                                        break;
                                }
                                break;
                            case 1:
                                String folio = (tipo_servicio == 1) ? "P" : "C";
                                folio += response.getString("response");
                                txtNumeroReporte.setText(folio);
                                txtEquipo.setEnabled(true);
                                btnBuscar.setEnabled(false);
                                btnBuscar.setAlpha((float) 0.4);
                                nuevo_servicio = true;
                                spinnerDialog.showSpinerDialog();
                                txtSerie.setEnabled(true);
                                txtCambs.setEnabled(true);
                                break;
                        }

                    }else{
                        Snackbar.make(view, getString(R.string.message_not_data_service), Snackbar.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    snackbar.dismiss();
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        };
    }

    private void llenarInformacionServicio(){
        ServiceRequest servicio_temporal =  ((AddServiceActivity) getActivity()).serviceRequest;
        txtSerie.setText(servicio_temporal.getSerie());
        txtCambs.setText(servicio_temporal.getCabms());
        txtModelo.setText(servicio_temporal.getModelo());
        txtMarca.setText(servicio_temporal.getMarca());
        txtEquipo.setText(servicio_temporal.getEquipo());
        txtNumeroReporte.setText(servicio_temporal.getNo_reporte());
        txtCambs.setText(servicio_temporal.getCabms());
        tipo_servicio = servicio_temporal.getTipo_servicio();
        tipo_reporte = servicio_temporal.getReporte_subir();
        btnNuevo.setVisibility(View.GONE);
        btnBuscar.setEnabled(false);
        btnBuscar.setAlpha((float) 0.4);
        txtNumeroReporte.setEnabled(false);
        switch (tipo_servicio){
            case 1:
                btnCorrectivo.setAlpha((float) 1);
                btnPreventivo.setAlpha((float) 0.4);
                break;
            case 2:
                btnCorrectivo.setAlpha((float) 0.4);
                btnPreventivo.setAlpha((float) 1);
                break;
        }
        switch (tipo_reporte){
            case 1:
                btnInt.setAlpha((float)1);
                btnTec.setAlpha((float) 0.4);
                btnRet.setAlpha((float) 0.4);
                break;
            case 2:
                btnInt.setAlpha((float)0.4);
                btnTec.setAlpha((float) 1);
                btnRet.setAlpha((float) 0.4);
                break;
            case 3:
                btnInt.setAlpha((float)0.4);
                btnTec.setAlpha((float) 0.4);
                btnRet.setAlpha((float) 1);
                break;
        }
        getActivity().setTitle(servicio_temporal.getNumero_reporte());
    }

    private void llenarInformacionServicioSelecionado(ServiceRequest servicio_seleccionado){
        txtNumeroReporte.setText(servicio_seleccionado.getNo_reporte());
        txtNumeroReporte.setEnabled(false);
        txtMarca.setText(servicio_seleccionado.getMarca());
        txtEquipo.setText(servicio_seleccionado.getEquipo());
        txtModelo.setText(servicio_seleccionado.getModelo());
        txtSerie.setText(servicio_seleccionado.getSerie());
        txtCambs.setText(servicio_seleccionado.getCabms());
        tipo_reporte = servicio_seleccionado.getReporte_subir();
        tipo_servicio = servicio_seleccionado.getTipo_servicio();
        btnBuscar.setEnabled(true);
        btnNuevo.setEnabled(false);
        btnNuevo.setAlpha((float) 0.4);
        btnCorrectivo.setEnabled(false);
        btnPreventivo.setEnabled(false);
        btnInt.setEnabled(false);
        btnTec.setEnabled(false);
        btnRet.setEnabled(false);
        ((AddServiceActivity) getActivity()).serviceRequest.setId_servidor(servicio_seleccionado.getId_servidor());
        ((AddServiceActivity) getActivity()).serviceRequest.setNo_reporte(servicio_seleccionado.getNo_reporte());
        switch (tipo_servicio){
            case 2:
                btnCorrectivo.setAlpha((float) 1);
                btnPreventivo.setAlpha((float) 0.4);
                break;
            case 1:
                btnCorrectivo.setAlpha((float) 0.4);
                btnPreventivo.setAlpha((float) 1);
                break;
        }
        switch (tipo_reporte){
            case 1:
                btnInt.setAlpha((float)1);
                btnTec.setAlpha((float) 0.4);
                btnRet.setAlpha((float) 0.4);
                break;
            case 2:
                btnInt.setAlpha((float)0.4);
                btnTec.setAlpha((float) 1);
                btnRet.setAlpha((float) 0.4);
                break;
            case 3:
                btnInt.setAlpha((float)0.4);
                btnTec.setAlpha((float) 0.4);
                btnRet.setAlpha((float) 1);
                break;
        }
    }


    private void mostrarMensajeServicioNoEncontrado(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Servicio no Encontrado");
        builder.setMessage("¿Estas seguro de reportar el servicio como no encontrado?");

        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                ((AddServiceActivity) getActivity()).finalizarServicioNoEncontrado();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private boolean verificarServicioRealizado(){
        boolean existe = false;
        for (ServiceRequest servicio : dataBaseService.GetServicios()){
            if(txtNumeroReporte.getText().toString().equals(servicio.getNumero_reporte()) && tipo_servicio == servicio.getTipo_servicio() && servicio.getTerminado() == 1){
                existe = true;
            }
        }
        return  existe;
    }
}