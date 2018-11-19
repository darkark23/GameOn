package br.com.daniel.gameon.fragments;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import br.com.daniel.gameon.R;
import br.com.daniel.gameon.activities.MenuPrincipalActivity;
import br.com.daniel.gameon.entity.Jogo;
import br.com.daniel.gameon.entity.Sessao;
import br.com.daniel.gameon.entity.Usuario;
import br.com.daniel.gameon.util.DataUtil;
import br.com.daniel.gameon.util.FormatUtil;
import br.com.daniel.gameon.util.SimNaoUtil;

public class SessoesCriarFragment extends Fragment {

    View view;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseAuth.AuthStateListener authStateListener;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    private String idUsuarioAtual;
    private Calendar dataInicioTemp;
    private Calendar dataFimTemp;

    private Usuario usuarioAtual;
    private Sessao sessao;
    private List<Jogo> listaGames;
    private List<String> listaGamesNome;

    private EditText nomeSessaoEditText;
    private AutoCompleteTextView gamesAutoCompleteTextView;
    private Spinner publicoSpinner;

    private TextView dataInicio;
    private TextView horarioInicio;
    private TextView dataFim;
    private TextView horarioFim;

    private Button botaoCancelar;
    private Button botaoSalvar;
    private Button botaoDataInicio;
    private Button botaoDataFim;
    private Button botaoHorarioInicio;
    private Button botaoHorarioFim;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        verificaAutenticacao();
        carregarInterface(inflater,container);
        carregarUsuarioAtual();
        return view;

    }


    public void carregarInterface(LayoutInflater inflater,ViewGroup container){

        view = inflater.inflate(R.layout.sessoes_criar_fragment,container,false);

        nomeSessaoEditText = view.findViewById(R.id.campo_nome);

        gamesAutoCompleteTextView = view.findViewById(R.id.jogo_text_view);
        carregarListaGames();


        publicoSpinner = view.findViewById(R.id.publico_spinner);
        ArrayAdapter<String> arrayAdapterSimNao = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_spinner_item,SimNaoUtil.listaSimNao());
        publicoSpinner.setAdapter(arrayAdapterSimNao);

        dataInicio = view.findViewById(R.id.data_inicio_text_view);
        horarioInicio = view.findViewById(R.id.horario_inicio_text_view);
        dataFim = view.findViewById(R.id.data_fim_text_view);
        horarioFim = view.findViewById(R.id.horario_fim_text_view);

        botaoCancelar = view.findViewById(R.id.botao_cancelar);
        botaoSalvar = view.findViewById(R.id.botao_salvar);

        dataInicioTemp = Calendar.getInstance();
        dataFimTemp = Calendar.getInstance();


        dataInicio.setText(DataUtil.formatCalendarData(dataInicioTemp));
        horarioInicio.setText(DataUtil.formatCalendarHorario(dataFimTemp));
        dataFim.setText(DataUtil.formatCalendarData(dataInicioTemp));
        horarioFim.setText(DataUtil.formatCalendarHorario(dataFimTemp));

        botaoDataInicio = view.findViewById(R.id.botao_data_inicio);
        carregarBotaoDataInicio();

        botaoDataFim = view.findViewById(R.id.botao_data_fim);
        carregarBotaoDataFim();

        botaoHorarioInicio = view.findViewById(R.id.botao_hora_inicio);
        carregarBotaoHorarioInicio();

        botaoHorarioFim = view.findViewById(R.id.botao_hora_fim);
        carregarBotaoHorarioFim();

        carregarBotaoSalvar();
        carregarBotaoCancelar();

    }

    public void carregarListaGames(){

        databaseReference.child("jogos").orderByChild("nome").addValueEventListener(new ValueEventListener() {



            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                listaGames = new ArrayList<Jogo>();
                listaGamesNome = new ArrayList<String>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child: children) {
                    Jogo jogo = child.getValue(Jogo.class);
                    listaGames.add(jogo);
                    listaGamesNome.add(jogo.getNome());
                }

                ArrayAdapter<String> arrayAdapterjogos = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_dropdown_item_1line,listaGamesNome);
                gamesAutoCompleteTextView.setAdapter(arrayAdapterjogos);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    public void carregarBotaoDataInicio(){

        botaoDataInicio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Integer ano = dataInicioTemp.get(Calendar.YEAR);
                Integer mes = dataInicioTemp.get(Calendar.MONTH);
                Integer dia = dataInicioTemp.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),android.R.style.Theme_Holo_Dialog_MinWidth,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                        dataInicioTemp.set(Calendar.YEAR,ano);
                        dataInicioTemp.set(Calendar.MONTH,mes);
                        dataInicioTemp.set(Calendar.DAY_OF_MONTH,dia);
                        dataInicio.setText(DataUtil.formatCalendarData(dataInicioTemp));
                    }
                },ano,mes,dia);
                datePickerDialog.show();
            }

        });
    }

    public void carregarBotaoHorarioInicio(){

        botaoHorarioInicio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Integer hora = dataInicioTemp.get(Calendar.HOUR_OF_DAY);
                Integer minuto = dataInicioTemp.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hora, int minuto) {
                        dataInicioTemp.set(Calendar.HOUR_OF_DAY,hora);
                        dataInicioTemp.set(Calendar.MINUTE,minuto);
                        horarioInicio.setText(DataUtil.formatCalendarHorario(dataInicioTemp));
                    }
                },hora,minuto,true);
                timePickerDialog.show();
            }

        });

    }

    public void carregarBotaoDataFim(){

        botaoDataFim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Integer ano = dataFimTemp.get(Calendar.YEAR);
                Integer mes = dataFimTemp.get(Calendar.MONTH);
                Integer dia = dataFimTemp.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(view.getContext(),android.R.style.Theme_Holo_Dialog_MinWidth,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int ano, int mes, int dia) {
                        dataFimTemp.set(Calendar.YEAR,ano);
                        dataFimTemp.set(Calendar.MONTH,mes);
                        dataFimTemp.set(Calendar.DAY_OF_MONTH,dia);
                        dataFim.setText(DataUtil.formatCalendarData(dataFimTemp));
                    }
                },ano,mes,dia);
                datePickerDialog.show();
            }

        });

    }

    public void carregarBotaoHorarioFim(){

        botaoHorarioFim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Integer hora = dataFimTemp.get(Calendar.HOUR_OF_DAY);
                Integer minuto = dataFimTemp.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(view.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hora, int minuto) {
                        dataFimTemp.set(Calendar.HOUR_OF_DAY,hora);
                        dataFimTemp.set(Calendar.MINUTE,minuto);
                        horarioFim.setText(DataUtil.formatCalendarHorario(dataFimTemp));
                    }
                },hora,minuto,true);
                timePickerDialog.show();
            }

        });

    }

    public void carregarBotaoSalvar(){

        botaoSalvar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                sessao = new Sessao();
                sessao.setDataFim(DataUtil.formatCalendarBanco(dataFimTemp));
                sessao.setDataInicio(DataUtil.formatCalendarBanco(dataInicioTemp));
                sessao.setIdUsuarioAdministrador(usuarioAtual.getIdUsuario());
                sessao.setAtivo("S");
                sessao.setNomeSessao(nomeSessaoEditText.getText().toString());
                if (publicoSpinner.getSelectedItem().toString().equals("Sim")){
                    sessao.setPublico(true);
                } else {
                    sessao.setPublico(false);
                }

                sessao.getUsuarios().add(usuarioAtual.getIdUsuario());

                sessao.setIdSessao(databaseReference.child("sessoes").push().getKey());

                String nomeJogo = gamesAutoCompleteTextView.getText().toString();
                Integer index = listaGamesNome.indexOf(nomeJogo);

                sessao.setIdJogo(listaGames.get(index).getIdJogo());

                databaseReference.child("sessoes").child(sessao.getIdSessao()).setValue(sessao);

                usuarioAtual.getSessoes().add(sessao.getIdSessao());
                databaseReference.child("usuarios").child(usuarioAtual.getIdUsuario()).setValue(usuarioAtual);

                getFragmentManager().beginTransaction().
                        replace(R.id.content_frame, new SessoesProcuraFragment()).commit();
            }

        });

    }

    public void carregarBotaoCancelar(){

        botaoCancelar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().
                        replace(R.id.content_frame, new SessoesProcuraFragment()).commit();
            }

        });

    }

    public void carregarUsuarioAtual(){

        idUsuarioAtual = firebaseAuth.getCurrentUser().getUid();

        databaseReference.child("usuarios").orderByChild("idAutenticacao").equalTo(idUsuarioAtual).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                usuarioAtual = children.iterator().next().getValue(Usuario.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });

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



}
