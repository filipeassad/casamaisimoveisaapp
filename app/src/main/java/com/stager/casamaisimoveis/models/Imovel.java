package com.stager.casamaisimoveis.models;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Imovel {

    private Integer id;
    private Integer endereco_id;
    private Integer proprietario_id;
    private Integer dados_imovel_id;

    private EnderecoImovel enderecoImovel;
    private Proprietario proprietario;
    private DadosImovel dadosImovel;

    public Imovel(JSONObject resposta) {
        try {
            this.id = resposta.has("id") ? resposta.getInt("id") : 0;
            this.endereco_id = resposta.has("endereco_id") ? resposta.getInt("endereco_id") : 0;
            this.proprietario_id = resposta.has("proprietario_id") ? resposta.getInt("proprietario_id") : 0;
            this.dados_imovel_id = resposta.has("dados_imovel_id") ? resposta.getInt("dados_imovel_id") : 0;
            this.enderecoImovel = resposta.has("endereco") ?  new EnderecoImovel(resposta.getJSONObject("endereco")) : null;
            this.proprietario = resposta.has("proprietario") ? new Proprietario(resposta.getJSONObject("proprietario")) : null;
            this.dadosImovel = resposta.has("dadosImovel") ? new DadosImovel(resposta.getJSONObject("dadosImovel")) : null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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
            jsonObject.put("dadosImovel", this.dadosImovel.gerarDadosImovelJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject gerarImovelComEnderecoRotaJson(EnderecoRota enderecoRota){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("enderecoImovel", this.enderecoImovel.gerarEnderecoImovelJSON());
            jsonObject.put("proprietarioImovel", this.proprietario.gerarProprietarioJSON());
            jsonObject.put("dadosImovel", this.dadosImovel.gerarDadosImovelJson());
            jsonObject.put("enderecoRota",enderecoRota.gerarEnderecoRotaJSON());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static List<Imovel> gerarListaImoveisBuscaImovel(JSONObject resposta){
        List<Imovel> imoveis = new ArrayList<>();

        try {
            if(resposta.has("array")) {
                JSONArray arrayResposta = resposta.getJSONArray("array");

                for(int i = 0; i < arrayResposta.length(); i++){
                    JSONObject itemResposta = arrayResposta.getJSONObject(i);
                    imoveis.add(new Imovel(itemResposta));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return imoveis;
    }

}
