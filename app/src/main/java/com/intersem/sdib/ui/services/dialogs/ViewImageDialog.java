package com.intersem.sdib.ui.services.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.intersem.sdib.R;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.ui.services.activities.AddServiceActivity;
import com.intersem.sdib.ui.services.fragments.ServiceAfterFragment;
import com.intersem.sdib.ui.services.fragments.ServiceBeforeFragment;
import com.intersem.sdib.ui.services.fragments.ServiceBinnacleFragment;
import com.intersem.sdib.ui.services.fragments.ServiceCambsFragment;
import com.intersem.sdib.ui.services.fragments.ServiceGafetFragment;
import com.intersem.sdib.ui.services.fragments.ServiceIdFragment;
import com.intersem.sdib.ui.services.fragments.ServicePendigFragment;
import com.intersem.sdib.ui.services.fragments.ServiceRepairFragment;
import com.intersem.sdib.ui.services.fragments.ServiceTagFragment;
import com.intersem.sdib.ui.services.models.ServiceRequest;
import com.intersem.sdib.core.utilities.Constants;
import com.intersem.sdib.core.utilities.UI;

public class ViewImageDialog extends  AppCompatDialogFragment {
    private View view;
    ImageButton imgCerrar , btnRotar;
    ImageView imgFotografia;
    ServiceRequest.Fotografia fotografia;
    int tipo_fotografia;
    TextView btnEliminar;
    Bitmap bitmapFotografia;
    DataBaseService dataBaseService;

    public ViewImageDialog(ServiceRequest.Fotografia fotografia,int tipo_fotografia) {
        this.fotografia = fotografia;
        this.tipo_fotografia = tipo_fotografia;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_view_image_dialog,null);
        imgCerrar = view.findViewById(R.id.btnCerrar);
        imgFotografia = view.findViewById(R.id.imgFotografia);
        btnEliminar = view.findViewById(R.id.btnEliminar);
        btnRotar = view.findViewById(R.id.btnRotar);
        dataBaseService = new DataBaseService(getContext());
        imgCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseService.ModificarDetalleServicio(fotografia);
                actualizarComponentePadre();
                dismiss();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                UI.createDialog(getContext(), "Eliminar Fotografia", "¿Esta seguro que quiere?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (tipo_fotografia){
                            case 1:
                                ((AddServiceActivity)getActivity()).EliminarFotografiaAntes(fotografia);
                                ((ServiceBeforeFragment) getParentFragment()).actualizarData();
                                dismiss();
                                break;
                            case 2:
                                ((AddServiceActivity)getActivity()).EliminarFotografiaDurante(fotografia);
                                ((ServicePendigFragment) getParentFragment()).actualizarData();
                                dismiss();
                                break;
                            case 3:
                                ((AddServiceActivity)getActivity()).EliminarFotografiaDespues(fotografia);
                                ((ServiceAfterFragment) getParentFragment()).actualizarData();
                                dismiss();
                                break;
                            case 4:
                                ((AddServiceActivity)getActivity()).EliminarFotografiaTag(fotografia);
                                ((ServiceTagFragment) getParentFragment()).actualizarData();
                                dismiss();
                                break;
                            case 6:
                                ((AddServiceActivity)getActivity()).EliminarFotografiaCambs(fotografia);
                                ((ServiceCambsFragment) getParentFragment()).actualizarData();
                                dismiss();
                                break;
                            case 5:
                                ((AddServiceActivity)getActivity()).EliminarFotografiaId(fotografia);
                                ((ServiceIdFragment) getParentFragment()).actualizarData();
                                dismiss();
                                break;
                            case 7:
                                ((AddServiceActivity)getActivity()).EliminarFotografiaBitacora(fotografia);
                                ((ServiceBinnacleFragment) getParentFragment()).actualizarData();
                                dismiss();
                                break;
                            case 8:
                                ((AddServiceActivity)getActivity()).EliminarFotografiaRefaccion(fotografia);
                                ((ServiceRepairFragment) getParentFragment()).actualizarData();
                                dismiss();
                                break;
                            case 9:
                                ((AddServiceActivity)getActivity()).EliminarFotografiaGafete(fotografia);
                                ((ServiceGafetFragment) getParentFragment()).actualizarData();
                                dismiss();
                                break;
                        }
                    }
                });
            }
        });
        btnRotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fotografia.setRotacion(fotografia.getRotacion() + 90);
                if(fotografia.getRotacion() > 360) fotografia.setRotacion(0);
                imgFotografia.setImageBitmap(Constants.obtenerFotografiaRotacion(fotografia,false));
            }
        });
        imgFotografia.setImageBitmap(Constants.obtenerFotografiaRotacion(fotografia,false));
        builder.setView(view);

        return builder.create();
    }



    private void actualizarComponentePadre() {
        switch (tipo_fotografia){
            case 1:
                ((ServiceBeforeFragment) getParentFragment()).actualizarData();
                dismiss();
                break;
            case 2:
                ((ServicePendigFragment) getParentFragment()).actualizarData();
                dismiss();
                break;
            case 3:
                ((ServiceAfterFragment) getParentFragment()).actualizarData();
                dismiss();
                break;
            case 4:
                ((ServiceTagFragment) getParentFragment()).actualizarData();
                dismiss();
                break;
            case 6:
                ((ServiceCambsFragment) getParentFragment()).actualizarData();
                dismiss();
                break;
            case 5:
                ((ServiceIdFragment) getParentFragment()).actualizarData();
                dismiss();
                break;
            case 7:
                ((ServiceBinnacleFragment) getParentFragment()).actualizarData();
                dismiss();
                break;
            case 8:
                ((ServiceRepairFragment) getParentFragment()).actualizarData();
                dismiss();
                break;
            case 9:
                ((ServiceGafetFragment) getParentFragment()).actualizarData();
                dismiss();
                break;
        }
    }

}
