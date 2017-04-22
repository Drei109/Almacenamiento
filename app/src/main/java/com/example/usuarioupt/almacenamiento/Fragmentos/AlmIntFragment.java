package com.example.usuarioupt.almacenamiento.Fragmentos;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.usuarioupt.almacenamiento.R;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by USUARIOUPT on 22-04-2017.
 */

public class AlmIntFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.alm_interno,container,false);
    }

    @Override
    public void onResume(){
        super.onResume();
        Button btn_save = (Button)getActivity().findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt_namefile = (EditText)getActivity().findViewById(R.id.edt_namefile);
                final String fileName = txt_namefile.getText().toString();

                EditText text_content = (EditText)getActivity().findViewById(R.id.edt_texto);
                final String textContent = text_content.getText().toString();

                if (fileName.isEmpty()){
                    Snackbar.make(getView(),"Seleccione un Fichero",Snackbar.LENGTH_SHORT).show();
                }else{
                    if(textContent.isEmpty()){
                        Snackbar.make(getView(),"Ingrese contenido",Snackbar.LENGTH_SHORT).show();
                    }else{
                        FileOutputStream outputStream;
                        try{
                            outputStream = getContext().openFileOutput(fileName,0);
                            outputStream.write(textContent.getBytes());
                            outputStream.close();
                            Toast.makeText(getContext(), "!!!",Toast.LENGTH_SHORT).show();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        Button btn_load = (Button)getActivity().findViewById(R.id.btn_load);
        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText txt_namefile = (EditText)getActivity().findViewById(R.id.edt_namefile);
                final String fileName = txt_namefile.getText().toString();

                EditText text_content = (EditText)getActivity().findViewById(R.id.edt_texto);
                final String textContent = text_content.getText().toString();

                if (fileName.isEmpty()){
                    Snackbar.make(getView(),"Ingrese contenido",Snackbar.LENGTH_SHORT).show();
                }
                else{
                    try{
                        FileInputStream fileInputStream = getContext().openFileInput(fileName);
                        int c = 0;
                        String temp = "";

                        while((c=fileInputStream.read())!= -1) {
                            temp = temp + Character.toString((char)c);
                        }
                        ((EditText)getActivity().findViewById(R.id.edt_texto)).setText(temp);
                        Toast.makeText(getContext(),"Fichero Cargado",Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
