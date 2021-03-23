package com.stager.casamaisimoveis.interfaces;

import com.stager.casamaisimoveis.models.EnderecoRota;
import com.stager.casamaisimoveis.models.Imovel;

public interface MapaRotaInterface {
    public void gerarRota(EnderecoRota enderecoRota);
    public void criarImovel(EnderecoRota enderecoRota);
    public void excluirEndereco(EnderecoRota enderecoRota);
    public void visualizarImovel(Imovel imovel);
    public void editarImovel(Imovel imovel);
}
