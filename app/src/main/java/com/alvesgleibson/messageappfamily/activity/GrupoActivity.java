package com.alvesgleibson.messageappfamily.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.alvesgleibson.messageappfamily.R;
import com.alvesgleibson.messageappfamily.adapter.ListaContatoAdapter;
import com.alvesgleibson.messageappfamily.adapter.ListaMembroGrupoAdapter;
import com.alvesgleibson.messageappfamily.helper.RecyclerItemClickListener;
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
    private List<Usuario> usuarioListSelecionados =  new ArrayList<>();
    private ValueEventListener eventListener;
    private DatabaseReference databaseReferenceParaRecycleView;
    private ListaContatoAdapter listaContatoAdapter;
    private ListaMembroGrupoAdapter listaMembroGrupoAdapter;
    private FirebaseUser usuarioAtual;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);


        //Configurando ToolBar
        toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Novo Grupo");
        setSupportActionBar(toolbar);



        //Adicionando o botão Voltar na Toolbar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //findById
        recyclerViewMembro = findViewById(R.id.recyclerMembros);
        recyclerViewMembroSelecionado = findViewById(R.id.recyclerMembrosSelecionados);

        //RecyclerView
        recyclerViewMembroCreate();
        recyclerViewMembroSelecionadosCreate();

        //OnclickNoRecyclerView
        onClickRecyclerViewMembros();
        onClickRecyclerViewMembrosSelecionados();


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

    public void atualizarMembroToolBar(){

        int totalSelecionados = usuarioList.size() + usuarioListSelecionados.size();

        toolbar.setSubtitle( usuarioListSelecionados.size()+" de "+ totalSelecionados+ " selecionados");
    }

    private void onClickRecyclerViewMembrosSelecionados() {

        recyclerViewMembroSelecionado.addOnItemTouchListener( new RecyclerItemClickListener(this, recyclerViewMembroSelecionado, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Usuario usuarioSelecionado = usuarioListSelecionados.get( position );

                usuarioList.add( usuarioSelecionado );
                usuarioListSelecionados.remove( usuarioSelecionado );


                listaMembroGrupoAdapter.notifyDataSetChanged();
                listaContatoAdapter.notifyDataSetChanged();
                atualizarMembroToolBar();

            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));

    }

    private void onClickRecyclerViewMembros() {

        recyclerViewMembro.addOnItemTouchListener( new RecyclerItemClickListener(this, recyclerViewMembro, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Usuario usuarioSelecionado = usuarioList.get( position );

                //Adicionar Usuario na Lista

                usuarioListSelecionados.add( usuarioSelecionado );
                listaMembroGrupoAdapter.notifyDataSetChanged();

                //Remover Usuario da lista
                usuarioList.remove( usuarioSelecionado );
                listaContatoAdapter.notifyDataSetChanged();
                atualizarMembroToolBar();


            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }));


    }


    private void recyclerViewMembroSelecionadosCreate() {

        listaMembroGrupoAdapter = new ListaMembroGrupoAdapter( getApplicationContext(), usuarioListSelecionados);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewMembroSelecionado.setHasFixedSize( true );
        recyclerViewMembroSelecionado.setLayoutManager( layoutManager );
        recyclerViewMembroSelecionado.setAdapter( listaMembroGrupoAdapter );


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
                atualizarMembroToolBar();
                listaContatoAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}