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
        List<ItemSpinner> tiposImovel = new ArrayList<>();

        tiposImovel.add(new ItemSpinner(0, "Selecione"));
        tiposImovel.add(new ItemSpinner(1, "Início"));
        tiposImovel.add(new ItemSpinner(2, "Laje"));
        tiposImovel.add(new ItemSpinner(3, "Reboco"));
        tiposImovel.add(new ItemSpinner(4, "Pintura"));
        tiposImovel.add(new ItemSpinner(5, "Final de Obra"));
        tiposImovel.add(new ItemSpinner(6, "Obra Pronta"));
        tiposImovel.add(new ItemSpinner(7, "Rede de Esgoto"));
        tiposImovel.add(new ItemSpinner(8, "Fossa"));
        tiposImovel.add(new ItemSpinner(9, "Rua de Asfalto"));
        tiposImovel.add(new ItemSpinner(10, "Rua de Terra"));

        return tiposImovel;
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
}
