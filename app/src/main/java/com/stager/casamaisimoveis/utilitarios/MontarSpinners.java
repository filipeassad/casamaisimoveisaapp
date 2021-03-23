package com.stager.casamaisimoveis.utilitarios;

import com.stager.casamaisimoveis.models.ItemSpinner;

import java.util.ArrayList;
import java.util.List;

public class MontarSpinners {

    public List<ItemSpinner> listarTiposImovel(){
        List<ItemSpinner> tiposImovel = new ArrayList<>();

        tiposImovel.add(new ItemSpinner(0, "Selecione"));
        tiposImovel.add(new ItemSpinner(1, "Casa Térrea"));
        tiposImovel.add(new ItemSpinner(2, "Casa Condomínio"));
        tiposImovel.add(new ItemSpinner(3, "Sobrado"));
        tiposImovel.add(new ItemSpinner(4, "Sobrado Condomínio"));
        tiposImovel.add(new ItemSpinner(5, "Terreno"));

        return tiposImovel;
    }

    public List<ItemSpinner> listarFaseImovel(){
        List<ItemSpinner> tiposFaseObra = new ArrayList<>();

        tiposFaseObra.add(new ItemSpinner(0, "Selecione"));
        tiposFaseObra.add(new ItemSpinner(1, "Início"));
        tiposFaseObra.add(new ItemSpinner(2, "Laje"));
        tiposFaseObra.add(new ItemSpinner(3, "Reboco"));
        tiposFaseObra.add(new ItemSpinner(4, "Pintura"));
        tiposFaseObra.add(new ItemSpinner(5, "Final de Obra"));
        tiposFaseObra.add(new ItemSpinner(6, "Obra Pronta"));

        return tiposFaseObra;
    }

    public List<ItemSpinner> listarTipoEsgoto(){
        List<ItemSpinner> tiposEsgoto = new ArrayList<>();

        tiposEsgoto.add(new ItemSpinner(0, "Selecione"));
        tiposEsgoto.add(new ItemSpinner(1, "Rede de Esgoto"));
        tiposEsgoto.add(new ItemSpinner(2, "Fossa"));

        return tiposEsgoto;
    }

    public List<ItemSpinner> listarTipoRua(){
        List<ItemSpinner> tiposRua = new ArrayList<>();

        tiposRua.add(new ItemSpinner(0, "Selecione"));
        tiposRua.add(new ItemSpinner(1, "Asfalto"));
        tiposRua.add(new ItemSpinner(2, "Terra"));

        return tiposRua;
    }

    public List<ItemSpinner> listarAmbiente(){
        List<ItemSpinner> ambientesImovel = new ArrayList<>();

        ambientesImovel.add(new ItemSpinner(0, "Selecione"));
        ambientesImovel.add(new ItemSpinner(1, "Quarto"));
        ambientesImovel.add(new ItemSpinner(2, "Suíte"));
        ambientesImovel.add(new ItemSpinner(3, "Closet"));
        ambientesImovel.add(new ItemSpinner(4, "Lavanderia"));
        ambientesImovel.add(new ItemSpinner(5, "Banheiro"));
        ambientesImovel.add(new ItemSpinner(6, "Lavabo"));
        ambientesImovel.add(new ItemSpinner(7, "Área de Serviço/Deposito"));
        ambientesImovel.add(new ItemSpinner(8, "Varanda Gourmet"));
        ambientesImovel.add(new ItemSpinner(9, "Sala de Estar"));
        ambientesImovel.add(new ItemSpinner(10, "Sala de Jantar"));
        ambientesImovel.add(new ItemSpinner(11, "Sala de TV"));
        ambientesImovel.add(new ItemSpinner(12, "Jardom de Inverno"));
        ambientesImovel.add(new ItemSpinner(13, "Escritório"));
        ambientesImovel.add(new ItemSpinner(14, "Cozinha"));
        ambientesImovel.add(new ItemSpinner(15, "Cozinha Americana"));
        ambientesImovel.add(new ItemSpinner(16, "Copa"));
        ambientesImovel.add(new ItemSpinner(17, "Despensa"));
        ambientesImovel.add(new ItemSpinner(18, "Churrasqueira"));
        ambientesImovel.add(new ItemSpinner(19, "Vaga Descoberta"));
        ambientesImovel.add(new ItemSpinner(20, "Vaga Coberta"));
        ambientesImovel.add(new ItemSpinner(21, "Varanda"));
        ambientesImovel.add(new ItemSpinner(22, "Cerca Elétrica"));
        ambientesImovel.add(new ItemSpinner(23, "Concertina"));
        ambientesImovel.add(new ItemSpinner(24, "Alarme"));
        ambientesImovel.add(new ItemSpinner(25, "Motor Portão"));
        ambientesImovel.add(new ItemSpinner(26, "Forro Rebaixado"));
        ambientesImovel.add(new ItemSpinner(27, "Geminada"));
        ambientesImovel.add(new ItemSpinner(28, "Corredor Lateral"));

        return ambientesImovel;
    }

