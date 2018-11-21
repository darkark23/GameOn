package br.com.daniel.gameon.adapter;

import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
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
import br.com.daniel.gameon.fragments.EditarAmigoFragment;
import br.com.daniel.gameon.fragments.EditarGamesFragment;
import br.com.daniel.gameon.util.DownloadImagemUtil;

public class JogoAdapter extends RecyclerView.Adapter<JogoAdapter.ViewHolder> {

    private Integer tipo;
    private List<Jogo> listaJogo = new ArrayList<Jogo>();
    private Context context;
    private FragmentManager fragmentManager;

    public JogoAdapter(List<Jogo> listaJogo, Context context) {

        this.listaJogo = listaJogo;
        this.context = context;

    }

    public JogoAdapter(List<Jogo> listaJogo, Context context, FragmentManager fragmentManager, Integer tipo) {

        this.listaJogo = listaJogo;
        this.context = context;
        this.tipo = tipo;
        this.fragmentManager = fragmentManager;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.games_recycler_view,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Log.d(TAG,"onBindViewHolder: called");
        holder.nomeJogo.setText(listaJogo.get(position).getNome());

        if (listaJogo.get(position).getUrlImagem()!= null){
            new DownloadImagemUtil(holder.imageJogo).execute(listaJogo.get(position).getUrlImagem());
        }

        holder.layoutJogo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                EditarGamesFragment editarGamesFragment = new EditarGamesFragment();
                Bundle args = new Bundle();
                args.putString("idGame",listaJogo.get(position).getIdJogo());
                if (tipo == 1){
                    args.putInt("tipo",1);
                    editarGamesFragment.setArguments(args);
                    fragmentManager.beginTransaction().
                            replace(R.id.content_frame, editarGamesFragment).addToBackStack("EditarGamesFragment").commit();
                } else {
                    args.putInt("tipo",2);
                    editarGamesFragment.setArguments(args);
                    fragmentManager.beginTransaction().
                            replace(R.id.content_frame, editarGamesFragment).addToBackStack("EditarGamesFragment").commit();
                }

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
