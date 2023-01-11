package com.intersem.sdib.core.utilities;

import android.content.Context;
import android.graphics.Bitmap;

import com.intersem.sdib.ui.services.models.ServiceRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PdfServices {
    final Context context;
    final ArrayList<ServiceRequest.Fotografia> detallesArray;
    final ArrayList<ServiceRequest.Fotografia> detallesArrayCopy = new ArrayList<>();
    Bitmap fotografia_bitacora;

    final ServiceRequest servicio_selecionado;

    String  name_pdf;
    PdfControl pdf_control;
    String [] path_pdf = {null, null, null};

    float [] margins_pdf_1 = {(float) 0, (float) 0, (float) 131.30, (float) 0};
    float [] margins_pdf_2 = {(float) 0, (float) 0, (float) 80, (float) 0};
    float [] margins_pdf_3 = {(float) 0, (float) 0, (float) 78, (float) 0};
    public PdfServices(ArrayList<ServiceRequest.Fotografia> detallesArray, ServiceRequest servicio_selecionado, Context context) {
        this.detallesArray = detallesArray;
        this.servicio_selecionado = servicio_selecionado;
        this.context = context;
        seleccionarPedidos();
        iniciarPdf();
        agregarFotos();
        guardarPdf();
    }

    public void seleccionarPedidos(){
        //Ordenamos las fotografias por el orden inverso
        //Collections.reverse(detallesArray);

        //Eliminamos las fotografias opcionales que no esten checkeadas
        ArrayList<ServiceRequest.Fotografia> fotografias_imprimir = new ArrayList<>();
        //Recorremos las fotografias
        for(ServiceRequest.Fotografia fotografia : detallesArray){
            //Si la fotografia es mayor a 6  y menor a 9 entra al proceso si esta chekeada
            if(fotografia.getTipo_fotografia() > 6 && fotografia.getTipo_fotografia() != 9){
                //Validamos que esta verificada
                if(fotografia.getCheck() == 1){
                    fotografias_imprimir.add(fotografia);
                }
            }else{
                fotografias_imprimir.add(fotografia);
            }
        }

        //Agreamos las fotografias checkeadas
        Boolean aBoolean;
        for (ServiceRequest.Fotografia fotografia : fotografias_imprimir){
            aBoolean = false;
            for (int i = 0; i < detallesArrayCopy.size(); i++){
                if(fotografia.getTipo_fotografia() == detallesArrayCopy.get(i).getTipo_fotografia()){
                    aBoolean = true;
                    i = detallesArrayCopy.size();
                }
            }
            if(!aBoolean && fotografia.getCheck() == 1 ){  this.detallesArrayCopy.add(fotografia); }
        }

        //Agreamos las fotografias que no fueron checkeadas
        Boolean existe_checked;
        for (ServiceRequest.Fotografia fotografia : fotografias_imprimir){
            existe_checked = false;
            for (int i = 0; i < detallesArrayCopy.size(); i++){
                if(fotografia.getTipo_fotografia() == detallesArrayCopy.get(i).getTipo_fotografia()){
                    existe_checked = true;
                    i = detallesArrayCopy.size();
                }
            }
            if(!existe_checked){  this.detallesArrayCopy.add(fotografia); }
        }

        //Ordenamos por tipo de servicio
        Collections.sort(detallesArrayCopy, new Comparator<ServiceRequest.Fotografia>() {
            @Override
            public int compare(ServiceRequest.Fotografia fotografia1, ServiceRequest.Fotografia fotografia2) {
                return new Integer(fotografia1.getTipo_fotografia()).compareTo(new Integer(fotografia2.getTipo_fotografia()));
            }
        });

        int bandera_2 = 0;
    }

    public void iniciarPdf(){
        String shortYear = Constants.get_short_year();
        switch (servicio_selecionado.getReporte_subir()){
            case 1:
                name_pdf = Constants.generar_name_reporte(1, String.valueOf(this.servicio_selecionado.getNo_reporte()));
                pdf_control = new PdfControl(context);
                pdf_control.CreateFile(Constants.CARPETA_PDFS, name_pdf, margins_pdf_1);
                pdf_control.AddMetaData(name_pdf, "Documento 1", "SDIB");
                pdf_control.addRectangleCenterText("Reporte fotografico No. I" + servicio_selecionado.getNo_reporte()+shortYear,150);
                break;
            case  2:
                name_pdf = Constants.generar_name_reporte(2, String.valueOf(this.servicio_selecionado.getNo_reporte()));
                pdf_control = new PdfControl(context);
                pdf_control.CreateFile(Constants.CARPETA_PDFS, name_pdf, margins_pdf_2);
                pdf_control.AddMetaData(name_pdf, "Documento 2", "SDIB");
                pdf_control.agregarEncabezadoMemoriaFotografica("T"+servicio_selecionado.getNo_reporte()+shortYear);
                break;
            case 3:
                name_pdf = Constants.generar_name_reporte(3, String.valueOf(this.servicio_selecionado.getNo_reporte()));
                pdf_control = new PdfControl(context);
                pdf_control.CreateFile(Constants.CARPETA_PDFS, name_pdf, margins_pdf_3);
                pdf_control.AddMetaData(name_pdf, "Documento 3", "SDIB");
                pdf_control.agregarEncabezadoEvidenciaFotografica("R"+servicio_selecionado.getNo_reporte()+shortYear);
                break;
        }
    }


    public void agregarFotos(){
        switch (servicio_selecionado.getReporte_subir()){
            case 1:
                // Comenzamos agregando al primer pdf
                Bitmap[] path_images_pdf_0 = {null, null};
                ServiceRequest.Fotografia[] servicios_array_two =  new ServiceRequest.Fotografia[2];
                int contador = 0;
                for (int i = 0; i < detallesArrayCopy.size(); i++){
                    ServiceRequest.Fotografia detalle = detallesArrayCopy.get(i);
                    path_images_pdf_0[contador] = Constants.obtenerFotografiaRotacion(detalle,false);
                    servicios_array_two[contador] = detalle;
                    contador++;

                    if(contador == 2){
                        pdf_control.AddTableImgTwoColumns(
                                path_images_pdf_0, 5f, 25.51f, 25.51f, 14.17f, (int)(4138.31), (int)(2097.50), 18.58f
                                ,servicios_array_two);

                        path_images_pdf_0[0] = null;
                        path_images_pdf_0[1] = null;
                        contador = 0;
                    }

                    if(i == (detallesArrayCopy.size() - 1)){
                        //Verificamos si es el ultimo
                        if(contador > 0){
                            pdf_control.AddTableImgTwoColumns(
                                    path_images_pdf_0, 5f, 25.51f, 25.51f, 14.17f, (int)(4138.31), (int)(2097.50), 18.58f
                                    ,servicios_array_two);
                        }
                    }
                }
                break;
            case  2:
                Bitmap[] path_images_pdf_2 = {null, null, null};
                ServiceRequest.Fotografia[] servicios_array =  new ServiceRequest.Fotografia[3];
                contador = 0;
                for (int i = 0; i < detallesArrayCopy.size(); i++){
                    ServiceRequest.Fotografia detalle = detallesArrayCopy.get(i);
                    path_images_pdf_2[contador] = Constants.obtenerFotografiaRotacion(detalle,false);
                    servicios_array[contador] = detalle;
                    contador++;

                    if(contador == 3){
                        pdf_control.AddTableImgThreeColumns(
                                path_images_pdf_2, -4f, 28.51f,(int)(3061.206), (int)(2947.828), 18.58f
                                ,servicios_array);

                        path_images_pdf_2[0] = null;
                        path_images_pdf_2[1] = null;
                        path_images_pdf_2[2] = null;
                        servicios_array =  new ServiceRequest.Fotografia[3];
                        contador = 0;
                    }
                    if(i == (detallesArrayCopy.size() - 1)){
                        //Verificamos si es el ultimo
                        if(contador > 0){
                            pdf_control.AddTableImgThreeColumns(
                                    path_images_pdf_2, -4f, 28.51f,(int)(3061.206), (int)(2947.828), 18.58f,servicios_array
                            );
                        }
                        pdf_control.addLineaDivisora();
                    }
                }
                break;
            case 3:
                Bitmap[] path_images_pdf_3 = {null, null, null};
                ServiceRequest.Fotografia[] servicios_array_evidencia =  new ServiceRequest.Fotografia[3];
                contador = 0;
                for (int i = 0; i < detallesArrayCopy.size(); i++){
                    ServiceRequest.Fotografia detalle = detallesArrayCopy.get(i);
                    path_images_pdf_3[contador] = Constants.obtenerFotografiaRotacion(detalle,false);
                    servicios_array_evidencia[contador] = detalle;
                    contador++;

                    if(contador == 3){
                        pdf_control.AddTableImgThreeColumnsEvidencia(
                                path_images_pdf_3, -25f, 5f,  (int)(2947.828), (int)(2947.828), 18.58f
                        ,servicios_array_evidencia);
                        path_images_pdf_3[0] = null;
                        path_images_pdf_3[1] = null;
                        path_images_pdf_3[2] = null;
                        servicios_array =  new ServiceRequest.Fotografia[3];
                        contador = 0;
                    }
                    if(i == (detallesArrayCopy.size() - 1)){
                        //Verificamos si es el ultimo
                        if(contador > 0){

                            pdf_control.AddTableImgThreeColumnsEvidencia(
                                    path_images_pdf_3, -25f, 5f,  (int)(2947.828), (int)(2947.828), 18.58f
                                    ,servicios_array_evidencia);
                        }
                        pdf_control.addLineaDivisora();
                    }
                }
                break;
        }
    }


    public void guardarPdf(){
        pdf_control.CloseDocument();
    }
}
