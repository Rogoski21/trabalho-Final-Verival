package com.quarteto.o.servico.validacao;

import com.quarteto.o.entidade.ItemVenda;
import com.quarteto.o.repositorio.Estoque;
import com.quarteto.o.repositorio.Produtos;

import java.util.List;

public interface RegraValidacao {
    void valida(Produtos produtos, Estoque estoque, List<ItemVenda> itens);
}
