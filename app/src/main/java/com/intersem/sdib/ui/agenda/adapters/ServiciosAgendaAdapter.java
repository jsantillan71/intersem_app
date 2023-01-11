package com.intersem.sdib.ui.agenda.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.intersem.sdib.R;
import com.intersem.sdib.ui.agenda.models.AgendaModel;
import com.intersem.sdib.ui.services.models.ServiceRequest;
import com.intersem.sdib.core.utilities.Cliente;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.intersem.sdib.core.utilities.Constants.obtenerFormato;
import static com.intersem.sdib.core.utilities.Constants.obtenerTipoServicio;

public class ServiciosAgendaAdapter extends RecyclerView.Adapter <ServiciosAgendaAdapter.ViewHolder> implements Filterable {

    private final ClickListener listener;
    private Context context;
    private ArrayList<AgendaModel> serviceRequestArrayList;
    private ArrayList<AgendaModel> full_serviceRequestArrayList;
    private View.OnClickListener onClickListener;

    public ServiciosAgendaAdapter(ClickListener listener, Context context, ArrayList<AgendaModel> elementos_agenda) {
        this.listener = listener;
        this.context = context;
        this.full_serviceRequestArrayList = elementos_agenda;
        this.serviceRequestArrayList = elementos_agenda;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.servicio_agenda_item ;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AgendaModel servicio_selecionado  = serviceRequestArrayList.get(position);
        holder.txtNumeroReporte.setText(servicio_selecionado.getFolio());
        holder.txtCliente.setText(servicio_selecionado.getCliente());
        holder.txtEquipo.setText(servicio_selecionado.getEquipo());
        holder.txtTipoServicio.setText(servicio_selecionado.getTipo_servicio());
        if(servicio_selecionado.getTipo_id() == 2){
            holder.imgAccion.setImageResource(R.drawable.publicalo);
        }
    }

    @Override
    public int getItemCount() {
        return serviceRequestArrayList.size();
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    @Override
    public Filter getFilter() {
        return addFilter();
    }

    private Filter addFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                ArrayList<AgendaModel> filter_list = new ArrayList<>();

                if(constraint == null || constraint.length() == 0){
                    filter_list = full_serviceRequestArrayList;
                }else{

                    for (AgendaModel response : full_serviceRequestArrayList) {
                        if(response.getCliente().contains(constraint)){
                            filter_list.add(response);
                        }
                    }
                }

                FilterResults results = new FilterResults();
                results.values = filter_list;

                return  results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                serviceRequestArrayList = (ArrayList<AgendaModel>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView txtNumeroReporte , txtCliente , txtEquipo , txtTipoServicio;
        ImageView imgAccion;
        private WeakReference<ClickListener> listenerWeakReference;

        ViewHolder(View view , ClickListener listener) {
            super(view);
            listenerWeakReference = new WeakReference<>(listener);
            imgAccion = view.findViewById(R.id.btnAccion);
            txtNumeroReporte = view.findViewById(R.id.txtNumeroServicio);
            txtCliente = view.findViewById(R.id.txtCliente);
            txtEquipo = view.findViewById(R.id.txtEquipo);
            txtTipoServicio = view.findViewById(R.id.txtTipoServicio);
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
