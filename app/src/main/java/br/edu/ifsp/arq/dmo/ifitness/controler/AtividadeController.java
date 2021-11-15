package br.edu.ifsp.arq.dmo.ifitness.controler;


import android.content.Context;

import br.edu.ifsp.arq.dmo.ifitness.model.Atividade;

import java.util.ArrayList;
import java.util.List;

public class AtividadeController {

    List<Atividade> atividades;

    public AtividadeController(Context ctx) {
        atividades = new ArrayList<>();
    }

    public List<Atividade> getAtividades() {
        return  atividades;
    }

    public int getTotalMinutos() {
        int contador = 0;
        for(int i = 0; i < atividades.size(); i++) {
            contador += atividades.get(i).getDuracao();
        }
        return contador;
    }

    public void adicioarAtividade(Atividade atividade){
        this.atividades.add(atividade);
    }

    public int qntAtividade() {
        return atividades.size();
    }

    public Double getTotalKM() {
        Double contador = 0.0;
        for(int i = 0; i < atividades.size(); i++) {
            contador += atividades.get(i).getKm();
        }
        return contador;
    }

    public int getTotalCalorias() {
        int contador = 0;
        for(int i = 0; i < atividades.size(); i++) {
            contador += atividades.get(i).getCalorias();
        }
        return contador;
    }

    public void setAtividades(List<Atividade> atividades) {
        this.atividades = atividades;
    }
}