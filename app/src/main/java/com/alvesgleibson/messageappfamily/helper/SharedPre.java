package com.alvesgleibson.messageappfamily.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPre {

    private Context context;
    private SharedPreferences.Editor editorLoginEmail, editorLoginSenha;
    private SharedPreferences spEmail, spSenha;
    private final String KEY_EMAIL = "email";
    private final String KEY_SENHA = "senha";


    private final String NAME_EMAIL = "save_email";
    private final String NAME_SENHA = "save_senha";


    public SharedPre(Context context){
        this.context = context;
        spEmail = context.getSharedPreferences(NAME_EMAIL,0);
        spSenha = context.getSharedPreferences(NAME_SENHA,0);
        editorLoginEmail = spEmail.edit();
        editorLoginSenha = spSenha.edit();

    }

    public void SaveSP(String saveEmail, String saveSenha){

        editorLoginEmail.putString(KEY_EMAIL, saveEmail).commit();
        editorLoginSenha.putString(KEY_SENHA, saveSenha).commit();

    }

    public void limparSPLogin(){
        editorLoginEmail.putString(KEY_EMAIL, null).commit();
        editorLoginSenha.putString(KEY_SENHA, null).commit();
    }

    public String[] recoverSP(){
        String emails = spEmail.getString(KEY_EMAIL,"");
        String senhas = spSenha.getString(KEY_SENHA,"");


        String[] s = {emails, senhas};
        return s;
    }


}
