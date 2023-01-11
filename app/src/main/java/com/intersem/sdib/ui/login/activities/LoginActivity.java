package com.intersem.sdib.ui.login.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import com.intersem.sdib.R;
import com.intersem.sdib.core.services.ApiBodyService;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.core.services.SharedPreferencesService;
import com.intersem.sdib.ui.login.models.ResponseLoginModel;
import com.intersem.sdib.ui.principal.activities.ActivityMain;
import com.intersem.sdib.core.utilities.Constants;
import com.intersem.sdib.core.utilities.Network;
import com.intersem.sdib.core.utilities.UI;

public class LoginActivity extends AppCompatActivity {
    private EditText txtUsuario, txtPassword;
    private Button btnIniciar , btnOlvidarContrasena;
    private ProgressDialog progressDialog;
    private TextView txtDevelopmentCompanySession;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        view = findViewById(android.R.id.content);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtPassword = findViewById(R.id.txtPassword);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnOlvidarContrasena = findViewById(R.id.btnOlvidar);
        txtDevelopmentCompanySession = findViewById(R.id.txtDevelopmentCompanySession);

        btnIniciar.setOnClickListener(addClickListenerBtnIniciar());
        btnOlvidarContrasena.setOnClickListener(addClickListenerBtnOlvidar());
        txtDevelopmentCompanySession.setOnClickListener(addClickListenerServidor());
    }

    private View.OnClickListener addClickListenerBtnOlvidar() {
        return  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Comunicate con el administrador del sistema para solicitar un cambio", Snackbar.LENGTH_SHORT).show();
            }
        };
    }

    private View.OnClickListener addClickListenerBtnIniciar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Validamos datos
                if(txtUsuario.getText().toString().trim().length() > 0){
                    if(txtPassword.getText().toString().trim().length() > 0){
                        if(Network.VerifityConnection(getApplicationContext())){
                            iniciarSesion(txtUsuario.getText().toString(),txtPassword.getText().toString());
                        }else{
                            Snackbar.make(view, getString(R.string.meesage_internet), Snackbar.LENGTH_SHORT).show();
                        }
                    }else{
                        Snackbar.make(view,getString(R.string.message_login_datos), Snackbar.LENGTH_SHORT).show();
                    }
                }else{
                    Snackbar.make(view,getString(R.string.message_login_datos), Snackbar.LENGTH_SHORT).show();
                }
            }
        };
    }

    private View.OnClickListener addClickListenerServidor() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,Constants.url_servidor_ftp, Snackbar.LENGTH_SHORT).show();
            }
        };
    }

    private void iniciarSesion(String toString, String toString1) {

        //Oculatmos el teclado
        UI.hideSoftKeyboard(this);
        //Creamos el dialog
        progressDialog = UI.createProgressDialog(LoginActivity.this,"Buscando Usuario","Cargando.....");
        progressDialog.show();
        //Creamos json que se enviara
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", txtUsuario.getText().toString());
            jsonBody.put("password", txtPassword.getText().toString());

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            ApiBodyService apiBodyService =new ApiBodyService(Request.Method.POST, Constants.URL_API_LOGIN,jsonBody.toString(),this.RequestSuccessListener(),this.RequestErrorListener());
            requestQueue.add(apiBodyService);
        } catch (JSONException e) {
            progressDialog.dismiss();
            Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    private Response.ErrorListener RequestErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Snackbar.make(view, error.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        };
    }

    private Response.Listener<JSONObject> RequestSuccessListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();
                try {
                    //Parceamos la respuesta del servidor a un modelo para su manipulacion
                    ResponseLoginModel responseLoginModel = new Gson().fromJson(response.toString(),ResponseLoginModel.class);
                    //Revisamos respuesta
                    switch (responseLoginModel.getResponse_flag()){
                        case 1 :
                            //Si la respuesta fue correcta gaurdamos el token en sqllite
                            //Inicializamos el serivicos
                            DataBaseService dataBaseService = new DataBaseService(getApplicationContext());
                            //Insertamos los datos que mandas en el serviicos
                            dataBaseService.InsertUsuario(responseLoginModel.getResponse());

                            ResponseLoginModel.Response user_save = dataBaseService.GetUsuarios();
                            SharedPreferencesService sharedPreferencesService = new SharedPreferencesService(getApplicationContext());
                            sharedPreferencesService.addCredentials("",user_save.getUser(),user_save.getToken());
                            Intent intent = new Intent(getApplicationContext(), ActivityMain.class);
                            startActivity(intent);
                            finish();

                            break;
                        case 2 :
                            Snackbar.make(view, "Ocurrio un error en el servidor", Snackbar.LENGTH_LONG).show();
                            break;
                        case 3 :
                            Snackbar.make(view, "Usuario o Contraseña incorrectos", Snackbar.LENGTH_LONG).show();
                            break;
                    }
                }catch (Exception e){
                    progressDialog.dismiss();
                    Snackbar.make(view, "Usuario o Contraseña incorrectos", Snackbar.LENGTH_LONG).show();
                }
            }
        };
    }
}
