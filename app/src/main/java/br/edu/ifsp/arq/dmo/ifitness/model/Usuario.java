package br.edu.ifsp.arq.dmo.ifitness.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(tableName = "usuario")
public class Usuario implements Serializable {

    @NonNull
    @PrimaryKey
    private String id;
    private String nome;
    private String dataNascimento;
    private String sexo;
    private String telefone;
    private String email;
    private String senha;
    private Integer pontuacao;
    private List<Trofeu> emblemas;


    public Usuario(String nome, String dataNascimento, String sexo, String telefone, String email, String senha) {
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.sexo = sexo;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.pontuacao = 0;
        this.emblemas = Collections.emptyList();
    }

    @Ignore
    public Usuario() {
        this("", "", "", "", "", "");
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Integer getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(Integer pontuacao) {
        this.pontuacao = pontuacao;
    }

    public List<Trofeu> getEmblemas() {
        return emblemas;
    }

    public void setEmblemas(List<Trofeu> emblemas) {
        this.emblemas = emblemas;
    }

    public boolean addEmblema(Trofeu emblema) {
        return this.emblemas.add(emblema);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return id.equals(usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
