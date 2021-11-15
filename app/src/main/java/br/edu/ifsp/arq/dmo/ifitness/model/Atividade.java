package br.edu.ifsp.arq.dmo.ifitness.model;

import java.io.Serializable;

public class Atividade implements Serializable {

    private int tipoAtividade;
    private int duracao;
    private Double km;
    private int calorias;

    public Atividade(int tipoAtividade, int duracao, double km, int calorias) {
        this.tipoAtividade = tipoAtividade;
        this.duracao = duracao;
        this.km = km;
        this.calorias = calorias;
    }

    public int getTipoAtividade() {
        return tipoAtividade;
    }

    public int getDuracao() {
        return duracao;
    }

    public Double getKm() {
        return km;
    }

    public int getCalorias() {
        return calorias;
    }

    public void setTipoAtividade(int tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }

    public static String getTipoAtividade(int tipoAtividade){
        String tipo = new String();
        switch (tipoAtividade){
            case 0:
                tipo = "Caminhada";
                break;
            case 1:
                tipo = "Ciclismo";
                break;
            case 2:
                tipo = "Corrida";
                break;
            case 3:
                tipo = "Natação";
                break;
            default:
                break;
        }

        return tipo;
    }

    @Override
    public String toString() {
        return "Atividade{" +
                "tipoAtividade=" + tipoAtividade +
                ", duracao=" + duracao +
                ", km=" + km +
                ", calorias=" + calorias +
                '}';
    }
}

