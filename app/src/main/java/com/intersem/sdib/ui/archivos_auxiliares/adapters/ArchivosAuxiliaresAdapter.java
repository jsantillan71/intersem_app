package com.intersem.sdib.ui.archivos_auxiliares.adapters;

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

import com.intersem.sdib.R;
import com.intersem.sdib.ui.archivos_auxiliares.models.ArchivoAuxialiarModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class ArchivosAuxiliaresAdapter extends RecyclerView.Adapter <ArchivosAuxiliaresAdapter.ViewHolder> implements Filterable{
    private final ClickListener listener;
    private Context context;
    private ArrayList<ArchivoAuxialiarModel> archivoAuxialiarModelArrayList;
    private ArrayList<ArchivoAuxialiarModel> full_archivoAuxialiarModelArrayList;
    private View.OnClickListener onClickListener;

    public ArchivosAuxiliaresAdapter(ClickListener listener, Context context, ArrayList<ArchivoAuxialiarModel> archivoAuxialiarModelArrayList) {
        this.listener = listener;
        this.context = context;
        this.archivoAuxialiarModelArrayList = archivoAuxialiarModelArrayList;
        this.full_archivoAuxialiarModelArrayList = archivoAuxialiarModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.archivo_auxiliar_item ;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v,listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ArchivoAuxialiarModel archivo_actual  = archivoAuxialiarModelArrayList.get(position);
        //Verficicamos si esta terminado
        holder.txtNombreReporte.setText(archivo_actual.getNombre_archvio());
        switch (archivo_actual.getTipo()){
            case ".docx":
                holder.imgAccion.setImageResource(R.drawable.ic_documento);
            break;
            case ".pdf":
                holder.imgAccion.setImageResource(R.drawable.ic_pdf);
                break;
            case ".xls":
            case ".xlsx":
                holder.imgAccion.setImageResource(R.drawable.ic_excel);
                break;
            case ".png":
            case ".jpeg":
            case ".jpg":
                holder.imgAccion.setImageResource(R.drawable.ic_image);
                break;
            case ".pptx":
                holder.imgAccion.setImageResource(R.drawable.ic_powerpoint);
                break;
            default:
                holder.imgAccion.setImageResource(R.drawable.ic_archive);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return archivoAuxialiarModelArrayList.size();
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
                ArrayList<ArchivoAuxialiarModel> filter_list = new ArrayList<>();

                if(constraint == null || constraint.length() == 0){
                    filter_list = full_archivoAuxialiarModelArrayList;
                }else{

                    for (ArchivoAuxialiarModel response : full_archivoAuxialiarModelArrayList) {
                        if(response.getNombre_archvio().contains(constraint)){
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
                archivoAuxialiarModelArrayList = (ArrayList<ArchivoAuxialiarModel>)results.values;
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {
        TextView txtNombreReporte ;
        ImageView imgAccion;
        private WeakReference<ClickListener> listenerWeakReference;

        ViewHolder(View view , ClickListener listener) {
            super(view);
            listenerWeakReference = new WeakReference<>(listener);
            imgAccion = view.findViewById(R.id.btnAccion);
            txtNombreReporte = view.findViewById(R.id.txtNumeroReporte);
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
