package com.intersem.sdib.core.database;

import android.provider.BaseColumns;

public class ScriptDataBase {
    public static final String STRING_TYPE = "TEXT";
    public static final String DATE_TYPE = "DATE";
    public static final String INT_TYPE = "INTEGER";
    public static final String BLOB_TYPE = "BLOB";
    public static final String TEXT_TYPE = "TEXT";    
    public static final String DECIMAL_TYPE = "DECIMAL";
    public static final String FLOAT_TYPE = "FLOAT";
    public static final String BOOLEAN_TYPE = "BOOLEAN";

    public static class User {
        public static final String TABLE_NAME = "users";

        public static final String ID = BaseColumns._ID;
        public static final String USER = "user";
        public static final String TOKEN = "token";
        public static final String PROFILE = "profile";
        public static final String COMPANY = "company";
        public static final String DATE_CREATE = "date_create";
        public static final String USER_ID = "user_id_servidor";

        public static final int ID_INDEX = 0;
        public static final int USER_INDEX = 1;
        public static final int TOKEN_INDEX = 2;
        public static final int PROFILE_INDEX = 3;
        public static final int COMPANY_INDEX = 4;
        public static final int DATE_CREATE_INDEX = 5;
        public static final int USER_ID_INDEX = 6;
    }


    public static class Servicio{
        public static final String TABLE_NAME = "servicios";
        public static final String TABLE_NAME_AGENDA = "servicios_agenda";
        public static final String ID = BaseColumns._ID;
        public static final String NO_REPORTE = "no_reporte";
        public static final String TIPO_SERVICIO = "tipo_servicio";
        public static final String ID_SERVIDOR = "id_servidor";
        public static final String PATH_PDF_1 = "path_pdf_1";
        public static final String PATH_PDF_2 = "path_pdf_2";
        public static final String PATH_PDF_3 = "path_pdf_3";
        public static final String PRINTED = "printed";
        public static final String OBSERVACIONES_FINALES = "observaciones_finales";
        public static final String SUBIDO = "subido";
        public static final String TERMINADO = "terminado";
        public static final String MODELO = "modelo";
        public static final String EQUIPO = "equipo";
        public static final String MARCA = "marca";
        public static final String SERIE = "serie";
        public static final String REPORTE_SUBIR = "reporte_subir_id";
        public static final String CAMBS = "cambs";
        public static final String EQUIPO_ID = "equipo_id";
        public static final String GASTO = "gasto_servicio";
        //Datos solamnete de la tabla de servicios
        public static final String CLIENTE_DATOS = "cliente_datos";
        public static final String FECHA_SERVICIO = "fecha_servicio";


        public static final int ID_INDEX = 0;
        public static final int NO_REPORTE_INDEX = 1;
        public static final int TIPO_SERVICIO_INDEX = 2;
        public static final int ID_SERVIDOR_INDEX = 3;
        public static final int PATH_PDF_1_INDEX = 4;
        public static final int PATH_PDF_2_INDEX = 5;
        public static final int PATH_PDF_3_INDEX = 6;
        public static final int PRINTED_INDEX = 7;
        public static final int OBSERVACIONES_FINALES_INDEX = 8;
        public static final int SUBIDO_INDEX = 9;
        public static final int TERMINADO_INDEX = 10;
        public static final int MODELO_INDEX = 11;
        public static final int EQUIPO_INDEX = 12;
        public static final int MARCA_INDEX = 13;
        public static final int SERIE_INDEX = 14;
        public static final int REPORTE_SUBIR_INDEX = 15;
        public static final int CAMBS_INDEX = 16;
        public static final int EQUIPO_ID_INDEX = 17;
        //Datos solamnete de la tabla de servicios
        public static final int CLIENTE_DATOS_INDEX = 18;
        public static final int FECHA_SERVICIO_INDEX = 19;
    }

    public static class Observaciones{
        public static final String TABLE_NAME = "observaciones";
        public static final String ID = BaseColumns._ID;
        public static final String OBSERVACION =  "observacion";
        public static final String ID_TIPO_OBSERVACION = "id_tipo_observacion";
    }

    public static class Empleado{
        public static final String TABLE_NAME = "empleados";
        public static final String ID = BaseColumns._ID;
        public static final String NOMBRE =  "nombre";
        public static final String FOTO_GAFETE =  "FotoGafete";
        public static final String USER_ID =  "user_id";
        public static final String EMPLEADO_ID =  "empleado_id";
    }

    public static class Equipo{
        public static final String TABLE_NAME = "equipos";
        public static final String ID = BaseColumns._ID;
        public static final String EQUIPO = "equipo";
        public static final String EQUIPO_ID = "equipo_id";
        public static final String MODELO  = "modelo";
        public static final String MODELO_ID = "modelo_id";
        public static final String MARCA = "marca";
        public static final String MARCA_ID = "marca_id";

