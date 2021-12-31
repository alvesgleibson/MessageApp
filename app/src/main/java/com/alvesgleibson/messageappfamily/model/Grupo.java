package com.alvesgleibson.messageappfamily.model;

import com.alvesgleibson.messageappfamily.helper.Base64Costum;
import com.alvesgleibson.messageappfamily.setting.SettingInstanceFirebase;
import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.List;

public class Grupo implements Serializable {

    private String idGrupo, nomeGrupo, fotoPerfilGrupo;
    private List<Usuario> membrosGrupo;

    public Grupo() {

        DatabaseReference referenceDataBase = SettingInstanceFirebase.getDatabaseReference();
        setIdGrupo( referenceDataBase.push().getKey() );

    }

    public void salvarGrupo() {

        DatabaseReference referenceDataBase = SettingInstanceFirebase.getDatabaseReference();
        DatabaseReference databaseRefGrupo =  referenceDataBase.child("grupos");

        databaseRefGrupo.child( getIdGrupo() ).setValue( this );

        for ( Usuario usuario: getMembrosGrupo() ){

            Conversa conversa = new Conversa();
            conversa.setIdUsuarioEnvio(Base64Costum.encodeBase64(usuario.getEmail()));
            conversa.setIdUsuarioRecebendo(getIdGrupo());
            conversa.setUltimaMensagem("");
            conversa.setIsGroup("true");
            conversa.setGrupo( this );

            conversa.salvarMensagemConversa();


        }


    }



    public String getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(String idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNomeGrupo() {
        return nomeGrupo;
    }

    public void setNomeGrupo(String nomeGrupo) {
        this.nomeGrupo = nomeGrupo;
    }

    public String getFotoPerfilGrupo() {
        return fotoPerfilGrupo;
    }

    public void setFotoPerfilGrupo(String fotoPerfilGrupo) {
        this.fotoPerfilGrupo = fotoPerfilGrupo;
    }

    public List<Usuario> getMembrosGrupo() {
        return membrosGrupo;
    }

    public void setMembrosGrupo(List<Usuario> membrosGrupo) {
        this.membrosGrupo = membrosGrupo;
    }


}
