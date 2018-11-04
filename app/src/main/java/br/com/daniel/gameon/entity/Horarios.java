package br.com.daniel.gameon.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Horarios {

    private String idHorarios;
    private String idUsuario;
    private Map<String,String> hoarios = new HashMap<>();
    private String observacao;

    public Horarios() {
    }

    public Horarios(String idHorarios, String idUsuario, Map<String,String> hoarios, String observacao) {
        this.idHorarios = idHorarios;
        this.idUsuario = idUsuario;
        this.hoarios = hoarios;
        this.observacao = observacao;
    }

    public Horarios(Map<String,String> hoarios, String observacao) {
        this.hoarios = hoarios;
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

    public Map<String,String> getHoarios() {
        return hoarios;
    }

    public void setHoarios(Map<String,String> hoarios) {
        this.hoarios = hoarios;
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
        return Objects.equals(idHorarios, horarios.idHorarios);
    }

    @Override
    public int hashCode() {

        return Objects.hash(idHorarios);
    }
}
