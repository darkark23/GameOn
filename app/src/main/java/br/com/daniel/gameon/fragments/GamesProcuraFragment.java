package br.com.daniel.gameon.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import br.com.daniel.gameon.R;
import br.com.daniel.gameon.activities.MenuPrincipalActivity;

public class GamesProcuraFragment extends Fragment {

    private View view;
    private FirebaseAuth.AuthStateListener authStateListener;
    private EditText campoNome;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        verificaAutenticacao();
        view = inflater.inflate(R.layout.games_procurar_fragment,container,false);
        campoNome = view.findViewById(R.id.campo_nome);
        adicionarBotaoPesquisar();
        adicionarBotaoVoltar();
        return view;

    }

    public void verificaAutenticacao(){
        authStateListener = new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() != null){
                    startActivity( new Intent(view.getContext(), MenuPrincipalActivity.class));
                }

            }
        };
    }

    public void adicionarBotaoVoltar(){

        Button botaoVoltar = view.findViewById(R.id.botao_voltar);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View view) {

                getFragmentManager().beginTransaction().
                        replace(R.id.content_frame, new GamesFragment()).commit();

            }

        });

    }

    public void adicionarBotaoPesquisar(){


        Button botaoRemover = view.findViewById(R.id.botao_pesquisar);
        botaoRemover.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                InputMethodManager imm =(InputMethodManager) view.getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(),0);

                Bundle bundle = new Bundle();
                bundle.putString("nomePesquisa",campoNome.getText().toString());
                GamesResultadoPesquisaFragment gamesResultadoPesquisaFragment = new GamesResultadoPesquisaFragment();
                gamesResultadoPesquisaFragment.setArguments(bundle);

                getFragmentManager().beginTransaction().
                        replace(R.id.content_frame, gamesResultadoPesquisaFragment).commit();

            }

        });
    }

}
