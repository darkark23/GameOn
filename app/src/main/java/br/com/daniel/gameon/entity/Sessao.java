package br.com.daniel.gameon.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Sessao {

    private String idSessao;
    private String idUsuarioAdministrador;
    private String idJogo;
    private LocalDate dataInicio;
    private LocalDateTime horarioInicio;
    private LocalDate dataFim;
    private LocalDateTime horarioFim;
    private List<String> usuarios;
    private String ativo;
    private Boolean publico;

    public Sessao() {
    }

    public Sessao(String idSessao, String idUsuarioAdministrador, String idJogo, LocalDate dataInicio, LocalDateTime horarioInicio, LocalDate dataFim, LocalDateTime horarioFim, List<String> usuarios, String ativo, Boolean publico) {
        this.idSessao = idSessao;
        this.idUsuarioAdministrador = idUsuarioAdministrador;
        this.idJogo = idJogo;
        this.dataInicio = dataInicio;
        this.horarioInicio = horarioInicio;
        this.dataFim = dataFim;
        this.horarioFim = horarioFim;
        this.usuarios = usuarios;
        this.ativo = ativo;
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

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalDateTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public LocalDateTime getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(LocalDateTime horarioFim) {
        this.horarioFim = horarioFim;
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