        public static final int ID_INDEX = 0;
        public static final int EQUIPO_INDEX = 1;
        public static final int EQUIPO_ID_INDEX = 2;
        public static final int MODELO_INDEX  = 3;
        public static final int MODEL_ID_INDEX = 4;
        public static final int MARCA_INDEX = 5;
        public static final int MARCA_ID_INDEX = 6;
    }

    public static class Nota{
        public static final String TABLE_NAME = "notas";
        public static final String ID = BaseColumns._ID;
        public static final String NOTA = "nota";
        public static final String FECHA = "fecha";
        public static final String CLIENTE  = "cliente";

        public static final int ID_INDEX = 0;
        public static final int NOTA_INDEX = 1;
        public static final int FECHA_INDEX = 2;
        public static final int CLIENTE_INDEX  = 3;
    }

    public static class ArchivosAuxiliares{
        public static final String TABLE_NAME = "archivos_auxiliares";
        public static final String ID = BaseColumns._ID;
        public static final String RUTA_ARCHIVO =  "ruta_archivo";
        public static final String NOMBRE_ARCHIVO = "nombre_archvio";
        public static final String TIPO = "tipo_archivo";

        public static final int ID_INDEX = 0;
        public static final int RUTA_ARCHIVO_INDEX = 1;
        public static final int NOMBRE_ARCHIVO_INDEX = 2;
        public static final int TIPO_INDEX = 3;

    }

    public static class ServicioDetalle{
        public static final String TABLE_NAME = "servicio_detalle";

        public static final String ID = BaseColumns._ID;
        public static final String FOTOGRAFIA_LOCAL = "fotografia_local";
        public static final String FOTOGRAFIA_SERVIDOR = "fotografia_servidor";
        public static final String SERVICIO_ID = "servicio_id";
        public static final String TIPO = "tipo";
        public static final String FOTOGRAFIA = "fotografia";
        public static final String PDF_PARCIAL = "pdf_parcial";
        public static final String PDF_COMPLETO = "pdf_completo";
        public static final String ORDEN = "orden";
        public static final String CHECK = "verificado";
        public static final String CALIDAD_PATH = "calidad_path";
        public static final String ROTACION = "rotacion";


        public static final int ID_INDEX = 0;
        public static final int FOTOGRAFIA_LOCAL_INDEX = 1;
        public static final int FOTOGRAFIA_SERVIDOR_INDEX = 2;
        public static final int SERVICIO_ID_INDEX = 3;
        public static final int TIPO_INDEX = 4;
        public static final int FOTOGRAFIA_INDEX = 5;
        public static final int PDF_PARCIAL_INDEX= 6;
        public static final int PDF_COMPLETO_INDEX = 7;
        public static final int ORDEN_INDEX = 8;
        public static final int CHECK_INDEX = 9;
        public static final int CALIDAD_PATH_INDEX = 10;
        public static final int ROTACION_INDEX = 11;
    }

    public static String CREATE_USER(){
        return "CREATE TABLE " + User.TABLE_NAME + "(" +
                User.ID + " " + INT_TYPE + " primary key autoincrement," +
                User.USER + " " + STRING_TYPE + "," +
                User.TOKEN + " " + STRING_TYPE +"," +
                User.PROFILE + " " + STRING_TYPE + "," +
                User.COMPANY + " " + STRING_TYPE + "," +
                User.DATE_CREATE + " " + DATE_TYPE + "," +
                User.USER_ID + " " + INT_TYPE +")" ;
    }

    public static String CREATE_ARCHIVO_AUXILIAR(){
        return "CREATE TABLE " + ArchivosAuxiliares.TABLE_NAME + "(" +
                ArchivosAuxiliares.ID + " " + INT_TYPE + " primary key autoincrement," +
                ArchivosAuxiliares.RUTA_ARCHIVO + " " + STRING_TYPE + "," +
                ArchivosAuxiliares.NOMBRE_ARCHIVO + " " + STRING_TYPE +"," +
                ArchivosAuxiliares.TIPO + " " + STRING_TYPE +")" ;
    }

    public static String CREATE_NOTA(){
        return "CREATE TABLE " + Nota.TABLE_NAME + "(" +
                Nota.ID + " " + INT_TYPE + " primary key autoincrement," +
                Nota.NOTA + " " + STRING_TYPE + "," +
                Nota.FECHA + " " + STRING_TYPE +"," +
                Nota.CLIENTE + " " + STRING_TYPE +")" ;
    }

