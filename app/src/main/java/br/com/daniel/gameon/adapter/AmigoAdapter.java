package br.com.daniel.gameon.adapter;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.daniel.gameon.R;
import br.com.daniel.gameon.entity.Sessao;
import br.com.daniel.gameon.entity.Usuario;
import br.com.daniel.gameon.fragments.EditarAmigoFragment;
import br.com.daniel.gameon.fragments.SessoesListaAmigosFragment;
import br.com.daniel.gameon.util.DownloadImagemUtil;

public class AmigoAdapter extends RecyclerView.Adapter<AmigoAdapter.ViewHolder> {

    private Integer tipo;
    private List<Usuario> listaAmigos = new ArrayList<Usuario>();
    private Context context;
    private FragmentManager fragmentManager;
    private String idSessao;
    private String idUsuario;
    private Usuario usuario;
    private Sessao sessao;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public AmigoAdapter(List<Usuario> listaAmigos, Context context, FragmentManager fragmentManager, Integer tipo) {

        this.tipo = tipo;
        this.listaAmigos = listaAmigos;
        this.context = context;
        this.fragmentManager = fragmentManager;

    }

    public AmigoAdapter(List<Usuario> listaAmigos, Context context, FragmentManager fragmentManager, Integer tipo, String idSessao, String idUsuario) {

        this.tipo = tipo;
        this.listaAmigos = listaAmigos;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.idUsuario = idUsuario;
        this.idSessao = idSessao;

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
        if (listaAmigos.get(position).getUrlImagem()!= null){
            new DownloadImagemUtil(holder.imageAmigo).execute(listaAmigos.get(position).getUrlImagem());
        }
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
                            replace(R.id.content_frame, editarAmigoFragment).addToBackStack("EditarAmigoFragment").commit();
                } else if(tipo == 2) {
                    args.putInt("tipo",2);
                    editarAmigoFragment.setArguments(args);
                    fragmentManager.beginTransaction().
                            replace(R.id.content_frame, editarAmigoFragment).addToBackStack("EditarAmigoFragment").commit();
                } else if(tipo == 3){

                } else if (tipo == 4) {
                    args.putInt("tipo",4);
                    args.putString("idSessao",idSessao);
                    editarAmigoFragment.setArguments(args);
                    fragmentManager.beginTransaction().replace(R.id.content_frame, editarAmigoFragment).addToBackStack("EditarAmigoFragment").commit();

                } else {

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
