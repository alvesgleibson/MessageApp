package com.alvesgleibson.messageappfamily.helper;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MyPermission {

    public static Boolean validarPermissao(String[] permissaoParametro, Activity activityParametro, int requestCode){

        if (Build.VERSION.SDK_INT >= 23){

            List<String> listaPermisao = new ArrayList<>();

            for (String permissaoDoFor: permissaoParametro){
               Boolean temPermisssao = ContextCompat.checkSelfPermission(activityParametro, permissaoDoFor) == PackageManager.PERMISSION_GRANTED;

               if (!temPermisssao) listaPermisao.add( permissaoDoFor );
            }

            if (listaPermisao.isEmpty()) return true;

            String[] permissaoNaoConcedida = new String[ listaPermisao.size() ];
            listaPermisao.toArray( permissaoNaoConcedida );

            ActivityCompat.requestPermissions( activityParametro, permissaoNaoConcedida, requestCode);


        }


        return true;
    }


}
