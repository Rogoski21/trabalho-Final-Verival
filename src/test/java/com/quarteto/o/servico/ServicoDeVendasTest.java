package com.quarteto.o.servico;

import com.quarteto.o.entidade.ItemVenda;
import com.quarteto.o.entidade.Produto;
import com.quarteto.o.repositorio.Estoque;
import com.quarteto.o.repositorio.Produtos;
import com.quarteto.o.servico.imposto.RegraImposto;
import com.quarteto.o.servico.validacao.FactoryValidacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ServicoDeVendasTest {
    private ServicoDeVendas servicoDeVendas;
    private RegraImposto regraImposto;
    private Produtos produtos;
    private Estoque estoque;
    private FactoryValidacao factoryValidacao;
    private List<ItemVenda> lista = new ArrayList<>();

    @BeforeEach
    void setUp() {
        regraImposto = mock(RegraImposto.class);
        produtos = mock(Produtos.class);
        estoque = mock(Estoque.class);
        factoryValidacao = mock(FactoryValidacao.class);
        servicoDeVendas = new ServicoDeVendas(produtos, estoque, regraImposto, factoryValidacao);
    }

    @Test
    void calculaSubtotal() {
        ItemVenda p1 = new ItemVenda(1, 10, 4, 2);
        ItemVenda p2 = new ItemVenda(1, 10, 5, 3);
        ItemVenda p3 = new ItemVenda(1, 10, 6, 4);

        lista.add(p1);
        lista.add(p2);
        lista.add(p3);

        int somatorio = servicoDeVendas.calculaSubtotal(lista);

        assertEquals(47, somatorio);

    }

    @Test
    void calculaImpostos() {
        ItemVenda p1 = new ItemVenda(1, 10, 4, 2);
        ItemVenda p2 = new ItemVenda(1, 10, 5, 3);
        ItemVenda p3 = new ItemVenda(1, 10, 6, 4);

        lista.add(p1);
        lista.add(p2);
        lista.add(p3);

        when(regraImposto.calcular(lista)).thenReturn(4.70D);
        Integer valorImposto = servicoDeVendas.calculaImpostos(lista);

        assertEquals(4, valorImposto);

    }

    @Test
    void calculaPrecoFinal() {
        ItemVenda p1 = new ItemVenda(1, 10, 4, 2);
        ItemVenda p2 = new ItemVenda(1, 10, 5, 3);
        ItemVenda p3 = new ItemVenda(1, 10, 6, 4);

        lista.add(p1);
        lista.add(p2);
        lista.add(p3);

        when(regraImposto.calcular(lista)).thenReturn(4.70D);
        Integer preco = servicoDeVendas.calculaPrecoFinal(lista);

        assertEquals(51, preco);
    }

    @Test
    void todosValores() {

        ItemVenda p1 = new ItemVenda(1, 10, 4, 2);
        ItemVenda p2 = new ItemVenda(1, 10, 5, 3);
        ItemVenda p3 = new ItemVenda(1, 10, 6, 4);

        lista.add(p1);
        lista.add(p2);
        lista.add(p3);

        when(regraImposto.calcular(lista)).thenReturn(4.70D);
        Integer[] total = servicoDeVendas.todosValores(lista);

        assertArrayEquals(new Integer[]{47, 4, 51}, total);
    }
}
