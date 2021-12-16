package com.alvesgleibson.messageappfamily.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.alvesgleibson.messageappfamily.R;
import com.alvesgleibson.messageappfamily.activity.ChatActivity;
import com.alvesgleibson.messageappfamily.adapter.ListaConversaAdapter;
import com.alvesgleibson.messageappfamily.helper.Base64Costum;
import com.alvesgleibson.messageappfamily.helper.RecyclerItemClickListener;
import com.alvesgleibson.messageappfamily.helper.UsuarioFirebase;
import com.alvesgleibson.messageappfamily.model.Conversa;
import com.alvesgleibson.messageappfamily.model.Usuario;
import com.alvesgleibson.messageappfamily.setting.SettingInstanceFirebase;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class ConversasFragment extends Fragment {

    private RecyclerView recyclerViewConversas;
    private List<Conversa> conversaList = new ArrayList<>();
    private ListaConversaAdapter listaConversaAdapter;
    private DatabaseReference databaseReferenceUsuarioParaConversa, databaseRef;
    private ChildEventListener eventListener;

    private FirebaseUser firebaseUserLogadoAtual;

    public ConversasFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversas, container, false);

        recyclerViewConversas = view.findViewById(R.id.rvConversas);
        firebaseUserLogadoAtual = UsuarioFirebase.getUsuarioAtual();

        listaConversaAdapter = new ListaConversaAdapter(conversaList, getActivity());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager( getActivity() );
        recyclerViewConversas.setLayoutManager( layoutManager );
        recyclerViewConversas.setHasFixedSize( true );

        recyclerViewConversas.setAdapter( listaConversaAdapter );

        //Configura conversas ref
        String usuario = UsuarioFirebase.getIdentificadorUsuarioRetornoEmailBase64();
        databaseRef = SettingInstanceFirebase.getDatabaseReference();

        databaseReferenceUsuarioParaConversa = databaseRef.child("conversas").child( usuario );



        return view;
    }



    @Override
    public void onStart() {
        super.onStart();

        buscarConversaFirebase();

    }

    @Override
    public void onStop() {
        super.onStop();
        databaseReferenceUsuarioParaConversa.removeEventListener( eventListener );
    }




    private void buscarConversaFirebase() {

        eventListener = databaseReferenceUsuarioParaConversa.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Conversa conversa = snapshot.getValue(Conversa.class);
                if (conversa != null){
                    conversaList.add( conversa );
                    listaConversaAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }




}