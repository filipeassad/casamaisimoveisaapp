package com.stager.casamaisimoveis.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Imovel {

    private EnderecoImovel enderecoImovel;
    private Proprietario proprietario;
    private DadosImovel dadosImovel;

    public Imovel(EnderecoImovel enderecoImovel, Proprietario proprietario, DadosImovel dadosImovel) {
        this.enderecoImovel = enderecoImovel;
        this.proprietario = proprietario;
        this.dadosImovel = dadosImovel;
    }

    public EnderecoImovel getEnderecoImovel() {
        return enderecoImovel;
    }

    public void setEnderecoImovel(EnderecoImovel enderecoImovel) {
        this.enderecoImovel = enderecoImovel;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public DadosImovel getDadosImovel() {
        return dadosImovel;
    }

    public void setDadosImovel(DadosImovel dadosImovel) {
        this.dadosImovel = dadosImovel;
    }

    public JSONObject gerarImovelJson(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("enderecoImovel", this.enderecoImovel.gerarEnderecoImovelJSON());
            jsonObject.put("proprietarioImovel", this.proprietario.gerarProprietarioJSON());
            jsonObject.put("enderecoImovel", this.dadosImovel.gerarDadosImovelJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

}
