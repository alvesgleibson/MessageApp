package com.alvesgleibson.messageappfamily.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.alvesgleibson.messageappfamily.R;
import com.alvesgleibson.messageappfamily.setting.SettingInstanceFirebase;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth = SettingInstanceFirebase.getInstanceFirebaseAuth();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("MessageAPP");
        setSupportActionBar(toolbar);

    }

    //Criar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //Selecionar opção menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_pesquisa:
                Toast.makeText(this, "Pesquisa", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_conficuracao:
                Toast.makeText(this, "Configuração", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_sair:
                deslogarUser();
                finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void deslogarUser() {

        try {
            auth.signOut();
        }catch (Exception e){
            Toast.makeText(this, "Erro ao sair "+e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }


}