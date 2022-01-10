package br.edu.ifsp.arq.dmo.ifitness.model;

import androidx.room.Ignore;

import java.io.Serializable;
import java.util.UUID;

public class Atividade implements Serializable {

    private String id;
    private String usuarioId;
    private int tipoAtividade;
    private int duracao;
    private Double km;
    private String dataAtividade;
    private int calorias;

    public Atividade(String usuarioId, int tipoAtividade, int duracao, double km, String dataAtividade, int calorias) {
        this.id = UUID.randomUUID().toString();
        this.usuarioId = usuarioId;
        this.tipoAtividade = tipoAtividade;
        this.duracao = duracao;
        this.km = km;
        this.dataAtividade = dataAtividade;
        this.calorias = calorias;
    }

    @Ignore
    public Atividade() {
        this("", 0, 0, 0, "", 0);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getTipoAtividade() {
        return tipoAtividade;
    }

    public void setTipoAtividade(int tipoAtividade) {
        this.tipoAtividade = tipoAtividade;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public Double getKm() {
        return km;
    }

    public void setKm(Double km) {
        this.km = km;
    }

    public String getDataAtividade() {
        return dataAtividade;
    }

    public void setDataAtividade(String dataAtividade) {
        this.dataAtividade = dataAtividade;
    }

    public int getCalorias() {
        return calorias;
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
}

