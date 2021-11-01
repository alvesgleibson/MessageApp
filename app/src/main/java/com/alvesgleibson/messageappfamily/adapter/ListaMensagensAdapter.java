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
import com.alvesgleibson.messageappfamily.helper.UsuarioFirebase;
import com.alvesgleibson.messageappfamily.model.Mensagem;

import java.util.List;

public class ListaMensagensAdapter extends RecyclerView.Adapter<ListaMensagensAdapter.MyViewHolder> {

    private static final int TIPO_REMETENTE = 0, TIPO_DESTINATARIO = 1;

    private Context context;
    private List<Mensagem> mensagemList;

    public ListaMensagensAdapter(Context context, List<Mensagem> mensagemList) {
        this.context = context;
        this.mensagemList = mensagemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == TIPO_REMETENTE){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_mensagem_rementente, parent, false);
        } else view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_mensagem_destinatario, parent, false);


        return new MyViewHolder( view );
    }

    //Para definir qual layout usar
    @Override
    public int getItemViewType(int position) {

        Mensagem mensagem = mensagemList.get( position );
        String usuario = UsuarioFirebase.getIdentificadorUsuarioRetornoEmailBase64();

        if (mensagem.getIdUsuarioMensagemEnviada().equals( usuario )){
            return TIPO_REMETENTE;
        }else return TIPO_DESTINATARIO;

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Mensagem mensagem = mensagemList.get( position );

        if (mensagem.getImageMensagem() != null && !mensagem.getImageMensagem().isEmpty()){
            holder.imageView.setImageURI(Uri.parse( mensagem.getImageMensagem()));
            holder.imageView.setVisibility( View.VISIBLE );
            holder.textView.setVisibility( View.GONE );

        }else holder.textView.setText( mensagem.getMensagem() );

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
