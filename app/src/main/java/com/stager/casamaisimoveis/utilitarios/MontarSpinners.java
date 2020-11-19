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
}
