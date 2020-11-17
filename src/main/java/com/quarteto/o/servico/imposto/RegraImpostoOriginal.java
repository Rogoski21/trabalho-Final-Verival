package com.quarteto.o.servico.imposto;

import com.quarteto.o.entidade.ItemVenda;

import java.util.List;

public class RegraImpostoOriginal implements RegraImposto {
    @Override
    public double calcular(List<ItemVenda> itens) {
        double totalImposto = 0.0;
        for(ItemVenda itemVenda : itens) {
            double valor = itemVenda.getQuantidade() * itemVenda.getValorVendido();
            totalImposto += valor * 0.1;
        }
        return totalImposto;
    }
}
