package com.stager.casamaisimoveis.utilitarios;

import android.graphics.Bitmap;

import com.stager.casamaisimoveis.interfaces.FragmentInterface;
import com.stager.casamaisimoveis.interfaces.ImagemImovelInterface;
import com.stager.casamaisimoveis.interfaces.TelaInicialInterface;
import com.stager.casamaisimoveis.models.Autenticacao;
import com.stager.casamaisimoveis.models.Captador;
import com.stager.casamaisimoveis.models.Composicao;
import com.stager.casamaisimoveis.models.Coordenador;
import com.stager.casamaisimoveis.models.DadosImovel;
import com.stager.casamaisimoveis.models.EnderecoImovel;
import com.stager.casamaisimoveis.models.EnderecoRota;
import com.stager.casamaisimoveis.models.ImagemUpload;
import com.stager.casamaisimoveis.models.Imovel;
import com.stager.casamaisimoveis.models.Proprietario;
import com.stager.casamaisimoveis.models.Rota;
import com.stager.casamaisimoveis.models.RotaCaptador;
import com.stager.casamaisimoveis.models.VisitaImovel;

import java.util.ArrayList;
import java.util.List;

public class VariaveisEstaticas {

    private static Autenticacao autenticacao;
    private static Captador captador;
    private static Captador captadorHistorico;
    private static Coordenador coordenador;
    private static Rota rotaSelecionada;
    private static Proprietario proprietarioCadastro;
    private static EnderecoImovel enderecoImovelCadastro;
    private static DadosImovel dadosImovelCadastro;
    private static List<Composicao> composicoesImovelCadastro;
    private static VisitaImovel visitaImovelCadastro;
    private static EnderecoRota enderecoRotaSelecionado;
    private static RotaCaptador rotaCaptadorHistoricoSelecionado;
    private static Imovel imovelBusca;
    private static List<Bitmap> imagensImovelCadastro;
    private static Imovel imovelCadastro;
    private static int contadorEnvioRotaCaptador = 0;

    private static TelaInicialInterface telaInicialInterface;
    private static FragmentInterface fragmentInterface;
    private static ImagemImovelInterface imagemImovelInterface;

    private static List<ImagemUpload> imagensUpload = new ArrayList<>();

    public static Imovel getImovelCadastro() {
        return imovelCadastro;
    }

    public static void setImovelCadastro(Imovel imovelCadastro) {
        VariaveisEstaticas.imovelCadastro = imovelCadastro;
    }

    public static Captador getCaptadorHistorico() {
        return captadorHistorico;
    }

    public static void setCaptadorHistorico(Captador captadorHistorico) {
        VariaveisEstaticas.captadorHistorico = captadorHistorico;
    }

    public static List<Bitmap> getImagensImovelCadastro() {
        return imagensImovelCadastro;
    }

    public static void setImagensImovelCadastro(List<Bitmap> imagensImovelCadastro) {
        VariaveisEstaticas.imagensImovelCadastro = imagensImovelCadastro;
    }

    public static ImagemImovelInterface getImagemImovelInterface() {
        return imagemImovelInterface;
    }

    public static void setImagemImovelInterface(ImagemImovelInterface imagemImovelInterface) {
        VariaveisEstaticas.imagemImovelInterface = imagemImovelInterface;
    }

    public static Imovel getImovelBusca() {
        return imovelBusca;
    }

    public static void setImovelBusca(Imovel imovelBusca) {
        VariaveisEstaticas.imovelBusca = imovelBusca;
    }

    public static RotaCaptador getRotaCaptadorHistoricoSelecionado() {
        return rotaCaptadorHistoricoSelecionado;
    }

    public static void setRotaCaptadorHistoricoSelecionado(RotaCaptador rotaCaptadorHistoricoSelecionado) {
        VariaveisEstaticas.rotaCaptadorHistoricoSelecionado = rotaCaptadorHistoricoSelecionado;
    }

