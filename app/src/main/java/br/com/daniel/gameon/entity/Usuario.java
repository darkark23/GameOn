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

    public Usuario(String idAutenticacao, String nomeUsuario, String nomeComlpeto, String frase, List<String> amigos) {
        this.idAutenticacao = idAutenticacao;
        this.nomeUsuario = nomeUsuario;
        this.nomeComlpeto = nomeComlpeto;
        this.frase = frase;
        this.amigos = amigos;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(idAutenticacao, usuario.idAutenticacao) &&
                Objects.equals(idImagem, usuario.idImagem) &&
                Objects.equals(idUsuario, usuario.idUsuario) &&
                Objects.equals(idHorarios, usuario.idHorarios) &&
                Objects.equals(nomeUsuario, usuario.nomeUsuario) &&
                Objects.equals(nomeComlpeto, usuario.nomeComlpeto) &&
                Objects.equals(frase, usuario.frase) &&
                Objects.equals(amigos, usuario.amigos) &&
                Objects.equals(jogos, usuario.jogos) &&
                Objects.equals(sessoes, usuario.sessoes);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idAutenticacao, idImagem, idUsuario, idHorarios, nomeUsuario, nomeComlpeto, frase, amigos, jogos, sessoes);
    }
}