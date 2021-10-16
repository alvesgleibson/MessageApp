package com.alvesgleibson.messageappfamily.helper;

import android.net.Uri;
import android.util.Log;

import com.alvesgleibson.messageappfamily.setting.SettingInstanceFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UsuarioFirebase {
    private static FirebaseAuth auth = SettingInstanceFirebase.getInstanceFirebaseAuth();

    public static String getIdentificadorUsuario(){
       return Base64Costum.encodeBase64( auth .getCurrentUser().getEmail() );
    }

    public static FirebaseUser getUsuarioAtual(){
        return auth.getCurrentUser();
    }

    public static boolean atualizarFotoUsuarioMetodoClass(Uri url){
        try {
            FirebaseUser user = getUsuarioAtual();
            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setPhotoUri( url ).build();

            user.updateProfile(userProfileChangeRequest).addOnCompleteListener(task -> {
                if ( !task.isSuccessful()){
                    Log.d("Perfil", "Erro ao atualizar foto");
                }
            });
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }



}
