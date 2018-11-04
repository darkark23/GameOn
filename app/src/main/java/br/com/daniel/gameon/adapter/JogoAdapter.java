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
import br.com.daniel.gameon.entity.Jogo;

public class JogoAdapter extends RecyclerView.Adapter<JogoAdapter.ViewHolder> {

    private List<Jogo> listaJogo = new ArrayList<Jogo>();
    private Context context;

    public JogoAdapter(List<Jogo> listaJogo, Context context) {
        this.listaJogo = listaJogo;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.games_recycler_view,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder: called");
        holder.nomeJogo.setText(listaJogo.get(position).getNome());
        holder.layoutJogo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"ok",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaJogo.size();
    }

    private static final String TAG = "JogoAdapter";

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageJogo;
        TextView nomeJogo;
        RelativeLayout layoutJogo;

        public ViewHolder(View itemView) {
            super(itemView);
            imageJogo = itemView.findViewById(R.id.imagem_jogo);
            nomeJogo = itemView.findViewById(R.id.nome_jogo);
            layoutJogo = itemView.findViewById(R.id.layout_jogo);

        }
    }
}
