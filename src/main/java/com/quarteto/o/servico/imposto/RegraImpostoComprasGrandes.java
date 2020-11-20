package com.quarteto.o.servico.imposto;

import com.quarteto.o.entidade.ItemVenda;

import java.util.List;

public class RegraImpostoComprasGrandes implements RegraImposto {
    @Override
    public double calcular(List<ItemVenda> itens) {
        // Ordena a lista em ordem crescente do custo dos itens
        itens.sort((it1,it2)->
                (int)((it2.getValorVendido()*it2.getQuantidade()) - (it1.getValorVendido()*it1.getQuantidade())));
        // Calcula 10% para os 3 itens mais caros (se houverem)
        int qtdade = Math.min(3,itens.size());
        double imposto = 0.0;
        for(int i=0;i<qtdade;i++){
            ItemVenda item = itens.get(i);
            imposto += (item.getValorVendido() * item.getQuantidade() * 0.1);
        }
        // Calcula o imposto sobre os demais itens se houverem
        for(int i=qtdade;i<itens.size();i++){
            ItemVenda item = itens.get(i);
            imposto += (item.getValorVendido() * item.getQuantidade() * 0.05);
        }
        return imposto;
    }
}