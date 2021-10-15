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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.alvesgleibson.messageappfamily.R;
import com.alvesgleibson.messageappfamily.helper.MyPermission;



public class ConfiguracoesActivity extends AppCompatActivity {

    private String[] permissionRequired = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };

    private ImageView ivPerfil;
    private ImageButton imCamera, imGaleria;
    private static final int SELECAO_CAMERA = 100;
    private static final int SELECAO_GALERIA = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        imCamera = findViewById(R.id.imCamera);
        imGaleria = findViewById(R.id.imGaleria);
        ivPerfil = findViewById(R.id.circleImageViewFotoPerfil);


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
                        }

                        break;

                    case SELECAO_GALERIA:

                        Uri uriImagem = data.getData();
                        bitmapImagens = MediaStore.Images.Media.getBitmap( getContentResolver(), uriImagem);

                        if (bitmapImagens != null){
                            ivPerfil.setImageBitmap( bitmapImagens );
                        }
                        break;
                }


            }catch (Exception e){
                e.printStackTrace();
            }

        }


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

