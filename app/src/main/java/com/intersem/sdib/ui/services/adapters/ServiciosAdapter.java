package com.intersem.sdib.ui.services.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.intersem.sdib.R;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.core.utilities.Constants;
import com.intersem.sdib.core.utilities.Equipo;
import com.intersem.sdib.core.utilities.HiloGenerarPdf;
import com.intersem.sdib.core.utilities.HiloGenerarPdfModificar;
import com.intersem.sdib.ui.services.activities.AddServiceActivity;
import com.intersem.sdib.ui.services.activities.ServiceUpdateActivity;
import com.intersem.sdib.ui.services.models.ServiceRequest;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class ServiciosAdapter extends RecyclerView.Adapter <ServiciosAdapter.ViewHolder>{
    private final ClickListener listener;
    private Context context;
    private ArrayList<ServiceRequest> serviciosArrayList;
    private View.OnClickListener onClickListener;
    private DataBaseService dataBaseService;


    public ServiciosAdapter(ClickListener listener, Context context, ArrayList<ServiceRequest> serviciosArrayList) {
        this.listener = listener;
        this.context = context;
        this.serviciosArrayList = serviciosArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.servicio_item ;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ServiciosAdapter.ViewHolder(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ServiceRequest servicio_actual  = serviciosArrayList.get(position);
        //Verficicamos si esta terminado
        switch (servicio_actual.getTerminado()){
            case 1:
                holder.imgAccion.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        PopupMenu popupMenu = new PopupMenu(view.getContext() , view);
                        if(servicio_actual.getSubido() == 1){
                            popupMenu.inflate(R.menu.opciones_servidor);
                        }else {
                            popupMenu.inflate(R.menu.opciones);
                        }
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()){
                                    case R.id.modificar_orden:
                                        Intent i = new Intent(context, ServiceUpdateActivity.class);
                                        i.putExtra("id_servicio",servicio_actual.getId());
                                        context.startActivity(i);
                                        break;
                                    case R.id.modificar_numero:
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        Activity activity = (Activity) context;
                                        LayoutInflater inflater = activity.getLayoutInflater();

                                        View v = inflater.inflate(R.layout.dialog_modificar_servicio, null);
                                        builder.setView(v);
                                        dataBaseService = new DataBaseService(context);
                                        TextInputEditText txtNumeroServicio = v.findViewById(R.id.txtNumeroServicio);
                                        TextInputEditText txtFormatoImpresion = v.findViewById(R.id.txtFormatoImpresion);

                                        txtNumeroServicio.setText(servicio_actual.getNo_reporte());
                                        txtFormatoImpresion.setText(Constants.obtenerFormato(servicio_actual.getReporte_subir()));

                                        ArrayList<String> items = new ArrayList<>();

                                        items.add("INT");
                                        items.add("TEC");
                                        items.add("RET");

                                        ArrayList<ServiceRequest> servicios = dataBaseService.GetServiciosAgenda();
                                        ArrayList<String> items_servicios = new ArrayList<>();
                                        for (ServiceRequest servicio : servicios){
                                            items_servicios.add(servicio.getNumero_reporte());
                                        }

                                        SpinnerDialog spinnerDialog=new SpinnerDialog(activity,items,"Selecionar Formato de Impresión",R.style.DialogAnimations_SmileWindow,"Cerrar");
                                        spinnerDialog.setCancellable(false); // for cancellable
                                        spinnerDialog.setShowKeyboard(false);// for open keyboard by default
                                        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
                                            @Override
                                            public void onClick(String item, int position) {
                                                txtFormatoImpresion.setText(items.get(position));
                                            }
                                        });

                                        SpinnerDialog spinnerDialogServicio =new SpinnerDialog(activity,items_servicios,"Numero de Servicio Asignados",R.style.DialogAnimations_SmileWindow,"Cerrar");
                                        spinnerDialogServicio.setCancellable(false); // for cancellable
                                        spinnerDialogServicio.setShowKeyboard(false);// for open keyboard by default
                                        spinnerDialogServicio.bindOnSpinerListener(new OnSpinerItemClick() {
                                            @Override
                                            public void onClick(String item, int position) {
                                                txtNumeroServicio.setText(items_servicios.get(position));
                                            }
                                        });

                                        txtFormatoImpresion.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                spinnerDialog.showSpinerDialog();
                                            }
                                        });

                                        txtNumeroServicio.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                spinnerDialogServicio.showSpinerDialog();
                                            }
                                        });

                                        builder.setTitle("Modificar Servicio");

                                        builder.setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //Modificamos el servicio
                                                servicio_actual.setNumero_reporte( txtNumeroServicio.getText().toString());
                                                servicio_actual.setNo_reporte( txtNumeroServicio.getText().toString());
                                                servicio_actual.setReporte_subir(Constants.obtenerFormatoId(txtFormatoImpresion.getText().toString()));
                                                dataBaseService = new DataBaseService(context);
                                                dataBaseService.ModificarServicio(servicio_actual);
                                                //Generamos nuevamente el PDF
                                                // Se insertan las fotos
                                                ArrayList<ServiceRequest.Fotografia> fotografias =  dataBaseService.GetDetalles(servicio_actual.getId());
                                                //Se generan los pdf
                                                String [] path_reportes_pdf = {
                                                        Constants.generar_path_reporte(1, servicio_actual.getNumero_reporte()),
                                                        Constants.generar_path_reporte(2, servicio_actual.getNumero_reporte()),
                                                        Constants.generar_path_reporte(3, servicio_actual.getNumero_reporte()),
                                                };
                                                switch (servicio_actual.getReporte_subir()){
                                                    case 1:
                                                        servicio_actual.setPath_pdf_1(path_reportes_pdf[0]);
                                                        servicio_actual.setPath_pdf_2("");
                                                        servicio_actual.setPath_pdf_3("");
                                                        break;
                                                    case 2:
                                                        servicio_actual.setPath_pdf_2(path_reportes_pdf[1]);
                                                        servicio_actual.setPath_pdf_1("");
                                                        servicio_actual.setPath_pdf_3("");
                                                        break;
                                                    case 3:
                                                        servicio_actual.setPath_pdf_3(path_reportes_pdf[2]);
                                                        servicio_actual.setPath_pdf_2("");
                                                        servicio_actual.setPath_pdf_1("");
                                                        break;
                                                }
                                                //Generamos el hilo para generar los pdfs
                                                //Mostramos la alerta
                                                HiloGenerarPdfModificar hiloGenerarPdf = new HiloGenerarPdfModificar(fotografias, servicio_actual, context , 1);
                                                hiloGenerarPdf.execute();
                                               notifyItemChanged(position);
                                            }
                                        });
                                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }
                                        });

                                        builder.show();
                                        break;
                                }
                                return true;
                            }
                        });
                        popupMenu.show();
                        return  true;
                    }
                });
                if(servicio_actual.getSubido() == 1){
                    if(servicio_actual.getPrinted() == 0){
                        holder.imgAccion.setImageResource(R.drawable.icono_imprimir);
                        holder.txtEstatus.setText("Guardado en el servidor");
                    }else{
                        holder.imgAccion.setImageResource(R.drawable.icono_imprimir);
                        holder.txtEstatus.setText("Guardado en el servidor-impreso");
                    }
                }else{
                    if(servicio_actual.getPrinted() == 0){
                        holder.imgAccion.setImageResource(R.drawable.icono_imprimir);
                        holder.txtEstatus.setText("No Subido");
                    }else{
                        holder.imgAccion.setImageResource(R.drawable.icono_imprimir);
                        holder.txtEstatus.setText("No Subido-Impreso");
                    }
                }

                //Validamos si es servicio Extra
                if(servicio_actual.getTipo_servicio() == 5){
                    holder.imgAccion.setVisibility(View.GONE);
                    if(servicio_actual.getSubido() == 1){
                        holder.txtEstatus.setText("Servicio Extra-Servidor");
                    }else{
                        holder.txtEstatus.setText("Servicio Extra-Local");
                    }
                }
                break;
            case -1:
                holder.imgAccion.setImageResource(R.drawable.icono_cancelar);
                holder.txtEstatus.setText("Servicio No Realizado");
                break;
            case -2:
                holder.imgAccion.setImageResource(R.drawable.icono_cancelar);
                holder.txtEstatus.setText("Servicio No Econtrado");
                break;
            default:
                holder.imgAccion.setImageResource(R.drawable.ic_servicio_progreso);
                holder.txtEstatus.setText("En desarrollo");
                break;
        }
        holder.txtNumeroReporte.setText((servicio_actual.getNumero_reporte() == null) ? "Falta Numero de reporte" : servicio_actual.getNumero_reporte());
    }


    @Override
    public int getItemCount() {
        return serviciosArrayList.size();
    }


    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView txtNumeroReporte , txtEstatus;
        ImageView imgAccion;
        private WeakReference<ClickListener> listenerWeakReference;

        ViewHolder(View view , ClickListener listener) {
            super(view);
            listenerWeakReference = new WeakReference<>(listener);
            imgAccion = view.findViewById(R.id.btnAccion);
            txtNumeroReporte = view.findViewById(R.id.txtNumeroReporte);
            txtEstatus = view.findViewById(R.id.txtEstatus);
            imgAccion.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listenerWeakReference.get().onPositionClicked(getAdapterPosition());
        }
    }

    public interface ClickListener {
        void onPositionClicked(int position);
    }
}
