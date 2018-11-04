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

import com.google.android.gms.common.util.DataUtils;

import java.util.ArrayList;
import java.util.List;

import br.com.daniel.gameon.R;
import br.com.daniel.gameon.entity.Jogo;
import br.com.daniel.gameon.entity.Sessao;
import br.com.daniel.gameon.util.dateUtil;

public class SessaoAdapter extends RecyclerView.Adapter<SessaoAdapter.ViewHolder> {

    private List<Sessao> listaSessao = new ArrayList<Sessao>();
    private Context context;

    public SessaoAdapter(List<Sessao> listaSessao, Context context) {
        this.listaSessao = listaSessao;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sessao_recycler_view,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d(TAG,"onBindViewHolder: called");
        holder.nomeSessao.setText(listaSessao.get(position).getNomeSessao());
        holder.nomeJogoSessao.setText(listaSessao.get(position).getIdJogo());
        if(listaSessao.get(position).getAtivo().equals("S")){
            holder.encerradoSessao.setText("Ativo");
        } else {
            holder.encerradoSessao.setText("Encerrado");
        }
        holder.inicioSessao.setText(dateUtil.getDataHora(listaSessao.get(position).getDataInicio(),6));
        holder.fimSFim.setText(dateUtil.getDataHora(listaSessao.get(position).getDataFim(),6));
        holder.layoutSessao.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"ok",Toast.LENGTH_SHORT).show();
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
