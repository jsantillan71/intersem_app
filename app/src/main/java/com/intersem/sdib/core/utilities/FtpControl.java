package com.intersem.sdib.core.utilities;

import com.intersem.sdib.ui.services.models.ArchivoPath;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;

public class FtpControl {
    String ruta_archivo;
    String path_server;
    ArrayList<ArchivoPath> files;

    public FtpControl(String ruta_archivo, String path_server){
        this.ruta_archivo = ruta_archivo;
        this.path_server = path_server;
    }

    public FtpControl(ArrayList<ArchivoPath> files){
        this.files = files;
    }

    public FtpControl() {

    }

    public void SubirArchivo() throws Exception {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(InetAddress.getByName(Constants.url_servidor_ftp), Constants.url_servidor_ftp_port);
            int code = ftpClient.getReplyCode();
            String code_str = ftpClient.getReplyString();
            boolean respuesta =  ftpClient.login(Constants.usuario_servidor_ftp,Constants.contrasenia_servidor_ftp);
            code = ftpClient.getReplyCode();
            code_str = ftpClient.getReplyString();
            if(!respuesta) throw new Exception(code_str);
            //Activar que se envie cualquier tipo de archivo
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            BufferedInputStream buffIn = null;
            buffIn = new BufferedInputStream(new FileInputStream(ruta_archivo));//Ruta del archivo para enviar

            //Crear directorios
            String [] array_path_server = path_server.split("/");
            String directorio = "";
            for (int i = 0; i < array_path_server.length - 1; i++){
                directorio = array_path_server[i];
                if(ftpClient.cwd(directorio)==550) ftpClient.makeDirectory(directorio);
                ftpClient.changeWorkingDirectory(directorio);
            }

            // Guardar archivo
            ftpClient.enterLocalPassiveMode();
            respuesta =  ftpClient.storeFile(array_path_server[array_path_server.length - 1] , buffIn);
            code = ftpClient.getReplyCode();
            code_str = ftpClient.getReplyString();
            if(!respuesta) throw new Exception(code_str);
            buffIn.close();
            ftpClient.logout();
            ftpClient.disconnect();
    }

    public void SubirArchivos() throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(InetAddress.getByName(Constants.url_servidor_ftp), Constants.url_servidor_ftp_port);
        int code = ftpClient.getReplyCode();
        String code_str = ftpClient.getReplyString();
        ftpClient.login(Constants.usuario_servidor_ftp,Constants.contrasenia_servidor_ftp);
        code = ftpClient.getReplyCode();
        code_str = ftpClient.getReplyString();

        //Activar que se envie cualquier tipo de archivo
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        //Recorremos todos los archivos
        for(ArchivoPath archivo_actual : files){
            BufferedInputStream buffIn = null;
            buffIn = new BufferedInputStream(new FileInputStream(archivo_actual.getPath_local()));//Ruta del archivo para enviar
            //Crear directorios
            String [] array_path_server = archivo_actual.getPath_server().split("/");
            String directorio = "";
            for (int i = 0; i < array_path_server.length - 1; i++){
                directorio = array_path_server[i];
                if(ftpClient.cwd(directorio)==550) ftpClient.makeDirectory(directorio);
                ftpClient.changeWorkingDirectory(directorio);
            }

            // Guardar archivo
            ftpClient.enterLocalPassiveMode();
            boolean respuesta =  ftpClient.storeFile(array_path_server[array_path_server.length - 1] , buffIn);
            if(!respuesta) throw new Exception("Error en el servidor ");
            code = ftpClient.getReplyCode();
            code_str = ftpClient.getReplyString();
            buffIn.close();
            ftpClient.changeToParentDirectory();
        }
        ftpClient.logout();
        ftpClient.disconnect();
    }

    public int obtenerCantidadArchivos ()  throws Exception{
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(InetAddress.getByName(Constants.url_servidor_ftp), Constants.url_servidor_ftp_port);
        ftpClient.login(Constants.usuario_servidor_ftp,Constants.contrasenia_servidor_ftp);
        //Activar que se envie cualquier tipo de archivo
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        //Navegamos al directorio de archvios
        ftpClient.changeWorkingDirectory(Constants.get_carpeta_server()+ Constants.URL_CARPETA_ARCHIVOS_AUXILIARES);
        //Consultamos todos los archivos
        FTPFile[] arreglo_archvios =  ftpClient.listFiles();
        return arreglo_archvios.length;
    }
    public void DescargarArchivos() throws Exception {
        //Variables de debugueo
        int code;
        String code_str;
        int sucess;
        //Comenzamos proceso de conexion FTP
        FTPClient ftpClient = new FTPClient();
        ftpClient.connect(InetAddress.getByName(Constants.url_servidor_ftp), Constants.url_servidor_ftp_port);
        code = ftpClient.getReplyCode();
        code_str = ftpClient.getReplyString();
        ftpClient.login(Constants.usuario_servidor_ftp,Constants.contrasenia_servidor_ftp);
        code = ftpClient.getReplyCode();
        code_str = ftpClient.getReplyString();
        sucess = 1;
        //Activar que se envie cualquier tipo de archivo
        ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
        //Navegamos al directorio de archvios
        ftpClient.changeWorkingDirectory(Constants.get_carpeta_server()+ Constants.URL_CARPETA_ARCHIVOS_AUXILIARES);
        //Consultamos todos los archivos
        FTPFile[] arreglo_archvios =  ftpClient.listFiles();
        //Recorremos todos los archvios
        for (FTPFile archivo : arreglo_archvios){
            File file_base = new File(Constants.CARPETA_DOCUMENTOS_AUXILIARES);
            if(!file_base.exists())
                sucess =  file_base.mkdirs() ? 1 : 0;
            File archivo_descarga = new File(file_base , archivo.getName());
            ftpClient.retrieveFile(archivo.getName(), new FileOutputStream(archivo_descarga));
        }
    }
}