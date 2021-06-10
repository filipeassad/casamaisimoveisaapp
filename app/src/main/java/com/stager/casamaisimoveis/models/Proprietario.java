package com.stager.casamaisimoveis.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Proprietario {

    private Integer id;
    private String nome;
    private String cpf;
    private String observacao_coordenador;
    private List<TelefoneProprietario> telefones;

    public Proprietario(String nome, String cpf, String observacao_coordenador, List<TelefoneProprietario> telefones) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefones = telefones;
        this.observacao_coordenador = observacao_coordenador;
    }

    public Proprietario(JSONObject resposta) {
        try {
            this.id = resposta.has("id") ? resposta.getInt("id") : 0;
            this.nome = resposta.has("nome") ? resposta.getString("nome") : new String();
            this.cpf = resposta.has("cpf") ? resposta.getString("cpf") : new String();
            this.observacao_coordenador = resposta.has("observacao_coordenador") ? resposta.getString("observacao_coordenador") : new String();
            this.telefones = resposta.has("telefones") ? TelefoneProprietario.gerarListaTelefonesProprietarioBuscaImovel(resposta.getJSONArray("telefones")) : new ArrayList<TelefoneProprietario>();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public String getObservacao_coordenador() {
        return observacao_coordenador;
    }

    public void setObservacao_coordenador(String observacao_coordenador) {
        this.observacao_coordenador = observacao_coordenador;
    }

    public JSONObject gerarProprietarioJSON(){
        JSONObject proprietarioJson = new JSONObject();

        try {
            if(this.id != null)
                proprietarioJson.put("id", this.id);
            proprietarioJson.put("nome", this.nome);
            proprietarioJson.put("cpf", this.cpf);
            proprietarioJson.put("observacao_coordenador", this.observacao_coordenador);
            proprietarioJson.put("telefones", TelefoneProprietario.gerarTelefoneProprietarioJSONArray(this.telefones));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return proprietarioJson;
    }
}
