package com.alvesgleibson.messageappfamily.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.alvesgleibson.messageappfamily.R;
import com.alvesgleibson.messageappfamily.activity.ChatActivity;
import com.alvesgleibson.messageappfamily.adapter.ListaContatoAdapter;
import com.alvesgleibson.messageappfamily.helper.Base64Costum;
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
import java.util.EventListener;
import java.util.List;

public class ContatoFragment extends Fragment {

    private List<Usuario> usuarioList = new ArrayList<>();
    private RecyclerView recyclerViewContatos;
    private ListaContatoAdapter listaContatoAdapter;
    private ValueEventListener eventListener;
    private  DatabaseReference databaseReferenceParaRecycleView;
    private FirebaseUser usuarioAtual;



    public ContatoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contato, container, false);


        recyclerViewContatos = view.findViewById(R.id.rvContato);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        usuarioAtual = UsuarioFirebase.getUsuarioAtual();


        recyclerViewContatos.setLayoutManager(layoutManager );

        recyclerViewContatos.setHasFixedSize(true);

        listaContatoAdapter = new ListaContatoAdapter(usuarioList, getActivity());

        recyclerViewContatos.setAdapter( listaContatoAdapter);
        buscarUsuariosFirebase();
        recyclerViewContatos.addOnItemTouchListener( new RecyclerItemClickListener(
                getActivity(), recyclerViewContatos, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity( new Intent(getActivity(), ChatActivity.class));
            }

            @Override
            public void onLongItemClick(View view, int position) {
                Toast.makeText(getActivity(), "Logon Item Click"+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        }
        ));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onStop() {
        super.onStop();
    databaseReferenceParaRecycleView.removeEventListener( eventListener );

    }

    private void buscarUsuariosFirebase() {

        databaseReferenceParaRecycleView = SettingInstanceFirebase.getDatabaseReference().child("Users");

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