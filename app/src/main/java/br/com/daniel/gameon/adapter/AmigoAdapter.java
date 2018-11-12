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

import java.util.ArrayList;
import java.util.List;

import br.com.daniel.gameon.R;
import br.com.daniel.gameon.entity.Usuario;
import br.com.daniel.gameon.fragments.EditarAmigoFragment;

public class AmigoAdapter extends RecyclerView.Adapter<AmigoAdapter.ViewHolder> {

    private Integer tipo;
    private List<Usuario> listaAmigos = new ArrayList<Usuario>();
    private Context context;
    private FragmentManager fragmentManager;

    public AmigoAdapter(List<Usuario> listaAmigos, Context context, FragmentManager fragmentManager, Integer tipo) {

        this.tipo = tipo;
        this.listaAmigos = listaAmigos;
        this.context = context;
        this.fragmentManager = fragmentManager;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.amigo_recycler_view,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Log.d(TAG,"onBindViewHolder: called");
        holder.nomeAmigo.setText(listaAmigos.get(position).getNomeUsuario());
        holder.fraseAmigo.setText(listaAmigos.get(position).getFrase());
        holder.layoutAmigo.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                EditarAmigoFragment editarAmigoFragment = new EditarAmigoFragment();
                Bundle args = new Bundle();
                args.putString("idAmigo",listaAmigos.get(position).getIdUsuario());
                if (tipo == 1){
                    args.putInt("tipo",1);
                    editarAmigoFragment.setArguments(args);
                    fragmentManager.beginTransaction().
                            replace(R.id.content_frame, editarAmigoFragment).commit();
                } else {
                    args.putInt("tipo",2);
                    editarAmigoFragment.setArguments(args);
                    fragmentManager.beginTransaction().
                            replace(R.id.content_frame, editarAmigoFragment).addToBackStack(null).commit();
                }


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
