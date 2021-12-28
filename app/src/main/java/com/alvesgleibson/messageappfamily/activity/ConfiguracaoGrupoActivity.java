package com.alvesgleibson.messageappfamily.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.alvesgleibson.messageappfamily.R;
import com.alvesgleibson.messageappfamily.adapter.ListaMembroGrupoAdapter;
import com.alvesgleibson.messageappfamily.model.Usuario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ConfiguracaoGrupoActivity extends AppCompatActivity {

    private List<Usuario> usuarioListSelecionados =  new ArrayList<>();
    private RecyclerView recyclerViewUsuariosSelecionados;
    private TextView textViewQuantidadesSelecionadas;
    private EditText editTextNomeGrupo;
    private FloatingActionButton fabConfirmarNomeImagem;
    private ImageView imageViewGrupo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao_grupo);

        //findViewById Gerenciar
        gerenciarFindViewById();

        //Configurar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Novo Grupo");
        toolbar.setSubtitle("Adicionar nome");
        setSupportActionBar( toolbar);

        //Adicionando o botão Voltar na Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Recuperar lista de membros Usuarios passado pela class GrupoActivity
        recuperarListaUsuario();

        configurarRecyclerView();




    }

    private void configurarRecyclerView() {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        ListaMembroGrupoAdapter listaMembroGrupoAdapter = new ListaMembroGrupoAdapter(getApplicationContext(), usuarioListSelecionados);
        recyclerViewUsuariosSelecionados.setLayoutManager( layoutManager );
        recyclerViewUsuariosSelecionados.setHasFixedSize( true );
        recyclerViewUsuariosSelecionados.setAdapter( listaMembroGrupoAdapter );


    }

    private void gerenciarFindViewById() {

        recyclerViewUsuariosSelecionados = findViewById(R.id.rvUsuariosSelecionados);
        textViewQuantidadesSelecionadas = findViewById(R.id.txtParticipante);

    }

    private void recuperarListaUsuario() {

        if (getIntent().getExtras() != null){
            List<Usuario> listaUsuarioSelecionadosParaGrupo = (List<Usuario>) getIntent().getExtras().getSerializable("UsuarioSelecionado");
            usuarioListSelecionados.addAll( listaUsuarioSelecionadosParaGrupo );
            textViewQuantidadesSelecionadas.setText("Participantes: "+usuarioListSelecionados.size());
        }

    }
}