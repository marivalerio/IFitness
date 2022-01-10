package br.edu.ifsp.arq.dmo.ifitness.model;

import java.util.ArrayList;
import java.util.List;

public class UsuarioComAtividade {

    private Usuario usuario;

    private List<Atividade> atividades;

    public UsuarioComAtividade() {
        this.atividades = new ArrayList<>();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Atividade> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }
}
