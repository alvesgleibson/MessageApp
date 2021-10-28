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
import com.alvesgleibson.messageappfamily.model.Mensagem;

import java.util.List;

public class ListaMensagensAdapter extends RecyclerView.Adapter<ListaMensagensAdapter.MyViewHolder> {

    private Context context;
    private List<Mensagem> mensagemList;

    public ListaMensagensAdapter(Context context, List<Mensagem> mensagemList) {
        this.context = context;
        this.mensagemList = mensagemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_mensagem_rementente, parent, false);

        return new MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Mensagem mensagem = mensagemList.get( position );
        holder.textView.setText( mensagem.getMensagem() );

        if (mensagem.getImageMensagem() != null && !mensagem.getImageMensagem().isEmpty()){
            holder.imageView.setImageURI(Uri.parse( mensagem.getImageMensagem()));
            holder.imageView.setVisibility( View.VISIBLE );
        }

    }

    @Override
    public int getItemCount() {
        return mensagemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.txtMensagem);
            imageView = itemView.findViewById(R.id.imagemMensagem);

        }
    }
}
