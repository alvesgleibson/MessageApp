package com.alvesgleibson.messageappfamily.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.alvesgleibson.messageappfamily.R;
import com.alvesgleibson.messageappfamily.helper.SharedPre;
import com.alvesgleibson.messageappfamily.setting.SettingInstanceFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail, txtSenha;
    private FirebaseAuth auth = SettingInstanceFirebase.getInstanceFirebaseAuth();
    private SharedPre mySharedPre;
    private Switch aSwitchSalvarSenha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtEmail = findViewById(R.id.txtLoginEmail);
        txtSenha = findViewById(R.id.txtLoginPassword);
        aSwitchSalvarSenha = findViewById(R.id.swtSalvarLoginSenha);

        mySharedPre  = new SharedPre(getApplicationContext());

    }

    @Override
    protected void onStart() {
        super.onStart();
        usuarioLogado();
        salvarSenhaSharedPreference();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (!aSwitchSalvarSenha.isChecked()){
            mySharedPre.limparSPLogin();
        }
    }

    private void salvarSenhaSharedPreference() {
        String valoresSharedPreference[] =  mySharedPre.recoverSP();

            if (!valoresSharedPreference[0].equals("")) {
                txtEmail.setText(valoresSharedPreference[0]);
                txtSenha.setText(valoresSharedPreference[1]);
                aSwitchSalvarSenha.setChecked(true);
            }else {
                aSwitchSalvarSenha.setChecked(false);
                txtEmail.setText("");
                txtSenha.setText("");
            }

        }


    private void usuarioLogado() {

        if (auth.getCurrentUser() != null){
            startActivity( new Intent(this, MainActivity.class));
        }

    }

    public void logarUsuario(View view){

        String email = txtEmail.getText().toString();
        String senha = txtSenha.getText().toString();

        isCheckFieldInput(email, senha);

    }

    private void isCheckFieldInput(String email, String senha) {

        if (!email.isEmpty()){
            if (!senha.isEmpty()){

                loginUsuario(email, senha);

            }else Toast.makeText(this, "Campo SENHA não pode ser vazio", Toast.LENGTH_SHORT).show();
        }else Toast.makeText(this, "Campo EMAIL não pode ser vazio", Toast.LENGTH_SHORT).show();

    }

    private void loginUsuario(String email, String senha) {


        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(this, x ->{

            if (x.isSuccessful()){

                //gravar o email e senha no SharedPreference se o switch estiver ativado
                if (aSwitchSalvarSenha.isChecked()){
                    mySharedPre.SaveSP(email, senha);
                }else {
                    mySharedPre.limparSPLogin();
                }


                startActivity( new Intent(this, MainActivity.class));

            }else {
                String myMessage = "";

                try {
                    throw x.getException();
                }catch (FirebaseAuthInvalidUserException e){
                    myMessage = "Usuario não está cadastrado!!!";
                }catch (FirebaseAuthInvalidCredentialsException e){
                    myMessage = "Email ou senha Invalido!!!";
                }catch (Exception e){
                    myMessage = "Problema ao logar "+e.getMessage();
                    e.printStackTrace();
                }

                Toast.makeText(this, myMessage, Toast.LENGTH_LONG).show();
            }

        });

    }



    public void cadastroTxt(View view){
       startActivity( new Intent(this, CadastroActivity.class));
    }



}