package com.alvesgleibson.messageappfamily.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alvesgleibson.messageappfamily.R;

public class LoginActivity extends AppCompatActivity {

    private TextView txtCadastro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void cadastroTxt(View view){
       startActivity( new Intent(this, CadastroActivity.class));
    }


}