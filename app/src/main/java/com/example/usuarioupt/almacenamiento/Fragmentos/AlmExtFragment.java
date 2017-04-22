package com.example.usuarioupt.almacenamiento.Fragmentos;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuarioupt.almacenamiento.R;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.Vector;

/**
 * Created by USUARIOUPT on 22-04-2017.
 */

public class AlmExtFragment extends Fragment {
    //Atributos para permisos
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    final String fichero = Environment.getExternalStorageDirectory() + "/Document.txt";
    Context context;

    public static void verifyStoragePermissions(Activity activity){
        int permission = ActivityCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(permission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        return inflater.inflate(R.layout.alm_externo,container,false);
    }

    public void guardarArchivo(String datos, Context context){
        String stadoSD = Environment.getExternalStorageState();
        if(!stadoSD.equals(Environment.MEDIA_MOUNTED)){
            Snackbar.make(getView(),"No es posible escribir en la memoria externa",Snackbar.LENGTH_LONG).show();
            return;
        }
        try {
            verifyStoragePermissions(getActivity());
            FileOutputStream fileOutputStream = new FileOutputStream(fichero,true);
            String texto = datos + "\n";
            fileOutputStream.write(texto.getBytes());

        }catch (Exception e){
            Log.e("App Ficheros",e.getMessage(),e);
        }
    }

    public Vector<String> obtenerData(Context context){
        Vector<String> result = new Vector<>();
        String stadoSD = Environment.getExternalStorageState();
        if(!stadoSD.equals(Environment.MEDIA_MOUNTED) && !stadoSD.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
            Toast.makeText(context,"No se puede leer la memoria externa",Toast.LENGTH_LONG).show();
            return result;
        }
        try{
            verifyStoragePermissions(getActivity());
            FileInputStream f = new FileInputStream(fichero);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(f));
            String linea;
            do{
                linea = entrada.readLine();
                if(linea != null){
                    result.add(linea);
                }
            }while(linea != null);
            f.close();
        } catch (Exception e){
            Log.e("App Ficheros",e.getMessage(),e);
        }
        return result;
    }

    public void actualizarEtiqueta(){
        context = getContext();
        TextView archivo = (TextView)getActivity().findViewById(R.id.txt_view);
        Vector<String> data = obtenerData(context);
        archivo.setText(data.toString());
    }

    @Override
    public void onResume(){
        super.onResume();
        actualizarEtiqueta();
        context = getContext();
        final EditText editar = (EditText)getActivity().findViewById(R.id.txt_editar);
        Button agregar = (Button)getActivity().findViewById(R.id.btn_agregar);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarArchivo(editar.getText().toString(),context);
                editar.setText("");
                actualizarEtiqueta();
            }
        });
    }
}
