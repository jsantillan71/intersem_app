package com.intersem.sdib.ui.services.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.intersem.sdib.R;
import com.intersem.sdib.core.services.DataBaseService;
import com.intersem.sdib.core.services.SharedPreferencesService;
import com.intersem.sdib.core.utilities.Constants;
import com.intersem.sdib.core.utilities.Empleado;
import com.intersem.sdib.ui.services.activities.AddServiceActivity;
import com.intersem.sdib.ui.services.adapters.ServiceAdapter;
import com.intersem.sdib.ui.services.dialogs.ViewImageDialog;
import com.intersem.sdib.ui.services.models.ServiceRequest;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.VerificationError;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ServiceGafetFragment extends Fragment  implements Step {
    private int option;
    private View view;
    private ImageButton btnTomarFoto;
    private final static int REQUEST_CAMERA = 1001;
    private String path;
    private String nombre;
    private File fileImage;
    private RecyclerView rcvFotoAntes;
    private DataBaseService dataBaseService;
    private ServiceAdapter serviceAdapter;
    private ServiceRequest.Fotografia fotografia;
    private ArrayList<ServiceRequest.Fotografia> arrayListFotografia;
    private GridLayoutManager gridLayoutManager;
    private int tipo_fotografia = 9;
    private Spinner cmbEmpleados;
    private static final String TAG = "MyActivity";


    public ServiceGafetFragment(int option) {
        this.option = option;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_service_before, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        iniciarComponentes();
        return view;
    }

    private void iniciarComponentes() {
        btnTomarFoto = view.findViewById(R.id.btnTomarFoto);
        rcvFotoAntes =  view.findViewById(R.id.rcvFotosBefore);
        cmbEmpleados = view.findViewById(R.id.cmbEmpleados);
        cmbEmpleados.setVisibility(View.VISIBLE);
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        rcvFotoAntes.setLayoutManager(gridLayoutManager);
        rcvFotoAntes.setHasFixedSize(true);
        btnTomarFoto.setOnClickListener(addBtnTomarFotografiaListener());
        dataBaseService = new DataBaseService(getContext());
        arrayListFotografia  = ((AddServiceActivity) getActivity()).consultarFotografiaGafete();
        serviceAdapter = new ServiceAdapter(getContext(),arrayListFotografia);
        serviceAdapter.setOnClickListener(addOnClickToList());
        rcvFotoAntes.setAdapter(serviceAdapter);
        CargarCmbEmpleados();
        serviceAdapter.notifyDataSetChanged();

        cmbEmpleados.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                Empleado mSelected = (Empleado) parentView.getItemAtPosition(position);
                int empleado_id = mSelected.getEmpleado_id();
                if (empleado_id==0){
                    return;
                }
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {

                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    String url = Constants.URL_API_EMPLEADO_GAFETE+String.valueOf(empleado_id);

                    Snackbar.make(view, "Descargando archivo", Snackbar.LENGTH_LONG).show();
                    boolean isFile = getGafeteFile(url, photoFile);

                    if(isFile){
                        fotografia = new ServiceRequest.Fotografia();
                        fotografia.setNombre_fotografia(photoFile.getName());
                        fotografia.setRuta_fotografia(photoFile.getAbsolutePath());
                        fotografia.setRuta_servidor(photoFile.getAbsolutePath());

                        arrayListFotografia = ((AddServiceActivity) getActivity()).agregarFotografiaGafete(fotografia);
                        serviceAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Log.d(TAG,"Not Selected");
            }

        });
    }



    private static boolean getGafeteFile(String url, File outputFile) {
        if (url == "https://www.intersem.com.mx/intersem/public/api/movil/empleado/get_badge/0"){
            return false;
        }
        boolean valid = true;
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
            fos.write(buffer);
            fos.flush();
            fos.close();
        } catch(FileNotFoundException e) {
            valid = false;
        } catch (NetworkOnMainThreadException e){
            valid = false;
        } catch (IOException e) {
            valid = false;
        }
        return valid;
    }



    private View.OnClickListener addUseGafeteListener() {
        return  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayListFotografia.size() <= 1){
                    tomarFotografia();
                }else{
                    Snackbar.make(view, getString(R.string.message_error_size_fotos), Snackbar.LENGTH_LONG).show();
                }
            }
        };
    }

    private void CargarCmbEmpleados(){
        ArrayList<Empleado> options  = new ArrayList<>();
        Empleado empleado = new Empleado();
        empleado.setEmpleado_id(0);
        empleado.setNombre("seleccione");
        options.add(empleado);
        ArrayList<Empleado> empleados  = dataBaseService.GetEmpleados();
        for (Empleado e: empleados) {
            options.add(e);
        }
        ArrayAdapter <Empleado> adapter = new ArrayAdapter<Empleado>( getContext(),android.R.layout.simple_dropdown_item_1line,options);
        cmbEmpleados.setAdapter(adapter);
    }


    private View.OnClickListener addOnClickToList() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    ServiceRequest.Fotografia fotografia = arrayListFotografia.get(rcvFotoAntes.getChildAdapterPosition(view));
                    ViewImageDialog dialog = new ViewImageDialog(fotografia,tipo_fotografia);
                    dialog.setCancelable(false);
                    dialog.show(getChildFragmentManager(),"Hola");
                }catch (Exception e){
                    Snackbar.make(view, e.getMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        };
    }

    private View.OnClickListener addBtnTomarFotografiaListener() {
        return  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayListFotografia.size() <= 1){
                    tomarFotografia();
                }else{
                    Snackbar.make(view, getString(R.string.message_error_size_fotos), Snackbar.LENGTH_LONG).show();
                }
            }
        };
    }

    private void tomarFotografia() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.intersem.sdib.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
            }
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        String path_intersem = Environment.DIRECTORY_PICTURES + File.separator +  ((AddServiceActivity)getActivity()).CARPETA_IMAGEN_GAFETE;
        File storageDir = getActivity().getExternalFilesDir(path_intersem);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        path = image.getAbsolutePath();
        return image;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK){
            try{
                /*
                //Creamos fotografia
                Bitmap fotografia_map =  (Bitmap) data.getExtras().get("data");
                File photoFile = createImageFile();
                FileOutputStream out = new FileOutputStream(photoFile);
                fotografia_map.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
                 */
                //Almacenamos la imagen en sqllite
                fotografia = new ServiceRequest.Fotografia();
                fotografia.setNombre_fotografia(nombre);
                fotografia.setRuta_fotografia(path);
                fotografia.setRuta_servidor(path);

                arrayListFotografia = ((AddServiceActivity) getActivity()).agregarFotografiaGafete(fotografia);
                serviceAdapter.notifyDataSetChanged();
            }catch (Exception e){
                Snackbar.make(view,getString(R.string.message_error), Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        /*
        if(arrayListFotografia.size() < 2){
            return new VerificationError("Se deben llenar dos fotografias como minimo");
        }else{

        }
         */
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }


    public void actualizarData(){
        serviceAdapter.notifyDataSetChanged();
    }
}