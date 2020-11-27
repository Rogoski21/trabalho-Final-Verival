package com.quarteto.o.servico.validacao;

import com.quarteto.o.entidade.ItemVenda;
import com.quarteto.o.excecoes.SistVendasException;
import com.quarteto.o.entidade.ItemEstoque;
import com.quarteto.o.entidade.Produto;
import com.quarteto.o.repositorio.Estoque;
import com.quarteto.o.repositorio.Produtos;

import java.util.List;

public class ValidacaoHorarioComercial implements RegraValidacao {

    @Override
    public void valida(Produtos produtos, Estoque estoque, List<ItemVenda> itens) {
        for (ItemVenda iv : itens) {
            final Produto produto = produtos.recupera(iv.getCodigoProduto());
            final int quantidade = iv.getQuantidade();
            ItemEstoque itemEstoque = estoque.recupera(produto.getCodigo());
            if (itemEstoque == null)
                throw new SistVendasException(SistVendasException.Causa.PRODUTO_NAO_CADASTRADO_ESTOQUE);
            if (!itemEstoque.disponivel(quantidade))
                throw new SistVendasException(SistVendasException.Causa.QUANTIDADE_INSUFICIENTE);
        }
    }
}