    public List<ItemSpinner> listarAmbientesCheckbox(){
        List<ItemSpinner> ambientesImovel = new ArrayList<>();

        ambientesImovel.add(new ItemSpinner(22, "Cerca Elétrica", 1));
        ambientesImovel.add(new ItemSpinner(9, "Sala de Estar", 1));
        ambientesImovel.add(new ItemSpinner(4, "Lavanderia", 1));
        ambientesImovel.add(new ItemSpinner(23, "Concertina", 1));
        ambientesImovel.add(new ItemSpinner(10, "Sala de Jantar", 1));
        ambientesImovel.add(new ItemSpinner(7, "Área de Serviço", 1));
        ambientesImovel.add(new ItemSpinner(25, "Motor Portão", 1));
        ambientesImovel.add(new ItemSpinner(11, "Sala de TV", 1));
        ambientesImovel.add(new ItemSpinner(29, "Edícula", 1));
        ambientesImovel.add(new ItemSpinner(24, "Alarme", 1));
        ambientesImovel.add(new ItemSpinner(26, "Forro Rebaixado", 1));
        ambientesImovel.add(new ItemSpinner(8, "Varanda Gourmet", 1));
        ambientesImovel.add(new ItemSpinner(28, "Corredor Lateral", 1));
        ambientesImovel.add(new ItemSpinner(14, "Cozinha", 1));
        ambientesImovel.add(new ItemSpinner(18, "Churrasqueira", 1));
        ambientesImovel.add(new ItemSpinner(30, "Jardim", 1));
        ambientesImovel.add(new ItemSpinner(15, "Cozinha Americana", 1));
        ambientesImovel.add(new ItemSpinner(31, "Piscina", 1));
        ambientesImovel.add(new ItemSpinner(20, "01 Vaga Coberta", 1));
        ambientesImovel.add(new ItemSpinner(20, "02 Vagas Coberta", 2));
        ambientesImovel.add(new ItemSpinner(17, "Despensa", 1));
        ambientesImovel.add(new ItemSpinner(19, "01 Vaga Descoberta", 1));
        ambientesImovel.add(new ItemSpinner(19, "02 Vagas Descoberta", 2));
        ambientesImovel.add(new ItemSpinner(5, "Banheiro", 1));
        ambientesImovel.add(new ItemSpinner(1, "01 Quarto", 1));
        ambientesImovel.add(new ItemSpinner(1, "02 Quartos", 2));
        ambientesImovel.add(new ItemSpinner(1, "03 Quartos", 3));
        ambientesImovel.add(new ItemSpinner(2, "01 Suíte", 1));
        ambientesImovel.add(new ItemSpinner(2, "02 Suítes", 2));
        ambientesImovel.add(new ItemSpinner(2, "03 Suítes", 3));
        ambientesImovel.add(new ItemSpinner(3, "01 Closet", 1));
        ambientesImovel.add(new ItemSpinner(3, "02 Closets", 2));
        ambientesImovel.add(new ItemSpinner(3, "03 Closets", 3));
        ambientesImovel.add(new ItemSpinner(32, "Aquecedor Solar", 1));
        ambientesImovel.add(new ItemSpinner(12, "Jardim de Inverno", 1));
        ambientesImovel.add(new ItemSpinner(6, "Lavabo", 1));
        ambientesImovel.add(new ItemSpinner(13, "Escritório", 1));
        ambientesImovel.add(new ItemSpinner(16, "Copa", 1));
        ambientesImovel.add(new ItemSpinner(21, "Varanda", 1));
        ambientesImovel.add(new ItemSpinner(27, "Geminada", 1));

        return ambientesImovel;
    }
}
