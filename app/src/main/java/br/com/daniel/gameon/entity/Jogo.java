package br.com.daniel.gameon.entity;

import java.util.List;
import java.util.Objects;

public class Jogo {

    private String idJogo;
    private String idImagem;
    private String nome;
    private String descricao;
    private List<String> jogadores;

    public Jogo() {
    }

    public Jogo(String idJogo, String idImagem, String nome, String descricao, List<String> jogadores) {
        this.idJogo = idJogo;
        this.idImagem = idImagem;
        this.nome = nome;
        this.descricao = descricao;
        this.jogadores = jogadores;
    }

    public String getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(String idJogo) {
        this.idJogo = idJogo;
    }

    public String getIdImagem() {
        return idImagem;
    }

    public void setIdImagem(String idImagem) {
        this.idImagem = idImagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<String> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<String> jogadores) {
        this.jogadores = jogadores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jogo jogo = (Jogo) o;
        return Objects.equals(idJogo, jogo.idJogo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idJogo);
    }
}
