package com.alvesgleibson.messageappfamily.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;


import com.alvesgleibson.messageappfamily.R;
import com.alvesgleibson.messageappfamily.adapter.ListaMembroGrupoAdapter;
import com.alvesgleibson.messageappfamily.model.Grupo;
import com.alvesgleibson.messageappfamily.model.Usuario;
import com.alvesgleibson.messageappfamily.setting.SettingInstanceFirebase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConfiguracaoGrupoActivity extends AppCompatActivity {

    private List<Usuario> usuarioListSelecionados =  new ArrayList<>();
    private RecyclerView recyclerViewUsuariosSelecionados;
    private TextView textViewQuantidadesSelecionadas;
    private EditText editTextNomeGrupo;
    private FloatingActionButton fabConfirmarNomeImagem;
    private static final int SELECAO_GALERIA = 200;
    private StorageReference storageReference;
    private Grupo grupo;
    private Bitmap bitmapImagens;

    private CircleImageView circleImageViewPerfilGrupo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao_grupo);

        //findViewById Gerenciar
        gerenciarFindViewById();

        //Iniciar Grupo
        grupo = new Grupo();

        storageReference = SettingInstanceFirebase.getStorageReference();

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

        configurarImagemPerfilGrupo();

        //Acao do FAB
        fabAcaoSalvarGrupo();




    }

    private void fabAcaoSalvarGrupo() {


        fabConfirmarNomeImagem.setOnClickListener( view -> {

            if ( !editTextNomeGrupo.getText().toString().trim().equals("")){

                if (bitmapImagens != null){
                    salvarImagemFirebase( bitmapImagens );
                }


            }else Toast.makeText(this, "Digite algum nome para grupo", Toast.LENGTH_SHORT).show();

        });


    }

    private void configurarImagemPerfilGrupo() {

        circleImageViewPerfilGrupo.setOnClickListener( view -> {

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            if (intent.resolveActivity(getPackageManager()) != null){
               startActivityForResult(intent, SELECAO_GALERIA);

            }

        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){

            bitmapImagens = null;

            try {
                        Uri uriImagem = data.getData();
                        bitmapImagens = MediaStore.Images.Media.getBitmap( getContentResolver(), uriImagem);

                        if (bitmapImagens != null){
                            circleImageViewPerfilGrupo.setImageBitmap( bitmapImagens );

                        }

            }catch (Exception e){
                e.printStackTrace();
            }

        }


    }

    private void salvarImagemFirebase(Bitmap bitmapImagens) {

        //Recuperar dados da imagem para Firebase
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmapImagens.compress(Bitmap.CompressFormat.JPEG, 75, baos);

        byte[] dadosImagem = baos.toByteArray();


        //Salvar imagem no Firebase
        StorageReference referenceImagem = storageReference
                .child("imagens")
                .child("perfil")
                .child( grupo.getIdGrupo()+".jpeg" );


        UploadTask uploadTask = referenceImagem.putBytes( dadosImagem );

        uploadTask.addOnFailureListener(  e -> {
            Toast.makeText(this, "Erro ao realizar o Upload da Imagem", Toast.LENGTH_SHORT).show();
        }).addOnSuccessListener( taskSnapshot -> {
            Toast.makeText(this, "Imagem Upload com sucesso", Toast.LENGTH_SHORT).show();

            //Obtendo a Uri da imagem para atualizar a foto
            referenceImagem.getDownloadUrl().addOnCompleteListener(task -> {
                Uri url = task.getResult();

                //Savar Imagem no Perfil do app
                grupo.setFotoPerfilGrupo( url.toString() );

            });
        });





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
        circleImageViewPerfilGrupo = findViewById(R.id.imgPerfilGrupo);
        fabConfirmarNomeImagem = findViewById(R.id.fabConfirmaGrupo);
        editTextNomeGrupo = findViewById(R.id.ettNomeGrupo);


    }

    private void recuperarListaUsuario() {

        if (getIntent().getExtras() != null){
            List<Usuario> listaUsuarioSelecionadosParaGrupo = (List<Usuario>) getIntent().getExtras().getSerializable("UsuarioSelecionado");
            usuarioListSelecionados.addAll( listaUsuarioSelecionadosParaGrupo );
            textViewQuantidadesSelecionadas.setText("Participantes: "+usuarioListSelecionados.size());
        }

    }
}