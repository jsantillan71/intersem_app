package com.intersem.sdib.ui.services.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.intersem.sdib.R;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.ui.services.adapters.FotografiaAdapter;
import com.intersem.sdib.ui.services.helpers.MyItemTouchHelperCallback;
import com.intersem.sdib.ui.services.helpers.OnStartDragListener;
import com.intersem.sdib.ui.services.models.ServiceRequest;
import com.intersem.sdib.core.utilities.UI;

import java.util.ArrayList;

public class ViewAllImageDialog extends AppCompatDialogFragment {
    private View view;
    private RecyclerView rcvViewAllImages;
    private FotografiaAdapter fotografiaAdapter;
    private ImageButton imgCerrar;
    ArrayList<ServiceRequest.Fotografia> fotografias;
    private GridLayoutManager gridLayoutManager;
    ItemTouchHelper itemTouchHelper;
    private TextView txtGuadarCambios;
    private DataBaseService dataBaseService;

    public ViewAllImageDialog(ArrayList<ServiceRequest.Fotografia> fotografias) {
        this.fotografias = fotografias;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.layout_view_all_image_dialog,null);
        imgCerrar = view.findViewById(R.id.btnCerrar);
        imgCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        inicializarComponentes();
        return builder.setView(view).create();
    }

    private void inicializarComponentes() {
        txtGuadarCambios = view.findViewById(R.id.txtGuardarCambios);
        rcvViewAllImages =  view.findViewById(R.id.rcvFotografias);
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        rcvViewAllImages.setLayoutManager(gridLayoutManager);
        rcvViewAllImages.setHasFixedSize(true);
        dataBaseService = new DataBaseService(getActivity().getApplicationContext());
        cargarInformacion();
    }

    private void cargarInformacion() {
        //Creamos una nueva arrayList
       // ArrayList<ServiceRequest.Fotografia> fotografias = dataBaseService.GetDetalles(servicio.getId());
        //Creamos nuestro adapter
        fotografiaAdapter = new FotografiaAdapter(getContext(),fotografias, new OnStartDragListener() {
            @Override
            public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
                itemTouchHelper.startDrag(viewHolder);
            };
        });
        rcvViewAllImages.setAdapter(fotografiaAdapter);
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback(fotografiaAdapter);
        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(rcvViewAllImages);
        txtGuadarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UI.createDialog(getContext(), "Guardar Cambios", "¿Esta seguro que quires guardar cambios?", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Modificamos el orden de las fotos
                        ArrayList<ServiceRequest.Fotografia> fotografias_nuevo_orden =  fotografiaAdapter.obtenerFotografias();
                        int orden = 0;
                        for(ServiceRequest.Fotografia fotografia : fotografias_nuevo_orden){
                            fotografia.setOrden(orden);
                            dataBaseService.ModificarDetalleServicio(fotografia);
                            orden += 1;
                        }
                        dismiss();
                    }
                });
            }
        });
    }
}
