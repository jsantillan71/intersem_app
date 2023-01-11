package com.intersem.sdib.ui.services.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.intersem.sdib.R;
import com.intersem.sdib.ui.services.helpers.ItemTouchHelperAdapter;
import com.intersem.sdib.ui.services.helpers.OnStartDragListener;
import com.intersem.sdib.ui.services.models.ServiceRequest;
import com.intersem.sdib.core.utilities.Constants;

import java.util.ArrayList;
import java.util.Collections;

public class FotografiaAdapter extends RecyclerView.Adapter <FotografiaAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    private Context context;
    private ArrayList<ServiceRequest.Fotografia> fotografias_array_list;
    OnStartDragListener listener;


    public FotografiaAdapter(Context context, ArrayList<ServiceRequest.Fotografia> fotografias_array_list, OnStartDragListener listener) {
        this.context = context;
        this.fotografias_array_list = fotografias_array_list;
        this.listener = listener;
    }

    public ArrayList<ServiceRequest.Fotografia> obtenerFotografias(){
        return fotografias_array_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        int layout = R.layout.layout_image_item;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        ServiceRequest.Fotografia fotografia = fotografias_array_list.get(position);
        try{

            holder.imgFotografia.setImageBitmap(Constants.obtenerFotografiaRotacion(fotografia,true));
            switch (fotografia.getTipo_fotografia()){
                case 1 :
                    holder.txtTipoImagen.setText("Antes");
                    break;
                case 2 :
                    holder.txtTipoImagen.setText("Durante");
                    break;
                case 3 :
                    holder.txtTipoImagen.setText("Despues");
                    break;
                case 4 :
                    holder.txtTipoImagen.setText("Etiqueta");
                    break;
                case 5 :
                    holder.txtTipoImagen.setText("Serie");
                    break;
                case 6 :
                    holder.txtTipoImagen.setText("Cambs");
                    break;
                case 7 :
                    holder.txtTipoImagen.setText("Bitacora");
                    break;
                case 8 :
                    holder.txtTipoImagen.setText("Refaccion");
                    break;
                case 9 :
                    holder.txtTipoImagen.setText("Gafete");
                    break;
            }
            holder.itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    final int action = motionEvent.getAction();
                    if(action == MotionEvent.ACTION_DOWN)
                        listener.onStartDrag(holder);
                    return false;
                }
            });

            if(fotografia.getCheck() == 1){
                holder.checkBox.setChecked(true);
            }
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(b){
                        fotografias_array_list.get(position).setCheck(1);
                    }else{
                        fotografias_array_list.get(position).setCheck(0);
                    }
                }
            });

        }catch (Exception e){

        }
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
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(fotografias_array_list,fromPosition,toPosition);
        notifyItemMoved(fromPosition,toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        fotografias_array_list.remove(position);
        notifyItemRemoved(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder  {
        ImageView imgFotografia;
        TextView txtTipoImagen;
        CheckBox checkBox;
        ViewHolder(View view) {
            super(view);
            txtTipoImagen = view.findViewById(R.id.txtTipoImagen);
            imgFotografia = view.findViewById(R.id.imgFotografia);
            checkBox = view.findViewById(R.id.checkbox);
        }
    }
}
