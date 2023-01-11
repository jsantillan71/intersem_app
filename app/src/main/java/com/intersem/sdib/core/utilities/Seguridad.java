package com.intersem.sdib.core.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.intersem.sdib.core.database.ScriptDataBase;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.core.services.SharedPreferencesService;
import com.intersem.sdib.ui.login.activities.LoginActivity;

public class Seguridad {
    public static void closeSession(final Context context){
        UI.createDialog(context,"Cerrar Sesión", "¿Quieres cerrar sesión?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new DataBaseService(context).cleanTable(ScriptDataBase.User.TABLE_NAME, null, null);
                new SharedPreferencesService(context).addCredentials("", "", "");

                context.startActivity(new Intent(context, LoginActivity.class));
                ((Activity) context).finish();
            }
        });
    }
}
