package com.stager.casamaisimoveis.fragments.cadastrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stager.casamaisimoveis.R;
import com.stager.casamaisimoveis.models.DadosImovel;
import com.stager.casamaisimoveis.models.ItemSpinner;
import com.stager.casamaisimoveis.utilitarios.MoneyTextWatcher;
import com.stager.casamaisimoveis.utilitarios.MontarSpinners;
import com.stager.casamaisimoveis.utilitarios.PorcentagemTextWatch;
import com.stager.casamaisimoveis.utilitarios.VariaveisEstaticas;

import java.util.List;

public class CadastrarInformacoesImovelFragment extends Fragment {

    private Button btnVoltar;
    private Button btnAvancar;
    private Button btnAvancarSecundario;
    private Spinner spTipoImovel;
    private Spinner spFaseObra;
    private Spinner spEsgoto;
    private Spinner spTipoRua;
    private EditText edtValor;
    private EditText edtHonorario;
    private EditText edtAreaTerreno;
    private EditText edtAreaConstruida;
    private EditText edtObservacao;

    private List<ItemSpinner> tiposImovel;
    private List<ItemSpinner> fasesObra;
    private List<ItemSpinner> esgotos;
    private List<ItemSpinner> tiposRua;

    private ItemSpinner tipoImovelSelecionado;
    private ItemSpinner faseImovelSelecionado;
    private ItemSpinner esgotoImovelSelecionado;
    private ItemSpinner tipoRuaImovelSelecionado;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_informacoes_imovel, container, false);

        btnVoltar  = (Button) view.findViewById(R.id.btnVoltar);
        btnAvancar  = (Button) view.findViewById(R.id.btnAvancar);
        btnAvancarSecundario  = (Button) view.findViewById(R.id.btnAvancarSecundario);

        spTipoImovel = (Spinner) view.findViewById(R.id.spTipoImovel);
        spFaseObra = (Spinner) view.findViewById(R.id.spFaseObra);
        spEsgoto = (Spinner) view.findViewById(R.id.spEsgoto);
        spTipoRua = (Spinner) view.findViewById(R.id.spTipoRua);

        edtValor = (EditText) view.findViewById(R.id.edtValor);
        edtHonorario = (EditText) view.findViewById(R.id.edtHonorario);
        edtAreaTerreno = (EditText) view.findViewById(R.id.edtAreaTerreno);
        edtAreaConstruida = (EditText) view.findViewById(R.id.edtAreaConstruida);
        edtObservacao = (EditText) view.findViewById(R.id.edtObservacao);

        edtValor.addTextChangedListener(new MoneyTextWatcher(edtValor));
        edtHonorario.addTextChangedListener(new PorcentagemTextWatch(edtHonorario));

        eventosBotoes();
        carregarSpinners();

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        if(VariaveisEstaticas.getDadosImovelCadastro() != null){
            VariaveisEstaticas.getDadosImovelCadastro().setDadosImovel(tipoImovelSelecionado.getId(),
                    faseImovelSelecionado.getId(),
                    esgotoImovelSelecionado.getId(),
                    tipoRuaImovelSelecionado.getId(),
                    edtValor.getText().toString(),
                    edtHonorario.getText().toString(),
                    edtAreaTerreno.getText().toString(),
                    edtAreaConstruida.getText().toString(),
                    edtObservacao.getText().toString());
        }else{
            DadosImovel dadosImovel = new DadosImovel(tipoImovelSelecionado.getId(),
                    faseImovelSelecionado.getId(),
                    esgotoImovelSelecionado.getId(),
                    tipoRuaImovelSelecionado.getId(),
                    edtValor.getText().toString(),
                    edtHonorario.getText().toString(),
                    edtAreaTerreno.getText().toString(),
                    edtAreaConstruida.getText().toString(),
                    edtObservacao.getText().toString());
            VariaveisEstaticas.setDadosImovelCadastro(dadosImovel);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        VariaveisEstaticas.getFragmentInterface().alterarTitulo("Cadastrar");

        if(VariaveisEstaticas.getDadosImovelCadastro() != null){
            DadosImovel dadosImovel = VariaveisEstaticas.getDadosImovelCadastro();

            spTipoImovel.setSelection(dadosImovel.getTipo() != null ? dadosImovel.getTipo(): 0);
            spFaseObra.setSelection(dadosImovel.getFase_obra() != null ? dadosImovel.getFase_obra(): 0);
            spEsgoto.setSelection(dadosImovel.getEsgoto() != null ? dadosImovel.getEsgoto(): 0);
            spTipoRua.setSelection(dadosImovel.getTipo_rua() != null ? dadosImovel.getTipo_rua(): 0);

            edtValor.setText(dadosImovel.getValor() != null ? dadosImovel.getValor(): "");
            edtHonorario.setText(dadosImovel.getHonorario() != null ? dadosImovel.getHonorario(): "");
            edtAreaTerreno.setText(dadosImovel.getArea_terreno() != null ? dadosImovel.getArea_terreno(): "");
            edtAreaConstruida.setText(dadosImovel.getArea_construida() != null ? dadosImovel.getArea_construida(): "");
            edtObservacao.setText(dadosImovel.getObservacao() != null ? dadosImovel.getObservacao(): "");
        }
    }

    private void eventosBotoes(){
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VariaveisEstaticas.getFragmentInterface().voltar();
            }
        });

        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avancarFormulario();
            }
        });

        btnAvancarSecundario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                avancarFormulario();
            }
        });
    }

    private void avancarFormulario(){
        VariaveisEstaticas.getFragmentInterface().fecharTeclado();

        if(VariaveisEstaticas.getImovelCadastro().getSituacao_anuncio() != 4 && VariaveisEstaticas.getImovelCadastro().getSituacao_anuncio() != 5){
            if(tipoImovelSelecionado == null || (tipoImovelSelecionado != null && tipoImovelSelecionado.getId() == 0)){
                Toast.makeText(getContext(), "Selecione o tipo do imóvel", Toast.LENGTH_SHORT).show();
                return;
            }

            if(faseImovelSelecionado == null || (faseImovelSelecionado != null && faseImovelSelecionado.getId() == 0)){
                Toast.makeText(getContext(), "Selecione a fase da obra", Toast.LENGTH_SHORT).show();
                return;
            }

            if(esgotoImovelSelecionado == null || (esgotoImovelSelecionado != null && esgotoImovelSelecionado.getId() == 0)){
                Toast.makeText(getContext(), "Selecione o tipo de esgoto", Toast.LENGTH_SHORT).show();
                return;
            }

            if(tipoRuaImovelSelecionado == null || (tipoRuaImovelSelecionado != null && tipoRuaImovelSelecionado.getId() == 0)){
                Toast.makeText(getContext(), "Selecione o tipo de rua", Toast.LENGTH_SHORT).show();
                return;
            }

            if(edtValor.getText().toString().trim().equals("")){
                edtValor.setError("Digite o valor.");
                return;
            }

            if(edtHonorario.getText().toString().trim().equals("")){
                edtHonorario.setError("Digite a porcentagem do honorário.");
                return;
            }

            if(edtAreaTerreno.getText().toString().trim().equals("")){
                edtAreaTerreno.setError("Digite a área do terreno.");
                return;
            }

            if(edtAreaConstruida.getText().toString().trim().equals("")){
                edtAreaConstruida.setError("Digite a área construída.");
                return;
            }
        }

        if(VariaveisEstaticas.getDadosImovelCadastro() != null){
            VariaveisEstaticas.getDadosImovelCadastro().setDadosImovel(tipoImovelSelecionado.getId(),
                    faseImovelSelecionado.getId(),
                    esgotoImovelSelecionado.getId(),
                    tipoRuaImovelSelecionado.getId(),
                    edtValor.getText().toString(),
                    edtHonorario.getText().toString(),
                    edtAreaTerreno.getText().toString(),
                    edtAreaConstruida.getText().toString(),
                    edtObservacao.getText().toString());
        }else{
            DadosImovel dadosImovel = new DadosImovel(tipoImovelSelecionado.getId(),
                    faseImovelSelecionado.getId(),
                    esgotoImovelSelecionado.getId(),
                    tipoRuaImovelSelecionado.getId(),
                    edtValor.getText().toString(),
                    edtHonorario.getText().toString(),
                    edtAreaTerreno.getText().toString(),
                    edtAreaConstruida.getText().toString(),
                    edtObservacao.getText().toString());
            VariaveisEstaticas.setDadosImovelCadastro(dadosImovel);
        }

        VariaveisEstaticas.getFragmentInterface().alterarFragment("CadastrarComposicaoImovel");
    }

    private void carregarSpinners(){
        MontarSpinners montarSpinners = new MontarSpinners();
        tiposImovel = montarSpinners.listarTiposImovel();
        fasesObra = montarSpinners.listarFaseImovel();
        esgotos = montarSpinners.listarTipoEsgoto();
        tiposRua = montarSpinners.listarTipoRua();

        ArrayAdapter adapterTipoImovel = new ArrayAdapter(getContext(),
                R.layout.adapter_item_spinner,
                tiposImovel);

        adapterTipoImovel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoImovel.setAdapter(adapterTipoImovel);

        spTipoImovel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicao, long l) {
                tipoImovelSelecionado = (ItemSpinner) adapterView.getItemAtPosition(posicao);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adapterFaseImovel = new ArrayAdapter(getContext(),
                R.layout.adapter_item_spinner,
                fasesObra);

        adapterFaseImovel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spFaseObra.setAdapter(adapterFaseImovel);

        spFaseObra.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicao, long l) {
                faseImovelSelecionado = (ItemSpinner) adapterView.getItemAtPosition(posicao);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adapterTipoEsgoto = new ArrayAdapter(getContext(),
                R.layout.adapter_item_spinner,
                esgotos);

        adapterTipoEsgoto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEsgoto.setAdapter(adapterTipoEsgoto);

        spEsgoto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicao, long l) {
                esgotoImovelSelecionado = (ItemSpinner) adapterView.getItemAtPosition(posicao);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter adapterTipoRua = new ArrayAdapter(getContext(),
                R.layout.adapter_item_spinner,
                tiposRua);

        adapterTipoRua.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTipoRua.setAdapter(adapterTipoRua);

        spTipoRua.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int posicao, long l) {
                tipoRuaImovelSelecionado = (ItemSpinner) adapterView.getItemAtPosition(posicao);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

}
