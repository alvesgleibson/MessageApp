package com.alvesgleibson.messageappfamily.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.alvesgleibson.messageappfamily.R;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Configurando ToolBar
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Chat");
        setSupportActionBar(toolbar);

        //Adicionando o botão Voltar na Toolbar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}