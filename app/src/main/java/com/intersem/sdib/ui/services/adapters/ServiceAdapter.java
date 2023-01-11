package com.intersem.sdib.ui.services.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intersem.sdib.R;
import com.intersem.sdib.ui.services.models.ServiceRequest;
import com.intersem.sdib.core.utilities.Constants;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter <ServiceAdapter.ViewHolder> implements View.OnClickListener {
    private Context context;
    private ArrayList<ServiceRequest.Fotografia> fotografias_array_list;
    private View.OnClickListener onClickListener;


    public ServiceAdapter(Context context, ArrayList<ServiceRequest.Fotografia> fotografias_array_list) {
        this.context = context;
        this.fotografias_array_list = fotografias_array_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.layout_image_service_item ;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        v.setOnClickListener(this);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ServiceRequest.Fotografia fotografia = fotografias_array_list.get(position);
        holder.imgFotografia.setImageBitmap(Constants.obtenerFotografiaRotacion(fotografia,true));
    }

    @Override
    public int getItemCount() {
        return fotografias_array_list.size();
    }

    public void  agregarElemento(ServiceRequest.Fotografia fotografia){
        fotografias_array_list.add(fotografia);
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if(onClickListener !=null){
            onClickListener.onClick(view);
        }
    }

    public void setOnClickListener(View.OnClickListener onClickListener){
        this.onClickListener = onClickListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        ImageView imgFotografia;

        ViewHolder(View view) {
            super(view);
            imgFotografia = view.findViewById(R.id.imgFotografia);
        }
    }
}
