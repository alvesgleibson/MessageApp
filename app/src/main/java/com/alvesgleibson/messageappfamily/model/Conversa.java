package com.alvesgleibson.messageappfamily.model;

import com.alvesgleibson.messageappfamily.setting.SettingInstanceFirebase;
import com.google.firebase.database.DatabaseReference;

public class Conversa {

    private String idUsuarioEnvio, idUsuarioRecebendo, ultimaMensagem, isGroup;
    private Usuario usuarioExibicao;
    private Grupo grupo;


    public Conversa() {
        this.setIsGroup("false");
    }

    public void salvarMensagemConversa(){

        DatabaseReference databaseReference = SettingInstanceFirebase.getDatabaseReference();
        DatabaseReference databaseRefeUsuario = databaseReference.child("conversas");



        //Copia Usuario Enviando
        databaseRefeUsuario.child(getIdUsuarioEnvio()).child(getIdUsuarioRecebendo()).setValue(this);


    }

    public String getIsGroup() {
        return isGroup;
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Usuario getUsuarioExibicao() {
        return usuarioExibicao;
    }

    public void setUsuarioExibicao(Usuario usuarioExibicao) {
        this.usuarioExibicao = usuarioExibicao;
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
