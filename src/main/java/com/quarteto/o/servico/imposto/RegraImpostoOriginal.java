package com.quarteto.o.servico.imposto;

import com.quarteto.o.entidade.ItemVenda;

import java.util.List;

public class RegraImpostoOriginal implements RegraImposto {
    @Override
    public double calcular(List<ItemVenda> itens) {
        double soma = itens.stream().mapToDouble(it->it.getValorVendido() * it.getQuantidade()).sum();
        return soma * 0.1;
    }
}