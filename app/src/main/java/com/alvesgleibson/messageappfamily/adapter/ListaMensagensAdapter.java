package com.alvesgleibson.messageappfamily.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alvesgleibson.messageappfamily.R;

import java.util.List;

public class ListaMensagensAdapter extends RecyclerView.Adapter<ListaMensagensAdapter.MyViewHolder> {

    private Context context;
    private List<String> mensagemList;

    public ListaMensagensAdapter(Context context, List<String> mensagemList) {
        this.context = context;
        this.mensagemList = mensagemList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_mensagem, parent, false);

        return new MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String mensagem = mensagemList.get( position );
        holder.textView.setText( mensagem );

    }

    @Override
    public int getItemCount() {
        return mensagemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.txtMensagem);

        }
    }
}
