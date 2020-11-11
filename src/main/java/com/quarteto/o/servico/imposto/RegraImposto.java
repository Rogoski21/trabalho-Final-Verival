package com.quarteto.o.servico.imposto;

import com.quarteto.o.entidade.ItemVenda;

import java.util.List;

public interface RegraImposto {
    double calcular(List<ItemVenda> itens);
}
