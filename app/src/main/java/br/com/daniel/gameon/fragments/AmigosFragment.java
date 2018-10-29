package br.com.daniel.gameon.fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import br.com.daniel.gameon.R;
import br.com.daniel.gameon.entity.Usuario;

public class AmigosFragment extends Fragment {

    DatabaseReference dtRef = FirebaseDatabase.getInstance().getReference();


    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.amigos_fragment, container, false);
        FloatingActionButton btn =(FloatingActionButton) myView.findViewById(R.id.adicionarAmigosButton);
        btn.setOnClickListener( new View.OnClickListener(){
            public void onClick(View view) {
               cadastrarUsuario();
            }

        });
        return myView;
    }

    public void cadastrarUsuario() {
        String usuarioId = dtRef.child("usuarios").push().getKey();
        Usuario usuario = new Usuario("SBIs71HP4PZYrOdKeGAvuJDi2A53", "testeIdImagem", usuarioId, "testeIdHorario",
                "Usuario02", "Usuario02",
                "Acabei de registrar no Gameon!");
        usuario.setAmigo("-LPy6htkoGAB6kaCDf7u");
        usuario.setJogo("testeJogo");
        usuario.setSessao("testeSessao");
        dtRef.child("usuarios").child(usuarioId).setValue(usuario);
    }

}
