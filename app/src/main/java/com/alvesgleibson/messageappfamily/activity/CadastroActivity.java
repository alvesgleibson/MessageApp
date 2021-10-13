package com.alvesgleibson.messageappfamily.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alvesgleibson.messageappfamily.R;
import com.alvesgleibson.messageappfamily.helper.Base64Costum;
import com.alvesgleibson.messageappfamily.model.Usuario;
import com.alvesgleibson.messageappfamily.setting.SettingInstanceFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class CadastroActivity extends AppCompatActivity {

    private EditText txtNome, txtEmail, txtSenha,txtConfirmeSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtNome = findViewById(R.id.txtCadastroName);
        txtEmail = findViewById(R.id.txtCadastroEmail);
        txtSenha = findViewById(R.id.txtCadastroSenha);
        txtConfirmeSenha = findViewById(R.id.txtCadastroConfirmarSenha);

    }

    public void cadastrarUsuarioFirebase(View view){

        String nome = txtNome.getText().toString();
        String email = txtEmail.getText().toString();
        String senha = txtSenha.getText().toString();
        String confSenha = txtConfirmeSenha.getText().toString();

        isCheckFieldInput(nome, email, senha, confSenha);


    }

    private void isCheckFieldInput(String nome, String email, String senha, String confSenha) {

        if (!nome.isEmpty()){
            if (!email.isEmpty()){
                if (!senha.isEmpty()){
                    if (!confSenha.isEmpty()){
                        if (senha.equals(confSenha)){
                            Usuario user = new Usuario(nome, email, senha);
                            createUserFirebase(user);
                        }else Toast.makeText(this, "Digite a mesma senha no campo confirmar senha", Toast.LENGTH_SHORT).show();
                    }else Toast.makeText(this, "Campo Confirmar Senha não pode ser vazio", Toast.LENGTH_SHORT).show();
                }else Toast.makeText(this, "Campo Senha não pode ser vazio", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(this, "Campo Email não pode ser vazio", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, "Campo nome não pode ser vazio", Toast.LENGTH_SHORT).show();

    }



    private void createUserFirebase(Usuario user) {
        FirebaseAuth auth = SettingInstanceFirebase.getInstanceFirebaseAuth();

        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword()).addOnCompleteListener(this, x ->{

            if (x.isSuccessful()){

                user.setIdCode( Base64Costum.encodeBase64(user.getEmail()) );

                user.salvarUsuario();
                finish();
                Toast.makeText(this, "Usuario "+user.getName()+" cadastrado com sucesso", Toast.LENGTH_LONG).show();
            }else {

                String msgError = "";

                try {
                    throw x.getException();
                }catch (FirebaseAuthWeakPasswordException e ){
                    msgError = "Senha Fraca Firebase";
                }catch (FirebaseAuthInvalidCredentialsException e ){
                    msgError = "Email esta incorreto Firebase";
                }catch (FirebaseAuthUserCollisionException e ){
                    msgError = "Email já existe Firebase";
                }catch (Exception e ){
                    msgError = "Erro ao Cadastrar Usuario Firebase " + e.getMessage();
                    e.printStackTrace();
                }

                Toast.makeText(this, msgError, Toast.LENGTH_SHORT).show();

            }


        });




    }


}