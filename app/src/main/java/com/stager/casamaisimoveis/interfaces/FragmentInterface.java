package com.stager.casamaisimoveis.interfaces;

import java.util.List;

public interface FragmentInterface {
    public void alterarFragment(String nomeFragment);
    public void alterarTitulo(String nometitulo);
    public void voltar();
    public void sair();
    public void fecharTeclado();
    public void removerFragment(String nomeFragment);
}
