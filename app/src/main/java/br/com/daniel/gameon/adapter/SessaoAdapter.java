package br.com.daniel.gameon.adapter;

import android.app.FragmentManager;
import android.content.Context;
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
import java.util.Calendar;
import java.util.List;

import br.com.daniel.gameon.R;
import br.com.daniel.gameon.entity.Jogo;
import br.com.daniel.gameon.entity.Sessao;
import br.com.daniel.gameon.entity.Usuario;
import br.com.daniel.gameon.fragments.EditarGamesFragment;
import br.com.daniel.gameon.fragments.EditarSessoesFragment;
import br.com.daniel.gameon.util.DataUtil;
import br.com.daniel.gameon.util.DownloadImagemUtil;

public class SessaoAdapter extends RecyclerView.Adapter<SessaoAdapter.ViewHolder> {

    private Integer tipo;
    private List<Sessao> listaSessao = new ArrayList<Sessao>();
    private Context context;
    private FragmentManager fragmentManager;
    private Calendar dataAtual;
    private Calendar dataFim;

    public SessaoAdapter(List<Sessao> listaSessao, Context context, FragmentManager fragmentManager, Integer tipo) {

        this.listaSessao = listaSessao;
        this.context = context;
        this.tipo = tipo;
        this.fragmentManager = fragmentManager;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sessao_recycler_view,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Log.d(TAG,"onBindViewHolder: called");
        holder.nomeSessao.setText(listaSessao.get(position).getNomeSessao());
        //holder.nomeJogoSessao.setText(listaSessao.get(position).getIdJogo());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("jogos").orderByChild("idJogo").equalTo(listaSessao.get(position).getIdJogo())
                .addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                        Jogo jogo = children.iterator().next().getValue(Jogo.class);
                        holder.nomeJogoSessao.setText(jogo.getNome());

                        if (jogo.getUrlImagem()!= null){
                            new DownloadImagemUtil(holder.imageSessao).execute(jogo.getUrlImagem());
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        holder.nomeJogoSessao.setText("Erro");
                    }

                });

        dataAtual = Calendar.getInstance();
        dataFim = Calendar.getInstance();
        String dataFimString = listaSessao.get(position).getDataFim();
        dataFim.set(Integer.valueOf(dataFimString.substring(4,8)),
                Integer.valueOf(dataFimString.substring(2,4)),
                Integer.valueOf(dataFimString.substring(0,2)),
                Integer.valueOf(dataFimString.substring(8,10)),
                Integer.valueOf(dataFimString.substring(10,12)),
                Integer.valueOf(dataFimString.substring(12,14)));

        if(dataAtual.before(dataFim)){
            holder.encerradoSessao.setText("Ativo");
        } else {
            holder.encerradoSessao.setText("Encerrado");
        }

        holder.inicioSessao.setText(DataUtil.formatDataHora(listaSessao.get(position).getDataInicio(),6));
        holder.fimSFim.setText(DataUtil.formatDataHora(listaSessao.get(position).getDataFim(),6));

        holder.layoutSessao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                EditarSessoesFragment editarSessoesFragment = new EditarSessoesFragment();
                Bundle args = new Bundle();
                args.putString("idSessao",listaSessao.get(position).getIdSessao());
                if (tipo == 1){

                    if(dataAtual.before(dataFim)){
                        args.putInt("tipo",1);
                        editarSessoesFragment.setArguments(args);
                        fragmentManager.beginTransaction().
                                replace(R.id.content_frame, editarSessoesFragment).addToBackStack("EditarSessoesFragment").commit();
                    } else {
                        Toast.makeText(view.getContext(),"Essa sessão está encerrada!",Toast.LENGTH_LONG).show();
                    }

                } else {
                    args.putInt("tipo",2);
                    editarSessoesFragment.setArguments(args);
                    fragmentManager.beginTransaction().
                            replace(R.id.content_frame, editarSessoesFragment).addToBackStack("EditarSessoesFragment").commit();
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return listaSessao.size();
    }

    private static final String TAG = "JogoAdapter";

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageSessao;
        TextView nomeSessao;
        TextView nomeJogoSessao;
        TextView encerradoSessao;
        TextView inicioSessao;
        TextView fimSFim;
        RelativeLayout layoutSessao;

        public ViewHolder(View itemView) {

            super(itemView);
            imageSessao = itemView.findViewById(R.id.imagem_jogo);
            nomeSessao = itemView.findViewById(R.id.nome_sessao);
            nomeJogoSessao = itemView.findViewById(R.id.nome_jogo_sessao);
            encerradoSessao = itemView.findViewById(R.id.encerrado_sessao);
            inicioSessao = itemView.findViewById(R.id.inicio_sessao);
            fimSFim = itemView.findViewById(R.id.fim_sessao);
            layoutSessao = itemView.findViewById(R.id.layout_sessao);

        }
    }
}
