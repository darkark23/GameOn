package br.com.daniel.gameon.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.daniel.gameon.R;
import br.com.daniel.gameon.entity.Usuario;

public class AmigoAdapter extends RecyclerView.Adapter<AmigoAdapter.ViewHolder> {

    private List<Usuario> listaAmigos = new ArrayList<Usuario>();
    private Context context;

    public AmigoAdapter(List<Usuario> listaAmigos, Context context) {
        this.listaAmigos = listaAmigos;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.amigo_recycler_view,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder: called");
        holder.nomeAmigo.setText(listaAmigos.get(position).getNomeUsuario());
        holder.fraseAmigo.setText(listaAmigos.get(position).getFrase());
        holder.layoutAmigo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"ok",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaAmigos.size();
    }

    private static final String TAG = "AmigoAdapter";

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageAmigo;
        TextView nomeAmigo;
        TextView fraseAmigo;
        RelativeLayout layoutAmigo;

        public ViewHolder(View itemView) {
            super(itemView);
            imageAmigo = itemView.findViewById(R.id.imagem_amigo);
            nomeAmigo = itemView.findViewById(R.id.nome_amigo);
            fraseAmigo = itemView.findViewById(R.id.frase_amigo);
            layoutAmigo = itemView.findViewById(R.id.layout_amigo);

        }
    }
}
