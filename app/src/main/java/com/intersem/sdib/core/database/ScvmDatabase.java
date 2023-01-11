package com.intersem.sdib.core.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class ScvmDatabase extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "scvm.db";

    public ScvmDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScriptDataBase.CREATE_USER());
        db.execSQL(ScriptDataBase.CREATE_SERVICIO());
        db.execSQL(ScriptDataBase.CREATE_DETALLE());
        db.execSQL(ScriptDataBase.CREATE_ARCHIVO_AUXILIAR());
        db.execSQL(ScriptDataBase.CREATE_EQUIPO());
        db.execSQL(ScriptDataBase.CREATE_SERVICIO_AGENDA());
        db.execSQL(ScriptDataBase.CREATE_NOTA());
        db.execSQL(ScriptDataBase.CREATE_EMPLEADO());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);
        onCreate(db);
    }

    public void dropTables(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + ScriptDataBase.User.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ScriptDataBase.Servicio.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ScriptDataBase.ServicioDetalle.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ScriptDataBase.ArchivosAuxiliares.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ScriptDataBase.Equipo.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ScriptDataBase.Empleado.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ScriptDataBase.Servicio.TABLE_NAME_AGENDA);
    }
}
