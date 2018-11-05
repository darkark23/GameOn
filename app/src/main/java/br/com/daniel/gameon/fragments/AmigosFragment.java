package br.com.daniel.gameon.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import br.com.daniel.gameon.entity.Usuario;

public class AmigosFragment extends Fragment {

    private final List<Usuario> listaAmigos = new ArrayList<>();
    private DatabaseReference dtRef = FirebaseDatabase.getInstance().getReference();
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.amigos_fragment, container, false);
        FloatingActionButton btn =(FloatingActionButton) view.findViewById(R.id.adicionarAmigosButton);
        btn.setOnClickListener( new View.OnClickListener(){
            public void onClick(View view) {
            }
        });
        dtRef.child("usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child: children) {
                    Usuario amigo = child.getValue(Usuario.class);
                    listaAmigos.add(amigo);
                }
                initRecyclerView(listaAmigos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return view;
    }

    public void initRecyclerView(List<Usuario> listaAmigos){
        RecyclerView recyclerView = view.findViewById(R.id.amigo_recycler_view);
        RecyclerView.Adapter adapter = new AmigoAdapter(listaAmigos,view.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

}
