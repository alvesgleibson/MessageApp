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
import com.alvesgleibson.messageappfamily.model.Conversa;
import com.alvesgleibson.messageappfamily.model.Usuario;
import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class ListaConversaAdapter extends RecyclerView.Adapter<ListaConversaAdapter.MyViewHolder> {

    private List<Conversa> conversaList;
    private Context context;



    public ListaConversaAdapter(List<Conversa> conversaList, Context context) {
        this.conversaList = conversaList;
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
        Conversa conversa = conversaList.get( position );
        Usuario usuario = conversa.getUsuarioExibicao();



        if (conversa != null){
            holder.txtNome.setText(usuario.getName() );
            holder.txtUltimaMensagem.setText( conversa.getUltimaMensagem() );

            if (usuario.getFoto() != null && !usuario.getFoto().equals("")) {

                Glide.with(context).load(Uri.parse(usuario.getFoto())).into(holder.imgPerfil);

            }else holder.imgPerfil.setImageResource(R.drawable.padrao);

        }


    }

    @Override
    public int getItemCount() {
        return conversaList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgPerfil;
        private TextView txtNome, txtUltimaMensagem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPerfil = itemView.findViewById(R.id.imgPerfilGrupo);
            txtNome =  itemView.findViewById(R.id.txtContatosNome);
            txtUltimaMensagem =  itemView.findViewById(R.id.txtContodosEmail);

        }
    }


}
