package com.bcopstein;

import java.util.List;

public class ValidacaoForaHorarioComercial implements RegraValidacao {
    @Override
    public void valida(Produtos produtos, Estoque estoque, List<ItemVenda> itens) throws SistVendasException {
        if (itens.size()>5){
            throw new SistVendasException(SistVendasException.Causa.VENDA_COM_EXCESSO_DE_ITENS);
        }
        for (ItemVenda iv : itens) {
            final Produto produto = produtos.recupera(iv.getCodigoProduto());
            final int quantidade = iv.getQuantidade();
            ItemEstoque itemEstoque = estoque.recupera(produto.getCodigo());
            if (itemEstoque == null)
                throw new SistVendasException(SistVendasException.Causa.PRODUTO_NAO_CADASTRADO_ESTOQUE);
            if (!itemEstoque.disponivel(quantidade))
                throw new SistVendasException(SistVendasException.Causa.QUANTIDADE_INSUFICIENTE);
            if ((produto.getPreco() * iv.getQuantidade()) > 5000.0){
                throw new SistVendasException(SistVendasException.Causa.VENDA_COM_ITEM_MUITO_CARO);
            }
        }       
    }
}
