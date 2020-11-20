package com.quarteto.o.servico;

import com.quarteto.o.entidade.ItemVenda;
import com.quarteto.o.entidade.Produto;
import com.quarteto.o.excecoes.SistVendasException;
import com.quarteto.o.repositorio.Estoque;
import com.quarteto.o.repositorio.Produtos;
import com.quarteto.o.servico.imposto.RegraImposto;
import com.quarteto.o.servico.validacao.FactoryValidacao;
import com.quarteto.o.servico.validacao.RegraValidacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicoDeVendasTest {
    private ServicoDeVendas servicoDeVendas;
    private RegraImposto regraImposto;
    private Produtos produtos;
    private Estoque estoque;
    private FactoryValidacao factoryValidacao;
    private List<ItemVenda> lista;

    @BeforeEach
    void setUp() {
        regraImposto = mock(RegraImposto.class);
        produtos = mock(Produtos.class);
        estoque = mock(Estoque.class);
        factoryValidacao = mock(FactoryValidacao.class);
        lista = new ArrayList<>();
        servicoDeVendas = new ServicoDeVendas(produtos, estoque, regraImposto, factoryValidacao);
    }

    @Test
    @DisplayName("Valida deve lançar excecao")
    void valida() {

        RegraValidacao regraValidacao = mock(RegraValidacao.class);

        when(factoryValidacao.getRegraValidacao()).thenReturn(regraValidacao);

        doThrow(SistVendasException.class).when(regraValidacao).valida(produtos, estoque, lista);

        assertThrows(SistVendasException.class, () -> servicoDeVendas.valida(lista));
    }

    @Test
    @DisplayName("Valida nao deve lançar excecao")
    void validaNaoLancaExcecao() {
        RegraValidacao regraValidacao = mock(RegraValidacao.class);

        when(factoryValidacao.getRegraValidacao()).thenReturn(regraValidacao);

        doNothing().when(regraValidacao).valida(produtos, estoque, lista);

        assertDoesNotThrow(() -> servicoDeVendas.valida(lista));

        verify(regraValidacao, times(1)).valida(produtos, estoque, lista);
    }

    @Test
    @DisplayName("Calcula subtotal e retorna o somatório")
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
    @DisplayName("Calcula subtotal e devolve zero")
    void calculaSubtotalDeveRetornarZero() {

        int resultado = servicoDeVendas.calculaSubtotal(lista);
        assertEquals(0, resultado);
    }

    @Test
    @DisplayName("Calcula imposto devolve imposto")
    void calculaImpostosDevolveImposto() {
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
    @DisplayName("Calcula imposto e devolve zero")
    void calculaImpostosDevolveZero() {

        when(regraImposto.calcular(lista)).thenReturn(0D);
        Integer valorImposto = servicoDeVendas.calculaImpostos(lista);

        assertEquals(0, valorImposto);

    }

    @Test
    @DisplayName("Calcula preço final devolve preço final com imposto")
    void calculaPrecoFinalComImposto() {
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
    @DisplayName("Calcula preço final e devolve zero")
    void calculaPrecoFinalDevolveZero() {

        when(regraImposto.calcular(lista)).thenReturn(0D);
        Integer preco = servicoDeVendas.calculaPrecoFinal(lista);

        assertEquals(0, preco);
    }

    @Test
    @DisplayName("Todos o valores devolve lista com todos os valores calculados")
    void todosValoresDeveDevolverValoresCalculados() {

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

    @Test
    @DisplayName("Todos valores devolve lista com zero")
    void todosValoresDevolveZero() {

        when(regraImposto.calcular(lista)).thenReturn(0D);
        Integer[] total = servicoDeVendas.todosValores(lista);

        assertArrayEquals(new Integer[]{0, 0, 0}, total);
    }
}