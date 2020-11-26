package com.quarteto.o.servico;

import com.quarteto.o.entidade.ItemEstoque;
import com.quarteto.o.entidade.ItemVenda;
import com.quarteto.o.entidade.Produto;
import com.quarteto.o.excecoes.SistVendasException;
import com.quarteto.o.repositorio.Estoque;
import com.quarteto.o.repositorio.Produtos;
import com.quarteto.o.servico.imposto.RegraImposto;
import com.quarteto.o.servico.imposto.RegraImpostoOriginal;
import com.quarteto.o.servico.validacao.FactoryValidacao;
import com.quarteto.o.servico.validacao.RegraValidacao;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class ServicoDeVendasIT {
    private FactoryValidacao factoryValidacao;
    private ServicoDeVendas servicoDeVendas;
    private RegraImposto regraImposto;
    private Produtos produtos;
    private Estoque estoque;

    @BeforeEach
    void setUp() {
        produtos = mock(Produtos.class);
        estoque = mock(Estoque.class);
    }

    private List<ItemVenda> criaLista() {
        List<ItemVenda> lista = new ArrayList<>();

        ItemVenda p1 = new ItemVenda(1, 10, 1, 2);
        ItemVenda p2 = new ItemVenda(1, 30, 1, 3);
        ItemVenda p3 = new ItemVenda(1, 50, 2, 4);

        lista.add(p1);
        lista.add(p2);
        lista.add(p3);

        return lista;
    }

    @Test
    @DisplayName("Valida horario comercial, nao deve lançar excecao")
    void validaHorarioComercialNaoDeveLancarExcecao() {
        LocalTime agora = LocalTime.of(9, 0);
        factoryValidacao = new FactoryValidacao(agora);

        regraImposto = new RegraImpostoOriginal();
        servicoDeVendas = new ServicoDeVendas(produtos, estoque, regraImposto, factoryValidacao);

        when(produtos.recupera(10)).thenReturn(new Produto(10, "Prod10", 1000.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30, "Prod30", 2000.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50, "Prod15", 1500.0));

        when(estoque.recupera(10)).thenReturn(new ItemEstoque(10, 5));
        when(estoque.recupera(30)).thenReturn(new ItemEstoque(30, 3));
        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50, 15));

        List<ItemVenda> lista = criaLista();

        assertDoesNotThrow(() -> servicoDeVendas.valida(lista));
    }

    @Test
    @DisplayName("Valida horario comercial, deve lançar excecao - produto nao encontrado")
    void validaHorarioComercialDeveLancarExcecaoProdutoNaoEncontrado() {
        LocalTime agora = LocalTime.of(9, 0);
        factoryValidacao = new FactoryValidacao(agora);

        regraImposto = new RegraImpostoOriginal();
        servicoDeVendas = new ServicoDeVendas(produtos, estoque, regraImposto, factoryValidacao);

        when(produtos.recupera(10)).thenReturn(new Produto(10, "Prod10", 1000.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30, "Prod30", 2000.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50, "Prod15", 1500.0));

        when(estoque.recupera(10)).thenReturn(null);

        List<ItemVenda> lista = criaLista();

        assertThrows(SistVendasException.class, () -> servicoDeVendas.valida(lista));
    }

    @Test
    @DisplayName("Valida horario comercial, deve lançar excecao - quantidade insuficiente")
    void validaHorarioComercialDeveLancarExcecaoQuantInsuficiente() {
        LocalTime agora = LocalTime.of(9, 0);
        factoryValidacao = new FactoryValidacao(agora);

        regraImposto = new RegraImpostoOriginal();
        servicoDeVendas = new ServicoDeVendas(produtos, estoque, regraImposto, factoryValidacao);

        when(produtos.recupera(10)).thenReturn(new Produto(10, "Prod10", 1000.0));
        when(produtos.recupera(30)).thenReturn(new Produto(30, "Prod30", 2000.0));
        when(produtos.recupera(50)).thenReturn(new Produto(50, "Prod15", 1500.0));

        when(estoque.recupera(50)).thenReturn(new ItemEstoque(50, 1));

        List<ItemVenda> lista = criaLista();

        assertThrows(SistVendasException.class, () -> servicoDeVendas.valida(lista));
    }
}