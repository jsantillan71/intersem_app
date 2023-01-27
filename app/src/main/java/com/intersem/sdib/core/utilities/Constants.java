package com.intersem.sdib.core.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;

import com.intersem.sdib.R;
import com.intersem.sdib.ui.services.models.ServiceRequest;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Constants {
    //region Api
    public static String PROD_SERVER = "https://www.intersem.com.mx/desarrollo/backend/public/";
    public static String HOME_SERVER = "http://192.168.194.54/portafolio/intersem_test/public/";
    public static String DEVOLP_SERVER = "http://192.168.1.66/intersem/public/";
    public static String ENVIROMENT = "PROD";

    public static String URL_API = ENVIROMENT == "PROD" ? PROD_SERVER:DEVOLP_SERVER;
    public static String URL_API_LOGIN = URL_API + "api/auth/login";
    public static String URL_API_SERVICES = URL_API + "api/services";
    public static String URL_API_OBSERVACIONES = URL_API + "api/observaciones";
    public static String URL_API_SERVICIO = URL_API + "api/movil/servicio";
    public static String URL_API_SERVICIO_FOLIO = URL_API + "api/movil/obtener_folio";
    public static String URL_API_SERVICIO_ERRONEO = URL_API + "api/movil/servicio_erroneo";
    public static String URL_API_SERVICIO_NO_ENCONTRADO = URL_API + "api/movil/servicio_no_econtrado";
    public static String URL_API_SERVICIO_PENDIENTES = URL_API + "api/movil/servicio_pendientes";
    public static String URL_API_DOCUMENTOS_AUXILIARES = URL_API + "api/movil/documentos_auxiliares";
    public static String URL_API_SERVICIO_ASIGNADOS = URL_API + "api/movil/servicios_asignados";
    public static String URL_API_EQUIPOS = URL_API + "api/movil/equipo";
    public static String URL_API_Empleado = URL_API + "api/movil/empleado";
    public static String URL_API_EMPLEADO_GAFETE = URL_API + "api/movil/empleado/get_badge/";

    //nombre de carpetas en el celular
    public static final String CARPETA_PRINCIPAL = Environment.getExternalStorageDirectory() + File.separator + "/reportes/";//Directorio Principal
    public static final String CARPETA_PDFS = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DOCUMENTS + File.separator + "pdfs";//Carpeta donde se guarda las fotos de id
    public static final String CARPETA_DOCUMENTOS_AUXILIARES = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DOCUMENTS + File.separator + "auxiliares";//Carpeta donde se guarda las fotos de id
    //nombre de carpetas en el server
    public static final String CARPETAS_SERVER = "FOTOS APLICACION";
    public static final String CARPETAS_PDFS_SERVER = "PDFs";//Carpeta donde se guarda las fotos de id
    public static final String CARPETAS_IMAGES_SERVER = "Imagenes";//Carpeta donde se guarda las fotos de id
    public static final String URL_CARPETA_ARCHIVOS_AUXILIARES = "Cotizaciones/archivos_auxiliares";
    //public static final String CARPETAS_PDFS_SERVER = "pdfs";//Carpeta donde se guarda las fotos de id
    //public static final String CARPETAS_IMAGES_SERVER = "img";//Carpeta donde se guarda las fotos de id

    //region AccesosSDIB
    /*
    public static String url_servidor_ftp = "192.168.194.54";
    public static int url_servidor_ftp_port = 21;
    public static String usuario_servidor_ftp = "intersem";
    public static String contrasenia_servidor_ftp = "pokemon";
    */
    //endregion

    //region AccesosOficina
    /*
    public static String url_servidor_ftp = "192.168.1.124";
    public static int url_servidor_ftp_port = 21;
    public static String usuario_servidor_ftp = "intersem";
    public static String contrasenia_servidor_ftp = "#Intersem21";
    */
    //endregion►


    //region AccesosIntersem
    public static String url_servidor_ftp = ENVIROMENT == "PROD" ? "intersem.synology.me" :"192.168.1.66";
    public static int url_servidor_ftp_port = 21;
    public static String usuario_servidor_ftp = ENVIROMENT == "PROD" ?"apk":"android";
    public static String contrasenia_servidor_ftp = ENVIROMENT == "PROD" ?"AppMovil2023":"......";
    //endregion

    //region Credential
    public enum CREDENTIALS {
        user_id,
        user,
        token
    }

    public static String observaciones_finales = "";

    public static String generar_path_reporte(int tipo, String numero_reporte) {
        String path = "";
        switch (tipo) {
            case 1:
                path = CARPETA_PDFS + "/I" + numero_reporte + "fotos.pdf";
                break;
            case 2:
                path = CARPETA_PDFS + "/T" + numero_reporte + "reporte_fotografico.pdf";
                break;
            case 3:
                path = CARPETA_PDFS + "/R" + numero_reporte + "evidencia.pdf";
                break;
            case 4:
                path = CARPETA_PDFS + "/B" + numero_reporte + "bitacora.pdf";
                break;
        }
        return path;
    }

    public static String generar_path_reporte_inverse(int tipo, String numero_reporte) {
        String path = "";
        switch (tipo) {
            case 1:
                path = CARPETA_PDFS + "/I" + numero_reporte + "fotos_inverse.pdf";
                break;
            case 2:
                path = CARPETA_PDFS + "/T" + numero_reporte + "reporte_fotografico_inverse.pdf";
                break;
            case 3:
                path = CARPETA_PDFS + "/R" + numero_reporte + "evidencia_inverse.pdf";
                break;
            case 4:
                path = CARPETA_PDFS + "/B" + numero_reporte + "bitacora_inverse.pdf";
                break;
        }
        return path;
    }

    public static String generar_name_reporte(int tipo, String numero_reporte) {
        String path = "";
        switch (tipo) {
            case 1:
                path = "I" + numero_reporte + "fotos.pdf";
                break;
            case 2:
                path = "T" + numero_reporte + "reporte_fotografico.pdf";
                break;
            case 3:
                path = "R" + numero_reporte + "evidencia.pdf";
                break;
            case 4:
                path = "B" + numero_reporte + "bitacora.pdf";
                break;
        }
        return path;
    }

    public static String generar_name_reporte_inverse(int tipo, String numero_reporte) {
        String path = "";
        switch (tipo) {
            case 1:
                path = "I" + numero_reporte + "fotos_inverse.pdf";
                break;
            case 2:
                path = "T" + numero_reporte + "reporte_fotografico_inverse.pdf";
                break;
            case 3:
                path = "R" + numero_reporte + "evidencia_inverse.pdf";
                break;
            case 4:
                path = "B" + numero_reporte + "bitacora_inverse.pdf";
                break;
        }
        return path;
    }

    public static String get_carpeta_server(){
        Date date = new Date();
        SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
        String currentYear = getYearFormat.format(date);

        return CARPETAS_SERVER+" "+currentYear+"/";
    }

    public static String get_short_year(){
        Date date = new Date();
        SimpleDateFormat getShortYearFormat = new SimpleDateFormat("yy");
        String shortYear = getShortYearFormat.format(date);
        return shortYear;
    }

    public static String generar_path_reporte_server(int tipo, String numero_reporte) {
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
        String currentYear = getYearFormat.format(date);

        SimpleDateFormat getShortYearFormat = new SimpleDateFormat("yy");
        String shortYear = getShortYearFormat.format(date);

        String tipo_formato =  obtenerFormato(tipo);

        String path = get_carpeta_server()+CARPETAS_PDFS_SERVER +"/"+ tipo_formato + "/";
        switch (tipo) {
            case 1:
                path +=  "I" + numero_reporte + shortYear+".pdf";
                break;
            case 2:
                path += "T" + numero_reporte + shortYear+".pdf";
                break;
            case 3:
                path +=  "R" + numero_reporte + shortYear+".pdf";
                break;
            case 4:
                path +=  "B" + numero_reporte + shortYear+".pdf";
                break;
            default:
                path +=  "E" + timeStamp + shortYear+".pdf";
                break;
        }
        return path;
    }

    public static String generar_path_reporte_server_inverse(int tipo, String numero_reporte) {
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
        String currentYear = getYearFormat.format(date);
        String tipo_formato =  obtenerFormato(tipo);
        String path = get_carpeta_server()+CARPETAS_PDFS_SERVER + "/" + currentYear +"/"+ tipo_formato + "/";
        switch (tipo) {
            case 1:
                path +=  "I" + numero_reporte + "fotos_inverse.pdf";
                break;
            case 2:
                path += "T" + numero_reporte + "reporte_fotografico_inverse.pdf";
                break;
            case 3:
                path +=  "R" + numero_reporte + "evidencia_inverse.pdf";
                break;
            case 4:
                path +=  "B" + numero_reporte + "bitacora_inverse.pdf";
                break;
            default:
                path +=  "E" + timeStamp + "extra_inverse.pdf";
                break;
        }
        return path;
    }

    public static String generar_path_imagen_server(int tipo, String numero_reporte, int contador , int tipo_servicio ) {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String tipo_formato =  obtenerFormato(tipo_servicio);
        Date date = new Date();
        SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
        String currentYear = getYearFormat.format(date);
        String path = get_carpeta_server()+CARPETAS_IMAGES_SERVER +"/"+ tipo_formato + "/";
        String nombre_archivo = "";
        if(tipo_servicio == 0){
            nombre_archivo = "_" +timeStamp+"_";
        }else{
            nombre_archivo = numero_reporte + "_" +timeStamp+"_";
        }
        switch (tipo) {
            case 1:
                path += "Antes/" + nombre_archivo + contador + ".jpg";
                break;
            case 2:
                path += "Durante/" + nombre_archivo + contador + ".jpg";
                break;
            case 3:
                path += "Despues/" + nombre_archivo +  contador + ".jpg";
                break;
            case 4:
                path += "Etiqueta/" + nombre_archivo +  contador + ".jpg";
                break;
            case 5:
                path += "Serie/" + nombre_archivo +  contador + ".jpg";
                break;
            case 6:
                path += "Cambs/" + nombre_archivo +  contador + ".jpg";
                break;
            case 7:
                path += "Bitacora/" + nombre_archivo +  contador + ".jpg";
                break;
            case 8:
                path += "Refaccion/" + nombre_archivo +  contador + ".jpg";
                break;
            case 9:
                path +=  "Gafete/" + nombre_archivo +  contador + ".jpg";
                break;
        }
        return path;
    }

    public static String obtener_path_reporte(ServiceRequest servicio_selecionado) {
        String path = "";
        switch (servicio_selecionado.getReporte_subir()) {
            case 1:
                path = Constants.generar_path_reporte(1, servicio_selecionado.getNumero_reporte());
                break;
            case 2:
                path = Constants.generar_path_reporte(2, servicio_selecionado.getNumero_reporte());
                break;
            case 3:
                path = Constants.generar_path_reporte(3, servicio_selecionado.getNumero_reporte());
                break;
        }
        return path;
    }

    public static Bitmap obtenerFotografiaRotacion(ServiceRequest.Fotografia fotografia, boolean filtro) {
        Bitmap bitmap = BitmapFactory.decodeFile(fotografia.getRuta_fotografia());
        if (filtro) {
            bitmap = Bitmap.createScaledBitmap(bitmap, 200 /*Ancho*/, 200 /*Alto*/, filtro /* filter*/);
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(fotografia.getRotacion());
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, filtro);
        return rotatedBitmap;
    }

    public static String obtenerTipoFografia(ServiceRequest.Fotografia fotografia) {
        String path = "";
        switch (fotografia.getTipo_fotografia()) {
            case 1:
                path = "ANTES";
                break;
            case 2:
                path = "DURANTE";
                break;
            case 3:
                path = "DESPUÉS";
                break;
            case 4:
                path = "ETIQUETA DE SERVICIO";
                break;
            case 5:
                path = "No. SERIE";
                break;
            case 6:
                path = "No. CABMS";
                break;
            case 7:
                path = "BITACORA";
                break;
            case 8:
                path = "REFACCIÓN";
                break;
            case 9:
                path = "GAFETE";
                break;
        }
        return path;
    }

    public static int obtenerDescripcionFografia(ServiceRequest.Fotografia fotografia) {
        int resource = 0;
        switch (fotografia.getTipo_fotografia()) {
            case 1:
                resource = R.drawable.antes_lateral;
                break;
            case 2:
                resource = R.drawable.durante_lateral;
                break;
            case 3:
                resource = R.drawable.despues_lateral;
                break;
            case 4:
                resource = R.drawable.etiqueta_lateral;
                break;
            case 5:
                resource = R.drawable.serie_lateral;
                break;
            case 6:
                resource = R.drawable.cambs_lateral;
                break;
            case 8:
                resource = R.drawable.refaccion_lateral;
                break;
            case 9:
                resource = R.drawable.gafete_lateral;
                break;
        }
        return resource;
    }

    public static int obtenerDescripcionFografiaEvidencia(ServiceRequest.Fotografia fotografia) {
        int resource = 0;
        switch (fotografia.getTipo_fotografia()) {
            case 1:
                resource = R.drawable.antes_vertical;
                break;
            case 2:
                resource = R.drawable.durante_vertical;
                break;
            case 3:
                resource = R.drawable.despues_vertical;
                break;
            case 4:
                resource = R.drawable.etiqueta_vertical;
                break;
            case 5:
                resource = R.drawable.serie_vertical;
                break;
            case 6:
                resource = R.drawable.cambs_vertical;
                break;
            case 8:
                resource = R.drawable.refaccion_vertical;
                break;
            case 9:
                resource = R.drawable.gafete_vertical;
                break;
        }
        return resource;
    }

    public static String getMimeType(String url)
    {
        String extension = url.substring(url.lastIndexOf("."));
        return extension;
    }

    public static String obtenerFormato(int formato_id){
        String formato = "";
        switch (formato_id){
            case 1:
                formato = "INT";
                break;
            case 2:
                formato = "TEC";
                break;
            case 3:
                formato = "RET";
                break;
            default:
                formato = "EXTRA";
                break;
        }
        return formato;
    }

    public static int obtenerFormatoId(String formato){
        int formato_id = 0;
        switch (formato){
            case "INT":
                formato_id = 1;
                break;
            case "TEC":
                formato_id = 2;
                break;
            case "RET":
                formato_id = 3;
                break;
        }
        return formato_id;
    }

    public static String obtenerTipoServicio(int tipo_servicio_id){
        String tipo_servicio = "";
        switch (tipo_servicio_id){
            case 1:
                tipo_servicio = "MANTENIMIENTO PREVENTIVO";
                break;
            case 2:
                tipo_servicio = "MANTENIMIENTO CORRECTIVO";
                break;
            case 3:
                tipo_servicio = "INTERNACIONAL";
                break;
        }

        return tipo_servicio;
    }

    public static String getCurrentTimeStamp(String fecha_texto) {
        try {
            Date fecha = null;
            fecha = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(fecha_texto);
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");//dd/MM/yyyy
            String strDate = sdfDate.format(fecha);
            return strDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getCurrentTimeStamp() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat timeOnly = new SimpleDateFormat("HH:mm:ss");
        return  timeOnly.format(cal.getTime());
    }

    public static String toTitleCase(String string) {

        // Check if String is null
        if (string == null) {

            return null;
        }

        boolean whiteSpace = true;

        StringBuilder builder = new StringBuilder(string); // String builder to store string
        final int builderLength = builder.length();

        // Loop through builder
        for (int i = 0; i < builderLength; ++i) {

            char c = builder.charAt(i); // Get character at builders position

            if (whiteSpace) {

                // Check if character is not white space
                if (!Character.isWhitespace(c)) {

                    // Convert to title case and leave whitespace mode.
                    builder.setCharAt(i, Character.toTitleCase(c));
                    whiteSpace = false;
                }
            } else if (Character.isWhitespace(c)) {

                whiteSpace = true; // Set character is white space

            } else {

                builder.setCharAt(i, Character.toLowerCase(c)); // Set character to lowercase
            }
        }

        return builder.toString(); // Return builders text
    }
}
