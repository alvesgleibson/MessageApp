package com.alvesgleibson.messageappfamily.helper;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.alvesgleibson.messageappfamily.activity.ConfiguracoesActivity;
import com.alvesgleibson.messageappfamily.model.Usuario;
import com.alvesgleibson.messageappfamily.setting.SettingInstanceFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UsuarioFirebase {
    private static FirebaseAuth auth = SettingInstanceFirebase.getInstanceFirebaseAuth();

    public static String getIdentificadorUsuario(){
       return Base64Costum.encodeBase64( auth.getCurrentUser().getEmail() );
    }

    public static FirebaseUser getUsuarioAtual(){
        return auth.getCurrentUser();
    }

    //Atualizar foto do perfil no FirebaseUser
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

    //Atualizar Nome do perfil no FirebaseUser
    public static boolean atualizarNomePerfilUsuario(String nomePerfil){

        try {
            FirebaseUser user = getUsuarioAtual();
            UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName( nomePerfil ).build();
            user.updateProfile( userProfileChangeRequest ).addOnCompleteListener( task -> {
                if (!task.isSuccessful()){
                    Log.d("Perfil", "Erro ao atualizar Nome Perfil");
                }
            });
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

    }

    public static Usuario getDadosUsuarioLogado(){
        FirebaseUser firebaseUser = getUsuarioAtual();

        Usuario usuarioLogado = new Usuario();

        usuarioLogado.setName( firebaseUser.getDisplayName() );
        usuarioLogado.setEmail( firebaseUser.getEmail() );

        if (firebaseUser.getPhotoUrl() != null){
            usuarioLogado.setFoto( firebaseUser.getPhotoUrl().toString() );
        }

        return usuarioLogado;
    }



}
