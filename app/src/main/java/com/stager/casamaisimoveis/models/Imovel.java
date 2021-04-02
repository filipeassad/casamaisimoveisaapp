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
    private Integer situacao_anuncio;

    private EnderecoImovel enderecoImovel;
    private Proprietario proprietario;
    private DadosImovel dadosImovel;
    private List<ImagemImovel> imagensImovel;

    public Imovel(JSONObject resposta) {
        try {
            this.id = resposta.has("id") ? resposta.getInt("id") : 0;
            this.endereco_id = resposta.has("endereco_id") ? resposta.getInt("endereco_id") : 0;
            this.proprietario_id = resposta.has("proprietario_id") ? resposta.getInt("proprietario_id") : 0;
            this.dados_imovel_id = resposta.has("dados_imovel_id") ? resposta.getInt("dados_imovel_id") : 0;
            this.enderecoImovel = resposta.has("endereco") ?  new EnderecoImovel(resposta.getJSONObject("endereco")) : null;
            this.proprietario = resposta.has("proprietario") ? new Proprietario(resposta.getJSONObject("proprietario")) : null;
            this.dadosImovel = resposta.has("dadosImovel") ? new DadosImovel(resposta.getJSONObject("dadosImovel")) : null;
            this.imagensImovel = resposta.has("imagensImovel") ? ImagemImovel.gerarListaImagemImovelBuscaImovel(resposta.getJSONArray("imagensImovel")) : null;
            this.situacao_anuncio = resposta.has("situacao_anuncio") ? resposta.getInt("situacao_anuncio") : 0;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Imovel(Integer situacao_anuncio) {
        this.situacao_anuncio = situacao_anuncio;
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

    public List<ImagemImovel> getImagensImovel() {
        return imagensImovel;
    }

    public void setImagensImovel(List<ImagemImovel> imagensImovel) {
        this.imagensImovel = imagensImovel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEndereco_id() {
        return endereco_id;
    }

    public void setEndereco_id(Integer endereco_id) {
        this.endereco_id = endereco_id;
    }

    public Integer getProprietario_id() {
        return proprietario_id;
    }

    public void setProprietario_id(Integer proprietario_id) {
        this.proprietario_id = proprietario_id;
    }

    public Integer getDados_imovel_id() {
        return dados_imovel_id;
    }

    public void setDados_imovel_id(Integer dados_imovel_id) {
        this.dados_imovel_id = dados_imovel_id;
    }

    public Integer getSituacao_anuncio() {
        return situacao_anuncio;
    }

    public void setSituacao_anuncio(Integer situacao_anuncio) {
        this.situacao_anuncio = situacao_anuncio;
    }

    public void preencherImovel(EnderecoImovel enderecoImovel, Proprietario proprietario, DadosImovel dadosImovel){
        this.enderecoImovel = enderecoImovel;
        this.proprietario = proprietario;
        this.dadosImovel = dadosImovel;
    }

    public JSONObject gerarImovelJson(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("enderecoImovel", this.enderecoImovel.gerarEnderecoImovelJSON());
            jsonObject.put("proprietarioImovel", this.proprietario.gerarProprietarioJSON());
            jsonObject.put("dadosImovel", this.dadosImovel.gerarDadosImovelJson());
            jsonObject.put("situacao_anuncio", this.getSituacao_anuncio());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public JSONObject gerarImovelSituacaoAnuncioJson(){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("endereco_id", this.endereco_id);
            jsonObject.put("proprietario_id", this.proprietario_id);
            jsonObject.put("dados_imovel_id", this.dados_imovel_id);
            jsonObject.put("situacao_anuncio", this.getSituacao_anuncio());
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
            jsonObject.put("situacao_anuncio", this.getSituacao_anuncio());
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
