package com.intersem.sdib.core.utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;
import com.intersem.sdib.core.database.ScriptDataBase;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.ui.archivos_auxiliares.activities.ArchivosAuxiliaresActivity;
import com.intersem.sdib.ui.archivos_auxiliares.models.ArchivoAuxialiarModel;
import com.intersem.sdib.ui.archivos_auxiliares.models.ResponseArchivosAuxiliares;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;

public class HiloDescargarArchivosFTP  extends AsyncTask<Void , Integer, Integer> {

    ProgressDialog progressDialog;
    Context context;
    int cantidad_archvios = 0;
    DataBaseService dataBaseService;
    ResponseArchivosAuxiliares response_archvios;

    public HiloDescargarArchivosFTP(Context context, ResponseArchivosAuxiliares response_archvios ) {
        this.context = context;
        dataBaseService =  new DataBaseService(context);
        this.response_archvios = response_archvios;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = ProgressDialog.show(context, "Comenzado Descarga de Archivos Auxiliares", "Este proceso puede tardar unos minutos.....", true);
        progressDialog.setCancelable(false);
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        try{
            dataBaseService.cleanTable(ScriptDataBase.ArchivosAuxiliares.TABLE_NAME,null,null);
            //Variables de debugueo
            int code;
            String code_str;
            boolean sucess = false;
            //Comenzamos proceso de conexion FTP
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(InetAddress.getByName(Constants.url_servidor_ftp), Constants.url_servidor_ftp_port);
            code = ftpClient.getReplyCode();
            code_str = ftpClient.getReplyString();
            ftpClient.login(Constants.usuario_servidor_ftp,Constants.contrasenia_servidor_ftp);
            code = ftpClient.getReplyCode();
            code_str = ftpClient.getReplyString();
            //Activar que se envie cualquier tipo de archivo
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            //Navegamos al directorio de archvios
            ftpClient.changeWorkingDirectory(Constants.get_carpeta_server()+ Constants.URL_CARPETA_ARCHIVOS_AUXILIARES);
            //Consultamos todos los archivos
            FTPFile[] arreglo_archvios =  ftpClient.listFiles();
            publishProgress(0,arreglo_archvios.length);
            //Recorremos todos los archvios y comparamos con la informacion
            int index = 1;
            boolean existe = false;
            for (FTPFile archivo : arreglo_archvios){
                existe = false;
                for (ResponseArchivosAuxiliares.ArchivoAuxiliar archivo_auxiliar : response_archvios.getResponse()){
                    if(archivo.getName().equals(archivo_auxiliar.getNombre())){
                        existe = true;
                        break;
                    }
                }
                if(existe){
                    String ruta_almacenamiento =  Environment.DIRECTORY_DOCUMENTS;
                    File storageDir =  context.getExternalFilesDir(ruta_almacenamiento);
                    File archivo_descarga = new File(storageDir.getPath() +"/"+  archivo.getName());
                    sucess =  ftpClient.retrieveFile(archivo.getName(), new FileOutputStream(archivo_descarga));
                    if(sucess){
                        ArchivoAuxialiarModel archivo_db = new ArchivoAuxialiarModel();
                        archivo_db.setNombre_archvio(archivo.getName());
                        archivo_db.setRuta_archivo(archivo_descarga.getPath());
                        archivo_db.setTipo(Constants.getMimeType(archivo_descarga.getPath()));
                        dataBaseService.InsertArchivoAuxiliar(archivo_db);
                        //Notificamos a la vista
                        publishProgress(index , arreglo_archvios.length);
                        index ++;
                    }
                }
            }
            return 1;
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressDialog.setMessage("("+(values[0])+"/"+values[1]+") " + "Este proceso puede tardar unos minutos.....");
    }

    @Override
    protected void onPostExecute(Integer resultado) {
        progressDialog.dismiss();
        View view = ((ArchivosAuxiliaresActivity) context).findViewById(android.R.id.content);
        if(resultado == 0){
            Snackbar.make( view, "Ocurrio un error al descargar los archivos. Contacte con el administrador de sistema", Snackbar.LENGTH_LONG).show();
        }else{
            ((ArchivosAuxiliaresActivity) context).consultarArchivos();
            Snackbar.make( view, "Se han descargado correctamente los archvios", Snackbar.LENGTH_LONG).show();
        }
    }

}
