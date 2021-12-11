package com.alvesgleibson.messageappfamily.model;

import com.alvesgleibson.messageappfamily.setting.SettingInstanceFirebase;
import com.google.firebase.database.DatabaseReference;

public class Conversa {

    private String mensagem, idUsuarioEnvio, idUsuarioRecebendo, ultimaMensagem;


    public Conversa() {
    }

    public void salvarMensagemConversa( String msg ){

        DatabaseReference databaseReference = SettingInstanceFirebase.getDatabaseReference();
        //Copia Usuario Enviando
        databaseReference.child("conversas").child(getIdUsuarioEnvio()).child(getIdUsuarioRecebendo()).push().setValue(msg);

        //Copia Usuario Recebendo
        databaseReference.child("conversas").child(getIdUsuarioRecebendo()).child(getIdUsuarioEnvio()).push().setValue(msg);

    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getIdUsuarioEnvio() {
        return idUsuarioEnvio;
    }

    public void setIdUsuarioEnvio(String idUsuarioEnvio) {
        this.idUsuarioEnvio = idUsuarioEnvio;
    }

    public String getIdUsuarioRecebendo() {
        return idUsuarioRecebendo;
    }

    public void setIdUsuarioRecebendo(String idUsuarioRecebendo) {
        this.idUsuarioRecebendo = idUsuarioRecebendo;
    }

    public String getUltimaMensagem() {
        return ultimaMensagem;
    }

    public void setUltimaMensagem(String ultimaMensagem) {
        this.ultimaMensagem = ultimaMensagem;
    }

}
