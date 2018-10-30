package br.com.daniel.gameon.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Usuario {

    private String idAutenticacao;
    private String idImagem;
    private String idUsuario;
    private String idHorarios;
    private String nomeUsuario;
    private String nomeComlpeto;
    private String frase;
    private List<String> amigos = new ArrayList<>();
    private List<String> jogos = new ArrayList<>();
    private List<String> sessoes = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String idAutenticacao, String idImagem, String idUsuario, String idHorarios, String nomeUsuario, String nomeComlpeto, String frase) {
        this.idAutenticacao = idAutenticacao;
        this.idImagem = idImagem;
        this.idHorarios = idHorarios;
        this.nomeUsuario = nomeUsuario;
        this.nomeComlpeto = nomeComlpeto;
        this.frase = frase;
    }

    public Usuario(String idAutenticacao, String nomeUsuario, String nomeComlpeto, String frase) {
        this.idAutenticacao = idAutenticacao;
        this.nomeUsuario = nomeUsuario;
        this.nomeComlpeto = nomeComlpeto;
        this.frase = frase;
    }

    public String getIdAutenticacao() {
        return idAutenticacao;
    }

    public void setIdAutenticacao(String idAutenticacao) {
        this.idAutenticacao = idAutenticacao;
    }

    public String getIdImagem() {
        return idImagem;
    }

    public void setIdImagem(String idImagem) {
        this.idImagem = idImagem;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdHorarios() {
        return idHorarios;
    }

    public void setIdHorarios(String idHorarios) {
        this.idHorarios = idHorarios;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getNomeComlpeto() {
        return nomeComlpeto;
    }

    public void setNomeComlpeto(String nomeComlpeto) {
        this.nomeComlpeto = nomeComlpeto;
    }

    public String getFrase() {
        return frase;
    }

    public void setFrase(String frase) {
        this.frase = frase;
    }

    public List<String> getAmigos() {
        return amigos;
    }

    public void setAmigos(List<String> amigos) {
        this.amigos = amigos;
    }

    public List<String> getJogos() {
        return jogos;
    }

    public void setJogos(List<String> jogos) {
        this.jogos = jogos;
    }

    public List<String> getSessoes() {
        return sessoes;
    }

    public void setSessoes(List<String> sessoes) {
        this.sessoes = sessoes;
    }

    public void setSessao(String sessao) {
        this.sessoes.add(sessao);
    }

    public void setAmigo(String amigo) {
        this.amigos.add(amigo);
    }

    public void setJogo(String jogo) {
        this.jogos.add(jogo);
    }

    public void removerSessao(String sessao) {
        for (int x = 0; x < this.sessoes.size(); x++){
            if (this.sessoes.get(x).equals(sessao)){
                this.sessoes.remove(x);
                break;
            }
        }
    }

    public void removerAmigo(String amigo) {
        for (int x = 0; x < this.amigos.size(); x++){
            if (this.amigos.get(x).equals(amigo)){
                this.amigos.remove(x);
                break;
            }
        }
    }

    public void removerJogo(String jogo) {
        for (int x = 0; x < this.jogos.size(); x++) {
            if (this.jogos.get(x).equals(jogo)) {
                this.jogos.remove(x);
                break;
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(idAutenticacao, usuario.idAutenticacao) &&
                Objects.equals(idUsuario, usuario.idUsuario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAutenticacao, idUsuario);
    }
}