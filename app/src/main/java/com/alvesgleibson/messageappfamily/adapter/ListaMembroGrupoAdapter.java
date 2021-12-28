package com.alvesgleibson.messageappfamily.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alvesgleibson.messageappfamily.R;
import com.alvesgleibson.messageappfamily.model.Usuario;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListaMembroGrupoAdapter extends RecyclerView.Adapter<ListaMembroGrupoAdapter.MyViewHolder>  {

    private Context context;
    private List<Usuario> usuarios;

    public ListaMembroGrupoAdapter(Context context, List<Usuario> usuarios) {
        this.context = context;
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.usuario_selecionado, parent, false );

        return new MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Usuario usuario = usuarios.get( position );

        holder.txtNomeMembro.setText( usuario.getName() );

        if (usuario.getFoto() != null && !usuario.getFoto().equals("")){

            Glide.with(context).load(Uri.parse( usuario.getFoto())).into( holder.circleImageView );

        }else holder.circleImageView.setImageResource( R.drawable.padrao );


    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView circleImageView;
        private TextView txtNomeMembro;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.imgMembro);
            txtNomeMembro = itemView.findViewById(R.id.txtNomeMembro);


        }
    }
}
