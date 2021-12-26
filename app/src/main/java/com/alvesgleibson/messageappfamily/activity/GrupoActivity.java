package com.alvesgleibson.messageappfamily.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.alvesgleibson.messageappfamily.R;
import com.alvesgleibson.messageappfamily.adapter.ListaContatoAdapter;
import com.alvesgleibson.messageappfamily.helper.UsuarioFirebase;
import com.alvesgleibson.messageappfamily.model.Usuario;
import com.alvesgleibson.messageappfamily.setting.SettingInstanceFirebase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GrupoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMembro, recyclerViewMembroSelecionado;
    private List<Usuario> usuarioList =  new ArrayList<>();
    private ValueEventListener eventListener;
    private DatabaseReference databaseReferenceParaRecycleView;
    private ListaContatoAdapter listaContatoAdapter;
    private FirebaseUser usuarioAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);


        //Configurando ToolBar
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //Adicionando o botão Voltar na Toolbar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //findById
        recyclerViewMembro = findViewById(R.id.recyclerMembros);
        recyclerViewMembroSelecionado = findViewById(R.id.recyclerMembrosSelecionados);

        //RecyclerView
        recyclerViewMembroCreate();

        usuarioAtual = UsuarioFirebase.getUsuarioAtual();
    }

    @Override
    protected void onStart() {
        super.onStart();
        buscarUsuariosFirebase();
    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReferenceParaRecycleView.removeEventListener( eventListener );

    }

    private void recyclerViewMembroCreate() {

        listaContatoAdapter = new ListaContatoAdapter( usuarioList, getApplicationContext() );

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getApplicationContext());
        recyclerViewMembro.setLayoutManager( layoutManager );
        recyclerViewMembro.setHasFixedSize( true );
        recyclerViewMembro.setAdapter( listaContatoAdapter );


    }

    private void buscarUsuariosFirebase() {

        databaseReferenceParaRecycleView = SettingInstanceFirebase.getDatabaseReference().child("Users");

        usuarioList.clear();

        eventListener = databaseReferenceParaRecycleView.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot usuarioSnapshot : snapshot.getChildren()){


                    Usuario usuario1 = usuarioSnapshot.getValue( Usuario.class );

                    if (!usuarioAtual.getEmail().equals(usuario1.getEmail())){

                        usuarioList.add( usuario1 );
                    }

                }
                listaContatoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}