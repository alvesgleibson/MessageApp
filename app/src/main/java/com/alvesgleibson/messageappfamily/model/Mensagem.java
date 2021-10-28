package com.alvesgleibson.messageappfamily.model;

import com.alvesgleibson.messageappfamily.setting.SettingInstanceFirebase;
import com.google.firebase.database.DatabaseReference;

public class Mensagem {

    private String mensagem, idUsuarioMensagemEnviada, imageMensagem;

    public Mensagem() {
    }

    public void salvarMensagemTexto(String idUsuarioDestino){

        DatabaseReference databaseReference = SettingInstanceFirebase.getDatabaseReference();
        databaseReference.child("Mensagens").child(getIdUsuarioMensagemEnviada()).child(idUsuarioDestino).child("Mensagem").push().setValue(this);

    }

    public void salvarMensagemImagem(String idUsuarioDestino){

        DatabaseReference databaseReference = SettingInstanceFirebase.getDatabaseReference();
        databaseReference.child("Mensagens").child(idUsuarioMensagemEnviada).child(idUsuarioDestino).child("Imagem").setValue(idUsuarioMensagemEnviada, imageMensagem);

    }


    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getIdUsuarioMensagemEnviada() {
        return idUsuarioMensagemEnviada;
    }

    public void setIdUsuarioMensagemEnviada(String idUsuarioMensagemEnviada) {
        this.idUsuarioMensagemEnviada = idUsuarioMensagemEnviada;
    }

    public String getImageMensagem() {
        return imageMensagem;
    }

    public void setImageMensagem(String imageMensagem) {
        this.imageMensagem = imageMensagem;
    }
}