    public static EnderecoRota getEnderecoRotaSelecionado() {
        return enderecoRotaSelecionado;
    }

    public static void setEnderecoRotaSelecionado(EnderecoRota enderecoRotaSelecionado) {
        VariaveisEstaticas.enderecoRotaSelecionado = enderecoRotaSelecionado;
    }

    public static VisitaImovel getVisitaImovelCadastro() {
        return visitaImovelCadastro;
    }

    public static void setVisitaImovelCadastro(VisitaImovel visitaImovelCadastro) {
        VariaveisEstaticas.visitaImovelCadastro = visitaImovelCadastro;
    }

    public static List<Composicao> getComposicoesImovelCadastro() {
        return composicoesImovelCadastro;
    }

    public static void setComposicoesImovelCadastro(List<Composicao> composicoesImovelCadastro) {
        VariaveisEstaticas.composicoesImovelCadastro = composicoesImovelCadastro;
    }

    public static DadosImovel getDadosImovelCadastro() {
        return dadosImovelCadastro;
    }

    public static void setDadosImovelCadastro(DadosImovel dadosImovelCadastro) {
        VariaveisEstaticas.dadosImovelCadastro = dadosImovelCadastro;
    }

    public static EnderecoImovel getEnderecoImovelCadastro() {
        return enderecoImovelCadastro;
    }

    public static void setEnderecoImovelCadastro(EnderecoImovel enderecoImovelCadastro) {
        VariaveisEstaticas.enderecoImovelCadastro = enderecoImovelCadastro;
    }

    public static Proprietario getProprietarioCadastro() {
        return proprietarioCadastro;
    }

    public static void setProprietarioCadastro(Proprietario proprietarioCadastro) {
        VariaveisEstaticas.proprietarioCadastro = proprietarioCadastro;
    }

    public static Rota getRotaSelecionada() {
        return rotaSelecionada;
    }

    public static void setRotaSelecionada(Rota rotaSelecionada) {
        VariaveisEstaticas.rotaSelecionada = rotaSelecionada;
    }

    public static FragmentInterface getFragmentInterface() {
        return fragmentInterface;
    }

    public static void setFragmentInterface(FragmentInterface fragmentInterface) {
        VariaveisEstaticas.fragmentInterface = fragmentInterface;
    }

    public static TelaInicialInterface getTelaInicialInterface() {
        return telaInicialInterface;
    }

    public static void setTelaInicialInterface(TelaInicialInterface telaInicialInterface) {
        VariaveisEstaticas.telaInicialInterface = telaInicialInterface;
    }

    public static Captador getCaptador() {
        return captador;
    }

    public static void setCaptador(Captador captador) {
        VariaveisEstaticas.captador = captador;
    }

    public static Coordenador getCoordenador() {
        return coordenador;
    }

    public static void setCoordenador(Coordenador coordenador) {
        VariaveisEstaticas.coordenador = coordenador;
    }

    public static Autenticacao getAutenticacao() {
        return autenticacao;
    }

    public static void setAutenticacao(Autenticacao autenticacao) {
        VariaveisEstaticas.autenticacao = autenticacao;
    }

    public static int getContadorEnvioRotaCaptador() {
        return contadorEnvioRotaCaptador;
    }

    public static void setContadorEnvioRotaCaptador() {
        contadorEnvioRotaCaptador++;
    }

    public static void zerarContadorEnvioRotaCaptador() {
        contadorEnvioRotaCaptador = 0;
    }

    public static void setContadorEnvioRotaCaptador(int contadorEnvioRotaCaptador) {
        VariaveisEstaticas.contadorEnvioRotaCaptador = contadorEnvioRotaCaptador;
    }

    public static List<ImagemUpload> getImagensUpload() {
        return imagensUpload;
    }

    public static void setImagensUpload(List<ImagemUpload> imagensUpload) {
        VariaveisEstaticas.imagensUpload = imagensUpload;
    }
}