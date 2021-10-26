package com.alvesgleibson.messageappfamily.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import com.alvesgleibson.messageappfamily.R;
import com.alvesgleibson.messageappfamily.adapter.ListaMensagensAdapter;
import com.alvesgleibson.messageappfamily.helper.UsuarioFirebase;
import com.alvesgleibson.messageappfamily.model.Usuario;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {

    private String[] permissionRequired = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private FirebaseUser firebaseUser;
    private CircleImageView circleImageView;
    private TextView txtPerfil, txtMensagem;



    private RecyclerView recyclerViewMensagem;
    private List<String> mensagemExibir =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        firebaseUser = UsuarioFirebase.getUsuarioAtual();

        circleImageView = findViewById(R.id.imToolbarPerfil);
        txtPerfil = findViewById(R.id.txtChatToolbarPerfil);
        recyclerViewMensagem = findViewById(R.id.rv_mensagens);
        txtMensagem = findViewById(R.id.txtChatMessagem);

        //Configurando ToolBar
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //Recuperar nome, foto
        Usuario usuarioIntent = (Usuario) getIntent().getSerializableExtra("usuario");

        //Adicionando o botão Voltar na Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Recuperar dados
        recuperarDadosFirebaseUser(usuarioIntent.getName(), usuarioIntent.getFoto());

        //Configurar adapter e recycleView

        recyclerViewMensagem.setLayoutManager(  new LinearLayoutManager(this));
        recyclerViewMensagem.setHasFixedSize( true );

        recyclerViewMensagem.setAdapter( new ListaMensagensAdapter( this,  mensagemExibir));

    }




    public void enviarMensagem(View view){
        String s = txtMensagem.getText().toString().trim();
        if (!s.isEmpty() && !s.equals("")){
            mensagemExibir.add( s );
            recyclerViewMensagem.setAdapter( new ListaMensagensAdapter( this,  mensagemExibir));
            txtMensagem.setText("");
        }


    }

    private void recuperarDadosFirebaseUser(String nome, String foto ) {

            if (foto != null && !foto.equals("")){
                Glide.with(this).load( foto ).into( circleImageView ) ;
            }else{
                circleImageView.setImageResource( R.drawable.padrao);
            }

            txtPerfil.setText( nome );


    }




}