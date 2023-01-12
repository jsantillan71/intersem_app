package com.intersem.sdib.core.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.util.Log;

import com.intersem.sdib.core.database.ScriptDataBase;
import com.intersem.sdib.core.database.ScvmDatabase;
import com.intersem.sdib.ui.agenda.models.NotaModel;
import com.intersem.sdib.ui.archivos_auxiliares.models.ArchivoAuxialiarModel;
import com.intersem.sdib.ui.login.models.ResponseLoginModel;
import com.intersem.sdib.ui.services.models.ServiceRequest;
import com.intersem.sdib.core.utilities.Equipo;
import com.intersem.sdib.core.utilities.Empleado;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataBaseService {
    private ScvmDatabase scvmDatabase;

    public DataBaseService(Context context)
    {
        scvmDatabase = new ScvmDatabase(context);

    }

    public void cleanTable(String table_name, String whereClause, String[] whereArgs){
        scvmDatabase.getWritableDatabase().delete(table_name, whereClause, whereArgs);
    }

    public boolean existsRows(String tableName) {
        boolean existe = false;
        long rows = DatabaseUtils.queryNumEntries(scvmDatabase.getReadableDatabase(), tableName, null, null);

        if(rows > 0) existe = true;

        return existe;
    }

    public void InsertUsuario(ResponseLoginModel.Response responseLoginModel){
        cleanTable(ScriptDataBase.User.TABLE_NAME, null, null);

        ContentValues values = new ContentValues();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        // manipulate date
        String strDate= formatter.format(date);

        //Tienes que modicar esto para que puedas agregar los datos que necesitas
        values.put(ScriptDataBase.User.USER, responseLoginModel.getUser());
        values.put(ScriptDataBase.User.TOKEN, responseLoginModel.getToken());
        values.put(ScriptDataBase.User.PROFILE, responseLoginModel.getProfile());
        values.put(ScriptDataBase.User.COMPANY, responseLoginModel.getCompany());
        values.put(ScriptDataBase.User.DATE_CREATE, strDate);
        values.put(ScriptDataBase.User.USER_ID, responseLoginModel.getUser_id());

        scvmDatabase.getWritableDatabase().insert(ScriptDataBase.User.TABLE_NAME, null, values);
    }

    public int InsertServicio(ServiceRequest servicio){

        ContentValues values = new ContentValues();
        //Tienes que modicar esto para que puedas agregar los datos que necesitas
        values.put(ScriptDataBase.Servicio.NO_REPORTE, servicio.getNumero_reporte());
        values.put(ScriptDataBase.Servicio.TIPO_SERVICIO, servicio.getTipo_servicio());
        values.put(ScriptDataBase.Servicio.EQUIPO, servicio.getEquipo());
        values.put(ScriptDataBase.Servicio.MARCA, servicio.getMarca());
        values.put(ScriptDataBase.Servicio.MODELO, servicio.getModelo());
        values.put(ScriptDataBase.Servicio.SERIE, servicio.getSerie());
        values.put(ScriptDataBase.Servicio.ID_SERVIDOR, servicio.getId_servidor());
        values.put(ScriptDataBase.Servicio.PRINTED, 0);
        values.put(ScriptDataBase.Servicio.SUBIDO, 0);
        values.put(ScriptDataBase.Servicio.TERMINADO, 0);
        values.put(ScriptDataBase.Servicio.OBSERVACIONES_FINALES, servicio.getObservaciones_finales());
        values.put(ScriptDataBase.Servicio.REPORTE_SUBIR, servicio.getReporte_subir());
        values.put(ScriptDataBase.Servicio.CAMBS, servicio.getCabms());
        values.put(ScriptDataBase.Servicio.EQUIPO_ID, servicio.getEquipo_id());
        values.put(ScriptDataBase.Servicio.GASTO, servicio.getGasto());
        values.put(ScriptDataBase.Servicio.EMPLEADO_GAFETE, servicio.getEmpleado_gafete());
        int id = (int)scvmDatabase.getWritableDatabase().insert(ScriptDataBase.Servicio.TABLE_NAME, null, values);
        return id;
    }


    public int InsertServicioAgenda(ServiceRequest servicio){

        ContentValues values = new ContentValues();
        //Tienes que modicar esto para que puedas agregar los datos que necesitas
        values.put(ScriptDataBase.Servicio.NO_REPORTE, servicio.getNumero_reporte());
        values.put(ScriptDataBase.Servicio.TIPO_SERVICIO, servicio.getTipo_servicio());
        values.put(ScriptDataBase.Servicio.EQUIPO, servicio.getEquipo());
        values.put(ScriptDataBase.Servicio.MARCA, servicio.getMarca());
        values.put(ScriptDataBase.Servicio.MODELO, servicio.getModelo());
        values.put(ScriptDataBase.Servicio.SERIE, servicio.getSerie());
        values.put(ScriptDataBase.Servicio.ID_SERVIDOR, servicio.getId_servidor());
        values.put(ScriptDataBase.Servicio.PRINTED, 0);
        values.put(ScriptDataBase.Servicio.SUBIDO, 0);
        values.put(ScriptDataBase.Servicio.TERMINADO, 0);
        values.put(ScriptDataBase.Servicio.OBSERVACIONES_FINALES, servicio.getObservaciones_finales());
        values.put(ScriptDataBase.Servicio.REPORTE_SUBIR, servicio.getReporte_subir());
        values.put(ScriptDataBase.Servicio.CAMBS, servicio.getCabms());
        values.put(ScriptDataBase.Servicio.EQUIPO_ID, servicio.getEquipo_id());
        values.put(ScriptDataBase.Servicio.CLIENTE_DATOS, servicio.getClientes_datos());
        values.put(ScriptDataBase.Servicio.FECHA_SERVICIO, servicio.getFecha_asignada());
        int id = (int)scvmDatabase.getWritableDatabase().insert(ScriptDataBase.Servicio.TABLE_NAME_AGENDA, null, values);
        return id;
    }


    public ServiceRequest ModificarServicio(ServiceRequest servicio){

        ContentValues values = new ContentValues();
        //Tienes que modicar esto para que puedas agregar los datos que necesitas
        values.put(ScriptDataBase.Servicio.PATH_PDF_1, servicio.getPath_pdf_1());
        values.put(ScriptDataBase.Servicio.PATH_PDF_2, servicio.getPath_pdf_2());
        values.put(ScriptDataBase.Servicio.PATH_PDF_3, servicio.getPath_pdf_3());
        values.put(ScriptDataBase.Servicio.OBSERVACIONES_FINALES, servicio.getObservaciones_finales());
        values.put(ScriptDataBase.Servicio.ID_SERVIDOR, servicio.getId_servidor());
        values.put(ScriptDataBase.Servicio.PRINTED, servicio.getPrinted());
        values.put(ScriptDataBase.Servicio.TERMINADO, 1);
        values.put(ScriptDataBase.Servicio.CAMBS, servicio.getCabms());
        values.put(ScriptDataBase.Servicio.NO_REPORTE, servicio.getNumero_reporte());
        values.put(ScriptDataBase.Servicio.REPORTE_SUBIR, servicio.getReporte_subir());
        values.put(ScriptDataBase.Servicio.EQUIPO_ID, servicio.getEquipo_id());
        scvmDatabase.getWritableDatabase().update(ScriptDataBase.Servicio.TABLE_NAME,values,"_id ="+ servicio.getId(),null);
        return servicio;
    }

    public ServiceRequest ModificarEmpleadoGafete(ServiceRequest servicio){

        ContentValues values = new ContentValues();
        values.put(ScriptDataBase.Servicio.EMPLEADO_GAFETE, servicio.getEmpleado_gafete());
        scvmDatabase.getWritableDatabase().update(ScriptDataBase.Servicio.TABLE_NAME,values,"_id ="+ servicio.getId(),null);
        return servicio;
    }

    public ServiceRequest ModificarObservacionesServicio(ServiceRequest servicio){

        ContentValues values = new ContentValues();
        values.put(ScriptDataBase.Servicio.GASTO, servicio.getGasto());
        values.put(ScriptDataBase.Servicio.OBSERVACIONES_FINALES, servicio.getObservaciones_finales());
        scvmDatabase.getWritableDatabase().update(ScriptDataBase.Servicio.TABLE_NAME,values,"_id ="+ servicio.getId(),null);
        return servicio;
    }


    public ServiceRequest ModificarServicioSubido(ServiceRequest servicio){

        ContentValues values = new ContentValues();
        values.put(ScriptDataBase.Servicio.SUBIDO, 0);
        scvmDatabase.getWritableDatabase().update(ScriptDataBase.Servicio.TABLE_NAME,values,"_id ="+ servicio.getId(),null);
        return servicio;
    }

    public ServiceRequest ModificarServicioAgenda(ServiceRequest servicio){

        ContentValues values = new ContentValues();
        //Tienes que modicar esto para que puedas agregar los datos que necesitas
        values.put(ScriptDataBase.Servicio.PATH_PDF_1, servicio.getPath_pdf_1());
        values.put(ScriptDataBase.Servicio.PATH_PDF_2, servicio.getPath_pdf_2());
        values.put(ScriptDataBase.Servicio.PATH_PDF_3, servicio.getPath_pdf_3());
        values.put(ScriptDataBase.Servicio.OBSERVACIONES_FINALES, servicio.getObservaciones_finales());
        values.put(ScriptDataBase.Servicio.ID_SERVIDOR, servicio.getId_servidor());
        values.put(ScriptDataBase.Servicio.PRINTED, servicio.getPrinted());
        values.put(ScriptDataBase.Servicio.TERMINADO, 1);
        values.put(ScriptDataBase.Servicio.CAMBS, servicio.getCabms());
        values.put(ScriptDataBase.Servicio.EQUIPO_ID, servicio.getEquipo_id());
        values.put(ScriptDataBase.Servicio.CLIENTE_DATOS, servicio.getClientes_datos());
        values.put(ScriptDataBase.Servicio.FECHA_SERVICIO, servicio.getFecha_servicio());
        scvmDatabase.getWritableDatabase().update(ScriptDataBase.Servicio.TABLE_NAME,values,"_id ="+ servicio.getId(),null);
        return servicio;
    }

    public ServiceRequest ModificarServicioErroneo(ServiceRequest servicio){

        ContentValues values = new ContentValues();
        //Tienes que modicar esto para que puedas agregar los datos que necesitas
        values.put(ScriptDataBase.Servicio.PATH_PDF_1, servicio.getPath_pdf_1());
        values.put(ScriptDataBase.Servicio.PATH_PDF_2, servicio.getPath_pdf_2());
        values.put(ScriptDataBase.Servicio.PATH_PDF_3, servicio.getPath_pdf_3());
        values.put(ScriptDataBase.Servicio.OBSERVACIONES_FINALES, servicio.getObservaciones_finales());
        values.put(ScriptDataBase.Servicio.ID_SERVIDOR, servicio.getId_servidor());
        values.put(ScriptDataBase.Servicio.PRINTED, servicio.getPrinted());
        values.put(ScriptDataBase.Servicio.TERMINADO, -1);
        values.put(ScriptDataBase.Servicio.CAMBS, servicio.getCabms());
        values.put(ScriptDataBase.Servicio.EQUIPO_ID, servicio.getEquipo_id());
        scvmDatabase.getWritableDatabase().update(ScriptDataBase.Servicio.TABLE_NAME,values,"_id ="+ servicio.getId(),null);
        return servicio;
    }

    public ServiceRequest ModificarServicioNoRealizado(ServiceRequest servicio){

        ContentValues values = new ContentValues();
        //Tienes que modicar esto para que puedas agregar los datos que necesitas
        values.put(ScriptDataBase.Servicio.PATH_PDF_1, servicio.getPath_pdf_1());
        values.put(ScriptDataBase.Servicio.PATH_PDF_2, servicio.getPath_pdf_2());
        values.put(ScriptDataBase.Servicio.PATH_PDF_3, servicio.getPath_pdf_3());
        values.put(ScriptDataBase.Servicio.OBSERVACIONES_FINALES, servicio.getObservaciones_finales());
        values.put(ScriptDataBase.Servicio.ID_SERVIDOR, servicio.getId_servidor());
        values.put(ScriptDataBase.Servicio.PRINTED, servicio.getPrinted());
        values.put(ScriptDataBase.Servicio.TERMINADO, -2);
        values.put(ScriptDataBase.Servicio.CAMBS, servicio.getCabms());
        values.put(ScriptDataBase.Servicio.EQUIPO_ID, servicio.getEquipo_id());
        scvmDatabase.getWritableDatabase().update(ScriptDataBase.Servicio.TABLE_NAME,values,"_id ="+ servicio.getId(),null);
        return servicio;
    }


    public ServiceRequest TerminadoServicio(ServiceRequest servicio){

        ContentValues values = new ContentValues();
        //Tienes que modicar esto para que puedas agregar los datos que necesitas
        values.put(ScriptDataBase.Servicio.SUBIDO, 1);
        scvmDatabase.getWritableDatabase().update(ScriptDataBase.Servicio.TABLE_NAME,values,"_id ="+ servicio.getId(),null);
        return servicio;
    }



    public int InsertDetalleServicio(ServiceRequest.Fotografia fotografia){

        ContentValues values = new ContentValues();
        //Tienes que modicar esto para que puedas agregar los datos que necesitas
        values.put(ScriptDataBase.ServicioDetalle.FOTOGRAFIA_LOCAL, fotografia.getRuta_fotografia());
        values.put(ScriptDataBase.ServicioDetalle.FOTOGRAFIA_SERVIDOR, fotografia.getRuta_servidor());
        values.put(ScriptDataBase.ServicioDetalle.SERVICIO_ID, fotografia.getServicio_id());
        values.put(ScriptDataBase.ServicioDetalle.TIPO, fotografia.getTipo_fotografia());
        values.put(ScriptDataBase.ServicioDetalle.ORDEN,fotografia.getOrden());
        values.put(ScriptDataBase.ServicioDetalle.CHECK,0);
        values.put(ScriptDataBase.ServicioDetalle.CALIDAD_PATH,fotografia.getRuta_calidad());
        values.put(ScriptDataBase.ServicioDetalle.ROTACION,fotografia.getRotacion());
        return (int)scvmDatabase.getWritableDatabase().insert(ScriptDataBase.ServicioDetalle.TABLE_NAME, null, values);
    }

    public void ModificarDetalleServicio(ServiceRequest.Fotografia fotografia){

        ContentValues values = new ContentValues();
        //Tienes que modicar esto para que puedas agregar los datos que necesitas
        values.put(ScriptDataBase.ServicioDetalle.ORDEN,fotografia.getOrden());
        values.put(ScriptDataBase.ServicioDetalle.CHECK, fotografia.getCheck());
        values.put(ScriptDataBase.ServicioDetalle.ROTACION, fotografia.getRotacion());
        scvmDatabase.getWritableDatabase().update(ScriptDataBase.ServicioDetalle.TABLE_NAME,values,"_id ="+ fotografia.getServicio_detalle_id(),null);
    }

    public void DeleteDetalleServicio(ServiceRequest.Fotografia fotografia){
        scvmDatabase.getWritableDatabase().delete(ScriptDataBase.ServicioDetalle.TABLE_NAME,"_id ="+ fotografia.getServicio_detalle_id(),null);
    }

    public void EliminarServicio(ServiceRequest servicio) {
        scvmDatabase.getWritableDatabase().delete(ScriptDataBase.Servicio.TABLE_NAME,"_id ="+servicio.getId(),null);
    }

    public void InsertArchivoAuxiliar(ArchivoAuxialiarModel auxialiarModel){
        ContentValues values = new ContentValues();
        values.put(ScriptDataBase.ArchivosAuxiliares.NOMBRE_ARCHIVO, auxialiarModel.getNombre_archvio());
        values.put(ScriptDataBase.ArchivosAuxiliares.RUTA_ARCHIVO,auxialiarModel.getRuta_archivo());
        values.put(ScriptDataBase.ArchivosAuxiliares.TIPO, auxialiarModel.getTipo());
        Long id = scvmDatabase.getWritableDatabase().insert(ScriptDataBase.ArchivosAuxiliares.TABLE_NAME, null, values);
        int hola = 0;
    }


    public ArrayList<ArchivoAuxialiarModel> ConsultarArchivosAuxialiar(){
        Cursor cursor = scvmDatabase.getWritableDatabase()
                .rawQuery("select * from " + ScriptDataBase.ArchivosAuxiliares.TABLE_NAME,
                        null);
        ArrayList<ArchivoAuxialiarModel> auxialiarArrayList =  new ArrayList<>();
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do{
                    ArchivoAuxialiarModel archivo = new ArchivoAuxialiarModel();
                    archivo.setId(
                            cursor.getInt(ScriptDataBase.ArchivosAuxiliares.ID_INDEX)
                    );
                    archivo.setNombre_archvio(
                            cursor.getString(ScriptDataBase.ArchivosAuxiliares.NOMBRE_ARCHIVO_INDEX)
                    );
                    archivo.setRuta_archivo(
                            cursor.getString(ScriptDataBase.ArchivosAuxiliares.RUTA_ARCHIVO_INDEX)
                    );
                    archivo.setTipo(
                            cursor.getString(ScriptDataBase.ArchivosAuxiliares.TIPO_INDEX)
                    );

                    auxialiarArrayList.add(archivo);
                }while(cursor.moveToNext());
            }
        }
        return auxialiarArrayList;
    }

    public void InsertNota(NotaModel notaModel){
        ContentValues values = new ContentValues();
        values.put(ScriptDataBase.Nota.NOTA, notaModel.getNota());
        values.put(ScriptDataBase.Nota.FECHA,notaModel.getFecha());
        values.put(ScriptDataBase.Nota.CLIENTE, notaModel.getCliente());
        Long id = scvmDatabase.getWritableDatabase().insert(ScriptDataBase.Nota.TABLE_NAME, null, values);
    }

    public ArrayList<NotaModel> ConsultarNotas(){
        Cursor cursor = scvmDatabase.getWritableDatabase()
                .rawQuery("select * from " + ScriptDataBase.Nota.TABLE_NAME,
                        null);
        ArrayList<NotaModel> notasArrayList =  new ArrayList<>();
        if(cursor != null) {
            if (cursor.moveToFirst()) {
                do{
                    NotaModel nota = new NotaModel();
                    nota.setId(
                            cursor.getInt(ScriptDataBase.ArchivosAuxiliares.ID_INDEX)
                    );
                    nota.setCliente(
                            cursor.getString(ScriptDataBase.Nota.CLIENTE_INDEX)
                    );
                    nota.setNota(
                            cursor.getString(ScriptDataBase.Nota.NOTA_INDEX)
                    );
                    nota.setFecha(
                            cursor.getString(ScriptDataBase.Nota.FECHA_INDEX)
                    );

                    notasArrayList.add(nota);
                }while(cursor.moveToNext());
            }
        }
        return notasArrayList;
    }

    //Esta funcion te va permitir consultar si el telefono tiene token,
    public ResponseLoginModel.Response GetUsuarios(){
        Cursor cursor = scvmDatabase.getWritableDatabase()
                .rawQuery("select * from " + ScriptDataBase.User.TABLE_NAME,
                        null);

        ResponseLoginModel.Response responseSessionModel = new ResponseLoginModel.Response();

        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    responseSessionModel.setToken(
                            cursor.getString(ScriptDataBase.User.TOKEN_INDEX)
                    );
                    responseSessionModel.setUser(
                            cursor.getString(ScriptDataBase.User.USER_INDEX)
                    );
                    responseSessionModel.setCompany(
                            cursor.getString(ScriptDataBase.User.COMPANY_INDEX)
                    );
                    responseSessionModel.setProfile(
                            cursor.getString(ScriptDataBase.User.PROFILE_INDEX)
                    );
                    responseSessionModel.setDate_create(
                            cursor.getString(ScriptDataBase.User.DATE_CREATE_INDEX)
                    );

                    responseSessionModel.setUser_id(
                            cursor.getInt(ScriptDataBase.User.USER_ID_INDEX)
                    );

                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        return responseSessionModel;
    }

    public ArrayList<ServiceRequest> GetServicios(){
        Cursor cursor = scvmDatabase.getWritableDatabase()
                .rawQuery("select * from " + ScriptDataBase.Servicio.TABLE_NAME,
                        null);
        ServiceRequest servicio = new ServiceRequest();
        ArrayList<ServiceRequest> servicios_array_list = new ArrayList<>();
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    servicio = new ServiceRequest();
                    servicio.setNumero_reporte(
                            cursor.getString(ScriptDataBase.Servicio.NO_REPORTE_INDEX)
                    );
                    servicio.setNo_reporte(
                            cursor.getString(ScriptDataBase.Servicio.NO_REPORTE_INDEX)
                    );
                    servicio.setTipo_servicio(
                            cursor.getInt(ScriptDataBase.Servicio.TIPO_SERVICIO_INDEX)
                    );
                    servicio.setId_servidor(
                            cursor.getInt(ScriptDataBase.Servicio.ID_SERVIDOR_INDEX)
                    );
                    servicio.setId(
                            cursor.getInt(ScriptDataBase.Servicio.ID_INDEX)
                    );
                    servicio.setPath_pdf_1(
                            cursor.getString(ScriptDataBase.Servicio.PATH_PDF_1_INDEX)
                    );
                    servicio.setPath_pdf_2(
                            cursor.getString(ScriptDataBase.Servicio.PATH_PDF_2_INDEX)
                    );
                    servicio.setPath_pdf_3(
                            cursor.getString(ScriptDataBase.Servicio.PATH_PDF_3_INDEX)
                    );
                    servicio.setPrinted(
                            cursor.getInt(ScriptDataBase.Servicio.PRINTED_INDEX)
                    );
                    servicio.setObservaciones_finales(
                            cursor.getString(ScriptDataBase.Servicio.OBSERVACIONES_FINALES_INDEX)
                    );

                    servicio.setSubido(
                            cursor.getInt(ScriptDataBase.Servicio.SUBIDO_INDEX)
                    );

                    servicio.setTerminado(
                            cursor.getInt(ScriptDataBase.Servicio.TERMINADO_INDEX)
                    );

                    servicio.setEquipo(
                            cursor.getString(ScriptDataBase.Servicio.EQUIPO_INDEX)
                    );
                    servicio.setMarca(
                            cursor.getString(ScriptDataBase.Servicio.MARCA_INDEX)
                    );
                    servicio.setModelo(
                            cursor.getString(ScriptDataBase.Servicio.MODELO_INDEX)
                    );

                    servicio.setSerie(
                            cursor.getString(ScriptDataBase.Servicio.SERIE_INDEX)
                    );

                    servicio.setReporte_subir(
                            cursor.getInt(ScriptDataBase.Servicio.REPORTE_SUBIR_INDEX)
                    );
                    servicio.setCabms(
                            cursor.getString(ScriptDataBase.Servicio.CAMBS_INDEX)
                    );
                    servicio.setEquipo_id(
                            cursor.getInt(ScriptDataBase.Servicio.EQUIPO_ID_INDEX)
                    );
                    servicio.setGasto(
                            cursor.getString(18)
                    );
                    servicios_array_list.add(servicio);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        return servicios_array_list;
    }

    public ArrayList<Empleado> GetEmpleados(){
        Cursor cursor = scvmDatabase.getWritableDatabase()
                .rawQuery("select * from " + ScriptDataBase.Empleado.TABLE_NAME + " order by nombre",
                        null);
        Empleado empleado = new Empleado();
        ArrayList<Empleado> empleados_array_list = new ArrayList<>();
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    empleado = new Empleado();
                    empleado.setNombre(
                            cursor.getString(1)
                    );
                    empleado.setEmpleado_id(
                            cursor.getInt(3)
                    );
                    empleados_array_list.add(empleado);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        return empleados_array_list;
    }

    public ArrayList<ServiceRequest> GetServiciosAgenda(){
        Cursor cursor = scvmDatabase.getWritableDatabase()
                .rawQuery("select * from " + ScriptDataBase.Servicio.TABLE_NAME_AGENDA,
                        null);
        ServiceRequest servicio = new ServiceRequest();
        ArrayList<ServiceRequest> servicios_array_list = new ArrayList<>();
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    servicio = new ServiceRequest();
                    servicio.setNumero_reporte(
                            cursor.getString(ScriptDataBase.Servicio.NO_REPORTE_INDEX)
                    );
                    servicio.setNo_reporte(
                            cursor.getString(ScriptDataBase.Servicio.NO_REPORTE_INDEX)
                    );
                    servicio.setTipo_servicio(
                            cursor.getInt(ScriptDataBase.Servicio.TIPO_SERVICIO_INDEX)
                    );
                    servicio.setId_servidor(
                            cursor.getInt(ScriptDataBase.Servicio.ID_SERVIDOR_INDEX)
                    );
                    servicio.setId(
                            cursor.getInt(ScriptDataBase.Servicio.ID_INDEX)
                    );
                    servicio.setPath_pdf_1(
                            cursor.getString(ScriptDataBase.Servicio.PATH_PDF_1_INDEX)
                    );
                    servicio.setPath_pdf_2(
                            cursor.getString(ScriptDataBase.Servicio.PATH_PDF_2_INDEX)
                    );
                    servicio.setPath_pdf_3(
                            cursor.getString(ScriptDataBase.Servicio.PATH_PDF_3_INDEX)
                    );
                    servicio.setPrinted(
                            cursor.getInt(ScriptDataBase.Servicio.PRINTED_INDEX)
                    );
                    servicio.setObservaciones_finales(
                            cursor.getString(ScriptDataBase.Servicio.OBSERVACIONES_FINALES_INDEX)
                    );

                    servicio.setSubido(
                            cursor.getInt(ScriptDataBase.Servicio.SUBIDO_INDEX)
                    );

                    servicio.setTerminado(
                            cursor.getInt(ScriptDataBase.Servicio.TERMINADO_INDEX)
                    );

                    servicio.setEquipo(
                            cursor.getString(ScriptDataBase.Servicio.EQUIPO_INDEX)
                    );
                    servicio.setMarca(
                            cursor.getString(ScriptDataBase.Servicio.MARCA_INDEX)
                    );
                    servicio.setModelo(
                            cursor.getString(ScriptDataBase.Servicio.MODELO_INDEX)
                    );

                    servicio.setSerie(
                            cursor.getString(ScriptDataBase.Servicio.SERIE_INDEX)
                    );

                    servicio.setReporte_subir(
                            cursor.getInt(ScriptDataBase.Servicio.REPORTE_SUBIR_INDEX)
                    );
                    servicio.setCabms(
                            cursor.getString(ScriptDataBase.Servicio.CAMBS_INDEX)
                    );
                    servicio.setEquipo_id(
                            cursor.getInt(ScriptDataBase.Servicio.EQUIPO_ID_INDEX)
                    );
                    servicio.setClientes_datos(
                            cursor.getString(ScriptDataBase.Servicio.CLIENTE_DATOS_INDEX)
                    );
                    servicio.setFecha_servicio(
                            cursor.getString(ScriptDataBase.Servicio.FECHA_SERVICIO_INDEX)
                    );
                    servicio.setId_servidor(
                            cursor.getInt(ScriptDataBase.Servicio.ID_SERVIDOR_INDEX)
                    );
                    servicios_array_list.add(servicio);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        return servicios_array_list;
    }

    public ServiceRequest GetServicio(int servicio_id){
        Cursor cursor = scvmDatabase.getWritableDatabase()
                .rawQuery("select * from servicios where _id = " + servicio_id ,
                        null);
        ServiceRequest servicio = new ServiceRequest();
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    servicio.setNumero_reporte(
                            cursor.getString(ScriptDataBase.Servicio.NO_REPORTE_INDEX)
                    );
                    servicio.setNo_reporte(
                            cursor.getString(ScriptDataBase.Servicio.NO_REPORTE_INDEX)
                    );
                    servicio.setTipo_servicio(
                            cursor.getInt(ScriptDataBase.Servicio.TIPO_SERVICIO_INDEX)
                    );
                    servicio.setId_servidor(
                            cursor.getInt(ScriptDataBase.Servicio.ID_SERVIDOR_INDEX)
                    );
                    servicio.setId(
                            cursor.getInt(ScriptDataBase.Servicio.ID_INDEX)
                    );
                    servicio.setPath_pdf_1(
                            cursor.getString(ScriptDataBase.Servicio.PATH_PDF_1_INDEX)
                    );
                    servicio.setPath_pdf_2(
                            cursor.getString(ScriptDataBase.Servicio.PATH_PDF_2_INDEX)
                    );
                    servicio.setPath_pdf_3(
                            cursor.getString(ScriptDataBase.Servicio.PATH_PDF_3_INDEX)
                    );
                    servicio.setPrinted(
                            cursor.getInt(ScriptDataBase.Servicio.PRINTED_INDEX)
                    );
                    servicio.setObservaciones_finales(
                            cursor.getString(ScriptDataBase.Servicio.OBSERVACIONES_FINALES_INDEX)
                    );
                    servicio.setSubido(
                            cursor.getInt(ScriptDataBase.Servicio.SUBIDO_INDEX)
                    );
                    servicio.setTerminado(
                            cursor.getInt(ScriptDataBase.Servicio.TERMINADO_INDEX)
                    );
                    servicio.setEquipo(
                            cursor.getString(ScriptDataBase.Servicio.EQUIPO_INDEX)
                    );
                    servicio.setMarca(
                            cursor.getString(ScriptDataBase.Servicio.MARCA_INDEX)
                    );
                    servicio.setModelo(
                            cursor.getString(ScriptDataBase.Servicio.MODELO_INDEX)
                    );

                    servicio.setSerie(
                            cursor.getString(ScriptDataBase.Servicio.SERIE_INDEX)
                    );
                    servicio.setReporte_subir(
                            cursor.getInt(ScriptDataBase.Servicio.REPORTE_SUBIR_INDEX)
                    );
                    servicio.setCabms(
                            cursor.getString(ScriptDataBase.Servicio.CAMBS_INDEX)
                    );
                    servicio.setEquipo_id(
                            cursor.getInt(ScriptDataBase.Servicio.EQUIPO_ID_INDEX)
                    );
                    servicio.setGasto(
                            cursor.getString(ScriptDataBase.Servicio.CLIENTE_DATOS_INDEX)
                    );
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        return servicio;
    }

    public int GetOrdenSiguiente(int servicio_id){
        Cursor cursor = scvmDatabase.getWritableDatabase()
                .rawQuery("select MAX(_id) AS orden from servicio_detalle where servicio_id =  "+ servicio_id ,
                        null);
        int orden_max  = 0;
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    orden_max = cursor.getInt(0);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        return orden_max;
    }


    public ArrayList<ServiceRequest.Fotografia> GetDetalles(int servicio_id){
        Cursor cursor = scvmDatabase.getWritableDatabase()
                .rawQuery("select * from servicio_detalle where servicio_id =  "+ servicio_id+" order by orden ASC" ,
                        null);
        ServiceRequest.Fotografia fotografia = null;
        ArrayList<ServiceRequest.Fotografia> fotografias_array_list = new ArrayList<>();
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    fotografia = new ServiceRequest.Fotografia();
                    fotografia.setRuta_fotografia(
                            cursor.getString(ScriptDataBase.ServicioDetalle.FOTOGRAFIA_LOCAL_INDEX)
                    );
                    fotografia.setServicio_detalle_id(
                            cursor.getInt(ScriptDataBase.ServicioDetalle.ID_INDEX)
                    );
                    fotografia.setRuta_servidor(
                            cursor.getString(ScriptDataBase.ServicioDetalle.FOTOGRAFIA_SERVIDOR_INDEX)
                    );
                    fotografia.setNombre_fotografia(
                            cursor.getString(ScriptDataBase.ServicioDetalle.FOTOGRAFIA_INDEX)
                    );

                    fotografia.setServicio_id(
                            cursor.getInt(ScriptDataBase.ServicioDetalle.SERVICIO_ID_INDEX)
                    );

                    fotografia.setOrden(
                            cursor.getInt(ScriptDataBase.ServicioDetalle.ORDEN_INDEX)
                    );
                    fotografia.setTipo_fotografia(
                            cursor.getInt(ScriptDataBase.ServicioDetalle.TIPO_INDEX)
                    );

                    fotografia.setPdf_completo(
                            cursor.getString(ScriptDataBase.ServicioDetalle.PDF_COMPLETO_INDEX)
                    );

                    fotografia.setPdf_parcial(
                            cursor.getString(ScriptDataBase.ServicioDetalle.PDF_PARCIAL_INDEX)
                    );

                    fotografia.setCheck(
                            cursor.getInt(ScriptDataBase.ServicioDetalle.CHECK_INDEX)
                    );
                    fotografia.setRuta_calidad(
                            cursor.getString(ScriptDataBase.ServicioDetalle.CALIDAD_PATH_INDEX)
                    );
                    fotografia.setRotacion(
                            cursor.getFloat(ScriptDataBase.ServicioDetalle.ROTACION_INDEX)
                    );
                    fotografias_array_list.add(fotografia);
                }while(cursor.moveToNext());
            }
        }
        cursor.close();
        return fotografias_array_list;
    }

    public ArrayList<Equipo> GetEquipos(){
        try{
            Cursor cursor = scvmDatabase.getWritableDatabase()
                    .rawQuery("select * from " + ScriptDataBase.Equipo.TABLE_NAME,
                            null);
            Equipo equipo = null;
            ArrayList<Equipo> equipoArrayList = new ArrayList<>();
            if(cursor != null){
                if(cursor.moveToFirst()){
                    do{
                        equipo = new Equipo();
                        equipo.setEquipo(
                                cursor.getString(ScriptDataBase.Equipo.EQUIPO_INDEX)
                        );

                        equipo.setEquipo_id(
                                cursor.getInt(ScriptDataBase.Equipo.EQUIPO_ID_INDEX)
                        );

                        equipo.setMarca(
                                cursor.getString(ScriptDataBase.Equipo.MARCA_INDEX)
                        );
                        equipo.setMarca_id(
                                cursor.getInt(ScriptDataBase.Equipo.MARCA_ID_INDEX)
                        );
                        equipo.setModelo(
                                cursor.getString(ScriptDataBase.Equipo.MODELO_INDEX)
                        );
                        equipo.setModelo_id(
                                cursor.getInt(ScriptDataBase.Equipo.MODEL_ID_INDEX)
                        );

                        equipoArrayList.add(equipo);
                    }while(cursor.moveToNext());
                }
            }
            cursor.close();
            return equipoArrayList;
        }catch (Exception e){
            Log.println(Log.DEBUG,e.getMessage(),e.getMessage());
            return null;
        }
    }

    public int InsertarEquipo(Equipo equipo){

        ContentValues values = new ContentValues();
        values.put(ScriptDataBase.Equipo.EQUIPO, equipo.getEquipo());
        values.put(ScriptDataBase.Equipo.EQUIPO_ID, equipo.getEquipo_id());
        values.put(ScriptDataBase.Equipo.MARCA, equipo.getMarca());
        values.put(ScriptDataBase.Equipo.MARCA_ID, equipo.getMarca_id());
        values.put(ScriptDataBase.Equipo.MODELO, equipo.getModelo());
        values.put(ScriptDataBase.Equipo.MODELO_ID, equipo.getModelo_id());

        return (int)scvmDatabase.getWritableDatabase().insert(ScriptDataBase.Equipo.TABLE_NAME, null, values);
    }

    public int InsertarEmpleado(Empleado empleado){

        ContentValues values = new ContentValues();
        values.put(ScriptDataBase.Empleado.NOMBRE, empleado.getNombre());
        values.put(ScriptDataBase.Empleado.USER_ID, empleado.getUser_id());
        values.put(ScriptDataBase.Empleado.EMPLEADO_ID, empleado.getEmpleado_id());
        return (int)scvmDatabase.getWritableDatabase().insert(ScriptDataBase.Empleado.TABLE_NAME, null, values);
    }


}
