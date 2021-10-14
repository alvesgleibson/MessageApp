package com.alvesgleibson.messageappfamily.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.alvesgleibson.messageappfamily.R;
import com.alvesgleibson.messageappfamily.fragment.ContatoFragment;
import com.alvesgleibson.messageappfamily.fragment.ConversasFragment;
import com.alvesgleibson.messageappfamily.setting.SettingInstanceFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth = SettingInstanceFirebase.getInstanceFirebaseAuth();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("MessageAPP");
        setSupportActionBar(toolbar);

        smartTabLayAdapter();


    }

    private void smartTabLayAdapter() {

        FragmentPagerItemAdapter fragmentPagerItemAdapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(),
                FragmentPagerItems.with(this)
                .add("Conversas", ConversasFragment.class)
                .add("Contatos", ContatoFragment.class)
                .create()
        );

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter( fragmentPagerItemAdapter );
        SmartTabLayout smartTabLayout = findViewById(R.id.viewPagerTabSTL);
        smartTabLayout.setViewPager( viewPager );



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
                startActivity( new Intent(this, ConfiguracoesActivity.class));
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