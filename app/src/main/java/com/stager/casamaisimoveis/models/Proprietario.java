package com.stager.casamaisimoveis.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class Proprietario {

    private Integer id;
    private String nome;
    private String cpf;
    private List<TelefoneProprietario> telefones;

    public Proprietario(String nome, String cpf, List<TelefoneProprietario> telefones) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefones = telefones;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<TelefoneProprietario> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<TelefoneProprietario> telefones) {
        this.telefones = telefones;
    }

    public JSONObject gerarProprietarioJSON(){
        JSONObject rotaJson = new JSONObject();

        try {
            rotaJson.put("nome", this.nome);
            rotaJson.put("cpf", this.cpf);
            rotaJson.put("telefones", TelefoneProprietario.gerarTelefoneProprietarioJSONArray(this.telefones));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return rotaJson;
    }
}
