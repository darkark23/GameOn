package br.com.daniel.gameon.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.daniel.gameon.R;
import br.com.daniel.gameon.adapter.AmigoAdapter;
import br.com.daniel.gameon.adapter.JogoAdapter;
import br.com.daniel.gameon.entity.Jogo;
import br.com.daniel.gameon.entity.Usuario;

public class GamesFragment extends Fragment {

    final List<Jogo> listaJogos = new ArrayList<>();
    DatabaseReference dtRef = FirebaseDatabase.getInstance().getReference();
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.games_fragment,container,false);
        dtRef.child("jogos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child: children) {
                    Jogo jogo = child.getValue(Jogo.class);
                    listaJogos.add(jogo);
                }
                initRecyclerView(listaJogos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    public void initRecyclerView(List<Jogo> listaAmigos){
        RecyclerView recyclerView = view.findViewById(R.id.game_recycler_view);
        RecyclerView.Adapter adapter = new JogoAdapter(listaAmigos,view.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }
}