    public static String CREATE_SERVICIO(){
        return "CREATE TABLE " + Servicio.TABLE_NAME + "(" +
                Servicio.ID + " " + INT_TYPE + " primary key autoincrement," +
                Servicio.NO_REPORTE + " " + STRING_TYPE + "," +
                Servicio.TIPO_SERVICIO + " " + INT_TYPE + "," +
                Servicio.ID_SERVIDOR + " " + INT_TYPE + "," +
                Servicio.PATH_PDF_1 + " " + STRING_TYPE + "," +
                Servicio.PATH_PDF_2 + " " + STRING_TYPE + "," +
                Servicio.PATH_PDF_3 + " " + STRING_TYPE + "," +
                Servicio.PRINTED + " " + INT_TYPE + "," +
                Servicio.OBSERVACIONES_FINALES + " " + STRING_TYPE + "," +
                Servicio.SUBIDO + " " + INT_TYPE + "," +
                Servicio.TERMINADO + " " + INT_TYPE + "," +
                Servicio.MODELO + " " + STRING_TYPE + "," +
                Servicio.EQUIPO + " " + STRING_TYPE + "," +
                Servicio.MARCA + " " + STRING_TYPE + "," +
                Servicio.SERIE + " " + STRING_TYPE + "," +
                Servicio.REPORTE_SUBIR + " " + INT_TYPE + "," +
                Servicio.CAMBS + " " + STRING_TYPE + "," +
                Servicio.EQUIPO_ID + " " + INT_TYPE + "," +
                Servicio.GASTO + " " + STRING_TYPE +")" ;
    }

    public static String CREATE_DETALLE(){
        return "CREATE TABLE " + ServicioDetalle.TABLE_NAME + "(" +
                ServicioDetalle.ID + " " + INT_TYPE + " primary key autoincrement," +
                ServicioDetalle.FOTOGRAFIA_LOCAL + " " + STRING_TYPE + "," +
                ServicioDetalle.FOTOGRAFIA_SERVIDOR + " " + STRING_TYPE + "," +
                ServicioDetalle.SERVICIO_ID + " " + INT_TYPE + "," +
                ServicioDetalle.TIPO + " " + INT_TYPE + "," +
                ServicioDetalle.FOTOGRAFIA + " " + STRING_TYPE + "," +
                ServicioDetalle.PDF_PARCIAL + " " + STRING_TYPE + "," +
                ServicioDetalle.PDF_COMPLETO + " " + STRING_TYPE + "," +
                ServicioDetalle.ORDEN + " " + INT_TYPE + "," +
                ServicioDetalle.CHECK + " " + INT_TYPE + "," +
                ServicioDetalle.CALIDAD_PATH + " " + STRING_TYPE + "," +
                ServicioDetalle.ROTACION + " " + INT_TYPE + ")" ;
    }

    public static String CREATE_SERVICIO_AGENDA(){
        return "CREATE TABLE " + Servicio.TABLE_NAME_AGENDA + "(" +
                Servicio.ID + " " + INT_TYPE + " primary key autoincrement," +
                Servicio.NO_REPORTE + " " + STRING_TYPE + "," +
                Servicio.TIPO_SERVICIO + " " + INT_TYPE + "," +
                Servicio.ID_SERVIDOR + " " + INT_TYPE + "," +
                Servicio.PATH_PDF_1 + " " + STRING_TYPE + "," +
                Servicio.PATH_PDF_2 + " " + STRING_TYPE + "," +
                Servicio.PATH_PDF_3 + " " + STRING_TYPE + "," +
                Servicio.PRINTED + " " + INT_TYPE + "," +
                Servicio.OBSERVACIONES_FINALES + " " + STRING_TYPE + "," +
                Servicio.SUBIDO + " " + INT_TYPE + "," +
                Servicio.TERMINADO + " " + INT_TYPE + "," +
                Servicio.MODELO + " " + STRING_TYPE + "," +
                Servicio.EQUIPO + " " + STRING_TYPE + "," +
                Servicio.MARCA + " " + STRING_TYPE + "," +
                Servicio.SERIE + " " + STRING_TYPE + "," +
                Servicio.REPORTE_SUBIR + " " + INT_TYPE + "," +
                Servicio.CAMBS + " " + STRING_TYPE + "," +
                Servicio.EQUIPO_ID + " " + INT_TYPE + "," +
                Servicio.CLIENTE_DATOS + " " + STRING_TYPE + "," +
                Servicio.FECHA_SERVICIO + " " + STRING_TYPE +")" ;
    }

    public static String CREATE_EQUIPO(){
        return "CREATE TABLE " + Equipo.TABLE_NAME + "(" +
                Equipo.ID + " " + INT_TYPE + " primary key autoincrement," +
                Equipo.EQUIPO + " " + STRING_TYPE + " ," +
                Equipo.EQUIPO_ID + " " + INT_TYPE + " ," +
                Equipo.MODELO + " " + STRING_TYPE + " ," +
                Equipo.MODELO_ID + " " + INT_TYPE + " ," +
                Equipo.MARCA + " " + STRING_TYPE + " ," +
                Equipo.MARCA_ID + " " + INT_TYPE + ")" ;
    }

    public static String CREATE_EMPLEADO(){
        return "CREATE TABLE " + Empleado.TABLE_NAME + "(" +
                Empleado.ID + " " + INT_TYPE + " primary key autoincrement," +
                Empleado.NOMBRE + " " + STRING_TYPE + " ," +
                Empleado.USER_ID + " " + INT_TYPE + " ," +
                Empleado.EMPLEADO_ID + " " + INT_TYPE + " )";

    }
}
