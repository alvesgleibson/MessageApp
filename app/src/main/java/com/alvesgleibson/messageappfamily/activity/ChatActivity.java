package com.alvesgleibson.messageappfamily.activity;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import com.alvesgleibson.messageappfamily.R;
import com.alvesgleibson.messageappfamily.adapter.ListaMensagensAdapter;
import com.alvesgleibson.messageappfamily.helper.Base64Costum;
import com.alvesgleibson.messageappfamily.helper.UsuarioFirebase;
import com.alvesgleibson.messageappfamily.model.Mensagem;
import com.alvesgleibson.messageappfamily.model.Usuario;
import com.alvesgleibson.messageappfamily.setting.SettingInstanceFirebase;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {


    private String usuarioDestinatarioBase64;
    private CircleImageView circleImageView;
    private TextView txtPerfil, txtMensagem;

    private RecyclerView recyclerViewMensagem;
    private List<Mensagem> mensagemListaExibir =  new ArrayList<>();

    private Usuario usuarioDestinatarioIntent;
    private DatabaseReference databaseReferenceParaRecycleViewMensagem;
    private ValueEventListener eventListener;

    private ListaMensagensAdapter listaAdapterMensagem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);



        circleImageView = findViewById(R.id.imToolbarPerfil);
        txtPerfil = findViewById(R.id.txtChatToolbarPerfil);
        recyclerViewMensagem = findViewById(R.id.rv_mensagens);
        txtMensagem = findViewById(R.id.txtChatMessagem);

        //Configurando ToolBar
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //Recuperar nome, foto via intent
        if(getIntent().getSerializableExtra("usuario") != null){
            usuarioDestinatarioIntent = (Usuario) getIntent().getSerializableExtra("usuario");
            recuperarDadosFirebaseUser(usuarioDestinatarioIntent.getName(), usuarioDestinatarioIntent.getFoto());

        }

        //Adicionando o botão Voltar na Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //recuperando usuario atual base64

        usuarioDestinatarioBase64 = Base64Costum.encodeBase64( usuarioDestinatarioIntent.getEmail() );

        //Setar lista de mensagem para mostrar
        recuperarListaMensagemFirebase();

        //Configurar adapter e recycleView

        recyclerViewMensagem.setLayoutManager(  new LinearLayoutManager(this));
        recyclerViewMensagem.setHasFixedSize( true );

        listaAdapterMensagem = new ListaMensagensAdapter( this,  mensagemListaExibir);
        recyclerViewMensagem.setAdapter( listaAdapterMensagem );

    }


    public void enviarMensagem(View view){


        String s = txtMensagem.getText().toString().trim();
        if (!s.isEmpty() && !s.equals("")){

            Mensagem mensagem = new Mensagem();

            mensagem.setMensagem( s );
            mensagem.setIdUsuarioMensagemEnviada( UsuarioFirebase.getIdentificadorUsuarioRetornoEmailBase64() );

            mensagem.salvarMensagemTexto( usuarioDestinatarioBase64 );


            txtMensagem.setText("");
        }

    }

    private void recuperarListaMensagemFirebase() {

        databaseReferenceParaRecycleViewMensagem = SettingInstanceFirebase.getDatabaseReference().child("Mensagens").child(UsuarioFirebase.getIdentificadorUsuarioRetornoEmailBase64()).child(usuarioDestinatarioBase64).child("Mensagem");



        eventListener = databaseReferenceParaRecycleViewMensagem.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Limpar a lista para não juntar da listagem para outra
                mensagemListaExibir.clear();

                for (DataSnapshot mensagemSnapshot : snapshot.getChildren()){

                    Mensagem mensagem = mensagemSnapshot.getValue( Mensagem.class );
                    mensagemListaExibir.add( mensagem );
                }

                listaAdapterMensagem.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void recuperarDadosFirebaseUser(String nome, String foto ) {

            if (foto != null && !foto.equals("")){
                Glide.with(this).load( foto ).into( circleImageView ) ;
            }else{
                circleImageView.setImageResource( R.drawable.padrao);
            }
            txtPerfil.setText( nome );

    }


    @Override
    protected void onStop() {
        super.onStop();

        databaseReferenceParaRecycleViewMensagem.removeEventListener( eventListener );
    }
}