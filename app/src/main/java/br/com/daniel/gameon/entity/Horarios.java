package br.com.daniel.gameon.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Horarios {

    private String idHorarios;
    private String idUsuario;
    private List<String> hoariosInicio = new ArrayList<>();
    private List<String> hoariosFim = new ArrayList<>();
    private String observacao;

    public Horarios() {
    }

    public Horarios(String idHorarios, String idUsuario,List<String> hoariosInicio,List<String> hoariosFim, String observacao) {
        this.idHorarios = idHorarios;
        this.idUsuario = idUsuario;
        this.hoariosFim = hoariosFim;
        this.hoariosInicio = hoariosInicio;
        this.observacao = observacao;
    }

    public Horarios(List<String> hoariosInicio,List<String> hoariosFim, String observacao) {
        this.hoariosFim = hoariosFim;
        this.hoariosInicio = hoariosInicio;
        this.observacao = observacao;
    }

    public String getIdHorarios() {
        return idHorarios;
    }

    public void setIdHorarios(String idHorarios) {
        this.idHorarios = idHorarios;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<String> getHoariosInicio() {
        return hoariosInicio;
    }

    public void setHoariosInicio(List<String> hoariosInicio) {
        this.hoariosInicio = hoariosInicio;
    }

    public List<String> getHoariosFim() {
        return hoariosFim;
    }

    public void setHoariosFim(List<String> hoariosFim) {
        this.hoariosFim = hoariosFim;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horarios horarios = (Horarios) o;
        return Objects.equals(idHorarios, horarios.idHorarios) &&
                Objects.equals(idUsuario, horarios.idUsuario) &&
                Objects.equals(hoariosInicio, horarios.hoariosInicio) &&
                Objects.equals(hoariosFim, horarios.hoariosFim) &&
                Objects.equals(observacao, horarios.observacao);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idHorarios, idUsuario, hoariosInicio, hoariosFim, observacao);
    }
}
