package com.alvesgleibson.messageappfamily.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alvesgleibson.messageappfamily.R;
import com.alvesgleibson.messageappfamily.helper.MyPermission;
import com.alvesgleibson.messageappfamily.helper.UsuarioFirebase;
import com.alvesgleibson.messageappfamily.setting.SettingInstanceFirebase;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;


public class ConfiguracoesActivity extends AppCompatActivity {

    private String[] permissionRequired = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private ImageView ivPerfil;
    private ImageButton imCamera, imGaleria;
    private static final int SELECAO_CAMERA = 100;
    private static final int SELECAO_GALERIA = 200;
    private String idUsuario;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        imCamera = findViewById(R.id.imCamera);
        imGaleria = findViewById(R.id.imGaleria);
        ivPerfil = findViewById(R.id.circleImageViewFotoPerfil);

        idUsuario = UsuarioFirebase.getIdentificadorUsuario();
        storageReference = SettingInstanceFirebase.getStorageReference();


        acessarCamera();
        acessarGaleria();

        //Validar Permissões
        MyPermission.validarPermissao( permissionRequired , this, 1);

        //Configurando ToolBar
        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Configurações");
        setSupportActionBar(toolbar);

        //Adicionando o botão Voltar na Toolbar

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void acessarCamera() {
        imCamera.setOnClickListener(view -> {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null){
                startActivityForResult(intent, SELECAO_CAMERA);
            }

        });

    }

    private void acessarGaleria() {
        imGaleria.setOnClickListener( view ->{

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

            Bitmap bitmapImagens = null;

            try {

                switch ( requestCode ){

                    case SELECAO_CAMERA:

                        bitmapImagens = (Bitmap) data.getExtras().get("data");

                        if (bitmapImagens != null){
                            ivPerfil.setImageBitmap( bitmapImagens );
                            salvarImagemFirebase(bitmapImagens);
                        }

                        break;

                    case SELECAO_GALERIA:

                        Uri uriImagem = data.getData();
                        bitmapImagens = MediaStore.Images.Media.getBitmap( getContentResolver(), uriImagem);

                        if (bitmapImagens != null){
                            ivPerfil.setImageBitmap( bitmapImagens );
                            salvarImagemFirebase(bitmapImagens);
                        }
                        break;
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
                .child( idUsuario+".jpeg" );


        UploadTask uploadTask = referenceImagem.putBytes( dadosImagem );

        //Teste Upload
        uploadTask.addOnFailureListener(e ->  {
            Toast.makeText(this, "Erro ao realizar o Upload da Imagem", Toast.LENGTH_SHORT).show();
        }).addOnSuccessListener(taskSnapshot -> {
            Toast.makeText(this, "Imagem Upload com sucesso", Toast.LENGTH_SHORT).show();
        });




    }

    //Tratar Permissões negada
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //Saber de onde a requisição foi feita (Nesse caso da ConfiguracoesActivity, pois passamos o numero 1 no parametro);
        if (requestCode == 1){

            for (int permissaoResultado: grantResults){
                if (permissaoResultado == PackageManager.PERMISSION_DENIED){

                    alertaValidacaoPermissao();

                }
            }

        }

    }

    private void alertaValidacaoPermissao() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissões Negadas");
        builder.setMessage("Para utilizar o aplicativo é necessário aceitar as permissões");
        builder.setIcon(R.drawable.ic_permissoes_24);
        builder.setCancelable(false);
        builder.setPositiveButton("Confirmar", (dialogInterface, i) -> {
            finish();
        });
        builder.create().show();

    }

}

