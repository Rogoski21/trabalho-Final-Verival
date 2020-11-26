package com.quarteto.o.servico.validacao;

import com.quarteto.o.entidade.ItemEstoque;
import com.quarteto.o.entidade.ItemVenda;
import com.quarteto.o.entidade.Produto;
import com.quarteto.o.excecoes.SistVendasException;
import com.quarteto.o.repositorio.Estoque;
import com.quarteto.o.repositorio.Produtos;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValidacaoHorarioComercialTest {

    @Test
    void validaTresProdutosExistentesNaoLancaExcecao() {
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10, "Prod10", 1000.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30, "Prod30", 2000.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50, "Prod15", 1500.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10, 5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30, 3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50, 15));

        List<ItemVenda> itens = new ArrayList<>(3);
        itens.add(new ItemVenda(1, 10, 2, 1000));
        itens.add(new ItemVenda(2, 30, 3, 2000));
        itens.add(new ItemVenda(3, 50, 1, 1500));

        RegraValidacao regra = new ValidacaoHorarioComercial();
        assertDoesNotThrow(() -> regra.valida(produtos, estoque, itens));
    }

    @Test
    void validaProdutoLancaExcecaoSeNaoExistirNoEstoque() {

        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10, "Prod10", 1000.0));

        Estoque estoque = mock(Estoque.class);

        List<ItemVenda> itens = new ArrayList<>(3);
        itens.add(new ItemVenda(1, 10, 2, 1000));

        RegraValidacao regra = new ValidacaoHorarioComercial();

        assertThrows(SistVendasException.class, () -> regra.valida(produtos, estoque, itens));
    }

    @Test
    void validaProdutoExistenteLancaExcecaoSeNaoTiverQuantidadeSuficiente() {

        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10, "Prod10", 1000.0));

        var estoque1 = mock(ItemEstoque.class);

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(estoque1);

        List<ItemVenda> itens = new ArrayList<>(3);
        itens.add(new ItemVenda(1, 10, 2, 1000));

        when(estoque1.disponivel(anyInt()))
                .thenReturn(false);

        RegraValidacao regra = new ValidacaoHorarioComercial();

        assertThrows(SistVendasException.class, () -> regra.valida(produtos, estoque, itens));
    }

    @Test
    void validaTresProdutosLancaExcecaoMuitoCaro() {
        Produtos produtos = mock(Produtos.class);
        when(produtos.recupera(10)).thenReturn(new Produto(10, "Prod10", 1000.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30, "Prod30", 2000.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50, "Prod15", 1500.0));

        Estoque estoque = mock(Estoque.class);
        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10, 5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30, 3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50, 15));

        List<ItemVenda> itens = new ArrayList<>(3);
        itens.add(new ItemVenda(1, 10, 2, 1000));
        itens.add(new ItemVenda(2, 30, 3, 2000));
        itens.add(new ItemVenda(3, 50, 1, 1500));

        RegraValidacao regra = new ValidacaoForaHorarioComercial();
        assertThrows(SistVendasException.class, () -> regra.valida(produtos, estoque, itens));
    }
}