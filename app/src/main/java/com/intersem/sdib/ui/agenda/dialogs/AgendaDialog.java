package com.intersem.sdib.ui.agenda.dialogs;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.intersem.sdib.R;
import com.intersem.sdib.ui.agenda.activities.AgendaActivty;
import com.intersem.sdib.ui.services.models.ServiceRequest;
import com.intersem.sdib.core.utilities.Cliente;

public class AgendaDialog  extends AppCompatDialogFragment implements View.OnClickListener {
    View view;
    ImageButton imgClose;
    TextView txtTitleDialog;
    ServiceRequest servicio_selecionado;
    Cliente cliente_selecionado;
    TextInputEditText txtExistencia , txtLinea, txtCosto , txtCantidad ,  txtImpuestos , txtSubTotal , txtTotal;

    public static AgendaDialog newInstance() {
        return new AgendaDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(AppCompatDialogFragment.STYLE_NORMAL, R.style.FullScreenDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_agenda_layout, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            inicializarComponenetes();
        }
        return view;
    }

    private void inicializarComponenetes() {
        imgClose = view.findViewById(R.id.imgCloseSearchArticle);
        imgClose.setOnClickListener(this);
        txtTitleDialog = view.findViewById(R.id.txtTitleDialog);
        servicio_selecionado = ((AgendaActivty)getActivity()).servicio_selecionado;
        txtTitleDialog.setText("Servicio " + servicio_selecionado.getNo_reporte());
        Cliente cliente_selecionado = new Gson().fromJson(servicio_selecionado.getClientes_datos(), Cliente.class);
        ((TextInputEditText)view.findViewById(R.id.txtNumeroServicio)).setText(servicio_selecionado.getNo_reporte());
        ((TextInputEditText)view.findViewById(R.id.txtEquipo)).setText(servicio_selecionado.getEquipo());
        ((TextInputEditText)view.findViewById(R.id.txtModelo)).setText(servicio_selecionado.getModelo());
        ((TextInputEditText)view.findViewById(R.id.txtMarca)).setText(servicio_selecionado.getMarca());
        ((TextInputEditText)view.findViewById(R.id.txtCambs)).setText(servicio_selecionado.getCabms());
        ((TextInputEditText)view.findViewById(R.id.txtSerie)).setText(servicio_selecionado.getSerie());
        ((TextInputEditText)view.findViewById(R.id.txtCliente)).setText(cliente_selecionado.getRazon_social());
        ((TextInputEditText)view.findViewById(R.id.txtEstado)).setText(cliente_selecionado.getEstado());
        ((TextInputEditText)view.findViewById(R.id.txtMunicipio)).setText(cliente_selecionado.getMunicipio());
        ((TextInputEditText)view.findViewById(R.id.txtDireccion)).setText(cliente_selecionado.getCalle() +" "+ cliente_selecionado.getNumero() +" "+ cliente_selecionado.getNumero_int()+" "+cliente_selecionado.getColonia());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.imgCloseSearchArticle:
                dismiss();
                break;
        }
    }
}
