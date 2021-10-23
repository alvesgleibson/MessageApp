package com.alvesgleibson.messageappfamily.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alvesgleibson.messageappfamily.R;
import com.alvesgleibson.messageappfamily.model.Usuario;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ListaContatoAdapter extends RecyclerView.Adapter<ListaContatoAdapter.MyViewHolder> {

    private List<Usuario> usuarioList;
    private Context context;

    public ListaContatoAdapter(List<Usuario> usuarioList, Context context) {
        this.usuarioList = usuarioList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_contato, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Usuario usuario = usuarioList.get( position );


        holder.txtNome.setText( usuario.getName() );
        holder.txtEmail.setText( usuario.getEmail() );
        if (usuario.getFoto() != null && !usuario.getFoto().equals("")) {

            Glide.with(context).load(Uri.parse(usuario.getFoto())).into(holder.imgPerfil);

        }else holder.imgPerfil.setImageResource(R.drawable.padrao);

    }

    @Override
    public int getItemCount() {
        return usuarioList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgPerfil;
        private TextView txtNome, txtEmail;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPerfil = itemView.findViewById(R.id.imgContatoPerfil);
            txtNome =  itemView.findViewById(R.id.txtContatosNome);
            txtEmail =  itemView.findViewById(R.id.txtContodosEmail);

        }
    }
}
