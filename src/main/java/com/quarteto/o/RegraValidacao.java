package com.quarteto.o;

import java.util.List;

public interface RegraValidacao {
    void valida(Produtos produtos, Estoque estoque, List<ItemVenda> itens) throws SistVendasException;
}
