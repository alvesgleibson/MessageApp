package com.alvesgleibson.messageappfamily.helper;

import com.alvesgleibson.messageappfamily.setting.SettingInstanceFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class UsuarioFirebase {


    public static String getIdentificadorUsuario(){
       FirebaseAuth auth = SettingInstanceFirebase.getInstanceFirebaseAuth();
       return Base64Costum.encodeBase64( auth .getCurrentUser().getEmail() );
    }


}
