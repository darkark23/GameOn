package br.com.daniel.gameon.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class Sessao {

    private String idSessao;
    private String idUsuarioAdministrador;
    private String idJogo;
    private String nomeSessao;
    private String dataInicio;
    private String dataFim;
    private List<String> usuarios;
    private String ativo;
    private Boolean publico;

    public Sessao() {
    }

    public Sessao(String idSessao, String idUsuarioAdministrador, String idJogo, String nomeSessao, String dataInicio, String dataFim, List<String> usuarios, String ativo, Boolean publico) {
        this.idSessao = idSessao;
        this.idUsuarioAdministrador = idUsuarioAdministrador;
        this.idJogo = idJogo;
        this.nomeSessao = nomeSessao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.usuarios = usuarios;
        this.ativo = ativo;
        this.publico = publico;
    }

    public Sessao(String nomeSessao, String dataInicio, String dataFim, Boolean publico) {
        this.nomeSessao = nomeSessao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.ativo = "S";
        this.publico = publico;
    }



    public String getIdSessao() {
        return idSessao;
    }

    public void setIdSessao(String idSessao) {
        this.idSessao = idSessao;
    }

    public String getIdUsuarioAdministrador() {
        return idUsuarioAdministrador;
    }

    public void setIdUsuarioAdministrador(String idUsuarioAdministrador) {
        this.idUsuarioAdministrador = idUsuarioAdministrador;
    }

    public String getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(String idJogo) {
        this.idJogo = idJogo;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public List<String> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<String> usuarios) {
        this.usuarios = usuarios;
    }

    public String getAtivo() {
        return ativo;
    }

    public void setAtivo(String ativo) {
        this.ativo = ativo;
    }

    public Boolean getPublico() {
        return publico;
    }

    public void setPublico(Boolean publico) {
        this.publico = publico;
    }

    public String getNomeSessao() {
        return nomeSessao;
    }

    public void setNomeSessao(String nomeSessao) {
        this.nomeSessao = nomeSessao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sessao sessao = (Sessao) o;
        return Objects.equals(idSessao, sessao.idSessao);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idSessao);
    }
}
