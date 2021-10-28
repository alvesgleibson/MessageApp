package com.alvesgleibson.messageappfamily.model;

import com.alvesgleibson.messageappfamily.helper.UsuarioFirebase;
import com.alvesgleibson.messageappfamily.setting.SettingInstanceFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Usuario implements Serializable {

    private String name, email, password, idCode, foto;

    public Usuario(){
    }

    public Usuario(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void salvarUsuario(){

        DatabaseReference databaseReference = SettingInstanceFirebase.getDatabaseReference();
        databaseReference.child("Users").child(this.idCode).setValue(this);

    }

    public void atualizarUsuario(){

        String identificadorUsuario = UsuarioFirebase.getIdentificadorUsuarioRetornoEmailBase64();
        DatabaseReference reference = SettingInstanceFirebase.getDatabaseReference();

        DatabaseReference usuarioReference =  reference.child("Users").child( identificadorUsuario );

        Map<String, Object> usuarioJaConvertido = converterParaMap();

        usuarioReference.updateChildren( usuarioJaConvertido );

    }

    @Exclude
    public Map<String, Object> converterParaMap(){

        HashMap<String, Object> usuarioMap = new HashMap<>();
        usuarioMap.put("email", getEmail());
        usuarioMap.put("name", getName());
        usuarioMap.put("foto", getFoto());
        return  usuarioMap;

    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Exclude
    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }
}
